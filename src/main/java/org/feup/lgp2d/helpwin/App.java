package org.feup.lgp2d.helpwin;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.feup.lgp2d.helpwin.authentication.AuthenticationFilter;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

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
    public static Server setupServer(int port) {
        ResourceConfig config = setupResourceConfig();
        ServletHolder servletHolder = setupServletHolder(config);

        server = new Server(port);

        setupServletContextHandler(servletHolder, server);

        return server;
    }

    /**
     * Starts the server. If the server is null, it's initialized.
     * @return Server - the server
     * @throws Exception - case server fails to start
     */
    private static Server startServer() throws Exception {
        server.start();
        return server;
    }

    /**
     * Stops and destroys the server
     * @throws Exception - case server fails to stop
     */
    private static void destroyServer() throws Exception {
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
        return new ServletHolder(new ServletContainer(config));
    }

    /**
     * Gives the servlet context and extends ContextHandler
     * Allows for simple construction of a context with ServletHandler
     * Method <code>addServlet()</code> is a convenience method to add a Servlet
     */
    private static ServletContextHandler setupServletContextHandler(ServletHolder servletHolder, Server server) {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(servletHolder, "/api/*");

        ServletHolder staticImages = new ServletHolder("static-images", DefaultServlet.class);
        staticImages.setInitParameter("resourceBase", "./images");
        staticImages.setInitParameter("dirAllowed", "true");
        staticImages.setInitParameter("pathInfoOnly", "true");
        context.addServlet(staticImages, "/static/images/*");

        ServletHolder root = new ServletHolder("default", DefaultServlet.class);
        root.setInitParameter("dirAllowed", "true");
        context.addServlet(root, "/");

        FilterHolder cors = context.addFilter(CrossOriginFilter.class, "/api/*", EnumSet.of(DispatcherType.REQUEST));
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,DELETE,PUT,OPTIONS");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type, Origin, Authorization");

        return context;
    }

    /**
     * Main
     */
    public static void main(String[] argv) throws Exception {
        try {
            setupServer(SERVER_PORT);
            startServer();
            server.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            destroyServer();
        }
    }
}
