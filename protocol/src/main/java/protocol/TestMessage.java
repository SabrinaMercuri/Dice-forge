package protocol;

import org.json.JSONObject;

public class TestMessage {
	public static void main(String args[]) {
		System.out.println(new JSONObject(new RoundMessage(2)));
	}
}
