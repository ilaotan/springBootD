package com.springBootD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.Lists;
import com.springBootD.application.demo.dao.TestMapper;
import com.springBootD.application.demo.model.Test;
import com.springBootD.framework.utils.DateUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;


//@Component
public class MyCommandRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestMapper testMapper;


    @Override
    public void run(final String... strings) throws Exception {

        List<String> idList1 = readTxtFile("/root/eee_6_temp1.txt");

       for(int i = 0;i<100;i++){
           testTime_delete_insert(idList1);
//           testTime_update(idList1);
       }

    }

    public void testTime_delete_insert(List<String> idList1) {


        Long time1 = System.currentTimeMillis();
        //先删再插入
        for (String id1 : idList1) {
//
            Test test = new Test(id1, DateUtils.formatDateTime(new Date()), DateUtils.formatDateTime(new Date()), 3, new Date());
//            test.setUpdateTime(new Date());
//            test.setUserName(DateUtils.formatDateTime(new Date()));
            int num = testMapper.deleteByPrimaryKey(id1);
            int num2 = testMapper.insert(test);
        }
        Long final1 = System.currentTimeMillis() - time1;

        logger.error("delete_insert 随机Id数据量 "+idList1.size() + " 总耗时  " + final1);
    }

    public void testTime_update(List<String> idList2 ) {

        Long time2 = System.currentTimeMillis();
        for (String id2 : idList2) {
            Test test = new Test(id2,DateUtils.formatDateTime(new Date()),DateUtils.formatDateTime(new Date()),3,new Date());
            testMapper.updateByPrimaryKey(test);
        }
        Long final2 = System.currentTimeMillis() - time2;

        logger.error("update 随机Id数据量 "+idList2.size() + " 总耗时  " + final2);

    }

    public List<String> readTxtFile(String filePath) {

        List<String> idList = Lists.newArrayList();
        try {
            String encoding = "GBK";

            File file = new File(filePath);

            if (file.isFile() &&file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    idList.add(lineTxt);
                }
                read.close();
            } else {
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
