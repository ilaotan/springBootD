package com.springBootD.test.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.springBootD.application.demo.dao.TestMapper;
import com.springBootD.application.demo.model.Test;
import com.springBootD.framework.utils.DateUtils;
import com.springBootD.framework.utils.UuidUtil;

/**
 * @author tan20170813
 */
public class TestMapperTest2 extends Base {

    @Autowired
    private TestMapper testMapper;


    @org.junit.Test
    public void initData() throws IOException {

        for (int a = 1; a <= 10; a++) {

            String fileName = "fff_" + a;

            File file = new File("d:\\100000000\\" + fileName + ".txt");
            Writer out = null;
            try {
                // 创建文件
                file.createNewFile();
                out = new FileWriter(file, false);

                StringBuilder stringBuffer = new StringBuilder();
                String uuid;
                List<String> temp1 = Lists.newArrayList();
                List<String> temp2 = Lists.newArrayList();
                List<String> temp3 = Lists.newArrayList();
                List<String> temp4 = Lists.newArrayList();
                for (int i = 0; i <= 9999999; i++) {


                    uuid = UuidUtil.newid();

                    stringBuffer.append(uuid + ",aaa_" + a + "_" + i + ",bbb" +  + a + "_" + i + "," + i % 6 + "," + DateUtils.formatDateTime(new Date()));
                    stringBuffer.append("\r\n");

                    if (i % 49999 == 0 && i > 0) {
                        out.write(stringBuffer.toString());
                        stringBuffer = new StringBuilder();
                    }

                    if (i > 0) {
                        if (i % 5555 == 0) {
                            temp1.add(uuid);
                        }
                        else if (i % 6666 == 0) {
                            temp2.add(uuid);
                        }
                        else if (i % 7777 == 0) {
                            temp3.add(uuid);
                        }
                        else if (i % 8888 == 0) {
                            temp4.add(uuid);
                        }
                    }
                }

                if (stringBuffer.length() > 20) {
//                System.out.println(xxx);
                    out.write(stringBuffer.toString());
                }

                //随机id保存到文件里
                writeTempFile("d:\\100000000\\" + fileName + "_temp1.txt", temp1);
                writeTempFile("d:\\100000000\\" + fileName + "_temp2.txt", temp2);
                writeTempFile("d:\\100000000\\" + fileName + "_temp3.txt", temp3);
                writeTempFile("d:\\100000000\\" + fileName + "_temp4.txt", temp4);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                out.close();
            }

        }
    }


    private void writeTempFile(String fileName, List<String> idList) throws IOException {
        File file = new File(fileName); // 找到File类的实例
        Writer out = null;
        try {
            // 创建文件
            file.createNewFile();
            out = new FileWriter(file, true);

            for (String id : idList) {
                out.write(id + "\r\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            out.close();
        }

    }

}
