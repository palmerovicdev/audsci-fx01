package com.suehay.audscifx.services;

import com.suehay.audscifx.model.ComponentEntity;
import com.suehay.audscifx.repository.ComponentRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ComponentService {
    private static final ComponentRepository componentRepository = new ComponentRepository();

    public static void saveComponent(ComponentEntity componentEntity) {
        componentRepository.save(componentEntity);
    }

    public static void deleteComponent(ComponentEntity componentEntity) {
        componentRepository.delete(componentEntity);
    }

    public static void deleteComponentByTestCode(String testCode) {
        componentRepository.deleteByTestCode(testCode);
    }


    public static List<ComponentEntity> findAllByTestCode(String code) {
        return componentRepository.findAllByTestCode(code);
    }
}