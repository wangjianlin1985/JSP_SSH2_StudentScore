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
import com.shuangyulin.dao.TeacherDAO;
import com.shuangyulin.domain.Teacher;
import com.shuangyulin.test.TestUtil;

public class TeacherAction extends ActionSupport {

/*ͼƬ�ֶ�teacherPhoto��������*/
	 private File teacherPhotoFile;
	 private String teacherPhotoFileFileName;
	 private String teacherPhotoFileContentType;
	 public File getTeacherPhotoFile() {
		return teacherPhotoFile;
	}
	public void setTeacherPhotoFile(File teacherPhotoFile) {
		this.teacherPhotoFile = teacherPhotoFile;
	}
	public String getTeacherPhotoFileFileName() {
		return teacherPhotoFileFileName;
	}
	public void setTeacherPhotoFileFileName(String teacherPhotoFileFileName) {
		this.teacherPhotoFileFileName = teacherPhotoFileFileName;
	}
	public String getTeacherPhotoFileContentType() {
		return teacherPhotoFileContentType;
	}
	public void setTeacherPhotoFileContentType(String teacherPhotoFileContentType) {
		this.teacherPhotoFileContentType = teacherPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ��ʦ���*/
    private String teacherNumber;
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
    public String getTeacherNumber() {
        return this.teacherNumber;
    }

    /*�������Ҫ��ѯ������: ��ʦ����*/
    private String teacherName;
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTeacherName() {
        return this.teacherName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String teacherBirthday;
    public void setTeacherBirthday(String teacherBirthday) {
        this.teacherBirthday = teacherBirthday;
    }
    public String getTeacherBirthday() {
        return this.teacherBirthday;
    }

    /*�������Ҫ��ѯ������: ��ְ����*/
    private String teacherArriveDate;
    public void setTeacherArriveDate(String teacherArriveDate) {
        this.teacherArriveDate = teacherArriveDate;
    }
    public String getTeacherArriveDate() {
        return this.teacherArriveDate;
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
    TeacherDAO teacherDAO = new TeacherDAO();

    /*��������Teacher����*/
    private Teacher teacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
        return this.teacher;
    }

    /*��ת�����Teacher��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Teacher��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTeacher() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��ʦ����Ƿ��Ѿ�����*/
        String teacherNumber = teacher.getTeacherNumber();
        Teacher db_teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);
        if(null != db_teacher) {
            ctx.put("error",  java.net.URLEncoder.encode("�ý�ʦ����Ѿ�����!"));
            return "error";
        }
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String teacherPhotoFileName = ""; 
       	 	if(teacherPhotoFile != null) {
       	 		InputStream is = new FileInputStream(teacherPhotoFile);
       			String fileContentType = this.getTeacherPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
       				return "error";
       			}
       			File file = new File(path, teacherPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(teacherPhotoFile != null)
            	teacher.setTeacherPhoto("upload/" + teacherPhotoFileName);
            else
            	teacher.setTeacherPhoto("upload/NoImage.jpg");
            teacherDAO.AddTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTeacher��Ϣ*/
    public String QueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(teacherNumber == null) teacherNumber = "";
        if(teacherName == null) teacherName = "";
        if(teacherBirthday == null) teacherBirthday = "";
        if(teacherArriveDate == null) teacherArriveDate = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(teacherNumber, teacherName, teacherBirthday, teacherArriveDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, teacherName, teacherBirthday, teacherArriveDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = teacherDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherNumber", teacherNumber);
        ctx.put("teacherName", teacherName);
        ctx.put("teacherBirthday", teacherBirthday);
        ctx.put("teacherArriveDate", teacherArriveDate);
        return "query_view";
    }

    /*ǰ̨��ѯTeacher��Ϣ*/
    public String FrontQueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(teacherNumber == null) teacherNumber = "";
        if(teacherName == null) teacherName = "";
        if(teacherBirthday == null) teacherBirthday = "";
        if(teacherArriveDate == null) teacherArriveDate = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(teacherNumber, teacherName, teacherBirthday, teacherArriveDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, teacherName, teacherBirthday, teacherArriveDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = teacherDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherNumber", teacherNumber);
        ctx.put("teacherName", teacherName);
        ctx.put("teacherBirthday", teacherBirthday);
        ctx.put("teacherArriveDate", teacherArriveDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String ModifyTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teacherNumber��ȡTeacher����*/
        Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);

        ctx.put("teacher",  teacher);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String FrontShowTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teacherNumber��ȡTeacher����*/
        Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);

        ctx.put("teacher",  teacher);
        return "front_show_view";
    }

    /*�����޸�Teacher��Ϣ*/
    public String ModifyTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����ͼƬ�ϴ�*/
            String teacherPhotoFileName = ""; 
       	 	if(teacherPhotoFile != null) {
       	 		InputStream is = new FileInputStream(teacherPhotoFile);
       			String fileContentType = this.getTeacherPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				teacherPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
       				return "error";
       			}
       			File file = new File(path, teacherPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
            teacher.setTeacherPhoto("upload/" + teacherPhotoFileName);
       	 	}
            teacherDAO.UpdateTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Teacher��Ϣ*/
    public String DeleteTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teacherDAO.DeleteTeacher(teacherNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Teacherɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacherɾ��ʧ��!"));
            return "error";
        }
    }

}
