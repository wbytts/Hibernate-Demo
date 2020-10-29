package com.by.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.by.domain.SysRole;
import com.by.domain.SysUser;
import com.by.util.HibernateUtil;

/**
 * 测试Hibernate多对多的映射
 */
public class ManyToManyTest {

    /**
     * 保存多条记录，即保存多个用户和角色
     */
    @Test
    public void demo01() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //创建2个用户
        SysUser user1 = new SysUser();
        user1.setUser_name("酱爆");
        SysUser user2 = new SysUser();
        user2.setUser_name("大师兄");

        //创建3个角色
        SysRole role1 = new SysRole();
        role1.setRole_name("研发部");
        SysRole role2 = new SysRole();
        role2.setRole_name("市场部");
        SysRole role3 = new SysRole();
        role3.setRole_name("公关部");

        //设置双向的关联关系
        user1.getRoles().add(role1);
        user1.getRoles().add(role2);
        user2.getRoles().add(role2);
        user2.getRoles().add(role3);

        // 执行下面这几句，可能报异常
        // 因为如果多对多建立了双向的关联关系，那么必须有一方放弃外键维护
        // 一般是让被动方去放弃外键的维护权
        // 可以认为是角色的一方，在角色的配置文件中加入 inserse="true"
        role1.getUsers().add(user1);
        role2.getUsers().add(user1);
        role2.getUsers().add(user2);
        role3.getUsers().add(user2);

        //执行保存操作：如果多对多建立了双向的关联关系，那么必须有一方放弃外键维护
        //那么通常让哪一方放弃呢？一般是让被动方去放弃外键的维护权
        session.save(user1);
        session.save(user2);
        session.save(role1);
        session.save(role2);
        session.save(role3);

        tx.commit();
    }

    /**
     * 多对多的操作：
     * 		只保存一边是否可以？不可以，也会报瞬时对象异常
     *
     * 	级联是有方向性的，现在我们要做的是保存用户，然后去级联保存角色。由于现在操作的主体对象是用户对象，所以就需要在用户的映射文件中进行配置。
     *
     * 	反过来也是可以的，不过注意要取消掉 inverse="true"，因为还要靠主体来维护外键。。。
     */
    @Test
    public void demo02() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //创建1个用户
        SysUser user1 = new SysUser();
        user1.setUser_name("酱爆");

        //创建1个角色
        SysRole role1 = new SysRole();
        role1.setRole_name("研发部");

        //设置双向的关联关系
        user1.getRoles().add(role1);
        role1.getUsers().add(user1);

        //只保存用户
        session.save(user1);
        //session.save(role1);

        tx.commit();
    }

    /**
     * 多对多的级联保存
     * 		保存角色，同时去级联保存用户。要在角色的映射配置文件中去配置
     * 		暂时这样设置，外键得交给它来维护，在Role.hbm.xml中的set上去配置cascade="save-update"：
     * 			<set name="users" table="sys_user_role" cascade="save-update">
     *
     * 		User.hbm.xml：
     * 			<set name="roles" table="sys_user_role" cascade="save-update" inverse="true">
     *
     */
    @Test
    public void demo04() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //创建1个用户
        SysUser user1 = new SysUser();
        user1.setUser_name("阿星");

        //创建1个角色
        SysRole role1 = new SysRole();
        role1.setRole_name("公关部");

        //设置双向的关联关系
        user1.getRoles().add(role1);
        role1.getUsers().add(user1);

        //只保存角色
        session.save(role1);

        tx.commit();
    }

    /**
     * 先运行demo01方法，让数据库表中有些数据。
     *
     * 多对多的级联删除
     * 		删除用户，同时去级联删除角色。要在用户的映射配置文件中去配置
     * 		在User.hbm.xml中的set上去配置cascade="delete"：
     * 			<set name="roles" table="sys_user_role" cascade="save-update,delete">
     *
     * 		Role.hbm.xml：
     * 			<set name="roles" table="sys_user_role" cascade="save-update" inverse="true">
     *
     */
    @Test
    public void demo05() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //查询1号用户
        SysUser user = session.get(SysUser.class, 1l);
        session.delete(user);//<property name="hibernate.hbm2ddl.auto">update</property>

        tx.commit();
    }

    /**
     * 先运行demo01方法，让数据库表中有些数据。
     *
     * 多对多的级联删除
     * 		删除角色，同时去级联删除用户。要在角色的映射配置文件中去配置
     * 		在Role.hbm.xml中的set上去配置cascade="delete"：
     * 			<set name="users" table="sys_user_role" cascade="save-update,delete" inverse="true">
     *
     * 		User.hbm.xml：
     * 			<set name="roles" table="sys_user_role" cascade="save-update">
     *
     */
    @Test
    public void demo06() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //查询1号角色
        SysRole role = session.get(SysRole.class, 1l);
        session.delete(role);//<property name="hibernate.hbm2ddl.auto">update</property>

        tx.commit();
    }

    /**
     * 先运行demo01方法，让数据库表中初始化些数据。
     *
     * 给用户选择角色
     */
    @Test
    public void demo07() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //给1号用户多选择一个3号角色
        //查询1号用户
        SysUser user = session.get(SysUser.class, 1l);

        //查询3号角色
        SysRole role = session.get(SysRole.class, 3l);

        user.getRoles().add(role);//给1号用户多选择一个3号角色

        //<property name="hibernate.hbm2ddl.auto">update</property>

        tx.commit();
    }

    /**
     * 给用户改选角色
     */
    @Test
    public void demo08() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //给2号用户将原有的3号角色改为1号角色
        //查询2号用户
        SysUser user = session.get(SysUser.class, 2l);

        //查询1、3号角色
        SysRole role1 = session.get(SysRole.class, 1l);
        SysRole role3 = session.get(SysRole.class, 3l);

        user.getRoles().remove(role3);
        user.getRoles().add(role1);

        //<property name="hibernate.hbm2ddl.auto">update</property>

        tx.commit();
    }

    /**
     * 给用户删除角色
     */
    @Test
    public void demo09() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //给2号用户删除1号角色
        //查询2号用户
        SysUser user = session.get(SysUser.class, 2l);

        //查询1号角色
        SysRole role = session.get(SysRole.class, 1l);

        user.getRoles().remove(role);

        //<property name="hibernate.hbm2ddl.auto">update</property>

        tx.commit();
    }
}
