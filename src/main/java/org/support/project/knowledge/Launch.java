package org.support.project.knowledge;

import java.io.File;
import java.util.Optional;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class Launch {
    public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
    public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));
    
    public static void main(String[] args) throws ServletException, LifecycleException {
        String contextPath = "/knowledge" ;
        String appBase = "/data/project/red/src/knowledge/target/knowledge";
        Tomcat tomcat = new Tomcat();   
        tomcat.setPort(Integer.valueOf(PORT.orElse("8080") ));
        tomcat.setHostname(HOSTNAME.orElse("localhost"));
        tomcat.getHost().setAppBase(appBase);
        
        StandardContext ctx = (StandardContext) tomcat.addWebapp(contextPath, new File(appBase).getAbsolutePath());
        
        
        tomcat.start();
        tomcat.getServer().await();
        
    }

}
