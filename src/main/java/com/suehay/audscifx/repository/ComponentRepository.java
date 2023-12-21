package com.suehay.audscifx.repository;

import com.suehay.audscifx.model.ComponentEntity;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class ComponentRepository {
    private final EntityManager entityManager = getEntityManager();

    // implement the all method
    public Collection<ComponentEntity> findAll() {
        return entityManager.createQuery("SELECT c FROM ComponentEntity c", ComponentEntity.class)
                            .getResultList();
    }

    public void save(ComponentEntity componentEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(componentEntity);
        entityManager.getTransaction().commit();
    }

    public ComponentEntity findById(Integer id) {
        return entityManager.find(ComponentEntity.class, id);
    }

    public void delete(ComponentEntity componentEntity) {
        entityManager.getTransaction().begin();
        entityManager.remove(componentEntity);
        entityManager.getTransaction().commit();
    }

    public void update(ComponentEntity componentEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(componentEntity);
        entityManager.getTransaction().commit();
    }

    public ComponentEntity findByTestCode(String testCode) {
        return entityManager.createQuery("SELECT c FROM ComponentEntity c WHERE c.testCode = :testCode", ComponentEntity.class).setParameter("testCode", testCode).getSingleResult();
    }

    public void deleteByTestCode(String testCode) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM ComponentEntity c WHERE c.testCode = :testCode").setParameter("testCode", testCode).executeUpdate();
        entityManager.getTransaction().commit();
    }

    public List<ComponentEntity> findAllByTestCode(String code) {
        return entityManager.createQuery("SELECT c FROM ComponentEntity c WHERE c.testCode = :testCode", ComponentEntity.class).setParameter("testCode", code).getResultList();
    }
}