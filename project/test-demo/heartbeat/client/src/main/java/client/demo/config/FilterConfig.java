//package client.demo.config;
//
//import client.demo.filter.EntranceFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author Qiuxinchao
// * @version 1.0
// * @date 2021/8/26 14:22
// * @describe  配置filter使生效
// */
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean testFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new EntranceFilter());
//        List<String> urlList = new ArrayList<>();
//        urlList.add("/*");
//        registration.setUrlPatterns(urlList);
//        registration.setName("EntranceFilter");
//        registration.setOrder(0);
//        return registration;
//    }
//}
