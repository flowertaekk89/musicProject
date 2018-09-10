package clientMain;

import client.BasicPictureReceiving;
import client.ClientUI;

/**
 * 박소연
 * */
public class ClientMain {
	public static void main(String[] args) {
		new BasicPictureReceiving("C:/연근pic12/noun_677172_cc.png");
		new BasicPictureReceiving("C:/연근pic12/0.jpg");
		new BasicPictureReceiving("C:/연근pic12/noun_870956_cc.png");
		new BasicPictureReceiving("C:/연근pic12/noun_870956_cc-1.png");
		new ClientUI();
	}//main
}//class