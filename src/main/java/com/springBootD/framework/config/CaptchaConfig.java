package com.springBootD.framework.config;

import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;


/**
 * Created by tan on 2016/2/18.
 */

@Configuration
public class CaptchaConfig {

    @Bean
    public GenericManageableCaptchaService captchaService(){
        return new GenericManageableCaptchaService(imageEngine(),180,100000,75000);
    }

    @Bean
    public GenericCaptchaEngine imageEngine(){
        GimpyFactory[] gimpyFactoryList = new GimpyFactory[1];
        gimpyFactoryList[0] = captchaFactory();
        return new GenericCaptchaEngine(gimpyFactoryList);
    }

    @Bean
    public GimpyFactory captchaFactory(){
        return new GimpyFactory(wordgen(),wordtoimage());
    }

    /**
     可选字符
     */
    @Bean
    public RandomWordGenerator wordgen(){
        return new RandomWordGenerator("aabbccddeefgghhkkmnnooppqqsstuuvvwxxyyzz");
    }

    @Bean
    public ComposedWordToImage wordtoimage(){
        Font[] fontsList = new Font[1];
        fontsList[0]= new Font("Arial",0,32);
        return new ComposedWordToImage(new RandomFontGenerator(26,34,fontsList),new UniColorBackgroundGenerator(110,50),decoratedPaster());
    }

    @Bean
    public DecoratedRandomTextPaster decoratedPaster(){
        BaffleTextDecorator[] textDecoratorList = new BaffleTextDecorator[1];
        //textDecoratorList[0] = new BaffleTextDecorator(1,new Color(255,255,255));
        //最大 最小字符串长度 //文本颜色  //文本混淆
        return new DecoratedRandomTextPaster(4,4,new SingleColorGenerator(new Color(50,50,50)),textDecoratorList);
    }

}
