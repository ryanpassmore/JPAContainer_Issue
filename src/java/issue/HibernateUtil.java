/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package issue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author Ryan
 */
public class HibernateUtil 
{
    private static SessionFactory factory;
    private static ServiceRegistry registry;
    private static Configuration config;
    
    public static void main(String[] args) {
        recreateDatabase();
        populateTables();
    }
    
    public static void recreateDatabase()
    {
        Configuration config = HibernateUtil.getInitializedConfiguration();

        new SchemaExport(config).create(true, true);
    }
    
    public static Configuration getInitializedConfiguration()
    {
        config = new Configuration();
        
        config.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/jpacontainer_issue");
        config.setProperty(Environment.USER, "jpa_user");
        config.setProperty(Environment.PASS, "");
        config.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        config.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        
        config.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
        config.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        config.setProperty(Environment.C3P0_MIN_SIZE, "2");
        config.setProperty(Environment.C3P0_MAX_SIZE, "20");
        config.setProperty(Environment.C3P0_TIMEOUT, "2");
        config.setProperty(Environment.C3P0_MAX_STATEMENTS, "20");
        config.setProperty("javax.persistence.validation.mode", "none");
        
        //config.setProperty(Environment.SHOW_SQL, "true");

        config.addAnnotatedClass(Person.class);
        
        return config;
    }

    public static void populateTables() {
        Session session = HibernateUtil.beginTransaction();
     
        Person p = new Person();
        p.setName("Test");
        
        session.save(p);
        
        HibernateUtil.commitTransaction();
        
    }
    
    public static void commitTransaction()
    {
        HibernateUtil.getSession().getTransaction().commit();
    }
    
    public static Session getSession()
    {
        if (factory == null){
            buildFactory();
        }

        Session hibernateSession = factory.getCurrentSession();

        return hibernateSession;
    }

    public static Session beginTransaction()
    {
        Session hibernateSession = HibernateUtil.getSession();
        hibernateSession.beginTransaction();

        return hibernateSession;
    }
    
    public static void buildFactory(){
         HibernateUtil.getInitializedConfiguration();
         registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
         factory = config.buildSessionFactory(registry);
    }    
}
