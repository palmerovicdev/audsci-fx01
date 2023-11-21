package com.suehay.audscifx.repository;

import com.suehay.audscifx.model.AreaEntity;
import jakarta.persistence.EntityManager;

import java.util.Collection;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class AreaRepository {
    private final EntityManager entityManager = getEntityManager();

    // implement the all method
    public Collection<AreaEntity> findAll() {
        return entityManager.createQuery("SELECT a FROM AreaEntity a", AreaEntity.class)
                            .getResultList();
    }

    public void save(AreaEntity areaEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(areaEntity);
        entityManager.getTransaction().commit();
    }

    public AreaEntity findById(int id) {
        return entityManager.find(AreaEntity.class, id);
    }

    public void delete(AreaEntity areaEntity) {
        entityManager.getTransaction().begin();
        /*        entityManager.remove(areaEntity);*/
        entityManager.createQuery("delete AreaEntity a where a.id = :id").setParameter("id", areaEntity.getId());
        entityManager.getTransaction().commit();
    }

    public void deleteById(int id) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM AreaEntity a WHERE a.id = :id").setParameter("id", id).executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void update(AreaEntity areaEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(areaEntity);
        entityManager.getTransaction().commit();
    }

    public AreaEntity findByName(String name) {
        return entityManager.createQuery("SELECT a FROM AreaEntity a WHERE a.areaName = :name", AreaEntity.class).setParameter("name", name).getSingleResult();
    }

    public void deleteByName(String name) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM AreaEntity a WHERE a.areaName = :name").setParameter("name", name).executeUpdate();
        entityManager.getTransaction().commit();
    }

    public Integer getLatestId() {
        return entityManager.createQuery("SELECT MAX(a.id) FROM AreaEntity a", Integer.class).getSingleResult();
    }
}