package com.springBootD.framework.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springBootD.framework.exception.BizExceptionEnum;
import com.springBootD.framework.exception.BussinessException;


public class FileUtil extends org.apache.commons.io.FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new BussinessException(BizExceptionEnum.FILE_NOT_FOUND);
        }
        try {
            return readFileToByteArray(f);
        }
        catch (IOException e) {
            throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
        }
    }
}