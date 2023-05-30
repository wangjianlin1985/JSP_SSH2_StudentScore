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

    /*�������Ҫ��ѯ������: ѧ������*/
    private Student studentNumber;
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }
    public Student getStudentNumber() {
        return this.studentNumber;
    }

    /*�������Ҫ��ѯ������: �γ̶���*/
    private CourseInfo courseNumber;
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }
    public CourseInfo getCourseNumber() {
        return this.courseNumber;
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

    private int selectId;
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
    public int getSelectId() {
        return selectId;
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
    StudentSelectCourseInfoDAO studentSelectCourseInfoDAO = new StudentSelectCourseInfoDAO();

    /*��������StudentSelectCourseInfo����*/
    private StudentSelectCourseInfo studentSelectCourseInfo;
    public void setStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
        this.studentSelectCourseInfo = studentSelectCourseInfo;
    }
    public StudentSelectCourseInfo getStudentSelectCourseInfo() {
        return this.studentSelectCourseInfo;
    }

    /*��ת�����StudentSelectCourseInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�CourseInfo��Ϣ*/
        CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "add_view";
    }

    /*���StudentSelectCourseInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo���ʧ��!"));
            return "error";
        }
    }
    
    
    /*��ѯѡ�������ſγ̵�ѧ����Ϣ*/
    public String StudentListOfCourse() { 
    	ActionContext ctx = ActionContext.getContext();
        try {  
        	ArrayList<Student> studentList = new ArrayList<Student>();
            //��ѯ���ſγ̵�ѡ����Ϣ�����а���ѧ��
            ArrayList<StudentSelectCourseInfo> selectCourseList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            for(StudentSelectCourseInfo studentSelectCourseInfo:selectCourseList) {
            	studentList.add(studentSelectCourseInfo.getStudentNumber());
            }
            ctx.put("studentList",  studentList);
            return "course_studentlist_view";
            
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("���ݿ�ʧ��!"));
            return "error";
        }
    }

    
    /*ѧ��ѡ��*/
    public String AddSelectCourse() {
    	ActionContext ctx = ActionContext.getContext();
        try {
        
            StudentDAO studentDAO = new StudentDAO();
            Student studentNumber = studentDAO.GetStudentByStudentNumber((String)ctx.getSession().get("username"));
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            
     
            CourseInfoDAO courseInfoDAO = new CourseInfoDAO();
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
          
            
            //��ѯ��ѧ�����ſγ��Ƿ��Ѿ�ѡ��
            ArrayList<StudentSelectCourseInfo> selectCourseList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentSelectCourseInfo.getStudentNumber(), studentSelectCourseInfo.getCourseNumber(),1);
            if(selectCourseList.size() > 0 ) {
            	 ctx.put("error",  java.net.URLEncoder.encode("���ſγ���ѡ��"));
                 return "error";	
            } 
            studentSelectCourseInfoDAO.AddStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ѡ�γɹ���"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo���ʧ��!"));
            return "error";
        }
    }
    
    /*��ѯStudentSelectCourseInfo��Ϣ*/
    public String QueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*ǰ̨��ѯStudentSelectCourseInfo��Ϣ*/
    public String FrontQueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�StudentSelectCourseInfo��Ϣ*/
    public String ModifyStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectId��ȡStudentSelectCourseInfo����*/
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

    /*��ѯҪ�޸ĵ�StudentSelectCourseInfo��Ϣ*/
    public String FrontShowStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectId��ȡStudentSelectCourseInfo����*/
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

    /*�����޸�StudentSelectCourseInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��StudentSelectCourseInfo��Ϣ*/
    public String DeleteStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentSelectCourseInfoDAO.DeleteStudentSelectCourseInfo(selectId);
            ctx.put("message",  java.net.URLEncoder.encode("��ѡ�ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfoɾ��ʧ��!"));
            return "error";
        }
    }
    
    

}
