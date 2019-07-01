/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api;

import io.github.paulsiberian.armus.api.database.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DatabaseManager {

    private static StandardServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    static {
        try {
            if (sessionFactory == null) {
                var config = new Configuration();
                config.addProperties(SettingsManager.getInstance().getDatabaseProperties())
                        .addAnnotatedClass(Cathedra.class)
                        .addAnnotatedClass(Discipline.class)
                        .addAnnotatedClass(Email.class)
                        .addAnnotatedClass(Employee.class)
                        .addAnnotatedClass(EmployeePosition.class)
                        .addAnnotatedClass(Group.class)
                        .addAnnotatedClass(Institute.class)
                        .addAnnotatedClass(Person.class)
                        .addAnnotatedClass(Phone.class)
                        .addAnnotatedClass(Student.class);
                serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
                sessionFactory =config.buildSessionFactory(serviceRegistry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (serviceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            serviceRegistry.close();
        }
    }

}
