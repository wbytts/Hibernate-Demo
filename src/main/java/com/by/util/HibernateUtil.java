package com.by.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author wangby
 * @version 0.0.1
 * @date 2020-10-29 10:55
 */
public class HibernateUtil {
    public static final Configuration cfg;

    public static final SessionFactory sf; //一个项目只会创建一个SessionFactory对象

    static {
        cfg = new Configuration().configure();
        sf = cfg.buildSessionFactory();
    }

    /**
     * 获取 Session 对象
     * @return
     */
    public static Session openSession() {
        // return sf.openSession();
        return sf.getCurrentSession();
    }

    /**
     * // 若配置了 <property name="hibernate.current_session_context_class">thread</property> 使用
     * 这个 Session 不用关闭。一关闭反而会报错（Session已经关闭了）。因为当你的线程一结束，它就会自动关闭了。
     * @return
     */
    public static Session getCurrentSession() {
        return sf.getCurrentSession();
    }
}
