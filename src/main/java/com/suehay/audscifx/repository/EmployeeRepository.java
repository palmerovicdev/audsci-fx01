package com.suehay.audscifx.repository;

import jakarta.persistence.EntityManager;
import com.suehay.audscifx.model.EmployeeEntity;

import java.util.Collection;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class EmployeeRepository {
    private final EntityManager entityManager = getEntityManager();

    // implement the all method
    public Collection<EmployeeEntity> findAll() {
        return entityManager.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity.class)
                .getResultList();
    }
    public void save(EmployeeEntity employeeEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(employeeEntity);
        entityManager.getTransaction().commit();
    }
    public EmployeeEntity findById(int id) {
        return entityManager.find(EmployeeEntity.class, id);
    }
    public void delete(EmployeeEntity employeeEntity) {
        entityManager.getTransaction().begin();
        entityManager.remove(employeeEntity);
        entityManager.getTransaction().commit();
    }
    public void update(EmployeeEntity employeeEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(employeeEntity);
        entityManager.getTransaction().commit();
    }
    public EmployeeEntity findByEmail(String email) {
        return entityManager.createQuery("SELECT e FROM EmployeeEntity e WHERE e.email = :email", EmployeeEntity.class).setParameter("email", email).getSingleResult();
    }
    public void deleteByEmail(String email) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM EmployeeEntity e WHERE e.email = :email").setParameter("email", email).executeUpdate();
        entityManager.getTransaction().commit();
    }
    // implement the employeeName method
    public EmployeeEntity findByEmployeeName(String employeeName) {
        return entityManager.createQuery("SELECT e FROM EmployeeEntity e WHERE e.employeeName = :employeeName", EmployeeEntity.class).setParameter("employeeName", employeeName).getSingleResult();
    }
    public void deleteByEmployeeName(String employeeName) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM EmployeeEntity e WHERE e.employeeName = :employeeName").setParameter("employeeName", employeeName).executeUpdate();
        entityManager.getTransaction().commit();
    }
    // implement the findAllByPosition method
    public Collection<EmployeeEntity> findAllByPosition(String position) {
        return entityManager.createQuery("SELECT e FROM EmployeeEntity e WHERE e.position = :position", EmployeeEntity.class).setParameter("position", position).getResultList();
    }
    public void deleteByPosition(String position) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM EmployeeEntity e WHERE e.position = :position").setParameter("position", position).executeUpdate();
        entityManager.getTransaction().commit();
    }
    // implement the findAllByAreaId method, this returns a collection of employees
    public Collection<EmployeeEntity> findAllByAreaId(int areaId) {
        return entityManager.createQuery("SELECT e FROM EmployeeEntity e WHERE e.areaId = :areaId", EmployeeEntity.class).setParameter("areaId", areaId).getResultList();
    }

    public void deleteAllByAreaId(int areaId) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM EmployeeEntity e WHERE e.areaId = :areaId").setParameter("areaId", areaId).executeUpdate();
        entityManager.getTransaction().commit();
    }
}