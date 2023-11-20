package com.suehay.audscifx.services;

import com.suehay.audscifx.model.AreaEntity;
import com.suehay.audscifx.repository.AreaRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AreaService {
    private static AreaRepository areaRepository=new AreaRepository();

    public static void saveArea(Integer integer, String areaName){
        areaRepository.save(new AreaEntity(integer,areaName));
    }
    public static void deleteArea(AreaEntity areaEntity){
        areaRepository.delete(areaEntity);
    }
    public static void deleteArea(Integer integer){
        areaRepository.deleteById(integer);
    }

    public static List<AreaEntity> findAll() {
        return areaRepository.findAll().stream().toList();
    }
}