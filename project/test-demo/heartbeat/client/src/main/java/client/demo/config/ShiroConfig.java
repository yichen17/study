package client.demo.config;

import client.demo.filter.EntranceFilter;
import client.demo.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/13 10:12
 * @describe shiro配置
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        // 页面权限以及认证， 可参考  https://cloud.tencent.com/developer/article/1643122
        Map<String,String> map = new HashMap<>();
        map.put("/get","authc");
        map.put("/test","authc");
        factoryBean.setFilterChainDefinitionMap(map);
        //设置登录页面
        factoryBean.setLoginUrl("/user/login");
//        factoryBean.setLoginUrl("http://localhost:8080");
//        Map<String, Filter> filters=factoryBean.getFilters();
//        filters.put("authc",new EntranceFilter());
//        factoryBean.setFilters(filters);

        return factoryBean;
    }


    @Bean
    public DefaultWebSecurityManager manager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return manager;
    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
