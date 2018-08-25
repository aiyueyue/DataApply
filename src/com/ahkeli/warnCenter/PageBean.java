package com.ahkeli.warnCenter;

import com.ahkeli.model.UserInfo;

import java.util.List;

/**
 * Created by dell on 2015/12/8.
 */
public class PageBean {
    private List<UserInfo> userList;
    private int allRows;//总记录数
    private  int totalPage;
    private int currentPage;

    public int getAllRows() {
        return allRows;
    }

    public void setAllRows(int allRows) {
        this.allRows = allRows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }

    //pageSize每页记录数
    public int getTotalPages(int pageSize,int allRows){
        int totalPage=(allRows%pageSize==0)?(allRows/pageSize):(allRows/pageSize)+1;
        return totalPage;
    }
    //得到当前开始记录号
    public int getCurrentPageOffset(int pageSize,int currentPage){
        int offset=pageSize*(currentPage-1);
        return offset;
    }
    //得到当前页
    public int getCurPage(int page){
        int currentPage=(page==0)?1:page;
        return currentPage;
    }
}
