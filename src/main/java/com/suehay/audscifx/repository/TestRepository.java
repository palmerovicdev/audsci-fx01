package com.suehay.audscifx.repository;


import com.suehay.audscifx.model.TestEntity;
import jakarta.persistence.EntityManager;

import java.util.Collection;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class TestRepository {
    private final EntityManager entityManager = getEntityManager();

    // implement the all method
    public Collection<TestEntity> findAll() {
        return entityManager.createQuery("SELECT t FROM TestEntity t", TestEntity.class)
                            .getResultList();
    }

    public void save(TestEntity testEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(testEntity);
        entityManager.getTransaction().commit();
    }

    // implement the findByCode method
    public TestEntity findByCode(String code) {
        return entityManager.createQuery("SELECT t FROM TestEntity t WHERE t.code = :code", TestEntity.class)
                            .setParameter("code", code)
                            .getSingleResult();
    }

    // implement delete by code method
    public void deleteByCode(String code) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM TestEntity t WHERE t.code = :code")
                     .setParameter("code", code)
                     .executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void delete(TestEntity testEntity) {
        entityManager.getTransaction().begin();
        entityManager.remove(testEntity);
        entityManager.getTransaction().commit();
    }

    public void update(TestEntity testEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(testEntity);
        entityManager.getTransaction().commit();
    }

    public void truncateDb() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM TestEntity t")
                     .executeUpdate();
        entityManager.getTransaction().commit();
    }
}