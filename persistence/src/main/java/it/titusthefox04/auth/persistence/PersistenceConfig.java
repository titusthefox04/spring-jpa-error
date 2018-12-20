package it.titusthefox04.auth.persistence;

import it.titusthefox04.auth.persistence.model.BaseEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author titusthefox04
 */
@ComponentScan(basePackageClasses = {UserRepository.class})
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager",
        basePackageClasses = {UserRepository.class}
)
@EnableJpaAuditing(dateTimeProviderRef = "offsetDateTimeProvider")
@Configuration
public class PersistenceConfig {

    @Value("${auth.elasticsearch.url:http://localhost:9200}")
    private Object esURL;

    @Bean
    @Qualifier("offsetDateTimeProvider")
    public OffsetDateTimeProvider offsetDateTimeProvider() {
        return OffsetDateTimeProvider.INSTANCE;
    }

    @Bean
    @DependsOn("mainEntityManagerFactory")
    public DataSourceInitializer initMainDatabase() {
        ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
        pop.setContinueOnError(true);
        pop.setIgnoreFailedDrops(false);
        pop.setSqlScriptEncoding("UTF-8");
        // pop.addScript(new ClassPathResource("/authserver/sql/hibernate_sequence.sql"));
        pop.addScript(new ClassPathResource("/authserver/sql/users.sql"));

        DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(mainDataSource());
        dsi.setEnabled(true);
        dsi.setDatabasePopulator(pop);

        return dsi;
    }

    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        Map<String, Object> props = ImmutableMap.of("hibernate.hbm2ddl.auto", "update",
//                                                    "hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
//                                                    "hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        props.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        props.put("hibernate.search.default.indexmanager", "elasticsearch");
        props.put("hibernate.search.default.elasticsearch.host", esURL);
        props.put("hibernate.search.default.elasticsearch.required_index_status", "yellow");
        return builder
                .dataSource(mainDataSource())
                .packages(BaseEntity.class)
                .persistenceUnit("main")
                .properties(props)
                .build();
    }

    @Bean(name = "mainTransactionManager")
    public PlatformTransactionManager mainTransactionManager(@Qualifier("mainEntityManagerFactory") EntityManagerFactory entMgrFact) {
        return new JpaTransactionManager(entMgrFact);
    }

    @Bean(name = "mainDataSource")
    @Primary
    @ConfigurationProperties(prefix = "authserver.datasource.main")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public TransactionTemplate mainTransactionTemplate(@Qualifier("mainTransactionManager") PlatformTransactionManager txMgr) {
        return new TransactionTemplate(txMgr);
    }
}
