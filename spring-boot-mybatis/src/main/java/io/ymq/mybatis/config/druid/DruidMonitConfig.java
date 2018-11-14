package io.ymq.mybatis.config.druid;


import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 描述: druid监控配置
 * author: yanpenglei
 * Date: 2017/10/20 11:00
 */
@EnableConfigurationProperties(DruidDbProperties.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DruidMonitConfig {

    @Resource
    private DruidDbProperties druidDbProperties;

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");

        if (!StringUtils.isEmpty(druidDbProperties.getAllow())) {
            reg.addInitParameter("allow", druidDbProperties.getAllow()); // 白名单
        }
        if (!StringUtils.isEmpty(druidDbProperties.getDeny())) {
            reg.addInitParameter("deny", druidDbProperties.getDeny()); // 黑名单
        }
        reg.addInitParameter("loginUsername", druidDbProperties.getUsername());
        reg.addInitParameter("loginPassword", druidDbProperties.getPassword());
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 监听Spring 1.定义拦截器 2.定义切入点 3.定义通知类
     *
     * @return
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        String patterns = "io.ymq.mybatis*";
        druidStatPointcut.setPatterns(patterns);
        return druidStatPointcut;
    }

    @Bean
    public Advisor druidStatAdvisor() {
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }
}