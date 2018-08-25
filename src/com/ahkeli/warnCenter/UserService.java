package com.ahkeli.warnCenter;

import com.ahkeli.dao.DBInfoDao;
import com.ahkeli.model.UserInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliang on 2015/12/9.
 */
public class UserService {
    private DBInfoDao dbInfoDao;
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
    public PageBean getPageBean(int pageSize,int page){
        List<UserInfo> userList=new ArrayList<UserInfo>();
        dbInfoDao= (DBInfoDao) ac.getBean("dbInfoDao");
        PageBean pageBean=new PageBean();
        int allRows=dbInfoDao.getUserCount();
        int totalPage=pageBean.getTotalPages(pageSize, allRows);
        int currentPage=pageBean.getCurPage(page);
        int offset=pageBean.getCurrentPageOffset(pageSize, currentPage);
        Map map=new HashMap();
        map.put("startNum",offset);
        map.put("endNum",offset+pageSize);
        userList=dbInfoDao.selectPageUser(map);
        pageBean.setAllRows(allRows);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        pageBean.setUserList(userList);
        return pageBean;
    }
}
