package com.ahkeli.menu;

import com.ahkeli.dao.BaseDao;
import com.ahkeli.dao.GetSqlSession;
import com.ahkeli.model.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * Created by pangbo on 2015/11/13.
 */
public class UserPrivilegeData{
    public static List<PrivilegeInfo> SYS_PRIVILEGE_INFO_LIST=new ArrayList<PrivilegeInfo>();
    public static List<RoleInfo> SYS_ROLE_INFO_LIST=new ArrayList<RoleInfo>();
    //public static List<UserInfo> SYS_USER_INFO_LIST=new ArrayList<UserInfo>();
    public static List<RelRoleUser> SYS_REL_ROLE_USER_LIST=new ArrayList<RelRoleUser>();
    public static List<RelRolePrivilege> SYS_REL_ROLE_PRIVILEGE_LIST=new ArrayList<RelRolePrivilege>();
    //用于测试前端表格的数据
    public static List<TabelTestData> TABLE_TEST_DATA_LIST = new ArrayList<TabelTestData>();
    //全局变量sqlSession
    //public static SqlSession sqlSession;
    private SqlSession sqlSession;

    public UserPrivilegeData()
    {
        this.sqlSession = GetSqlSession.sqlSession;
    }
    /**
     * 该函数用户将数据库中和用户、角色、权限相关的表信息加载到内存中
     * 以便于后期使用
     */
    public void getData()
    {
        //sqlSession = this.getSqlSession();
        //sqlSession = GetSqlSession.sqlSession;
        SYS_PRIVILEGE_INFO_LIST = sqlSession.selectList("com.ahkeli.myBatisMapper.PrivilegeInfoMapper.selectPrivilegeInfo");
        SYS_ROLE_INFO_LIST = sqlSession.selectList("com.ahkeli.myBatisMapper.RoleInfoMapper.selectRoleInfo");
        //SYS_USER_INFO_LIST = sqlSession.selectList("com.ahkeli.myBatisMapper.UserInfoMapper.selectUserInfo");
        SYS_REL_ROLE_USER_LIST = sqlSession.selectList("com.ahkeli.myBatisMapper.RelRoleUserMapper.selectRelRoleUserInfo");
        SYS_REL_ROLE_PRIVILEGE_LIST = sqlSession.selectList("com.ahkeli.myBatisMapper.RelRolePrivilegeMapper.selectRelRolePrivilegeInfo");
        //TABLE_TEST_DATA_LIST = sqlSession.selectList("com.ahkeli.myBatisMapper.TabelTestDataMapper.selectAll");
        //test
        //System.out.println("the role info list size is: " + SYS_ROLE_INFO_LIST.size());
        //getUserPrivilegeByUserName("user1");
    }

    /**
     * 该函数用户根据前端传入的用户登录名称获取到和该用户相关的权限功能信息
     * @param userNo
     * @return
     */
    public List<Map> getUserPrivilegeByUserNo(String userNo)
    {
        UserInfo userInfo = sqlSession.selectOne("com.ahkeli.myBatisMapper.UserInfoMapper.selectTheUserInfo", userNo);
        int userId = userInfo.getUserId();
        //查询该userId对应的全部的系统权限功能信息
        try {
            List<PrivilegeInfo> privilegeInfos = sqlSession.selectList("com.ahkeli.myBatisMapper.UserPrivilegeMapper.selectUserPrivilegeData", userId);
            //处理数据库中获取的原始数据为前端可用的内容
            List<Map> result = dealPrivilegeData(privilegeInfos);
            return result;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public List<Map> dealPrivilegeData(List<PrivilegeInfo> privilegeInfos)
    {
        List<Map> result = new ArrayList<Map>();
        //通过队列结构整理节点数据
        Queue<PrivilegeInfo> queue = new LinkedList<PrivilegeInfo>();
        int rootId;
        //遍历寻找根节点
        for (PrivilegeInfo privilegeInfo:privilegeInfos)
        {
            if(privilegeInfo.getParentPrivilege()==null)
            {
                rootId = privilegeInfo.getPrivilegeId();
                queue.offer(privilegeInfo);
                break;
            }
        }
        //由根节点开始依次寻找各层节点
        while(queue.size()!=0) {
            PrivilegeInfo privilegeInfo = queue.poll();
            rootId = privilegeInfo.getPrivilegeId();
            //向Map添加信息,排除虚拟的根节点
            if(privilegeInfo.getParentPrivilege() != null)
            {
                Map<String, Object> temp = new HashMap<String, Object>();
                temp.put("id",privilegeInfo.getPrivilegeId());
                if(privilegeInfo.getParentPrivilege() == null)
                {
                    temp.put("pId",0);
                }
                else
                {
                    temp.put("pId",privilegeInfo.getParentPrivilege());
                }
                temp.put("name",privilegeInfo.getPrivilegeName());
                temp.put("open", false);
                if(privilegeInfo.getNodeType() == 3)
                {
                    temp.put("linkedPage", privilegeInfo.getFunctionUrl());
                    temp.put("isFunNode",true);
                }
                else
                {
                    temp.put("isFunNode",false);
                }
                result.add(temp);
            }
            //针对非叶子节点的操作
            if(privilegeInfo.getNodeType() != 3)
            {
                List<PrivilegeInfo> sortList = new ArrayList<PrivilegeInfo>();
                for (PrivilegeInfo info : privilegeInfos) {
                    if (info.getParentPrivilege()!=null && info.getParentPrivilege().equals(rootId)) {
                        sortList.add(info);
                    }
                }
                //对同层次的节点依据其排序字段对其进行排序
                for (int i = sortList.size(); i >= 0; i--) {
                    for (int j = 0; j < i - 1; j++) {
                        PrivilegeInfo before = sortList.get(j);
                        PrivilegeInfo after = sortList.get(j + 1);
                        if (before.getPriOrder() > after.getPriOrder()) {
                            sortList.set(j, after);
                            sortList.set(j + 1, before);
                        }
                    }
                }
                //排序完成后将各个节点放入队列中
                for (int i = 0; i < sortList.size(); i++) {
                    queue.offer(sortList.get(i));
                }
            }
        }
        return result;

    }
    public Map getUserRoleAndIcon(String userNo)
    {
        UserInfo userInfo = sqlSession.selectOne("com.ahkeli.myBatisMapper.UserInfoMapper.selectTheUserInfo",userNo);
        int userId = userInfo.getUserId();
        //查询该userId最高权限的角色名称
        try {
            Map result = new HashMap();
            String userIcon = userInfo.getUserIcon();
            String userRole = sqlSession.selectOne("com.ahkeli.myBatisMapper.UserPrivilegeMapper.selectUserRole", userId);
            result.put("userIcon",userIcon);
            result.put("userRole",userRole);
            return result;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
