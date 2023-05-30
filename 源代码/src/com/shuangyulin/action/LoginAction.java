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

	/*ֱ����ת����½����*/
	public String view() {
		
		return "login_view";
	}
	 
	
	/* ��֤�û���¼ */
	public String CheckLogin() {
		
		ActionContext ctx = ActionContext.getContext();
		
		if (admin.getIdentify().equals("admin")) {  //����Ա��ݵ�¼
			AdminDAO adminDAO = new AdminDAO();
			if (!adminDAO.CheckLogin(admin)) {
				ctx.put("error",  java.net.URLEncoder.encode(adminDAO.getErrMessage()));
				return "error";
			}
			ctx.getSession().put("username", admin.getUsername());
			return "main_view";
		} else if(admin.getIdentify().equals("teacher")) { //��ʦ��ݵ�¼
			TeacherDAO teacherDAO = new TeacherDAO();
			if (!teacherDAO.CheckLogin(admin)) {
				ctx.put("error",  java.net.URLEncoder.encode(teacherDAO.getErrMessage()));
				return "error";
			}
			ctx.getSession().put("username", admin.getUsername());
			return "teacher_main_view";
		} else {  //ѧ����ݵ�¼
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
