package com.suehay.audscifx.repository;

import com.suehay.audscifx.model.TestResultEntity;
import jakarta.persistence.EntityManager;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class TestResultRepository {
    private static final EntityManager entityManager = getEntityManager();
    public static TestResultEntity findById(String id) {
        return entityManager.find(TestResultEntity.class, id);
    }

    public static void createTestResults(String tesCode) {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("insert into test_results (test_code, yes, no, undef) values (:code, 0, 0, 0)")
                     .setParameter("code", tesCode)
                     .executeUpdate();
        entityManager.getTransaction().commit();
    }

    public static void save(TestResultEntity testResultEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(testResultEntity);
        entityManager.getTransaction().commit();
    }
}