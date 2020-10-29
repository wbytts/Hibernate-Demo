package com.by.test;

import com.by.domain.Customer;
import com.by.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;

/**
 * @author wangby
 * @version 0.0.1
 * @date 2020-10-29 10:56
 */
public class HibernateDemo02 {
    @Test
    public void demo01() {
        Session session = HibernateUtil.openSession(); // 开启Session
        Transaction tx = session.beginTransaction();  // 开始事务

        Customer customer = new Customer();
        customer.setCust_name("老王");

        Serializable id = session.save(customer);  // save 之后会返回 id
        System.out.println(id);

        tx.commit(); // 提交事务
        session.close(); // 关闭 Session
        // 注意：SessionFactory不要关闭，它开启就别关了，除非说你的项目结束了，否则，我们就不关闭它
    }
}
