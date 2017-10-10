package com.springBootD.framework.utils.support;

import java.security.MessageDigest;
import java.util.logging.Logger;


/**
 * 盐值加密工具类（常用登录密码加密）
 */
public class MD5Salt {

    private static final Logger logger = Logger.getLogger("PasswordEncoder");

    /**
     * 盐值
     */
    private String salt;

    /**
     * 算法
     */
    private String algorithm;


    protected MD5Salt() {
        /* 保护 */
    }


    public MD5Salt(String salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    /**
     * <p>
     * md5 盐值加密字符串
     * </p>
     *
     * @param salt    盐值
     * @param rawText 需要加密的字符串
     * @return
     */
    public static String md5SaltEncode(String salt, String rawText) {
        return new MD5Salt(salt, MD5.ALGORITHM).encode(rawText);
    }

    /**
     * <p>
     * 判断md5 盐值加密内容是否正确
     * </p>
     *
     * @param salt       盐值
     * @param encodeText 加密后的文本内容
     * @param rawText    加密前的文本内容
     * @return
     */
    public static boolean md5SaltValid(String salt, String encodeText, String rawText) {
        return new MD5Salt(salt, MD5.ALGORITHM).isValid(encodeText, rawText);
    }

    /**
     * <p>
     * 字符串盐值加密
     * </p>
     *
     * @param rawText 需要加密的字符串
     * @return
     */
    public String encode(String rawText) {
        try {
            MessageDigest md = MessageDigest.getInstance(this.getAlgorithm());
            //加密后的字符串
            return Byte2Hex.byte2Hex(md.digest(mergeRawTextAndSalt(rawText).getBytes("UTF-8")));
        }
        catch (Exception e) {
            logger.severe(" MD5Salt encode exception.");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * <p>
     * 判断加密内容是否正确
     * </p>
     *
     * @param encodeText 加密后的文本内容
     * @param rawText    加密前的文本内容
     * @return
     */
    public boolean isValid(String encodeText, String rawText) {
        return this.encode(rawText).equals(encodeText);
    }

    /**
     * <p>
     * 合并混淆盐值至加密内容
     * </p>
     *
     * @param rawText 需要加密的字符串
     * @return
     */
    private String mergeRawTextAndSalt(String rawText) {
        if (rawText == null) {
            rawText = "";
        }

        if (this.getSalt() == null || "".equals(this.getSalt())) {
            return rawText;
        }
        else {
            StringBuffer mt = new StringBuffer();
            mt.append(rawText);
            mt.append("#");
            mt.append(this.getSalt());
            return mt.toString();
        }
    }


    public String getSalt() {
        return salt;
    }


    public void setSalt(String salt) {
        this.salt = salt;
    }


    public String getAlgorithm() {
        return algorithm;
    }


    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

}