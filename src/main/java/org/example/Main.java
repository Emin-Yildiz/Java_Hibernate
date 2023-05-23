package org.example;

import org.example.entities.Consumer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;

public class Main {

    static Consumer consumer;
    static Session sessionObj;
    static SessionFactory factory;

    private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.addAnnotatedClass(Consumer.class);
        configObj.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        SessionFactory sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    public static void main(String[] args) {

        ArrayList<Consumer> consumerList = new ArrayList<>();

        try {
            sessionObj = buildSessionFactory().openSession();
            sessionObj.beginTransaction();

            Consumer con1 = new Consumer(0,"emin","yıldız","22");
            Consumer con2 = new Consumer(1,"zeynep","yıldız","10");
            Consumer con3 = new Consumer(2,"beyza","yıldız","26");

            consumerList.add(con1);
            consumerList.add(con2);
            consumerList.add(con3);

            consumerList.forEach(
                    s -> {
                        sessionObj.save(s);
                    }
            );

            System.out.println("\n.......Records Saved Successfully To The Database.......\n");

            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }

    }
}