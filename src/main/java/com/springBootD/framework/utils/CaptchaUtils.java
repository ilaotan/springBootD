package com.springBootD.framework.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2017/10/14 9:42
 */
@Component
public class CaptchaUtils implements InitializingBean {

    @Resource
    private Environment env;

    private static DefaultKaptcha defaultKaptcha;

    private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    private static final String[] CN_LOWER_NUMBER = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };


    public static DefaultKaptcha getDefaultKaptcha(){
        return defaultKaptcha;
    }

    /**
     * 核心方法在这.
     *
     * @param text
     * @return
     */
    public static byte[] createImage(String text,String formatName){

        BufferedImage bi = defaultKaptcha.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, formatName, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] createImage(String text){
        return createImage(text,"png");
    }

    public static String createImageString(String text){
        return new String(Base64.encodeBase64(createImage(text,"png")));
    }

    public static String createImageString(String text, String formatName){
        return new String(Base64.encodeBase64(createImage(text,formatName)));
    }


    /**
     * 我自测的例子 你可以不用 自己写一套
     *
     * @param type  type字段用来生成字符串范围
     *               type的建议值为2 4 6 8 具体效果请看代码
     *                type=2  rule 0-1
     *                type=4  rule 0-3
     *                type=6  rule 0-5
     *                type=8  rule 0-7
     * @return
     */
    public static String[] getSimpleText(int type){
        Random random = new Random();
        int rule = random.nextInt(type);
        // 第一个数据
        int rand1 = random.nextInt(10);
        if (rand1 == 0) {
            rand1 = 1;
        }
        int rand2 = random.nextInt(10);

        String result= null;
        int resultInt = 0;
        switch (rule){
            case 0:
                result = rand1 + " 加 "+ rand2 + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 1:
                result = rand1 + " 减 "+ rand2 + " = ?";
                resultInt = rand1 - rand2;
                break;
            case 2:
                result = CN_LOWER_NUMBER[rand1] + " 加 "+ rand2 + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 3:
                result = CN_LOWER_NUMBER[rand1] + " 减 "+ rand2 + " = ?";
                resultInt = rand1 - rand2;
                break;
            case 4:
                result = rand1 + " 加 "+ CN_LOWER_NUMBER[rand2] + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 5:
                result = rand1 + " 减 "+ CN_LOWER_NUMBER[rand2] + " = ?";
                resultInt = rand1 - rand2;
                break;
            case 6:
                result = CN_LOWER_NUMBER[rand1] + " 加 "+ CN_LOWER_NUMBER[rand2] + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 7:
                result = CN_LOWER_NUMBER[rand1] + " 减 "+ CN_LOWER_NUMBER[rand2] + " = ?";
                resultInt = rand1 - rand2;
                break;
            default:
                break;
        }

        return new String[]{result,resultInt+""};
    }
    /**
     * 我自测的例子 你可以不用 自己写一套
     * 这个跟上面的区别是中文改为人民币上的中文标识
     *
     * @param type  type字段用来生成字符串范围
     *               type的建议值为2 4 6 8 具体效果请看代码
     *                type=2  rule 0-1
     *                type=4  rule 0-3
     *                type=6  rule 0-5
     *                type=8  rule 0-7
     * @return
     */
    public static String[] getSimpleText2(int type){
        Random random = new Random();
        int rule = random.nextInt(type);
        // 第一个数据
        int rand1 = random.nextInt(10);
        if (rand1 == 0) {
            rand1 = 1;
        }
        int rand2 = random.nextInt(10);

        String result= null;
        int resultInt = 0;
        switch (rule){
            case 0:
                result = rand1 + " 加 "+ rand2 + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 1:
                result = rand1 + " 减 "+ rand2 + " = ?";
                resultInt = rand1 - rand2;
                break;
            case 2:
                result = CN_UPPER_NUMBER[rand1] + " 加 "+ rand2 + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 3:
                result = CN_UPPER_NUMBER[rand1] + " 减 "+ rand2 + " = ?";
                resultInt = rand1 - rand2;
                break;
            case 4:
                result = rand1 + " 加 "+ CN_UPPER_NUMBER[rand2] + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 5:
                result = rand1 + " 减 "+ CN_UPPER_NUMBER[rand2] + " = ?";
                resultInt = rand1 - rand2;
                break;
            case 6:
                result = CN_UPPER_NUMBER[rand1] + " 加 "+ CN_UPPER_NUMBER[rand2] + " = ?";
                resultInt = rand1 + rand2;
                break;
            case 7:
                result = CN_UPPER_NUMBER[rand1] + " 减 "+ CN_UPPER_NUMBER[rand2] + " = ?";
                resultInt = rand1 - rand2;
                break;
            default:
                break;
        }

        return new String[]{result,resultInt+""};
    }



    @Override
    public void afterPropertiesSet() throws Exception {

        String kaptchaBorder = ObjectUtils.toString(env.getProperty("kaptchaBorder"),"no");
        String kaptchaBorderColor = ObjectUtils.toString(env.getProperty("kaptchaBorderColor"),"105,179,90");
        String kaptchaTextproducerFontColor = ObjectUtils.toString(env.getProperty("kaptchaTextproducerFontColor"),"blue");
        String kaptchaTextproducerFontSize = ObjectUtils.toString(env.getProperty("kaptchaTextproducerFontSize"),"43");
        String kaptchaImageWidth = ObjectUtils.toString(env.getProperty("kaptchaImageWidth"),"150");
        String kaptchaImageHeight = ObjectUtils.toString(env.getProperty("kaptchaImageHeight"),"50");
        String kaptchaTextproducerCharLength = ObjectUtils.toString(env.getProperty("kaptchaTextproducerCharLength"),"7");
        String kaptchaTextproducerCharString = ObjectUtils.toString(env.getProperty("kaptchaTextproducerCharString"),"1234567890");
        String kaptchaTextproducerFontNames = ObjectUtils.toString(env.getProperty("kaptchaTextproducerFontNames"),"宋体,楷体");


        Properties properties = new Properties();
        //是否有边框 可选yes 或者 no
        properties.put("kaptcha.border", kaptchaBorder);
        //边框颜色
        properties.put("kaptcha.border.color", kaptchaBorderColor);
        //验证码文本字符颜色
        properties.put("kaptcha.textproducer.font.color", kaptchaTextproducerFontColor);
        //验证码文本字符大小
        properties.put("kaptcha.textproducer.font.size", kaptchaTextproducerFontSize);
        //验证码图片的宽度 默认200
        properties.put("kaptcha.image.width", kaptchaImageWidth);
        //验证码图片的高度 默认50
        properties.put("kaptcha.image.height", kaptchaImageHeight);
        //properties.put("kaptcha.session.key", "code");
        //验证码文本字符长度  默认为5
        properties.put("kaptcha.textproducer.char.length", kaptchaTextproducerCharLength);
        //文本集合，验证码值从此集合中获取
        properties.put("kaptcha.textproducer.char.string",kaptchaTextproducerCharString);

        //验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.put("kaptcha.textproducer.font.names", kaptchaTextproducerFontNames);
        Config config = new Config(properties);
        defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
    }
}
