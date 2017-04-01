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

        /**
         * This is the configuration of the resources to search for
         * It searches recursively inside the package
         * If the server needs to look for resources on any other above package, please add it to config.packages
         */
        ResourceConfig config = new ResourceConfig();
        config.packages("org.feup.lgp2d.helpwin");

        /**
         * Servlet instance and context holder
         * Holds names, parameters, and some states of <code>javax.servlet.Servlet</code> instance
         * Is a servlet/filter to deploy <i>root resource classes</i>
         */
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        /**
         * Main class for Jetty HTTP Servlet server
         * Aggregates connectors and requests
         * Server itself is a hander and a ThreadPool
         */
        Server server = new Server(8080);

        /**
         * Gives the servlet context and extends ContextHandler
         * Allows for simple construction of a context with ServletHandler
         * Method <code>addServlet()</code> is a convenience method to add a Servlet
         */
        ServletContextHandler context = new ServletContextHandler(server, "/api");
        context.addServlet(servlet, "/*");

        try {
            /**
             * Starts the server
             */
            server.start();
            /**
             * Blocks until stop button (maven) or Ctrl^C is pressed
             */
            server.join();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            /**
             * Destroy the server
             */
            server.destroy();
        }
    }
}
