package com.ahkeli.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by pangbo on 2015/12/7.
 */
public class GetSqlSession extends BaseDao {

    //全局变量sqlSession
    public static SqlSession sqlSession;

    public void getSqlSessionInstance()
    {
        sqlSession = this.getSqlSession();
    }
}
