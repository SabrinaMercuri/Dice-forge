package lanceur;

import java.util.ArrayList;
import java.util.Random;

import client.Client;
import io.socket.client.IO;
import server.Server;

public class App {

	public static void main(String[] args) {
		Random rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
		int port = 12777 + rdm.nextInt(5000);
		
		Thread server = new Thread() {
			@Override
			public void run() {
				try {
					Server server = new Server(port, Integer.parseInt(args[0]));
					server.startServer();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Thread client1 = new Thread() {
			@Override
			public void run() {
				try {
					Client j1 = new Client("Robert", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Thread client2 = new Thread() {
			@Override
			public void run() {
				try {
					Client j2 = new Client("Marco", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Thread client3 = new Thread() {
			@Override
			public void run() {
				try {
					Client j3 = new Client("Francis", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Thread client4 = new Thread() {
			@Override
			public void run() {
				try {
					Client j4 = new Client("Maxence", port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		server.start();
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

}