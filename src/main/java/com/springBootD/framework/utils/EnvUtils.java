package com.springBootD.framework.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 获取系统环境变量工具类
 */
public class EnvUtils {

    private static final Logger logger = Logger.getLogger("EnvUtils");

    private static Boolean OS_LINUX = null;

    /**
     * 判断当前系统是否为 linux
     *
     * @return true linux, false windows
     */
    public static boolean isLinux() {
        if (OS_LINUX == null) {
            String OS = System.getProperty("os.name").toLowerCase();
            logger.info("os.name: " + OS);
            if (OS != null && OS.contains("windows")) {
                OS_LINUX = false;
            } else {
                OS_LINUX = true;
            }
        }
        return OS_LINUX;
    }

    /**
     * 返回当前系统变量的函数 结果放至 Properties
     */
    public static Properties getEnv() {
        Properties prop = new Properties();
        try {
            Process p = null;
            if (isLinux()) {
                p = Runtime.getRuntime().exec("sh -c set");
            } else {
                // windows
                p = Runtime.getRuntime().exec("cmd /c set");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                int i = line.indexOf("=");
                if (i > -1) {
                    String key = line.substring(0, i);
                    String value = line.substring(i + 1);
                    prop.setProperty(key, value);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

}