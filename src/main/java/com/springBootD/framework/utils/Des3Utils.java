package com.springBootD.framework.utils;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Des3Utils {

    /**
     * 消息平台专用加密解密
     * <p>
     * 一个项目.net 要调用JAVA的WEB SERVICE，数据采用3DES加密，涉及到两种语言3DES一致性的问题，
     * 这里的KEY采用Base64编码，便用分发，因为Java的Byte范围为-128至127，c#的Byte范围是0-255
     * 核心是确定Mode和Padding，关于这两个的意思可以搜索3DES算法相关文章
     * 一个是 C#采用 CBC Mode，PKCS7 Padding,Java采用CBC Mode，PKCS5Padding Padding,
     * 另一个是C#采用ECB Mode，PKCS7 Padding,Java采用ECB Mode，PKCS5Padding Padding,
     * 注意：Java的ECB模式不需要IV
     * 对字符加密时，双方采用的都是UTF-8编码
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer("YWJjZGVmZ2h;amtsbW5vc,HFyc3R1dnd4");
        byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] data = "中国ABCabc123".getBytes("UTF-8");


        System.out.println("ECB加密解密");
        byte[] str3 = des3EncodeECB(key, data);
        byte[] str4 = ees3DecodeECB(key, str3);
        System.out.println(DatatypeConverter.printHexBinary(str3));
        System.out.println(new BASE64Encoder().encode(str3));
        System.out.println(new String(str4, "UTF-8"));
        System.out.println();


        System.out.println("CBC加密解密");
        byte[] str5 = des3EncodeCBC(key, keyiv, data);
        byte[] str6 = des3DecodeCBC(key, keyiv, str5);
        System.out.println(DatatypeConverter.printHexBinary(str5));
        System.out.println(new BASE64Encoder().encode(str5));
        System.out.println(new String(str6, "UTF-8"));

    }

    /**
     * ECB加密,不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     */
    public static byte[] des3EncodeECB(byte[] key, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        return cipher.doFinal(data);
    }

    /**
     * ECB加密,不要IV  key使用 BASE64Decoder().decodeBuffer转byte[]
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     */
    public static byte[] des3EncodeECB(String key, byte[] data) throws Exception {

        return des3EncodeECB(new BASE64Decoder().decodeBuffer(key), data);
    }

    /**
     * ECB解密,不要IV
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     */
    public static byte[] ees3DecodeECB(byte[] key, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        return cipher.doFinal(data);
    }

    /**
     * ECB解密,不要IV   key使用 BASE64Decoder().decodeBuffer转byte[]
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     */
    public static byte[] ees3DecodeECB(String key, byte[] data) throws Exception {

        return ees3DecodeECB(new BASE64Decoder().decodeBuffer(key), data);
    }

    /**
     * CBC加密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  明文
     * @return Base64编码的密文
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        return cipher.doFinal(data);
    }

    /**
     * CBC解密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  Base64编码的密文
     * @return 明文
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        return cipher.doFinal(data);
    }
}