package com.eliteams.quick4j.test.dao;

import java.util.List;
import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.feature.test.TestSupport;
import com.dai.jigsaw.web.dao.UserMapper;
import com.dai.jigsaw.web.model.User;
import com.dai.jigsaw.web.model.UserExample;
import com.dai.jigsaw.web.service.impl.UserServiceImpl;

public class UserMapperTest extends TestSupport {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;

    //@Test
    public void test_selectByExampleAndPage() {
        start();
        Page<User> page = new Page<>(1, 3);
        UserExample example = new UserExample();
        example.createCriteria().andIdGreaterThan(0L);
        final List<User> users = userMapper.selectByExampleAndPage(page, example);
        for (User user : users) {
            System.err.println(user);
        }
        end();
    }
   //@Test
    public void test_selectByWhereParam() {
        start();
        WhereParam whereParam = new WhereParam();
        whereParam.setWhereString("1=1");	
        final List<User> users = userMapper.selectByWhereParam(whereParam);
        for (User user : users) {
            System.err.println(user);
        }
        end();
    }
    //@Test
    public void test_selectByWhereParamAndPage() {
        start();
        Page<User> page = new Page<>(2, 1);
        WhereParam whereParam = new WhereParam();
        whereParam.setWhereString("1=1");	
        final List<User> users = userMapper.selectByWhereParamAndPage(page, whereParam);
        for (User user : users) {
            System.err.println(user);
        }
        end();
    }
    
    @Test
    public void test_Service() {
        start();
        Page<User> page = new Page<>(2, 1);
        WhereParam whereParam = new WhereParam();
        whereParam.setWhereString("1=1");	
        final List<User> users = userServiceImpl.selectPage(page, whereParam);
        for (User user : users) {
            System.err.println(user);
        }
        end();
    }
    
    
}
