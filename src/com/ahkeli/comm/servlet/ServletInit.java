package com.ahkeli.comm.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by dell on 2015/12/21.
 */
public class ServletInit implements ServletContextListener {
    /**
     * servlet初始化方法
     *
     * @throws
    @Override
    public void init() throws ServletException {
        System.out.println("hello,this is the servlet init method");
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");

    }*/

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("web init ... ");
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
