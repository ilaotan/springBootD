package com.springBootD.test1;

import com.springBootD.application.system.dao.UserDao;
import com.springBootD.application.system.entity.UserEntity;
import com.springBootD.application.system.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by tan on 2016/8/19.
 */
public class DaoTest extends Base {
    @Autowired
   private UserDao userDao;

    @Test
    public void test1(){

        List<UserEntity> userEntityList =  userDao.findAll();
        for(UserEntity userEntity:userEntityList){

            System.out.println(userEntity.getId());
        }
    }

    @Test
    public void test2(){

        System.out.println(userDao.findByEmail("280797045@qq.com"));
        System.out.println(userDao.findByEmailLike("280797045%"));
    }

}
