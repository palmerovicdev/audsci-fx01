package com.suehay.audscifx.services;

import com.suehay.audscifx.model.RegulationEntity;
import com.suehay.audscifx.repository.RegulationRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegulationService {
    private static final RegulationRepository regulationRepository = new RegulationRepository();
    public static void saveRegulation(RegulationEntity regulationEntity) {
        regulationRepository.save(regulationEntity);
    }
    public static void deleteRegulation(RegulationEntity regulationEntity) {
        regulationRepository.delete(regulationEntity);
    }
    public static void deleteRegulationByTestCode(Integer id) {
        regulationRepository.deleteById(id);
    }

}