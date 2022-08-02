package client;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

class ClientTest {

	Client client;
	
	@Test
	void testBuildClient() {
		try {
			client = new Client("Francis");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testSetAction() {
		try {
			client = new Client("Francis");
			RandomBot ba = new RandomBot(client);
			client.setAction(ba);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
