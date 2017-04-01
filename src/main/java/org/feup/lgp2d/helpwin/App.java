package org.feup.lgp2d.helpwin;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * This is the main application class
 */
public class App {

    public static void main(String[] argv) {

        ResourceConfig config = new ResourceConfig();
        config.packages("org.feup.lgp2d.helpwin");

        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(server, "/api");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            server.destroy();
        }
    }
}
