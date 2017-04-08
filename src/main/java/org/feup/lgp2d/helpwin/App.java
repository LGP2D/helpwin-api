package org.feup.lgp2d.helpwin;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.feup.lgp2d.helpwin.authentication.AuthenticationFilter;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Main application class
 */
public class App {

    private static final int SERVER_PORT = 8080;
    private static Server server;

    /**
     * Initialize the server with its properties
     * @return Server - the server
     */
    public static Server setupServer() {
        ResourceConfig config = setupResourceConfig();
        ServletHolder servletHolder = setupServletHolder(config);

        /**
         * Main class for Jetty HTTP Servlet server
         * Aggregates connectors and requests
         * Server itself is a handler and a ThreadPool
         */
        server = new Server(SERVER_PORT);

        //ServletContextHandler contextHandler =
        setupServletContextHandler(servletHolder, server) ;

        return server;
    }

    /**
     * Starts the server. If the server is null, it's initialized.
     * @return Server - the server
     * @throws Exception - case server fails to start
     */
    public static Server startServer() throws Exception {
        server.start();
        return server;
    }

    /**
     * Stops and destroys the server
     * @throws Exception - case server fails to stop
     */
    public static void destroyServer() throws Exception {
        server.stop();
        server.destroy();
    }

    /**
     * This is the configuration of the resources to search for
     * It searches recursively inside the package
     * If the server needs to look for resources on any other above package, please add it to config.packages
     */
    private static ResourceConfig setupResourceConfig() {
        ResourceConfig config = new ResourceConfig();
        config.packages("org.feup.lgp2d.helpwin", "org.feup.lgp2d.helpwin.authentication");
        config.register(LoggingFeature.class);

        //Authentication filter register
        config.register(AuthenticationFilter.class);

        return config;
    }

    /**
     * Servlet instance and context holder
     * Holds names, parameters, and some states of <code>javax.servlet.Servlet</code> instance
     * Is a servlet/filter to deploy <i>root resource classes</i>
     */
    private static ServletHolder setupServletHolder(ResourceConfig config) {
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        return servlet;
    }

    /**
     * Gives the servlet context and extends ContextHandler
     * Allows for simple construction of a context with ServletHandler
     * Method <code>addServlet()</code> is a convenience method to add a Servlet
     */
    private static ServletContextHandler setupServletContextHandler(ServletHolder servletHolder, Server server) {
        ServletContextHandler context = new ServletContextHandler(server, "/api");
        context.addServlet(servletHolder, "/*");
        return context;
    }

    public static void main(String[] argv) throws Exception {
        try {
            setupServer();
            startServer();
            server.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            destroyServer();
        }

//        /**
//         * This is the configuration of the resources to search for
//         * It searches recursively inside the package
//         * If the server needs to look for resources on any other above package, please add it to config.packages
//         */
//        ResourceConfig config = new ResourceConfig();
//        config.packages("org.feup.lgp2d.helpwin", "org.feup.lgp2d.helpwin.authentication");
//        config.register(LoggingFeature.class);
//
//
//        //Authentication filter register
//        config.register(AuthenticationFilter.class);
//
//        /**
//         * Servlet instance and context holder
//         * Holds names, parameters, and some states of <code>javax.servlet.Servlet</code> instance
//         * Is a servlet/filter to deploy <i>root resource classes</i>
//         */
//        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
//
//        Server server = setupServer();
//
//        /**
//         * Gives the servlet context and extends ContextHandler
//         * Allows for simple construction of a context with ServletHandler
//         * Method <code>addServlet()</code> is a convenience method to add a Servlet
//         */
//        ServletContextHandler context = new ServletContextHandler(server, "/api");
//        context.addServlet(servlet, "/*");
//
//        try {
//            /**
//             * Starts the server
//             */
//            startServer();
//            /**
//             * Blocks until stop button (maven) or Ctrl^C is pressed
//             */
//            server.join();
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        } finally {
//            /**
//             * Destroy the server
//             */
//            shutdownServer();
//        }
    }
}
