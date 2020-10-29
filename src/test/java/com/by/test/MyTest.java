package com.by.test;

import com.by.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * @author wangby
 * @version 0.0.1
 */
public class MyTest {

    /**
     * 保存数据
     */
    @Test public void test01() {
        // 创建Configuration对象
        // 如果核心配置文件不叫 hibernate.cfg.xml，则需要在configure里指明
        Configuration configuration = new Configuration().configure();
        // 获取SessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        // 获取Session对象
        Session session = sessionFactory.openSession();
        // 开启事务
        Transaction transaction = session.beginTransaction();

        User u = new User(null, "李珊珊", "123456");
        session.save(u); // 保存这个对象，Hibernate会自动生成SQL，将这个对象插入到数据库中

        // 提交事务
        transaction.commit();
        // 关闭会话
        session.close();
    }

    /**
     * 查询操作
     */
    @Test public void test02() {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        /*
            get 方法有四种重载：
                public Object get(Class clazz, Serializable id);
                public Object get(Class clazz, Serializable id, LockOptions lockOptions);
                public Object get(String entityName, Serializable id);
                public Object get(String entityName, Serializable id, LockOptions lockOptions);
         */
        User user = (User)session.get(User.class, 3L);
        // 或者
        // User user = (User) session.get("com.by.domain.User", 1L);
        System.out.println(user);

        // 由于Session缓存，第二次查询相同id的数据时，不会再发送SQL语句了
        User user2 = (User)session.get(User.class, 3L);

        // load 方法与get不同，是懒加载的，只有在被使用到时才会发送SQL语句
        // 返回的是一个代理的对象
        User user3 = (User)session.load(User.class, 5L);
        System.out.println("###User user3 = (User)session.load(User.class, 5L);");
        System.out.println(user3);

        transaction.commit();
        session.close();
    }

    /**
     * 删除操作
     */
    @Test public void test03() {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User user = (User)session.get(User.class, 1L);
        session.delete(user);

        transaction.commit();
        session.close();
    }

    /**
     * 测试修改操作
     */
    @Test public void test04() {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User user = (User)session.get(User.class, 12345L);
        user.setName("update_username");
        session.update(user);

        transaction.commit();
        session.close();
    }

}
