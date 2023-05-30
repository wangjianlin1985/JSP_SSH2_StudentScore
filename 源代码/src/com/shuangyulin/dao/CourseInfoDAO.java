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

    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
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

    /*查询CourseInfo信息*/
    public ArrayList<CourseInfo> QueryCourseInfoInfo(String courseNumber,String courseName,Teacher courseTeacher,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From CourseInfo courseInfo where 1=1";
            if(!courseNumber.equals("")) hql = hql + " and courseInfo.courseNumber like '%" + courseNumber + "%'";
            if(!courseName.equals("")) hql = hql + " and courseInfo.courseName like '%" + courseName + "%'";
            if(null != courseTeacher && !courseTeacher.getTeacherNumber().equals("")) hql += " and courseInfo.courseTeacher.teacherNumber='" + courseTeacher.getTeacherNumber() + "'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List courseInfoList = q.list();
            return (ArrayList<CourseInfo>) courseInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    /*教师查询自己的授课课程*/
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

    /*函数功能：查询所有的CourseInfo记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
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

    /*更新CourseInfo信息*/
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

    /*删除CourseInfo信息*/
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
