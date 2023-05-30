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
import com.shuangyulin.dao.StudentSelectCourseInfoDAO;
import com.shuangyulin.domain.StudentSelectCourseInfo;
import com.shuangyulin.dao.StudentDAO;
import com.shuangyulin.domain.Student;
import com.shuangyulin.dao.CourseInfoDAO;
import com.shuangyulin.domain.CourseInfo;
import com.shuangyulin.test.TestUtil;

public class StudentSelectCourseInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 学生对象*/
    private Student studentNumber;
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }
    public Student getStudentNumber() {
        return this.studentNumber;
    }

    /*界面层需要查询的属性: 课程对象*/
    private CourseInfo courseNumber;
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }
    public CourseInfo getCourseNumber() {
        return this.courseNumber;
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

    private int selectId;
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
    public int getSelectId() {
        return selectId;
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
    StudentSelectCourseInfoDAO studentSelectCourseInfoDAO = new StudentSelectCourseInfoDAO();

    /*待操作的StudentSelectCourseInfo对象*/
    private StudentSelectCourseInfo studentSelectCourseInfo;
    public void setStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
        this.studentSelectCourseInfo = studentSelectCourseInfo;
    }
    public StudentSelectCourseInfo getStudentSelectCourseInfo() {
        return this.studentSelectCourseInfo;
    }

    /*跳转到添加StudentSelectCourseInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的CourseInfo信息*/
        CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "add_view";
    }

    /*添加StudentSelectCourseInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            StudentDAO studentDAO = new StudentDAO();
            Student studentNumber = studentDAO.GetStudentByStudentNumber(studentSelectCourseInfo.getStudentNumber().getStudentNumber());
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            }
            if(true) {
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
            }
            studentSelectCourseInfoDAO.AddStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo添加失败!"));
            return "error";
        }
    }
    
    
    /*查询选择了这门课程的学生信息*/
    public String StudentListOfCourse() { 
    	ActionContext ctx = ActionContext.getContext();
        try {  
        	ArrayList<Student> studentList = new ArrayList<Student>();
            //查询该门课程的选课信息，其中包括学号
            ArrayList<StudentSelectCourseInfo> selectCourseList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            for(StudentSelectCourseInfo studentSelectCourseInfo:selectCourseList) {
            	studentList.add(studentSelectCourseInfo.getStudentNumber());
            }
            ctx.put("studentList",  studentList);
            return "course_studentlist_view";
            
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("数据库失败!"));
            return "error";
        }
    }

    
    /*学生选课*/
    public String AddSelectCourse() {
    	ActionContext ctx = ActionContext.getContext();
        try {
        
            StudentDAO studentDAO = new StudentDAO();
            Student studentNumber = studentDAO.GetStudentByStudentNumber((String)ctx.getSession().get("username"));
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            
     
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
          
            
            //查询该学生该门课程是否已经选了
            ArrayList<StudentSelectCourseInfo> selectCourseList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentSelectCourseInfo.getStudentNumber(), studentSelectCourseInfo.getCourseNumber(),1);
            if(selectCourseList.size() > 0 ) {
            	 ctx.put("error",  java.net.URLEncoder.encode("该门课程已选！"));
                 return "error";	
            } 
            studentSelectCourseInfoDAO.AddStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("选课成功！"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo添加失败!"));
            return "error";
        }
    }
    
    /*查询StudentSelectCourseInfo信息*/
    public String QueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*计算总的页数和总的记录数*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*获取到总的页码数目*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentSelectCourseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentSelectCourseInfoList",  studentSelectCourseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("courseNumber", courseNumber);
        CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "query_view";
    }

    /*前台查询StudentSelectCourseInfo信息*/
    public String FrontQueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*计算总的页数和总的记录数*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*获取到总的页码数目*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentSelectCourseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentSelectCourseInfoList",  studentSelectCourseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("courseNumber", courseNumber);
        CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "front_query_view";
    }

    /*查询要修改的StudentSelectCourseInfo信息*/
    public String ModifyStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectId获取StudentSelectCourseInfo对象*/
        StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfoBySelectId(selectId);

        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("studentSelectCourseInfo",  studentSelectCourseInfo);
        return "modify_view";
    }

    /*查询要修改的StudentSelectCourseInfo信息*/
    public String FrontShowStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectId获取StudentSelectCourseInfo对象*/
        StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfoBySelectId(selectId);

        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("studentSelectCourseInfo",  studentSelectCourseInfo);
        return "front_show_view";
    }

    /*更新修改StudentSelectCourseInfo信息*/
    public String ModifyStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            StudentDAO studentDAO = new StudentDAO();
            Student studentNumber = studentDAO.GetStudentByStudentNumber(studentSelectCourseInfo.getStudentNumber().getStudentNumber());
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            }
            if(true) {
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
            }
            studentSelectCourseInfoDAO.UpdateStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除StudentSelectCourseInfo信息*/
    public String DeleteStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentSelectCourseInfoDAO.DeleteStudentSelectCourseInfo(selectId);
            ctx.put("message",  java.net.URLEncoder.encode("退选成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo删除失败!"));
            return "error";
        }
    }
    
    

}
