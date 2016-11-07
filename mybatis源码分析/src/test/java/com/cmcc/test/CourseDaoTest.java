package com.cmcc.test;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.cmcc.dao.CourseDao;
import com.cmcc.entity.Course;

public class CourseDaoTest {
    
    @Test
    public void findCourseByIdTest() {
        String mybatisConf = "mybatis-conf.xml";
        
        try {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(mybatisConf));
            SqlSession sqlSession = sqlSessionFactory.openSession();
            CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
            
            Course course = courseDao.findCourseById(2);
            System.out.println("course = " + course);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
