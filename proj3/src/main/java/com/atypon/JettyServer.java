package com.atypon;

import com.atypon.config.AppConfiguration;
import com.atypon.config.SecurityConfig;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

public class JettyServer {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8081);

//        WebAppContext webAppContext = new WebAppContext();
        ServletContextHandler webAppContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("src/main/webapp"); // Point to the webapp directory

        // Initialize Spring
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(AppConfiguration.class);
        springContext.register(SecurityConfig.class);
        webAppContext.addEventListener(new ContextLoaderListener(springContext));


        // Register DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        ServletHolder servletHolder = new ServletHolder("dispatcher", dispatcherServlet);
        webAppContext.addServlet(servletHolder, "/*");


        FilterHolder securityFilterHolder = new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain"));
        webAppContext.addFilter(securityFilterHolder, "/*", EnumSet.allOf(DispatcherType.class));

        System.out.println("Starting Jetty...");
        // Set the handler for the server
        server.setHandler(webAppContext);
        server.start();
        server.join();
    }
}
