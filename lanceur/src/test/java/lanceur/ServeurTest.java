package lanceur;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import client.Client;
import server.Server;

import java.io.IOException;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
class ServeurTest {

	/*@Mock
	static Server server;

	@Mock
	static Server spyServer;

	@Mock
	static Client j1;

	@Mock
	static Client j2;

	@Mock
	static Client j3;

	@Mock
	static Client j4;

	static Thread client1;
	static Thread client2;
	static Thread client3;
	static Thread client4;
	static Thread serveur;

	@BeforeAll
	public static void setUpAll() throws Exception {

		Random rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
		int port = 12777 + rdm.nextInt(5000);

		serveur = new Thread() {
			@Override
			public void run() {
				try {
					server = new Server(port, 1);
					server.startServer();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		client1 = new Thread() {
			@Override
			public void run() {
				try {
					j1 = new Client("Robert", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		client2 = new Thread() {
			@Override
			public void run() {
				try {
					j2 = new Client("Marco", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		client3 = new Thread() {
			@Override
			public void run() {
				try {
					j3 = new Client("Francis", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		client4 = new Thread() {
			@Override
			public void run() {
				try {
					j4 = new Client("Maxence", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		serveur.start();
		client1.start();
		try {

			Thread.sleep(500);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client2.start();
		try {

			Thread.sleep(500);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client3.start();
		try {

			Thread.sleep(500);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client4.start();

	}

	@Test
	@Order(1)
	public void TestAcceptedConnection() throws IOException, InterruptedException {

		Thread.sleep(5000);
		assertEquals(4, server.getAcceptedConnection().size());

	}

	@AfterAll
	public static void tearDownAll() {

		j1.getSocket().disconnect();
		j2.getSocket().disconnect();
		j3.getSocket().disconnect();
		j4.getSocket().disconnect();

		server.stop();

	}*/

}
