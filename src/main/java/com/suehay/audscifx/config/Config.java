package com.suehay.audscifx.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Getter
@Setter
@Builder
@Jacksonized
class Config implements Serializable {
    @JsonProperty("isUpdated")
    Boolean isUpdated;
    @Getter
    @JsonProperty("logsCount")
    long logsCount;
}