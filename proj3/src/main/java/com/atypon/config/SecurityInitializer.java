//package com.atypon.config;
//
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//
//public class SecurityInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        // Root context (e.g., datasource, JPA)
//        return null;
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        // Servlet context (MVC + Security)
//        return new Class[] {
//                AppConfiguration.class,
//                SecurityConfig.class
//        };
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] { "/" };
//    }
//}