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

    /**
     * 级联保存或更新操作
     * 		保存客户，然后去级联联系人（现在操作的主体对象是客户对象，那么就需要在客户的映射文件中进行配置）
     * 		<set name="linkMans" cascade="save-upadte">
     */
    @Test
    public void demo03() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //创建1个客户
        Customer customer = new Customer();
        customer.setCust_name("崔器");

        //创建1个联系人
        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("小强");

        //设置关系（双向关联关系）
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        //只保存一边（只保存客户）是否可以？现在就可以
        session.save(customer);

        tx.commit();
    }

    /**
     * 级联保存或更新操作
     * 		保存联系人，然后去级联客户（现在操作的主体对象是联系人对象，那么就需要在联系人的映射文件中进行配置）
     * 		<many-to-one cascade="save-update" name="customer" class="com.meimeixia.hibernate.domain.Customer" column="lkm_cust_id"></many-to-one>
     */
    @Test
    public void demo04() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //创建1个客户
        Customer customer = new Customer();
        customer.setCust_name("阿昀");

        //创建1个联系人
        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("小小");

        //设置关系（双向关联关系，也即对象导航）
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        //只保存一边（只保存联系人）是否可以？现在就可以
        session.save(linkMan);

        tx.commit();
    }

    /**
     * 级联删除
     * 		删除客户，然后去级联删除联系人，
     * 		同样，要看现在删除的主体对象是客户对象，那么就需要在客户的映射文件中进行配置：<set name="linkMans" cascade="delete">
     *
     */
    @Test
    public void demo06() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //删除客户，同时删除联系人
        Customer customer = session.get(Customer.class, 1l);
        session.delete(customer);

        tx.commit();
    }


    /**
     * 演示delete-orphan
     */
    @Test
    public void tesDeleteOrphan() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Customer customer = session.get(Customer.class, 2l);
        LinkMan linkMan = session.get(LinkMan.class, 3l);

        customer.getLinkMans().remove(linkMan);//从集合里面移除掉一个联系人，能实现这个效果吗？不能

        tx.commit();
    }

    /**
     * 测试对象的导航
     * 		前提：一对多的双方都设置了cascade="save-update"
     */
    @Test
    public void demo05() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //创建1个客户
        Customer customer = new Customer();
        customer.setCust_name("阿昀");

        //创建3个联系人
        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name("常威");
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name("如花");
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name("来福");

        //现在不设置双向的关联了
        linkMan1.setCustomer(customer);
        customer.getLinkMans().add(linkMan2);
        customer.getLinkMans().add(linkMan3);


        //双方都设置了cascade，那么执行下面的操作，会发送几条insert语句？
        //会发送4条insert语句
        //session.save(linkMan1);

        //双方都设置了cascade，那么执行下面的操作，会发送几条insert语句？
        //会发送3条insert语句
        //session.save(customer);

        //双方都设置了cascade，那么执行下面的操作，会发送几条insert语句？
        //会发送1条insert语句
        session.save(linkMan2);

        tx.commit();
    }

    /**
     * 区分cascade和inverse的区别？
     * 		cascade：控制这个对象的关联对象
     * 		inverse：控制一的一方能不能去维护这个外键，管不管这个外键？
     * 	在客户中设置cascade为save-update，inverse 为 true，联系人什么都不做
     */
    @Test
    public void demo09() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("阿水");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("包租婆");

        //设置单向关联关系
        customer.getLinkMans().add(linkMan);

        session.save(customer);//此时，会有什么效果？客户会插入到数据库，联系人也会插入到数据库，但是外键为null。

        tx.commit();
    }




}
