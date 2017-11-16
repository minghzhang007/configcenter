package com.lewis.configcenter.common.component;


import com.lewis.configcenter.common.config.CacheConfig;
import com.lewis.configcenter.common.util.ProtostuffUtils;
import com.netease.backend.nkv.client.NkvClient;
import com.netease.backend.nkv.client.Result;
import com.netease.backend.nkv.client.error.NkvException;
import com.netease.backend.nkv.client.impl.DefaultNkvClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * NKV缓存
 *
 * @author hzlimaozhi
 */
@Component
public class NkvComponent {

    @Resource
    private CacheConfig cacheConfig;

    private static final Logger logger = LoggerFactory.getLogger(NkvComponent.class);

    private static final short VERSION_FOR_ADD = 999;

    private static final long DEFAULT_READ_TIMEOUT = 500; // 500ms

    private static final long DEFAULT_WRITE_TIMEOUT = 1000; // 1s

    private DefaultNkvClient client;

    @PostConstruct
    public void init() throws Exception {
        client = new DefaultNkvClient();
        client.setCompressEnabled(true);
        client.setCompressThreshold(1024 * 10); // 10k
        client.setMaster(cacheConfig.getMaster());
        client.setSlave(cacheConfig.getSlave());
        client.setGroup(cacheConfig.getGroup());

        try {
            client.init();
        } catch (NkvException e) {
            logger.error("nkv init error", e);
        }
    }

    public void shutdown() {
        DefaultNkvClient.shutdown();
    }

    /**
     * 添加，若有则覆盖
     */
    public void put(String key, Object obj, int expireSeconds) {
        if (obj == null) {
            throw new IllegalArgumentException("obj is null");
        }
        try {
            client.put(cacheConfig.getNamespace(), ProtostuffUtils.serialize(key), ProtostuffUtils.serialize(obj), option(DEFAULT_WRITE_TIMEOUT, expireSeconds));
        } catch (Exception e) {
            logger.error("nkv put error. namespace: {}, key: {}, object class: {}.", cacheConfig.getNamespace(), key, obj.getClass(), e);
        }
    }

    public boolean lock(String key, int expireSeconds) {
        boolean result = add(key, "1", expireSeconds);
        logger.info("nkv lock {}. namespace: {}, key: {}", (result ? "succ" : "fail"), cacheConfig.getNamespace(), key);
        return result;
    }

    /**
     * 添加，已存在则返回false
     */
    public boolean add(String key, Object obj, int expireSeconds) {
        if (obj == null) {
            throw new IllegalArgumentException("obj is null");
        }

        try {
            Result<Void> result = client.put(cacheConfig.getNamespace(), ProtostuffUtils.serialize(key), ProtostuffUtils.serialize(obj), option(DEFAULT_WRITE_TIMEOUT, expireSeconds, VERSION_FOR_ADD));
            return result != null && result.getCode() == Result.ResultCode.OK;
        } catch (Exception e) {
            logger.error("nkv add error. namespace: {}, key: {}, object class: {}.", cacheConfig.getNamespace(), key, obj.getClass(), e);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <E> E get(String key, Class<E> clazz) {
        try {
            Result<byte[]> value = client.get(cacheConfig.getNamespace(), ProtostuffUtils.serialize(key), option(DEFAULT_READ_TIMEOUT));
            byte[] bytes = value.getResult();
            if (value.getCode() == Result.ResultCode.OK && bytes != null) {
                E e = ProtostuffUtils.deSerialzier(bytes, clazz);
                return e;
            }
        } catch (Exception e) {
            logger.error("nkv get error. namespace: {}, key: {}", cacheConfig.getNamespace(), key, e);
        }
        return null;
    }

    public <E> List<E> getList(String key, Class<E> clazz) {
        try {
            Result<byte[]> value = client.get(cacheConfig.getNamespace(), ProtostuffUtils.serialize(key), option(DEFAULT_READ_TIMEOUT));
            byte[] bytes = value.getResult();
            if (value.getCode() == Result.ResultCode.OK && bytes != null) {
                List<E> list = ProtostuffUtils.deSerializeList(bytes, clazz);
                return list;
            }
        } catch (Exception e) {
            logger.error("nkv get error. namespace: {}, key: {}", cacheConfig.getNamespace(), key, e);
        }
        return null;
    }


    public Object get(String key) {
        return get(key, Object.class);
    }

    public boolean remove(String key) {
        try {
            Result<Void> result = client.invalidByProxy(cacheConfig.getNamespace(), ProtostuffUtils.serialize(key), option(DEFAULT_WRITE_TIMEOUT));
            return result.getCode() == Result.ResultCode.OK;
        } catch (Exception e) {
            logger.error("nkv remove error. namespace: {}, key: {}", cacheConfig.getNamespace(), key, e);
        }
        return false;
    }

    /**
     * 修改过期时间，从此时起的expireSeconds
     */
    public void touch(String key, int expireSeconds) {
        Object value = get(key);
        if (value == null) {
            return;
        }
        put(key, value, expireSeconds);
    }

    private static NkvClient.NkvOption option(long timeoutMillis) {
        NkvClient.NkvOption option = new NkvClient.NkvOption(timeoutMillis);
        return option;
    }

    private static NkvClient.NkvOption option(long timeoutMillis, int expireSeconds) {
        NkvClient.NkvOption option = new NkvClient.NkvOption(timeoutMillis);
        option.setExpireTime(expireSeconds);
        return option;
    }

    private static NkvClient.NkvOption option(long timeoutMillis, int expireSeconds, short version) {
        if (version <= 1) { // 0和1由NKV内部使用，另外使用负数作为version应该没有意义
            throw new IllegalArgumentException("" + version);
        }

        NkvClient.NkvOption option = new NkvClient.NkvOption(timeoutMillis);
        option.setExpireTime(expireSeconds);
        option.setVersion(version);
        return option;
    }

}
