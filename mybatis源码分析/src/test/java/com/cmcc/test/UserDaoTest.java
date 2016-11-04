package com.cmcc.test;

import java.io.IOException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.cmcc.dao.UserDao;
import com.cmcc.entity.User;

public class UserDaoTest {
    
    @Test
    public void findUserById() {
        SqlSession sqlSession = getSessionFactory().openSession();
        UserDao userMapper = sqlSession.getMapper(UserDao.class);
        User user = userMapper.findUserById(1);
        Assert.assertNotNull("没找到数据", user);
    }

    // Mybatis 通过SqlSessionFactory获取SqlSession, 然后才能通过SqlSession与数据库进行交互
    private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sessionFactory = null;
        String resource = "configuration.xml";
        try {
            /*
             * 1、org.apache.ibatis.io.Resources.getResourceAsReader(String resource)
             *   作用：一般从项目的classpath中获取resource文件的句柄Reader，从而可以通过该句柄来操作该resource文件
             *   原理：Resources类通过加载该类的ClassLoader的getResourceAsStream(String resource)方法来获取该resource文件的句柄；
             *        而ClassLoader.getResourceAsStream(String resource)方法的原理又是通过首先从父ClassLoader来加载，
             *        如果不成功，那么再从BootStrapClassLoader来加载该resource。如果都不成功，那么返回null
             *        
             * 2、再说下new SqlSessionFactoryBuilder().build(Reader)方法：
             *   作用：通过已经获取的配置文件的句柄Reader来生成SqlSessionFactory对象
             *   总结：mybatis就是通过XmlConfigBuilder这个类来解析mybatis的配置文件configuration.xml，最终生成一个Configuration对象，该对象是一个包含这个xml配置文件中的所有节点信息的对象
             *   过程：
             *      通过
                        public SqlSessionFactory build(Reader reader) {
                            return build(reader, null, null);
                        }
                                                       间接调用
                        public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
                            XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
                            return build(parser.parse());
                            ......
                        }
                                                       其中的方法"parser.parse()"方法的作用是：将configuration.xml配置文件解析出来，生成一个Configuration configuration对象。
                                                                  具体的，就是将xml配置文件中的根节点<configuration>以及下面的10个子节点<properties><typeAliases><plugins><mappers>
                        <objectFactory><objectWrapperFactory><settings><environments><databaseIdProvider><typeHandlers>解析
                   
                                                       然后，调用"build(parser.parse())"方法，即：
                        public SqlSessionFactory build(Configuration config) {
                            return new DefaultSqlSessionFactory(config);
                        }
                                                                  使用DefaultSqlSessionFactory类和configuration参数来生成我们需要的SqlSessionFactory对象
                                               
                                                                                    
             */
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
