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

import com.shuangyulin.domain.Admin;
import com.shuangyulin.domain.Teacher;

public class TeacherDAO {

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

    
    /*����ҵ���߼�������Ϣ�ֶ�*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
    
    /*��֤�û���¼*/
	public boolean CheckLogin(Admin admin) { 
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			Teacher db_teacher = (Teacher)s.get(Teacher.class, admin.getUsername());
			if(db_teacher == null) { 
				this.errMessage = " �˺Ų����� ";
				System.out.print(this.errMessage);
				return false;
			} else if( !db_teacher.getTeacherPassword().equals(admin.getPassword())) {
				this.errMessage = " ���벻��ȷ! ";
				System.out.print(this.errMessage);
				return false;
			}
		} finally {
			HibernateUtil.closeSession();
		} 
		return true;
	}
	
	
    /*���ͼ����Ϣ*/
    public void AddTeacher(Teacher teacher) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(teacher);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*��ѯTeacher��Ϣ*/
    public ArrayList<Teacher> QueryTeacherInfo(String teacherNumber,String teacherName,String teacherBirthday,String teacherArriveDate,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Teacher teacher where 1=1";
            if(!teacherNumber.equals("")) hql = hql + " and teacher.teacherNumber like '%" + teacherNumber + "%'";
            if(!teacherName.equals("")) hql = hql + " and teacher.teacherName like '%" + teacherName + "%'";
            if(!teacherBirthday.equals("")) hql = hql + " and teacher.teacherBirthday like '%" + teacherBirthday + "%'";
            if(!teacherArriveDate.equals("")) hql = hql + " and teacher.teacherArriveDate like '%" + teacherArriveDate + "%'";
            Query q = s.createQuery(hql);
            /*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List teacherList = q.list();
            return (ArrayList<Teacher>) teacherList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�������ܣ���ѯ���е�Teacher��¼*/
    public ArrayList<Teacher> QueryAllTeacherInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Teacher";
            Query q = s.createQuery(hql);
            List teacherList = q.list();
            return (ArrayList<Teacher>) teacherList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    public void CalculateTotalPageAndRecordNumber(String teacherNumber,String teacherName,String teacherBirthday,String teacherArriveDate) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Teacher teacher where 1=1";
            if(!teacherNumber.equals("")) hql = hql + " and teacher.teacherNumber like '%" + teacherNumber + "%'";
            if(!teacherName.equals("")) hql = hql + " and teacher.teacherName like '%" + teacherName + "%'";
            if(!teacherBirthday.equals("")) hql = hql + " and teacher.teacherBirthday like '%" + teacherBirthday + "%'";
            if(!teacherArriveDate.equals("")) hql = hql + " and teacher.teacherArriveDate like '%" + teacherArriveDate + "%'";
            Query q = s.createQuery(hql);
            List teacherList = q.list();
            recordNumber = teacherList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����������ȡ����*/
    public Teacher GetTeacherByTeacherNumber(String teacherNumber) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Teacher teacher = (Teacher)s.get(Teacher.class, teacherNumber);
            return teacher;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*����Teacher��Ϣ*/
    public void UpdateTeacher(Teacher teacher) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(teacher);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*ɾ��Teacher��Ϣ*/
    public void DeleteTeacher (String teacherNumber) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object teacher = s.get(Teacher.class, teacherNumber);
            s.delete(teacher);
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
