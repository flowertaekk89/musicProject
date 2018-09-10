package serverMain;

import server.BasicPictureSending;
import server.Server;

/**
 * 권근택
 * */
public class ServerMain {
	public static void main(String[] args) {
		new BasicPictureSending("연근pic/noun_677172_cc.png");
		new BasicPictureSending("연근pic/0.jpg");
		new BasicPictureSending("연근pic/noun_870956_cc.png");
		new BasicPictureSending("연근pic/noun_870956_cc-1.png");
		new Server();
	}//main
}//class
