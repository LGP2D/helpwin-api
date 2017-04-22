import org.eclipse.jetty.server.Server;
import org.feup.lgp2d.helpwin.App;
import org.feup.lgp2d.helpwin.models.Role;
import org.feup.lgp2d.helpwin.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Date;

import static org.junit.Assert.*;

public class UserControllerTest {

    private Server httpServer;
    private WebTarget webTarget;
    private final int SERVER_PORT = 8082;
    private final String RESOURCE_PATH = "user";
    private final String SERVER_URL = "http://localhost:" + SERVER_PORT + "/api/";

    @Before
    public void setUp() throws Exception {
        httpServer = App.setupServer(SERVER_PORT);
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
        Response response = webTarget.path(RESOURCE_PATH + "noResource").request().get();
        assertEquals(404, response.getStatus());
    }

    @Test
    public void createUser() throws Exception {
        User user = new User(0, "test", Date.valueOf("2017-01-01"), "test@test.com", "test",
                "profession", "", new Role(3, "VOLUNTEER"));
        user.generateUniqueId();
        Response response = webTarget.path(RESOURCE_PATH).request().post(Entity.json(user));
        assertEquals(200, response.getStatus());
    }

    @Test
    public void deleteUser() throws Exception {
        //createUser();
        Response response = webTarget.path(RESOURCE_PATH + "/1").request().delete();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldThrowErrorOnCreateUser() {
        User user = new User(0, "test", Date.valueOf("2017-01-01"), "test@test.com", null,
                "profession", "", new Role(3, "VOLUNTEER"));
        user.generateUniqueId();
        Response response = webTarget.path(RESOURCE_PATH).request().post(Entity.json(user));
        assertEquals(401, response.getStatus());
    }

}