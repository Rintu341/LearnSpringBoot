package com.sujan;

import com.sujan.util.DatabaseConfig;
import com.sujan.util.EnvConfig;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;
import com.sujan.config.RestConfig;
import java.io.File;

public class App {
    public static void main(String[] args) throws LifecycleException {

        System.out.println("JWT_SECRET = " + EnvConfig.get("JWT_SECRET"));

        // Step 1 — Initialize DB pool before anything else
        DatabaseConfig.initialize();

        // Step 2 — Start Tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        tomcat.setAddDefaultWebXmlToWebapp(false);

        Context context = tomcat.addWebapp("",
                new File("src/main/webapp").getAbsolutePath());

        ServletContainer container = new ServletContainer(new RestConfig());
        Tomcat.addServlet(context, "jersey-servlet", container);
        context.addServletMappingDecoded("/rest/*", "jersey-servlet");

        // Step 3 — Shutdown hook — closes DB pool when app stops
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConfig.shutdown();
            System.out.println("✅ App shut down cleanly.");
        }));

        tomcat.start();
        System.out.println("✅ Server started on http://localhost:8080");
        tomcat.getServer().await();
    }
}