/**
 *    Auth:riozenc
 *    Date:2019年3月11日 下午7:45:53
 *    Title:com.riozenc.cim.web.config.DatasourcesConfig.java
 **/
package org.fms.billing.server.web.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourcesConfig extends com.riozenc.titanTool.datasources.DatasourcesConfig {
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {// 主要实现web监控的配置处理
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
                new StatViewServlet(), "/druid/*");// 表示进行druid监控的配置处理操作
        // servletRegistrationBean.addInitParameter("allow",
        // "127.0.0.1,192.168.202.233");//白名单
        // servletRegistrationBean.addInitParameter("deny", "192.168.202.234");//黑名单
        servletRegistrationBean.addInitParameter("loginUsername", "root");// 用户名
        servletRegistrationBean.addInitParameter("loginPassword", "root");// 密码
        servletRegistrationBean.addInitParameter("resetEnable", "true");// 是否可以重置数据源
        return servletRegistrationBean;

    }

    @Bean // 监控
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");// 所有请求进行监控处理
        filterRegistrationBean.addInitParameter("exclusions", "/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");// 排除
        return filterRegistrationBean;
    }
}
