package com.shuangyulin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mysql.jdbc.Statement;
import com.shuangyulin.utils.HibernateUtil;

import com.shuangyulin.domain.Teacher;
import com.shuangyulin.domain.CourseInfo;

public class CourseInfoDAO {

    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddCourseInfo(CourseInfo courseInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(courseInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*��ѯCourseInfo��Ϣ*/
    public ArrayList<CourseInfo> QueryCourseInfoInfo(String courseNumber,String courseName,Teacher courseTeacher,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseInfo courseInfo where 1=1";
            if(!courseNumber.equals("")) hql = hql + " and courseInfo.courseNumber like '%" + courseNumber + "%'";
            if(!courseName.equals("")) hql = hql + " and courseInfo.courseName like '%" + courseName + "%'";
            if(null != courseTeacher && !courseTeacher.getTeacherNumber().equals("")) hql += " and courseInfo.courseTeacher.teacherNumber='" + courseTeacher.getTeacherNumber() + "'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List courseInfoList = q.list();
            return (ArrayList<CourseInfo>) courseInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*��ʦ��ѯ�Լ����ڿογ�*/
    public List<CourseInfo> QueryCourseInfoInfo(String teacherNumber) {
    	 Session s = null; 
         try {
             s = HibernateUtil.getSession();
             String hql = "From CourseInfo courseInfo where 1=1"; 
             if(null != teacherNumber && !teacherNumber.equals("")) hql += " and courseInfo.courseTeacher.teacherNumber='" + teacherNumber + "'";
             Query q = s.createQuery(hql); 
             List courseInfoList = q.list();
             return (ArrayList<CourseInfo>) courseInfoList;
         } finally {
             HibernateUtil.closeSession();
         }
	}

    /*�������ܣ���ѯ���е�CourseInfo��¼*/
    public ArrayList<CourseInfo> QueryAllCourseInfoInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseInfo";
            Query q = s.createQuery(hql);
            List courseInfoList = q.list();
            return (ArrayList<CourseInfo>) courseInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String courseNumber,String courseName,Teacher courseTeacher) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseInfo courseInfo where 1=1";
            if(!courseNumber.equals("")) hql = hql + " and courseInfo.courseNumber like '%" + courseNumber + "%'";
            if(!courseName.equals("")) hql = hql + " and courseInfo.courseName like '%" + courseName + "%'";
            if(null != courseTeacher && !courseTeacher.getTeacherNumber().equals("")) hql += " and courseInfo.courseTeacher.teacherNumber='" + courseTeacher.getTeacherNumber() + "'";
            Query q = s.createQuery(hql);
            List courseInfoList = q.list();
            recordNumber = courseInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public CourseInfo GetCourseInfoByCourseNumber(String courseNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            CourseInfo courseInfo = (CourseInfo)s.get(CourseInfo.class, courseNumber);
            return courseInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����CourseInfo��Ϣ*/
    public void UpdateCourseInfo(CourseInfo courseInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(courseInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��CourseInfo��Ϣ*/
    public void DeleteCourseInfo (String courseNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object courseInfo = s.get(CourseInfo.class, courseNumber);
            s.delete(courseInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
	

}
