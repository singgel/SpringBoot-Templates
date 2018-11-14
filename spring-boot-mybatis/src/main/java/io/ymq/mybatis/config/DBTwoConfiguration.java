package io.ymq.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.ymq.mybatis.config.druid.AbstractDruidDBConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

/**
 * 描述: 核心配置，配置数据源 事物 sqlSessionFactorYmqTwo
 * author: yanpenglei
 * Date: 2017/9/8 18:21
 */
@Configuration
@EnableTransactionManagement
public class DBTwoConfiguration extends AbstractDruidDBConfig {

    @Value("${ymq.two.datasource.url}")
    private String url;

    @Value("${ymq.two.datasource.username}")
    private String username;

    @Value("${ymq.two.datasource.password}")
    private String password;

    // 注册 datasourceTwo
    @Bean(name = "datasourceTwo", initMethod = "init", destroyMethod = "close")
    @Primary
    public DruidDataSource dataSource() {
        return super.createDataSource(url, username, password);
    }


    @Bean(name = "sqlSessionFactorYmqTwo")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return super.sqlSessionFactory(dataSource());
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
