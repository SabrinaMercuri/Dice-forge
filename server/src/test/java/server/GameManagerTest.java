package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import com.corundumstudio.socketio.SocketIOClient;

class GameManagerTest {

	Server server;
	SocketIOClient client;
	GameManager game;

	@BeforeEach
	public void setUp() throws Exception {

		server = new Server();

		ClientManager cm = new ClientManager(server, null);
		cm.setNom("Albert");
		server.getAcceptedConnection().add(cm);
		cm = new ClientManager(server, null);
		cm.setNom("Jose");
		server.getAcceptedConnection().add(cm);
		cm = new ClientManager(server, null);
		cm.setNom("Richard");
		server.getAcceptedConnection().add(cm);
		cm = new ClientManager(server, null);
		cm.setNom("Max");
		server.getAcceptedConnection().add(cm);

	}

	@AfterEach
	public void tearDown() throws Exception {
	}
	
	
	
	

}
