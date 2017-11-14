package com.lewis.configcenter;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
//@MapperScan(value = "com.netease.snailreader.financebackend.biz.dao")
@EnableScheduling
//@EnableConfigurationProperties({CacheConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

   /* @Bean(value = "snailMaster")
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

    @Bean(value = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        return new DynamicDataSource(snailMasterDataSource(), snailSlaveDataSource(), yueduSlave());
    }*/

   /* @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();*/
    //sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));
     /*   Interceptor[] plugins = {pageHelper(), new DynamicPlugin()};
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean.getObject();
    }*/


    /*@Bean
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
    public PlatformTransactionManager transactionManager() {
        DynamicDataSourceTransactionManager transactionManager = new DynamicDataSourceTransactionManager();
        transactionManager.setDataSource(dynamicDataSource());
        return transactionManager;
    }
*/
}
