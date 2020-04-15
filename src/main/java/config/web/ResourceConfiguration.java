package config.web;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*.html").addResourceLocations("/");
        registry.addResourceHandler("/3dviewer/js/*.js").addResourceLocations("/3dviewer/js/");
        registry.addResourceHandler("/*.js").addResourceLocations("/");
    }

}
