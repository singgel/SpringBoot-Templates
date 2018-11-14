package io.ymq.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;

import io.ymq.mybatis.config.druid.AbstractDruidDBConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

/**
 * 描述: 核心配置，配置数据源 事物 sqlSessionFactorYmqOne
 * author: yanpenglei
 * Date: 2017/9/8 18:21
 */
@Configuration
@EnableTransactionManagement
public class DBOneConfiguration extends AbstractDruidDBConfig {

    @Value("${ymq.one.datasource.url}")
    private String url;

    @Value("${ymq.one.datasource.username}")
    private String username;

    @Value("${ymq.one.datasource.password}")
    private String password;

    // 注册 datasourceOne
    @Bean(name = "datasourceOne", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() {
        return super.createDataSource(url, username, password);
    }

    @Bean(name = "sqlSessionFactorYmqOne")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return super.sqlSessionFactory(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
