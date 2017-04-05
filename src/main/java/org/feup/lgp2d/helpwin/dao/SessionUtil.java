package org.feup.lgp2d.helpwin.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionUtil {

    private static SessionUtil instance = new SessionUtil();
    private SessionFactory sessionFactory;

    /**
     * Get the SessionUtil singleton instance
     * @return SessionUtil - the SessionUtil class
     */
    public static SessionUtil getInstance(){
        return instance;
    }

    /**
     * Private constructor
     * Reads hibernate config file in resources folder
     * Apply settings to the session factory
     */
    private SessionUtil(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * Returns the Session
     * @return Session - the Session to be used in transactions
     */
    public static Session getSession(){
        Session session =  getInstance().sessionFactory.openSession();
        return session;
    }
}
