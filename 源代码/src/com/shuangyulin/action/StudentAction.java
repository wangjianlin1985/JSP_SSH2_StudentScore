package com.shuangyulin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.StudentDAO;
import com.shuangyulin.domain.Student;
import com.shuangyulin.dao.ClassInfoDAO;
import com.shuangyulin.domain.ClassInfo;
import com.shuangyulin.test.TestUtil;

public class StudentAction extends ActionSupport {

/*ͼƬ�ֶ�studentPhoto��������*/
	 private File studentPhotoFile;
	 private String studentPhotoFileFileName;
	 private String studentPhotoFileContentType;
	 public File getStudentPhotoFile() {
		return studentPhotoFile;
	}
	public void setStudentPhotoFile(File studentPhotoFile) {
		this.studentPhotoFile = studentPhotoFile;
	}
	public String getStudentPhotoFileFileName() {
		return studentPhotoFileFileName;
	}
	public void setStudentPhotoFileFileName(String studentPhotoFileFileName) {
		this.studentPhotoFileFileName = studentPhotoFileFileName;
	}
	public String getStudentPhotoFileContentType() {
		return studentPhotoFileContentType;
	}
	public void setStudentPhotoFileContentType(String studentPhotoFileContentType) {
		this.studentPhotoFileContentType = studentPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ѧ��*/
    private String studentNumber;
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    public String getStudentNumber() {
        return this.studentNumber;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String studentName;
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentName() {
        return this.studentName;
    }

    /*�������Ҫ��ѯ������: ���ڰ༶*/
    private ClassInfo studentClassNumber;
    public void setStudentClassNumber(ClassInfo studentClassNumber) {
        this.studentClassNumber = studentClassNumber;
    }
    public ClassInfo getStudentClassNumber() {
        return this.studentClassNumber;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String studentBirthday;
    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday;
    }
    public String getStudentBirthday() {
        return this.studentBirthday;
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
    StudentDAO studentDAO = new StudentDAO();

    /*��������Student����*/
    private Student student;
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return this.student;
    }

    /*��ת�����Student��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ClassInfo��Ϣ*/
        ClassInfoDAO classInfoDAO = new ClassInfoDAO();
        List<ClassInfo> classInfoList = classInfoDAO.QueryAllClassInfoInfo();
        ctx.put("classInfoList", classInfoList);
        return "add_view";
    }

    /*���Student��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddStudent() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤ѧ���Ƿ��Ѿ�����*/
        String studentNumber = student.getStudentNumber();
        Student db_student = studentDAO.GetStudentByStudentNumber(studentNumber);
        if(null != db_student) {
            ctx.put("error",  java.net.URLEncoder.encode("��ѧ���Ѿ�����!"));
            return "error";
        }
        try {
            if(true) {
            ClassInfoDAO classInfoDAO = new ClassInfoDAO();
            ClassInfo studentClassNumber = classInfoDAO.GetClassInfoByClassNumber(student.getStudentClassNumber().getClassNumber());
            student.setStudentClassNumber(studentClassNumber);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String studentPhotoFileName = ""; 
       	 	if(studentPhotoFile != null) {
       	 		InputStream is = new FileInputStream(studentPhotoFile);
       			String fileContentType = this.getStudentPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
       				return "error";
       			}
       			File file = new File(path, studentPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(studentPhotoFile != null)
            	student.setStudentPhoto("upload/" + studentPhotoFileName);
            else
            	student.setStudentPhoto("upload/NoImage.jpg");
            studentDAO.AddStudent(student);
            ctx.put("message",  java.net.URLEncoder.encode("Student��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Student���ʧ��!"));
            return "error";
        }
    }

    /*��ѯStudent��Ϣ*/
    public String QueryStudent() {
        if(currentPage == 0) currentPage = 1;
        if(studentNumber == null) studentNumber = "";
        if(studentName == null) studentName = "";
        if(studentBirthday == null) studentBirthday = "";
        List<Student> studentList = studentDAO.QueryStudentInfo(studentNumber, studentName, studentClassNumber, studentBirthday, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentDAO.CalculateTotalPageAndRecordNumber(studentNumber, studentName, studentClassNumber, studentBirthday);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = studentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentList",  studentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        ctx.put("studentName", studentName);
        ctx.put("studentClassNumber", studentClassNumber);
        ClassInfoDAO classInfoDAO = new ClassInfoDAO();
        List<ClassInfo> classInfoList = classInfoDAO.QueryAllClassInfoInfo();
        ctx.put("classInfoList", classInfoList);
        ctx.put("studentBirthday", studentBirthday);
        return "query_view";
    }

    /*ǰ̨��ѯStudent��Ϣ*/
    public String FrontQueryStudent() {
        if(currentPage == 0) currentPage = 1;
        if(studentNumber == null) studentNumber = "";
        if(studentName == null) studentName = "";
        if(studentBirthday == null) studentBirthday = "";
        List<Student> studentList = studentDAO.QueryStudentInfo(studentNumber, studentName, studentClassNumber, studentBirthday, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentDAO.CalculateTotalPageAndRecordNumber(studentNumber, studentName, studentClassNumber, studentBirthday);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = studentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentList",  studentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        ctx.put("studentName", studentName);
        ctx.put("studentClassNumber", studentClassNumber);
        ClassInfoDAO classInfoDAO = new ClassInfoDAO();
        List<ClassInfo> classInfoList = classInfoDAO.QueryAllClassInfoInfo();
        ctx.put("classInfoList", classInfoList);
        ctx.put("studentBirthday", studentBirthday);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Student��Ϣ*/
    public String ModifyStudentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������studentNumber��ȡStudent����*/
        Student student = studentDAO.GetStudentByStudentNumber(studentNumber);

        ClassInfoDAO classInfoDAO = new ClassInfoDAO();
        List<ClassInfo> classInfoList = classInfoDAO.QueryAllClassInfoInfo();
        ctx.put("classInfoList", classInfoList);
        ctx.put("student",  student);
        return "modify_view";
    }

    
    
   public String ModifySelfInfoView() {
	   ActionContext ctx = ActionContext.getContext();
       /*��������studentNumber��ȡStudent����*/
       Student student = studentDAO.GetStudentByStudentNumber((String)ctx.getSession().get("username"));

       ClassInfoDAO classInfoDAO = new ClassInfoDAO();
       List<ClassInfo> classInfoList = classInfoDAO.QueryAllClassInfoInfo();
       ctx.put("classInfoList", classInfoList);
       ctx.put("student",  student);
       return "self_modify_view";
   }
    
    
    
    /*��ѯҪ�޸ĵ�Student��Ϣ*/
    public String FrontShowStudentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������studentNumber��ȡStudent����*/
        Student student = studentDAO.GetStudentByStudentNumber(studentNumber);

        ClassInfoDAO classInfoDAO = new ClassInfoDAO();
        List<ClassInfo> classInfoList = classInfoDAO.QueryAllClassInfoInfo();
        ctx.put("classInfoList", classInfoList);
        ctx.put("student",  student);
        return "front_show_view";
    }

    /*�����޸�Student��Ϣ*/
    public String ModifyStudent() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            ClassInfoDAO classInfoDAO = new ClassInfoDAO();
            ClassInfo studentClassNumber = classInfoDAO.GetClassInfoByClassNumber(student.getStudentClassNumber().getClassNumber());
            student.setStudentClassNumber(studentClassNumber);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String studentPhotoFileName = ""; 
       	 	if(studentPhotoFile != null) {
       	 		InputStream is = new FileInputStream(studentPhotoFile);
       			String fileContentType = this.getStudentPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				studentPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
       				return "error";
       			}
       			File file = new File(path, studentPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
            student.setStudentPhoto("upload/" + studentPhotoFileName);
       	 	}
            studentDAO.UpdateStudent(student);
            ctx.put("message",  java.net.URLEncoder.encode("Student��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Student��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Student��Ϣ*/
    public String DeleteStudent() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentDAO.DeleteStudent(studentNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Studentɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Studentɾ��ʧ��!"));
            return "error";
        }
    }

}
