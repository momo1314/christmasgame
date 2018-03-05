package org.redrock.christmas.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.Reader;

public class DatabaseUtil {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sqlSessionFactory.openSession()默认返回的sqlSession无非执行 增删改操作 预计
     * @return
     */
    public static SqlSession getCachedSqlsession() {
        return sqlSessionFactory.openSession();
    }


    public static SqlSession getSqlsession(){
        return sqlSessionFactory.openSession(true);
    }

    public static void main(String[] args) {

    }
}
