package com.suehay.audscifx.services;

import com.suehay.audscifx.model.EmployeeEntity;
import com.suehay.audscifx.repository.EmployeeRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EmployeeService {
    private static EmployeeRepository employeeRepository=new EmployeeRepository();

    public static void saveEmployee(Integer integer, String employeeName, String position, Integer areaId){
        employeeRepository.save(new EmployeeEntity(integer,employeeName,position,areaId));
    }
    public static void deleteEmployee(EmployeeEntity employeeEntity){
        employeeRepository.delete(employeeEntity);
    }
    public static void deleteEmployee(Integer integer){
        employeeRepository.deleteById(integer);
    }
    public static List<EmployeeEntity> findAll(){
        return employeeRepository.findAll().stream().toList();
    }
    public static List<EmployeeEntity> findByAreaId(Integer areaId){
        return employeeRepository.findByAreaId(areaId).stream().toList();
    }
}