package com.ahkeli.login;

import com.ahkeli.dao.GetSqlSession;
import com.ahkeli.menu.UserPrivilegeData;
import com.ahkeli.model.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pangbo on 2015/12/4.
 */
public class ChangePasswordAction extends ActionSupport {
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public void changePassword() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("utf-8");
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        Map session = ctx.getSession();
        //��ȡǰ�˴����Ĳ���
        oldPassword = request.getParameter("oldPassword") == null ? "" : request.getParameter("oldPassword").toString();
        newPassword = request.getParameter("newPassword")== null ? "" : request.getParameter("newPassword").toString();
        repeatPassword = request.getParameter("repeatPassword")== null ? "" : request.getParameter("repeatPassword").toString();

        Map user = (Map) session.get("userSessionInfo");
        Short userId = (Short)user.get("userId");
        Map selectParams = new HashMap();
        Map updateParams = new HashMap();
        Map result = new HashMap();

        try {
            //�����û�Idȡ�ø��û�������
            selectParams.put("userId", userId);
            UserInfo theUser = GetSqlSession.sqlSession.selectOne("com.ahkeli.myBatisMapper.UserInfoMapper.selectTheUser",selectParams);
            //�û���Ҫ��������
            if (theUser.getIsNoPwd() == 0)
            {
                if (verifyPassword(oldPassword, theUser.getUserPassword()))
                {
                    //�����û�����
                    String newPasswordAfterEncrype = encrypeTheNewPassword(newPassword);
                    updateParams.put("newPassword",newPasswordAfterEncrype);
                    updateParams.put("isNoPassword",0);
                    updateParams.put("userId",userId);
                    GetSqlSession.sqlSession.update("com.ahkeli.myBatisMapper.UserInfoMapper.updatePassword",updateParams);
                    result.put("status",true);
                    result.put("errorMessage","");
                    response.getWriter().print(JSONArray.fromObject(result).toString());
                }else{
                    result.put("status",false);
                    result.put("errorMessage","����У��ʧ��");
                    response.getWriter().print(JSONArray.fromObject(result).toString());
                }
            }else{
                //�û�����Ҫ��������
                //�����û�����
                String newPasswordAfterEncrype = encrypeTheNewPassword(newPassword);
                updateParams.put("newPassword",newPasswordAfterEncrype);
                updateParams.put("isNoPassword",0);
                updateParams.put("userId",userId);
                GetSqlSession.sqlSession.update("com.ahkeli.myBatisMapper.UserInfoMapper.updatePassword", updateParams);
                result.put("status",true);
                result.put("errorMessage","");
                response.getWriter().print(JSONArray.fromObject(result).toString());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            result.put("status", false);
            result.put("errorMessage","�޸�ʧ��");
            response.getWriter().print(JSONArray.fromObject(result).toString());
        }
    }
    public boolean verifyPassword(String oldPassword,String storedPassword)
    {
        try {
            //ȷ�����㷽��
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //���ܺ���ַ���
            String newstr = base64en.encode(md5.digest(oldPassword.getBytes("utf-8")));
            //System.out.println(newstr);
            if (newstr.equals(storedPassword))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public String encrypeTheNewPassword(String oldPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //���ܺ���ַ���
        String newstr = base64en.encode(md5.digest(oldPassword.getBytes("utf-8")));
        return newstr;
    }
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
