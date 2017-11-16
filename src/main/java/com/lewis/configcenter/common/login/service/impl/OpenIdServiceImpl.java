package com.lewis.configcenter.common.login.service.impl;


import com.lewis.configcenter.common.component.NkvComponent;
import com.lewis.configcenter.common.config.ProjectConfig;
import com.lewis.configcenter.common.login.LoginCacheEnum;
import com.lewis.configcenter.common.login.OpenIdAssociate;
import com.lewis.configcenter.common.login.service.OpenIdService;
import com.lewis.configcenter.common.util.StringUtil;
import com.lewis.configcenter.common.util.http.HttpUtils;
import com.lewis.configcenter.common.util.uri.UriBuilder;
import com.lewis.configcenter.common.util.uri.UriParams;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenIdServiceImpl implements OpenIdService {

    @Resource
    private ProjectConfig projectConfig;

    private final String OPENID_SERVER = "https://login.netease.com/openid/";

    //todo 这里注意ip:port
    private String OPENID_CALLBACK;

    private final String OPENID_ASSOCIATE_CACHE_KEY = LoginCacheEnum.OPENID_ASSOCIATE.make("associate");

    @Resource
    private NkvComponent nkvComponent;

    @PostConstruct
    private void init() {
        OPENID_CALLBACK = projectConfig.getUrl() + "/login/openid/callback";
    }


    @Override
    public String getRedirectUrl(String redirect) throws Exception {
        OpenIdAssociate associate = nkvComponent.get(OPENID_ASSOCIATE_CACHE_KEY, OpenIdAssociate.class);
        if (associate == null) {
            associate = associate();
            nkvComponent.put(OPENID_ASSOCIATE_CACHE_KEY, associate, associate.getExpires_in());
        }

        UriParams uriParams = new UriParams();
        uriParams.add("openid.ns", "http://specs.openid.net/auth/2.0");
        uriParams.add("openid.mode", "checkid_setup");
        uriParams.add("openid.assoc_handle", associate.getAssoc_handle());
        uriParams.add("openid.return_to", new UriBuilder(OPENID_CALLBACK).add("redirect", redirect).toUri());
        uriParams.add("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");
        uriParams.add("openid.identity", "http://specs.openid.net/auth/2.0/identifier_select");
        //todo
        uriParams.add("openid.realm", projectConfig.getUrl());
        uriParams.add("openid.ns.sreg", "http://openid.net/extensions/sreg/1.1");
        uriParams.add("openid.sreg.required", "email,fullname,nickname");
        return new UriBuilder(OPENID_SERVER).add(uriParams).toUri();
    }


    @Override
    public boolean validateCallbackUrl(Map<String, String> callbackParams) {
        try {
            OpenIdAssociate associate = nkvComponent.get(OPENID_ASSOCIATE_CACHE_KEY, OpenIdAssociate.class);
            if (associate == null) {
                return false;
            }

            String openidMode = callbackParams.get("openid.mode");
            if (!StringUtils.equals(openidMode, "id_res")) {
                return false;
            }

            String openidIdentity = callbackParams.get("openid.identity");
            if (!openidIdentity.startsWith(OPENID_SERVER)) {
                return false;
            }

            String openidClaimedId = callbackParams.get("openid.claimed_id");
            if (!openidClaimedId.startsWith(OPENID_SERVER)) {
                return false;
            }

            String openidAssocHandle = callbackParams.get("openid.assoc_handle");
            if (StringUtils.equals(openidAssocHandle, associate.getAssoc_handle())) {

                String openidSig = callbackParams.get("openid.sig");
                String sig = null;
                {
                    String signedKey = callbackParams.get("openid.signed");
                    String[] signedKeyArr = signedKey.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (String key : signedKeyArr) {
                        builder.append(key).append(":").append(callbackParams.get("openid." + key)).append("\n");
                    }
                    sig = signString(Base64.decodeBase64(associate.getMac_key()), builder.toString());
                }
                if (!StringUtils.equals(openidSig, sig)) {
                    return false;
                }

            } else {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : callbackParams.entrySet()) {
                    String key = entry.getKey();
                    if (key.contains("openid")) {
                        parameters.add(new BasicNameValuePair(key, entry.getValue()));
                    }
                }
                parameters.add(new BasicNameValuePair("openid.mode", "check_authentication"));
                String response = HttpUtils.post(OPENID_SERVER, parameters).getEntity("UTF-8");
                if (!StringUtils.equals(parse(response).get("is_valid"), "true")) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            //
        }
        return false;
    }

    private static String signString(byte[] secretKey, String string)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(secretKey, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] enc = mac.doFinal(string.getBytes());
        return Base64.encodeBase64String(enc);
    }

    /**
     * 第一步：向OpenId Server发起关联请求
     */
    private OpenIdAssociate associate() throws Exception {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("openid.mode", "associate"));
        parameters.add(new BasicNameValuePair("openid.assoc_type", "HMAC-SHA256"));
        parameters.add(new BasicNameValuePair("openid.session_type", "no-encryption"));
        String response = HttpUtils.post(OPENID_SERVER, parameters).getEntity("UTF-8");
        OpenIdAssociate associate = from(response);
        if (associate != null) {
            return associate;
        }
        throw new IllegalStateException("Failed to associate OpenId Server.");
    }

    private static OpenIdAssociate from(String text) {
        OpenIdAssociate associate = null;
        try {
            Map<String, String> result = parse(text);

            String assoc_handle = result.get("assoc_handle");
            String assoc_type = result.get("assoc_type");
            int expires_in = Integer.parseInt(result.get("expires_in"));
            String mac_key = result.get("mac_key");

            if (StringUtils.isNotBlank(assoc_handle) && StringUtils.isNotBlank(assoc_type) && StringUtils.isNotBlank(mac_key)) {
                associate = new OpenIdAssociate();
                associate.setAssoc_handle(assoc_handle);
                associate.setAssoc_type(assoc_type);
                associate.setExpires_in(expires_in);
                associate.setMac_key(mac_key);
            }
        } catch (Exception e) {
            //
        }
        return associate;
    }

    /**
     * 从返回结果中解析出 map：每行为key:value格式
     */
    private static Map<String, String> parse(String text) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            String[] lines = text.split("\n");
            for (String line : lines) {
                String[] pair = StringUtil.removeBlank(line).split(":");
                if (pair.length == 2) {
                    result.put(pair[0], pair[1]);
                }
            }
        } catch (Exception e) {
            //
        }
        return result;
    }
}
