package com.springBootD.framework.utils.support;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;


/**
 * MD5加密工具类
 */
public class MD5 {

    private static final Logger logger = Logger.getLogger("MD5");
    public static final String ALGORITHM = "MD5";

    /**
     * <p>
     * MD5 签名算法
     * </p>
     *
     * @param plainText 需要加密的字符串
     * @return
     * @Description 字符串加密为MD5 中文加密一致通用,必须转码处理： plainText.getBytes("UTF-8")
     */
    public static String toMD5(String plainText) {
        StringBuffer rlt = new StringBuffer();
        try {
            rlt.append(md5String(plainText.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            logger.severe(" CipherHelper toMD5 exception.");
            e.printStackTrace();
        }
        return rlt.toString();
    }

    /**
     * <p>
     * MD5 参数签名生成算法
     * </p>
     *
     * @param params 请求参数集，所有参数必须已转换为字符串类型
     * @param secret 签名密钥
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(HashMap<String, String> params, String secret) {
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        return getSignature(basestring.toString(), secret);
    }

    /**
     * <p>
     * MD5 参数签名生成算法
     * </p>
     *
     * @param sigstr 签名字符串
     * @param secret 签名密钥
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(String sigstr, String secret) {
        StringBuilder text = new StringBuilder(sigstr);
        text.append("#");
        text.append(toMD5(secret));
        return toMD5(text.toString());
    }

    public static byte[] md5Raw(byte[] data) {
        byte[] md5buf = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
            md5buf = md5.digest(data);
        } catch (Exception e) {
            md5buf = null;
            logger.severe("md5Raw error.");
            e.printStackTrace();
        }
        return md5buf;
    }

    public static String md5String(byte[] data) {
        String md5Str = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
            byte[] buf = md5.digest(data);
            for (int i = 0; i < buf.length; i++) {
                md5Str += Byte2Hex.byte2Hex(buf[i]);
            }
        } catch (Exception e) {
            md5Str = null;
            logger.severe("md5String error.");
            e.printStackTrace();
        }
        return md5Str;
    }

}