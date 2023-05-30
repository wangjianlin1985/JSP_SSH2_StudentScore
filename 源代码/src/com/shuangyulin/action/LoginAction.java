package com.shuangyulin.action;

 

 

 

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.AdminDAO;
import com.shuangyulin.dao.StudentDAO;
import com.shuangyulin.dao.TeacherDAO;
import com.shuangyulin.domain.Admin;

public class LoginAction extends ActionSupport {
 
	
	private Admin admin;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/*直接跳转到登陆界面*/
	public String view() {
		
		return "login_view";
	}
	 
	
	/* 验证用户登录 */
	public String CheckLogin() {
		
		ActionContext ctx = ActionContext.getContext();
		
		if (admin.getIdentify().equals("admin")) {  //管理员身份登录
			AdminDAO adminDAO = new AdminDAO();
			if (!adminDAO.CheckLogin(admin)) {
				ctx.put("error",  java.net.URLEncoder.encode(adminDAO.getErrMessage()));
				return "error";
			}
			ctx.getSession().put("username", admin.getUsername());
			return "main_view";
		} else if(admin.getIdentify().equals("teacher")) { //教师身份登录
			TeacherDAO teacherDAO = new TeacherDAO();
			if (!teacherDAO.CheckLogin(admin)) {
				ctx.put("error",  java.net.URLEncoder.encode(teacherDAO.getErrMessage()));
				return "error";
			}
			ctx.getSession().put("username", admin.getUsername());
			return "teacher_main_view";
		} else {  //学生身份登录
			StudentDAO studentDAO = new StudentDAO(); 
			if (!studentDAO.CheckLogin(admin)) {
				ctx.put("error",  java.net.URLEncoder.encode(studentDAO.getErrMessage()));
				return "error";
			}
			ctx.getSession().put("username", admin.getUsername());
			return "student_main_view";
		} 
	}

	

}
