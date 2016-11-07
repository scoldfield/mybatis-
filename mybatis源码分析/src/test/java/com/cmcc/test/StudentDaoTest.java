package com.cmcc.test;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.cmcc.dao.StudentDao;
import com.cmcc.entity.Student;

public class StudentDaoTest {
    
    @Test
    public void findStudentById() {
        String mybatisConf = "mybatis-conf.xml";
        try {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(mybatisConf));
            SqlSession sqlSession = sqlSessionFactory.openSession();
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            
            Student student = studentDao.findStudentById("20140101");
            System.out.println("student = " + student);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
