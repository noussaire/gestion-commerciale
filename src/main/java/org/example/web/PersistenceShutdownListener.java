package org.example.web;

import org.example.dao.JPAUtil;
import org.example.util.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PersistenceShutdownListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.shutdown();
        HibernateUtil.shutdown();
    }
}
