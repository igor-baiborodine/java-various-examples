package org.mybatis.jpetstore.config;

import net.sourceforge.stripes.controller.StripesFilter;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import static javax.servlet.DispatcherType.REQUEST;

/**
 * @author Igor Baiborodine
 */
@Configuration
public class WebConfig implements ServletContextInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    servletContext.setInitParameter(
        "javax.servlet.jsp.jstl.fmt.localizationContext", "StripesResources");

    FilterRegistration.Dynamic stripesFilter = servletContext.addFilter(
        "StripesFilter", StripesFilter.class);
    stripesFilter.setInitParameter("ActionResolver.Packages", "org.mybatis.jpetstore.web");
    stripesFilter.setInitParameter(
        "Interceptor.Classes", "net.sourceforge.stripes.integration.spring.SpringInterceptor");
    stripesFilter.addMappingForServletNames(EnumSet.of(REQUEST), true, "StripesDispatcher");

    ServletRegistration.Dynamic stripesDispatcher = servletContext.addServlet(
        "StripesDispatcher", new net.sourceforge.stripes.controller.DispatcherServlet());
    stripesDispatcher.setLoadOnStartup(1);
    stripesDispatcher.addMapping("*.action");
  }

}
