package com.by.test;

import com.by.domain.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * @author wangby
 * @version 0.0.1
 * @date 2020-10-29 10:28
 */
public class HibernateDemo01 {

    /**
     * 保存用户的案例
     */
    @Test public void test01() {
        //1. 加载Hibernate的核心配置文件（Configuration类主要是用于加载Hibernate的核心配置文件）
        Configuration configuration = new Configuration().configure();
        // Configuration configuration = new Configuration() // 主要加载的是src目录下的hibernate.properties
        //如果在Hibernate的核心配置文件没有设置加载哪个映射文件，则可手动加载映射文件
        //configuration.addResource("com/meimeixia/hibernate/demo01/Customer.hbm.xml");
        //或者可以向下面这样，这种方式它会直接在实体类所在包下查找规范的映射配置文件
        //configuration.addClass(Customer.class);

        //2. 创建SessionFactory对象，类似于JDBC中的连接池
        // SessionFactory 负责初始化Hibernate，它充当数据存储源的代理，并负责创建Session对象
        // SessionFactory 并不是轻量级的，一般来说，一个项目只有一个。（所以不要频繁创建关闭它）
        // 当需要操作多个数据源时，可以为每个数据源指定一个 SessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        //3. 通过SessionFactory获取到Session对象，类似于JDBC中的Connection
        // 通过 session 可以进行各种操作
        Session session = sessionFactory.openSession();

        //4. 手动开启事务，（最好是手动开启事务）
        Transaction transaction = session.beginTransaction();

        //5. 编写代码
        Customer customer = new Customer();
        customer.setCust_name("张小敬aaa");

        session.save(customer); //保存一个用户

        //6. 事务提交
        transaction.commit();

        //7. 释放资源
        session.close();
        sessionFactory.close();
    }

}
