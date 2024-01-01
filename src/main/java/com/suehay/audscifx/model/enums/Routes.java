package com.suehay.audscifx.model.enums;

import lombok.Getter;

import java.io.File;
@Getter
public enum Routes {
    TEST_TEMPLATES("resources" + File.separator + "test-templates"),
    TEST_RESULTS("resources" + File.separator + "test-results"),
    LOGS_PATH("resources" + File.separator + "logs"),
    CONFIG_PATH("resources" + File.separator + "config.json");

    private final String path;
    Routes(String path) {
        this.path = path;
    }
}