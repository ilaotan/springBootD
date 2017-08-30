package com.springBootD.test.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.IdGenerator;

import com.google.common.collect.Lists;
import com.springBootD.application.demo.dao.TestMapper;
import com.springBootD.application.demo.model.Test;
import com.springBootD.framework.utils.UuidUtil;

/**
 * @author tan20170813
 */
public class TestMapperTest extends Base {

    @Autowired
    private TestMapper testMapper;

    @org.junit.Test
    public void initData(){

        System.out.println();


        List<Test> testList = Lists.newArrayList();
        for (int i = 0; i < 999999; i++) {
            Test test = new Test(UuidUtil.newid(),"aaa"+i%8,"bbb"+i%8,i%8,new Date());
            testMapper.insert(test);

        }

    }


    @org.junit.Test
    public void testTime(){
        final List<Test> tests = testMapper.selectAll();

        List<String> idList1 = Lists.newArrayList();
        List<String> idList2 = Lists.newArrayList();
        for(Test test:tests){
            if(Math.random()<0.2){
                idList1.add(test.getId());
                if(idList1.size()==500){
                    break;
                }
            }
        }
        for(Test test:tests){
            if(Math.random()>0.8){
                idList2.add(test.getId());
                if(idList2.size()==500){
                    break;
                }
            }
        }

        for(String id :idList1){
            System.out.println(id);
        }


        Long time1 = System.currentTimeMillis();
        //先删再插入
        for(String id1:idList1){
            Test test = testMapper.selectByPrimaryKey(id1);
            test.setUpdateTime(new Date());
            testMapper.deleteByPrimaryKey(id1);
            testMapper.insert(test);
        }

        Long final1 = System.currentTimeMillis() - time1;

//        new Thread(new Runnable() {
//            @Override
//
//            public void run() {
//
//                for(int i =0;i<5;i++){
//                    for(String id2:idList1){
//                        Test test = testMapper.selectByPrimaryKey(id2);
//                        test.setUpdateTime(new Date());
//                        testMapper.updateByPrimaryKey(test);
//                    }
//                }
//            }
//        }).start();

        Long time2 = System.currentTimeMillis();
        for(String id2:idList2){
            Test test = testMapper.selectByPrimaryKey(id2);
            test.setUpdateTime(new Date());
            testMapper.updateByPrimaryKey(test);
        }
        Long final2 = System.currentTimeMillis() - time2;

        System.out.println(idList1.size() + "   " + final1);
        System.out.println(idList2.size() + "   " + final2);

    }



    @org.junit.Test
    public void testTime2(){

        List<String> idList1 = readTxtFile("H:\\idList.txt");
        List<String> idList2 =  readTxtFile("H:\\idList.txt");

        Long time1 = System.currentTimeMillis();
        //先删再插入
        for(String id1:idList1){
            Test test = testMapper.selectByPrimaryKey(id1);
            test.setUpdateTime(new Date());
            testMapper.deleteByPrimaryKey(id1);
            testMapper.insert(test);
        }

        Long final1 = System.currentTimeMillis() - time1;


        Long time2 = System.currentTimeMillis();
        for(String id2:idList2){
            Test test = testMapper.selectByPrimaryKey(id2);
            test.setUpdateTime(new Date());
            testMapper.updateByPrimaryKey(test);
        }
        Long final2 = System.currentTimeMillis() - time2;

        System.out.println(idList1.size() + "   " + final1);
        System.out.println(idList2.size() + "   " + final2);

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
