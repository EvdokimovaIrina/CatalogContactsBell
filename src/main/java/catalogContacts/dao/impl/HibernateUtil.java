package catalogContacts.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 *
 */
public class HibernateUtil {
    private static SessionFactory session = buildSessionFactory();
    private static StandardServiceRegistry serviceRegistry;
    private static Logger logger = Logger.getLogger(HibernateUtil.class.getName());

    private static SessionFactory buildSessionFactory() {
        try {

            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable e) {
            logger.error("ошибка получения session ", e);
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        return session;
    }

    public static void closeSession() throws HibernateException {
        if (session != null) {
            session.close();
        }
    }


}
