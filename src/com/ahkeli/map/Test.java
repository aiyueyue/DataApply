package com.ahkeli.map;

import com.ahkeli.dao.BaseDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/11/9.
 */
public class Test {
    public static void main(String[] args){
        try {
            List<Map> list = new ArrayList<Map>();
            ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
            //SqlSession sqlSession = ((GetDrawLineData)ac.getBean("getDrawLineData")).getSqlSession();
            //list = sqlSession.selectList("com.ahkeli.myBatisMapper.getDrawLineData.selectDrawLineData");
            System.out.println(list.size());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
