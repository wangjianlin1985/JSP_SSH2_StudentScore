package com.shuangyulin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.CourseInfoDAO;
import com.shuangyulin.dao.StudentSelectCourseInfoDAO;
import com.shuangyulin.domain.CourseInfo;
import com.shuangyulin.domain.StudentSelectCourseInfo;
import com.shuangyulin.dao.TeacherDAO;
import com.shuangyulin.domain.Teacher;
import com.shuangyulin.test.TestUtil;

public class CourseInfoAction extends ActionSupport {

    /*�������Ҫ��ѯ������: �γ̱��*/
    private String courseNumber;
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public String getCourseNumber() {
        return this.courseNumber;
    }

    /*�������Ҫ��ѯ������: �γ�����*/
    private String courseName;
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseName() {
        return this.courseName;
    }

    /*�������Ҫ��ѯ������: �Ͽ���ʦ*/
    private Teacher courseTeacher;
    public void setCourseTeacher(Teacher courseTeacher) {
        this.courseTeacher = courseTeacher;
    }
    public Teacher getCourseTeacher() {
        return this.courseTeacher;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    CourseInfoDAO courseInfoDAO = new CourseInfoDAO();

    /*��������CourseInfo����*/
    private CourseInfo courseInfo;
    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }
    public CourseInfo getCourseInfo() {
        return this.courseInfo;
    }

    /*��ת�����CourseInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Teacher��Ϣ*/
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*���CourseInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�γ̱���Ƿ��Ѿ�����*/
        String courseNumber = courseInfo.getCourseNumber();
        CourseInfo db_courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);
        if(null != db_courseInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ÿγ̱���Ѿ�����!"));
            return "error";
        }
        try {
            if(true) {
            TeacherDAO teacherDAO = new TeacherDAO();
            Teacher courseTeacher = teacherDAO.GetTeacherByTeacherNumber(courseInfo.getCourseTeacher().getTeacherNumber());
            courseInfo.setCourseTeacher(courseTeacher);
            }
            courseInfoDAO.AddCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCourseInfo��Ϣ*/
    public String QueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseNumber", courseNumber);
        ctx.put("courseName", courseName);
        ctx.put("courseTeacher", courseTeacher);
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "query_view";
    }

    
    /*��ʦ��ѯ�Լ����ڿογ�*/
    public String TeacherQueryCourseInfo() { 
    	 ActionContext ctx = ActionContext.getContext();
    	 String teacherNumber = (String)ctx.getSession().get("username");
         List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(teacherNumber);
         
         ctx.put("courseInfoList",  courseInfoList); 
         return "teacher_query_view";
    }
    
    
    /*ѧ����ѯ�Ѿ�ѡ��Ŀγ�*/
    public String StudentSelectCourseList() {
    	ActionContext ctx = ActionContext.getContext();
    	List<CourseInfo> courseInfoList =  new ArrayList<CourseInfo>();
    	List<Integer> selectIdList = new ArrayList<Integer>();
    	StudentSelectCourseInfoDAO studentSelectCourseDAO = new StudentSelectCourseInfoDAO();
    	ArrayList<StudentSelectCourseInfo> selectList = studentSelectCourseDAO.QueryStudentSelectCourseInfoInfo((String)ctx.getSession().get("username"));
    	for(StudentSelectCourseInfo studentSelectCourseInfo:selectList) {
    		courseInfoList.add(studentSelectCourseInfo.getCourseNumber());
    		selectIdList.add(studentSelectCourseInfo.getSelectId());
    	} 
       
        ctx.put("courseInfoList",  courseInfoList); 
        ctx.put("selectIdList", selectIdList);
        
        return "student_selectQuery_view";
    }
    
    /*��ѯCourseInfo��Ϣ*/
    public String StudentQueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseNumber", courseNumber);
        ctx.put("courseName", courseName);
        ctx.put("courseTeacher", courseTeacher);
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "student_query_view";
    }
    
    
    /*ǰ̨��ѯCourseInfo��Ϣ*/
    public String FrontQueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = courseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseNumber", courseNumber);
        ctx.put("courseName", courseName);
        ctx.put("courseTeacher", courseTeacher);
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�CourseInfo��Ϣ*/
    public String ModifyCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������courseNumber��ȡCourseInfo����*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);

        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("courseInfo",  courseInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�CourseInfo��Ϣ*/
    public String FrontShowCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������courseNumber��ȡCourseInfo����*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);

        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("courseInfo",  courseInfo);
        return "front_show_view";
    }

    /*�����޸�CourseInfo��Ϣ*/
    public String ModifyCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            TeacherDAO teacherDAO = new TeacherDAO();
            Teacher courseTeacher = teacherDAO.GetTeacherByTeacherNumber(courseInfo.getCourseTeacher().getTeacherNumber());
            courseInfo.setCourseTeacher(courseTeacher);
            }
            courseInfoDAO.UpdateCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��CourseInfo��Ϣ*/
    public String DeleteCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            courseInfoDAO.DeleteCourseInfo(courseNumber);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
