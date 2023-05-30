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

    /*界面层需要查询的属性: 课程编号*/
    private String courseNumber;
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public String getCourseNumber() {
        return this.courseNumber;
    }

    /*界面层需要查询的属性: 课程名称*/
    private String courseName;
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseName() {
        return this.courseName;
    }

    /*界面层需要查询的属性: 上课老师*/
    private Teacher courseTeacher;
    public void setCourseTeacher(Teacher courseTeacher) {
        this.courseTeacher = courseTeacher;
    }
    public Teacher getCourseTeacher() {
        return this.courseTeacher;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    CourseInfoDAO courseInfoDAO = new CourseInfoDAO();

    /*待操作的CourseInfo对象*/
    private CourseInfo courseInfo;
    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }
    public CourseInfo getCourseInfo() {
        return this.courseInfo;
    }

    /*跳转到添加CourseInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Teacher信息*/
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*添加CourseInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证课程编号是否已经存在*/
        String courseNumber = courseInfo.getCourseNumber();
        CourseInfo db_courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);
        if(null != db_courseInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该课程编号已经存在!"));
            return "error";
        }
        try {
            if(true) {
            TeacherDAO teacherDAO = new TeacherDAO();
            Teacher courseTeacher = teacherDAO.GetTeacherByTeacherNumber(courseInfo.getCourseTeacher().getTeacherNumber());
            courseInfo.setCourseTeacher(courseTeacher);
            }
            courseInfoDAO.AddCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo添加失败!"));
            return "error";
        }
    }

    /*查询CourseInfo信息*/
    public String QueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*计算总的页数和总的记录数*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*获取到总的页码数目*/
        totalPage = courseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    
    /*教师查询自己的授课课程*/
    public String TeacherQueryCourseInfo() { 
    	 ActionContext ctx = ActionContext.getContext();
    	 String teacherNumber = (String)ctx.getSession().get("username");
         List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(teacherNumber);
         
         ctx.put("courseInfoList",  courseInfoList); 
         return "teacher_query_view";
    }
    
    
    /*学生查询已经选择的课程*/
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
    
    /*查询CourseInfo信息*/
    public String StudentQueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*计算总的页数和总的记录数*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*获取到总的页码数目*/
        totalPage = courseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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
    
    
    /*前台查询CourseInfo信息*/
    public String FrontQueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*计算总的页数和总的记录数*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*获取到总的页码数目*/
        totalPage = courseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的CourseInfo信息*/
    public String ModifyCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键courseNumber获取CourseInfo对象*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);

        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("courseInfo",  courseInfo);
        return "modify_view";
    }

    /*查询要修改的CourseInfo信息*/
    public String FrontShowCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键courseNumber获取CourseInfo对象*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);

        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("courseInfo",  courseInfo);
        return "front_show_view";
    }

    /*更新修改CourseInfo信息*/
    public String ModifyCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            TeacherDAO teacherDAO = new TeacherDAO();
            Teacher courseTeacher = teacherDAO.GetTeacherByTeacherNumber(courseInfo.getCourseTeacher().getTeacherNumber());
            courseInfo.setCourseTeacher(courseTeacher);
            }
            courseInfoDAO.UpdateCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除CourseInfo信息*/
    public String DeleteCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            courseInfoDAO.DeleteCourseInfo(courseNumber);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo删除失败!"));
            return "error";
        }
    }

}
