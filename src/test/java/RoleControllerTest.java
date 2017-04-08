import org.eclipse.jetty.server.Server;
import org.feup.lgp2d.helpwin.App;
import org.feup.lgp2d.helpwin.models.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class RoleControllerTest {

    private Server httpServer;
    private WebTarget webTarget;
    private static final String RESOURCE_PATH = "/roles";
    private static final String SERVER_URL = "http://localhost:8080/api";

    @Before
    public void setUp() throws Exception {
        httpServer = App.setupServer();
        httpServer.start();

        Client c = ClientBuilder.newClient();
        webTarget =  c.target(SERVER_URL);
    }

    @After
    public void tearDown() throws Exception {
        httpServer.stop();
        httpServer.destroy();
    }

    @Test
    public void testRootForHttpOkCode() {
        Response responseMessage = webTarget.path(RESOURCE_PATH).request().get();
        assertEquals(200, responseMessage.getStatus());
    }

    @Test
    public void testRootForHttpNotFound() {
       Response responseResponse = webTarget.path(RESOURCE_PATH + "/noResource").request().get();
       assertEquals(404, responseResponse.getStatus());
    }
}
