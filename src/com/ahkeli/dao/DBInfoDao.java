package com.ahkeli.dao;

import com.ahkeli.model.RoleInfo;
import com.ahkeli.model.RoleWarnConfig;
import com.ahkeli.model.UserInfo;
import com.ahkeli.model.UserWarnConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliang on 2015/12/7.
 */
public class DBInfoDao extends BaseDao {

    public List<RoleInfo> selectAllRole(){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.RoleInfoMapper.selectRoleInfo");
    }

    public int getUserCount(){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.UserInfoMapper.getCountOfUser");
    }

    public List<UserInfo> selectPageUser(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.UserInfoMapper.selectPageUser", map);
    }

    public  void insertWarnRole(RoleWarnConfig roleWarnConfig){
         this.getSqlSession().insert("com.ahkeli.myBatisMapper.RoleWarnConfigMapper.insertRoleConfig",roleWarnConfig);
    }

    public RoleInfo selectByRoleName(String roleName){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.RoleInfoMapper.selectByRoleName", roleName);
    }

    public void updateWarnRole(Map map){
        this.getSqlSession().update("com.ahkeli.myBatisMapper.RoleWarnConfigMapper.updateRoleConfig", map);
    }

    public void insertWarnUser(UserWarnConfig userWarnConfig){
        this.getSqlSession().insert("com.ahkeli.myBatisMapper.UserWarnConfigMapper.insertUserConfig", userWarnConfig);
    }

    public void updateWarnUser(Map map){
        this.getSqlSession().update("com.ahkeli.myBatisMapper.UserWarnConfigMapper.updateUserConfig", map);
    }

    public UserInfo selectUserByNo(String userNo){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.UserInfoMapper.selectByNo", userNo);
    }

    public List<UserInfo> selectUserByName(String userName){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.UserInfoMapper.selectByName", userName);
    }

    public List<Map> selectDetailList(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePage", map);
    }

    public int selectSize(){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.getDataSize");
    }
    //监控明细模糊查询
    public List<Map> selectDetailListSearch1(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePageSearch1", map);
    }

    public  int selectSearchSize1(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.getDataSearch1", map);
    }

    public List<Map> selectDetailListSearch2(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePageSearch2", map);
    }

    public int selectSearchSize2(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.getDataSearch2", map);
    }

    public List<Map> selectDetailListSearch3(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePageSearch3", map);
    }

    public int selectSearchSize3(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.getDataSearch3", map);
    }
    
    public List<Map> selectDetailListSearch4(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePageSearch4", map);
    }

    public int selectSearchSize4(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.getDataSearch4", map);
    }

    public  List<Map> selectDetailListSearchTime(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePageSearchTime", map);
    }

    public int selectSearchSizeTime(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.getDataSearchTime", map);
    }

    //监控统计
    public List<Map> selectStatList(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterStatMapper.selectOnePage", map);
    }

    public int selectStatSize(){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterStatMapper.getDataSize");
    }

    public List<Map> searchStatList1(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterStatMapper.selectOnePageSearch1", map);
    }

    public  int searchStatSize1(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterStatMapper.getDataSearch1", map);
    }

    public List<Map> searchStatList2(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterStatMapper.selectOnePageSearch2", map);
    }

    public  int searchStatSize2(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterStatMapper.getDataSearch2", map);
    }

    public List<Map> searchStatList4(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterStatMapper.selectOnePageSearch4", map);
    }
    public  int searchStatSize4(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterStatMapper.getDataSearch4", map);
    }

    public  List<Map> searchStatListTime(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterStatMapper.selectOnePageSearchTime", map);
    }

    public int searchSizeTime(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterStatMapper.getDataSearchTime", map);
    }

    //查询明细
    public  List<Map> searchStatListDetail(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePageSearchDetail", map);
    }

    public int searchSizeDeatil(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.getDataSearchDetail", map);
    }

    //监控处理

    public List<Map> selectHandleList(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePage",map);
    }

    public int selectHandleSize(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSize",map);
    }

    public List<Map> selectHandleListAll(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePageAll",map);
    }

    public int selectHandleSizeAll(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSizeAll",map);
    }

    public  List<Map> searchHandleListTime(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePageSearchTime", map);
    }

    public int searchHandleSizeTime(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSearchTime", map);
    }

    public  List<Map> searchHandleListTimeAll(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePageSearchTimeAll", map);
    }

    public int searchHandleSizeTimeAll(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSearchTimeAll", map);
    }
    //模糊查询
    public List<Map> searchHandleList1(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePageSearch1", map);
    }

    public  int searchHandleSize1(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSearch1", map);
    }

    public List<Map> searchHandleList11(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePageSearch11", map);
    }

    public  int searchHandleSize11(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSearch11", map);
    }

    public List<Map> searchHandleList2(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePageSearch2", map);
    }

    public int searchHandleSize2(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSearch2", map);
    }

    public List<Map> searchHandleList3(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePageSearch3", map);
    }

    public int searchHandleSize3(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSearch3", map);
    }

    public List<Map> searchHandleList4(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectOnePageSearch4", map);
    }

    public int searchHandleSize4(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getDataSearch4", map);
    }






    //刷新

    public  List<Map> selectWarnFreshList1(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectWarnFreshPage1", map);
    }

    public int selectWarnFreshSize1(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getWarnFreshSize1", map);
    }
    public  List<Map> selectWarnFreshList(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.selectWarnFreshPage2", map);
    }

    public int selectWarnFreshSize(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getWarnFreshSize2", map);
    }
    //获取最大时间

    public Date selectWarnFreshBigTime(){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.getWarnFreshBigDate");
    }

    //更新处理结果
    public void updateWarnHandle(Map map){
        this.getSqlSession().update("com.ahkeli.myBatisMapper.WarnCenterHandleMapper.updateWarnHandle",map);
    }

    //监控等级
    public List<Map> selectWarnLevelList(Map map){
        return this.getSqlSession().selectList("com.ahkeli.myBatisMapper.WarnCenterLevelMapper.selectOnePage", map);
    }

    public int selectWarnLevelSize(Map map){
        return this.getSqlSession().selectOne("com.ahkeli.myBatisMapper.WarnCenterLevelMapper.getDataSize", map);
    }









}
