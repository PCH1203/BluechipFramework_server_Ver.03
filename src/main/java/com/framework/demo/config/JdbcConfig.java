package com.framework.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@EntityScan
@RequiredArgsConstructor
@MapperScan(value = "com.framework.demo.mapper.**")
public class JdbcConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userId;
    @Value("${spring.datasource.password}")
    private String passwd;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.framework.demo.domain");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

//    //
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//        return jpaTransactionManager;
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userId);
        dataSource.setPassword(passwd);
        return dataSource;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    public BasicDataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userId);
        dataSource.setPassword(passwd);
        dataSource.setMaxTotal(10);
        dataSource.setMaxIdle(10);
        dataSource.setMaxWaitMillis(5000);
        dataSource.setDefaultReadOnly(false);
        dataSource.setDefaultAutoCommit(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(180000);
        dataSource.setNumTestsPerEvictionRun(4);
        dataSource.setValidationQuery("select 1");
        dataSource.setMinEvictableIdleTimeMillis(-1);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws  Exception {
        log.trace("SqlSessionFactory");
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("mybatis/mappers/**/*.xml"));
        SqlSessionFactory factory = sessionFactory.getObject();
        // 테스트
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
//    @Bean
//    public JdbcDaoImpl UserDetailsService() {
//        System.out.println(">>>>> JdbcDaoImpl 실행");
//        JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
//        jdbcDaoImpl.setDataSource(dataSource());
//        return jdbcDaoImpl;
//    }

    }
