package com.springBootD.test.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.IdGenerator;

import com.google.common.collect.Lists;
import com.springBootD.application.demo.dao.TestMapper;
import com.springBootD.application.demo.model.Test;
import com.springBootD.framework.utils.DateUtils;
import com.springBootD.framework.utils.UuidUtil;

/**
 * @author tan20170813
 */
public class TestMapperTest extends Base {

    @Autowired
    private TestMapper testMapper;


    @org.junit.Test
    public void genIds() throws IOException {

        List<String> idList = Lists.newArrayList();
        try {
            String encoding = "GBK";
            File file = new File("D:\\ddd.txt");

            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    int count = 1;
                    String[] strArr = lineTxt.split("\\('");
                    for(int i = 1;i<strArr.length;i++){
                        if(i%1235==0){
                            if(count <30){
                                idList.add(strArr[i].substring(0,32));
                                count++;
                            }else {
                                break;
                            }
                        }
                    }
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

        writeTempFile("D:\\ddd_temp2_2.txt",idList);

    }




    @org.junit.Test
    public void initData() throws IOException {

        String fileName = "eee_9";

        File file = new File("d:\\"+fileName+".txt");
        Writer out = null;
        try {
            // 创建文件
            file.createNewFile();
            out = new FileWriter(file, false);

            final String prefix = "insert into `test2`(`id`,`userName`,`password`,`age`,`updatetime`) VALUES";
            StringBuilder stringBuffer = new StringBuilder();
            String uuid;
            List<String> temp1 = Lists.newArrayList();
            List<String> temp2 = Lists.newArrayList();
            List<String> temp3 = Lists.newArrayList();
            List<String> temp4 = Lists.newArrayList();
            for (int i = 0; i <= 9999999; i++) {
//            Test test = new Test(UuidUtil.newid(),"aaa"+i%8,"bbb"+i%8,i%8,new Date());
//            testMapper.insert(test);
                uuid = UuidUtil.newid();
                if (i % 49999 == 0 && i > 0) {

                    stringBuffer.append("('" + uuid + "', '" + "aaa" + i % 8 + "', '" + "bbb" + i % 7 + "', '" + i % 6 + "', '" + DateUtils.formatDateTime(new Date()) + "');");
                    stringBuffer.append("\r\n");
                    out.write(stringBuffer.toString());
                    stringBuffer = new StringBuilder();
                } else {
                    if (stringBuffer.length() < 5) {
                        stringBuffer.append(prefix);
                    }
                    stringBuffer.append("('" + uuid + "', '" + "aaa" + i % 8 + "', '" + "bbb" + i % 7 + "', '" + i % 6 + "', '" + DateUtils.formatDateTime(new Date()) + "'),");
                }

                if (i > 0) {
                    if (i % 5555 == 0) {
                        temp1.add(uuid);
                    } else if (i % 6666 == 0) {
                        temp2.add(uuid);
                    } else if (i % 7777 == 0) {
                        temp3.add(uuid);
                    } else if (i % 8888 == 0) {
                        temp4.add(uuid);
                    }
                }
            }

            if (stringBuffer.length() > 20) {
                String xxx = stringBuffer.substring(0, stringBuffer.lastIndexOf(","));
//                System.out.println(xxx);
                out.write(xxx + ";");
            }

            //随机id保存到文件里
            writeTempFile("d:\\"+fileName+"_temp1.txt",temp1);
            writeTempFile("d:\\"+fileName+"_temp2.txt",temp2);
            writeTempFile("d:\\"+fileName+"_temp3.txt",temp3);
            writeTempFile("d:\\"+fileName+"_temp4.txt",temp4);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            out.close();
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


    @org.junit.Test
    public void testTime() {
        final List<Test> tests = testMapper.selectAll();

        List<String> idList1 = Lists.newArrayList();
        List<String> idList2 = Lists.newArrayList();
        for (Test test : tests) {
            if (Math.random() < 0.2) {
                idList1.add(test.getId());
                if (idList1.size() == 500) {
                    break;
                }
            }
        }
        for (Test test : tests) {
            if (Math.random() > 0.8) {
                idList2.add(test.getId());
                if (idList2.size() == 500) {
                    break;
                }
            }
        }

        for (String id : idList1) {
            System.out.println(id);
        }


        Long time1 = System.currentTimeMillis();
        //先删再插入
        for (String id1 : idList1) {
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
        for (String id2 : idList2) {
            Test test = testMapper.selectByPrimaryKey(id2);
            test.setUpdateTime(new Date());
            testMapper.updateByPrimaryKey(test);
        }
        Long final2 = System.currentTimeMillis() - time2;

        System.out.println(idList1.size() + "   " + final1);
        System.out.println(idList2.size() + "   " + final2);

    }


    @org.junit.Test
    public void testTime_delete_insert() {

        List<String> idList1 = readTxtFile("D:\\ddd_temp2_2.txt");

        Long time1 = System.currentTimeMillis();
        //先删再插入
        for (String id1 : idList1) {
            Test test = testMapper.selectByPrimaryKey(id1);
            test.setUpdateTime(new Date());
            int num = testMapper.deleteByPrimaryKey(id1);
            int num2 = testMapper.insert(test);
        }

        Long final1 = System.currentTimeMillis() - time1;

        System.out.println("delete_insert 随机Id数据量 "+idList1.size() + " 总耗时  " + final1);

    }
    @org.junit.Test
    public void testTime_update() {

        List<String> idList2 = readTxtFile("D:\\ddd_temp2_2.txt");

        Long time2 = System.currentTimeMillis();
        for (String id2 : idList2) {
            Test test = testMapper.selectByPrimaryKey(id2);
            test.setUpdateTime(new Date());
            testMapper.updateByPrimaryKey(test);
        }
        Long final2 = System.currentTimeMillis() - time2;

        System.out.println("update 随机Id数据量 "+idList2.size() + " 总耗时  " + final2);

    }


    @org.junit.Test
    public void allInOne(){

        testTime_update();
        testTime_delete_insert();
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
