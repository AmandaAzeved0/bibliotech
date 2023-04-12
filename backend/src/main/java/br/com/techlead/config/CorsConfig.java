package br.com.techlead.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

//    @Bean
//    public FilterRegistrationBean<?> corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }


      @Override
      public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/biblioteca/api/v1/**")
                  .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                  .allowedHeaders("*")
                  .allowedOriginPatterns("*")
                  .allowCredentials(true);
      }


}