package catalogContacts.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 */
public class HibernateSessionFactory {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static StandardServiceRegistry serviceRegistry;

   /* protected static SessionFactory buildSessionFactory2() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );

        }
        return sessionFactory;
    }*/


    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);

        }catch (Throwable e){
            System.err.println("Initial SessionFactory failed" + e);
        }
       return null;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void shutdown (){
        getSessionFactory().close();
    }

}
