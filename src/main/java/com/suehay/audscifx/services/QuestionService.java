package com.suehay.audscifx.services;

import com.suehay.audscifx.model.QuestionEntity;
import com.suehay.audscifx.repository.QuestionRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QuestionService {
    private static final QuestionRepository questionRepository = new QuestionRepository();

    public static void saveQuestion(QuestionEntity questionEntity) {
        questionRepository.save(questionEntity);
    }

    public static void deleteQuestion(QuestionEntity questionEntity) {
        questionRepository.delete(questionEntity);
    }

    public static List<QuestionEntity> getQuestionsByRegulationId(Integer id) {
        return questionRepository.findAllByRegulationId(id).stream().toList();
    }

    public static List<QuestionEntity> getQuestionsBySuperQuestionId(Integer id) {
        return questionRepository.findAllBySuperQuestionId(id).stream().toList();
    }
}