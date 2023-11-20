package com.suehay.audscifx.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class EntityManagerProvider {
    static EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider().createEntityManagerFactory("audsci_persistence_unit", null);
    static EntityManager entityManager;
    public static EntityManager getEntityManager() {
        return entityManager != null?entityManager:entityManagerFactory.createEntityManager();
    }
}