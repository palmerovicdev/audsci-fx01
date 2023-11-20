package com.suehay.audscifx.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;
public class Persistence implements PersistenceUnitInfo {
    @Override
    public String getPersistenceUnitName() {
        return "audsci_persistence_unit";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        // create a hikariCP
        HikariDataSource ds = new HikariDataSource();
        // configure ds for postgres
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/audsci");
        ds.setUsername("victor");
        ds.setPassword("victor");
        ds.setDriverClassName("org.postgresql.Driver");
        return ds;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return List.of(
                "com.suehay.models.AreaEntity.java",
                "com.suehay.models.ComponentEntity.java",
                "com.suehay.models.EvaluatedComponentEntity.java",
                "com.suehay.models.EvaluatorComponentEntity.java",
                "com.suehay.models.EmployeeEntity.java",
                "com.suehay.mosels.QuestionEntity.java",
                "com.suehay.models.RegulationEntity.java",
                "com.suehay.models.TestEntity.java"
                      );
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return Persistence.class.getProtectionDomain().getCodeSource().getLocation();
    }

    @Override
    public List<String> getManagedClassNames() {
        return List.of(
                "com.suehay.models.AreaEntity",
                "com.suehay.models.ComponentEntity",
                "com.suehay.models.EvaluatedComponentEntity",
                "com.suehay.models.EvaluatorComponentEntity",
                "com.suehay.models.EmployeeEntity",
                "com.suehay.mosels.QuestionEntity",
                "com.suehay.models.RegulationEntity",
                "com.suehay.models.TestEntity"
                      );
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}