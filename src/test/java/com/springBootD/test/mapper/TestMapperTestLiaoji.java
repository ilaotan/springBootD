package com.springBootD.test.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.springBootD.application.demo.dao.TestMapper;
import com.springBootD.application.demo.model.Test;

/**
 * @author tan20170813
 */
public class TestMapperTestLiaoji extends Base {

    @Autowired
    private TestMapper testMapper;


    @org.junit.Test
    public void testTime() {

        List<String> idList = readTxtFile("H:\\idList.txt");

        for (int i = 0; i < 999; i++) {

            for(String id :idList){
                final Test test = testMapper.selectByPrimaryKey(id);
                if(test == null){
                    continue;
                }
                test.setUpdateTime(new Date());
                testMapper.updateByPrimaryKey(test);
            }
        }


    }


    public static List<String> readTxtFile(String filePath) {

        List<String> idList = Lists.newArrayList();
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    idList.add(lineTxt);
                }
                read.close();
            }
            else {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return idList;
    }


}
