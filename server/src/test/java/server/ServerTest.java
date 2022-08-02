package server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.corundumstudio.socketio.SocketIOClient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServerTest {

	static Random rdm;

	
	
	
	@Mock
	SocketIOClient client1;

	@Mock
	SocketIOClient client2;

	@Mock
	SocketIOClient client3;

	@Mock
	SocketIOClient client4;

	static Server serverTeste;

	@BeforeAll
	public static void setUp() throws Exception {

		System.out.println("Avant tout les test on initialise le serveur et on le démarre");

		rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
		serverTeste = new Server(12777 + rdm.nextInt(1500));
		serverTeste.startServer();
	}

	@Test
	@Order(1)
	public void TestAcceptedConnection() throws IOException {

		serverTeste.acceptConnection(client1);
		serverTeste.acceptConnection(client2);
		serverTeste.acceptConnection(client3);
		serverTeste.acceptConnection(client4);
		

		assertEquals(4, serverTeste.getAcceptedConnection().size());

	}

	/*@Test
	@Order(2)
	public void TestfindClientManager() {

		System.out.println(client1.getSessionId());


		System.out.println(serverTeste.findClientManager(client1));

	}*/

	/*
	 * @Test
	 * 
	 * @Order(2) public void TestaddName() { serverTeste.addName(client1,
	 * "Maxence"); serverTeste.addName(client2, "Marco");
	 * serverTeste.addName(client3, "Alexis"); serverTeste.addName(client4,
	 * "Thomas");
	 * 
	 * assertTrue(serverTeste.allHaveName());
	 * 
	 * //assertEquals(4,
	 * serverTeste.getGameManager().getPlateau().getPlayers().size()); }
	 */
	@AfterAll
	public static void tearDownAll() {
		serverTeste.stop();
	}

}
