package com.suehay.audscifx.model.common;

import lombok.*;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResultData {
    Map<String, Result> componentsRessults = new HashMap<>();
    Map<String, Result> regulationsResult = new HashMap<>();
}

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Result {
    Integer yesCount;
    Integer noCount;
}