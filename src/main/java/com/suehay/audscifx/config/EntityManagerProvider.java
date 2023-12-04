package com.suehay.audscifx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suehay.audscifx.model.common.Properties;
import com.suehay.audscifx.utils.FileExtractor;
import com.suehay.audscifx.utils.FileLocator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

public class EntityManagerProvider {
    public static String DATABASE, USER, PASSWORD, SCHEMA;
    static EntityManagerFactory entityManagerFactory;
    static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        chargeProperties();
        entityManagerFactory = Objects.requireNonNullElseGet(entityManagerFactory, () -> new HibernatePersistenceProvider().createEntityManagerFactory("audsci_persistence_unit", new HashMap<String, Object>() {
            {
                put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
                put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/" + DATABASE);
                put("jakarta.persistence.jdbc.user", USER);
                put("jakarta.persistence.jdbc.password", PASSWORD);
                put("hibernate.default_schema", SCHEMA);
                put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                put("show_sql", "true");
                put("hbm2ddl.auto", "update");
                put("hibernate.hbm2ddl.auto", "update");
                put("hibernate.temp.use_jdbc_metadata_defaults", "false");
                put("hibernate.jdbc.lob.non_contextual_creation", "true");
                put("hibernate.dialect.storage_engine", "innodb");
            }
        }));
        return Objects.requireNonNullElseGet(entityManager, () -> entityManagerFactory.createEntityManager());
    }

    private static void chargeProperties() {
        if (DATABASE == null || USER == null || PASSWORD == null||SCHEMA==null) {
            Properties properties;
            try {
                properties = new ObjectMapper().readValue(
                        FileExtractor.loadFileAsAString(FileLocator.getPath("resources" + File.separator + "properties.json")),
                        Properties.class);
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
            DATABASE = properties.getDatabase();
            USER = properties.getUser();
            PASSWORD = properties.getPassword();
            SCHEMA=properties.getSchema();
        }
    }

    public static void close() {
        entityManagerFactory.close();
    }

    public static void closeEntityManager() {
        entityManager.close();
    }

    public static void saveProperties(Properties properties) {
        try {
            new ObjectMapper().writeValue(
                    new File(FileLocator.getPath("resources" + File.separator + "properties.json")),
                    properties);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}