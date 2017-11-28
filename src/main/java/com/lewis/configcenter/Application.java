package com.lewis.configcenter;


import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.lewis.configcenter.common.component.datasource.DynamicDataSource;
import com.lewis.configcenter.common.component.datasource.DynamicDataSourceTransactionManager;
import com.lewis.configcenter.common.component.datasource.DynamicPlugin;
import com.lewis.configcenter.common.config.CacheConfig;
import com.lewis.configcenter.common.config.ProjectConfig;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
@MapperScan(value = "com.lewis.configcenter.biz.dao")
@EnableScheduling
@EnableConfigurationProperties({CacheConfig.class, ProjectConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(value = "snailMaster")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.snail-master")
    public DataSource snailMasterDataSource() {
        return new DruidDataSource();
    }


    @Bean(value = "snailSlave")
    @ConfigurationProperties(prefix = "spring.datasource.snail-slave")
    public DataSource snailSlaveDataSource() {
        return new DruidDataSource();
    }

    @Bean(value = "yueduSlave")
    @ConfigurationProperties(prefix = "spring.datasource.yuedu-slave")
    public DataSource yueduSlave() {
        return new DruidDataSource();
    }

    @Bean(value = "local")
    @ConfigurationProperties(prefix = "spring.datasource.local")
    public DataSource local() {
        return new DruidDataSource();
    }

    @Bean(value = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        return new DynamicDataSource(snailMasterDataSource(), snailSlaveDataSource(), yueduSlave(),local());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        //DefaultVFS在获取jar上存在问题，使用springboot只能修改
        VFS.addImplClass(SpringBootVFS.class);
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));
        Interceptor[] plugins = {pageHelper(), new DynamicPlugin()};
        sqlSessionFactoryBean.setPlugins(plugins);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.lewis.configcenter.biz.model.entity");
        return sqlSessionFactoryBean.getObject();
    }


    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("dialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean
    public PlatformTransactionManager dynamicTransactionManager() {
        DynamicDataSourceTransactionManager transactionManager = new DynamicDataSourceTransactionManager();
        transactionManager.setDataSource(dynamicDataSource());
        return transactionManager;
    }
}
