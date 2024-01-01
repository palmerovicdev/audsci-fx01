package com.suehay.audscifx.services;

import com.suehay.audscifx.model.RegulationEntity;
import com.suehay.audscifx.repository.RegulationRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RegulationService {
    private static final RegulationRepository regulationRepository = new RegulationRepository();

    public static void saveRegulation(RegulationEntity regulationEntity) {
        regulationRepository.save(regulationEntity);
    }

    public static List<RegulationEntity> getRegulationsByComponentId(Integer id) {
        return regulationRepository.findAllByComponentId(id).stream().toList();
    }
}