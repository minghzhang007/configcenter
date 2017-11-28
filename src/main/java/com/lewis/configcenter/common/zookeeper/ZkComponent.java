package com.lewis.configcenter.common.zookeeper;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangminghua
 */
@Component
public class ZkComponent {


    private final String CONNECT_STRING = "localhost:2181";

    private final int SESSTION_TIMEOUT = 50000;

    private CuratorFramework curator;

    @Resource
    private DResourceHolder dResourceHolder;

    private ZkComponent() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        curator = CuratorFrameworkFactory.builder().connectString(CONNECT_STRING).sessionTimeoutMs(SESSTION_TIMEOUT)
                .retryPolicy(retryPolicy).build();
        curator.start();
    }


    public void main(String[] args) throws InterruptedException {

        String path = "/configcenter/snailreader/test1";
        addChildrenListener(path);
        //createPersistentData(path + "/test1", "test1内容");

        //setData(path + "/test1", "test1新内容");

        Thread.sleep(2000);
        deleteDataInCludeChildren(path);
        Thread.sleep(50000);
    }

    public boolean createPersistentData(String path, String value) {
        boolean result = true;
        try {
            curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, getBytes(value));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean createEphemeralData(String path, String value) {
        boolean result = true;
        try {
            curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, getBytes(value));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public String getData(String path) {
        try {
            byte[] bytes = curator.getData().forPath(path);
            return new String(bytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteDataInCludeChildren(String path) {
        boolean result = true;
        try {
            curator.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteDataExCludeChildren(String path) {
        boolean result = true;
        try {
            curator.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getChildren(String path) {
        try {
            return curator.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    public boolean setData(String path, String value) {
        boolean result = true;
        try {
            curator.setData().forPath(path, getBytes(value));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public void addNodeListener(String path) {
        NodeCache nodeCache = new NodeCache(curator, path);
        try {
            nodeCache.start(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nodeCache.getListenable().addListener(() -> {
            ChildData currentData = nodeCache.getCurrentData();
            if (currentData != null) {
                List<String> strings = Splitter.on("/").omitEmptyStrings().splitToList(currentData.getPath());
                String configcenter = strings.get(0);
                String appName = strings.get(1);
                String env = strings.get(2);
                String dictKey = strings.get(3);
                Object obj = dResourceHolder.getDictKeyObjectMapping().get(dictKey);
                Class clazz = dResourceHolder.getDictKeyClassMapping().get(dictKey);

                Field[] declaredFields = clazz.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(declaredFields)) {
                    Arrays.asList(declaredFields).stream().filter(item -> item.getAnnotation(DResource.class) != null).forEach(field -> {
                        DResource dResource = field.getAnnotation(DResource.class);
                        if (Objects.equals(dictKey, dResource.dictKey())) {
                            try {
                                String newValue = new String(currentData.getData(), "utf-8");
                                if (Modifier.isPrivate(field.getModifiers())) {
                                    field.setAccessible(true);
                                }
                                field.set(obj, newValue);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                System.out.println("路径分隔：" + strings);
                System.out.println("数据：" + new String(currentData.getData(), "utf-8"));
                System.out.println("路径：" + currentData.getPath());
                System.out.println("状态：" + currentData.getStat());
            }
        });
    }

    public void addChildrenListener(String path) {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curator, path, false);
        try {
            pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            pathChildrenCache.getListenable().addListener((client, event) -> {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("类型：" + event.getType() + ",路径：" + event.getData().getPath() + ",数据：" + new String(event.getData().getData(), "utf-8") +
                                "状态：" + event.getData().getStat());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("类型：" + event.getType() + ",路径：" + event.getData().getPath() + ",数据：" + new String(event.getData().getData(), "utf-8") +
                                "状态：" + event.getData().getStat());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("类型：" + event.getType() + ",路径：" + event.getData().getPath() + ",数据：" + new String(event.getData().getData(), "utf-8") +
                                "状态：" + event.getData().getStat());
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(String value) {
        try {
            return value.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
