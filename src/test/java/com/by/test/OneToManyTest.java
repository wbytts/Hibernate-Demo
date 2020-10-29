package com.by.test;

import com.by.domain.Customer;
import com.by.domain.LinkMan;
import com.by.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @author wangby
 * @version 0.0.1
 * @date 2020-10-29 14:47
 */
public class OneToManyTest {

    /**
     * 现在想要保存2个客户，3个联系人，并且建立好关系
     */
    @Test
    public void demo01() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 创建2个客户
        Customer customer1 = new Customer();
        customer1.setCust_name("张小敬");
        Customer customer2 = new Customer();
        customer2.setCust_name("李必");

        // 创建3个联系人
        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name("凤姐");
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name("如花");
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name("来福");

        // 设置关系（双向关联关系）
        linkMan1.setCustomer(customer1);
        linkMan2.setCustomer(customer1);
        linkMan3.setCustomer(customer2);

        customer1.getLinkMans().add(linkMan1);
        customer1.getLinkMans().add(linkMan2);
        customer2.getLinkMans().add(linkMan3);

        // 保存数据
        session.save(linkMan1);
        session.save(linkMan2);
        session.save(linkMan3);

        session.save(customer1);
        session.save(customer2);

        tx.commit();
    }

}
