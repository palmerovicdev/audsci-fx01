package com.suehay.audscifx.services;

import com.suehay.audscifx.model.QuestionEntity;
import com.suehay.audscifx.repository.QuestionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuestionService {
    private static final QuestionRepository questionRepository = new QuestionRepository();

    public static void saveQuestion(QuestionEntity questionEntity) {
        questionRepository.save(questionEntity);
    }

    public static void deleteQuestion(QuestionEntity questionEntity) {
        questionRepository.delete(questionEntity);
    }

}