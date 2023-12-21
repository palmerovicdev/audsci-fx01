package com.suehay.audscifx.model.common;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    Integer yesCount;
    Integer noCount;
}