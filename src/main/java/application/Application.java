package application;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import application.config.security.SecurityConfig;
import config.web.ResourceConfiguration;
import config.web.WebConfiguration;

public class Application implements WebApplicationInitializer {

	private final static String SECURITY_FILTER_NAME =  "springSecurityFilterChain";
	public void onStartup(ServletContext context) throws ServletException {
		System.out.println("Web application initializer started!");
		System.out.println("Context path: " + context.getContextPath());
		
				AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
				springContext.register(WebConfiguration.class);
				springContext.register(ResourceConfiguration.class);
				springContext.register(SecurityConfig.class);
				springContext.setServletContext(context);
				springContext.refresh();//				
		        ServletRegistration.Dynamic servlet = context.addServlet("dispatcher", new DispatcherServlet(springContext));
		        servlet.setLoadOnStartup(1);
		        servlet.addMapping("/");	
		        
	}
	
	private void initSecurityContext(ServletContext context, AnnotationConfigWebApplicationContext springContext) {
		DelegatingFilterProxy filterChain = new DelegatingFilterProxy(SECURITY_FILTER_NAME);
		Dynamic registration = context.addFilter(SECURITY_FILTER_NAME, filterChain);
		if (registration == null) {
			throw new IllegalStateException("Security filter chain wasn't registered");
		}
		registration.setAsyncSupported(true);
		registration.addMappingForUrlPatterns(getSecurityDispatcherTypes(), false, getSecuredUrlPatterns());;
	}

	private EnumSet<DispatcherType> getSecurityDispatcherTypes() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC);
	}
	
	private String[] getSecuredUrlPatterns() {
		String[] patterns = new String[1];
		patterns[0] = "/*";
		return patterns;
	}
}
