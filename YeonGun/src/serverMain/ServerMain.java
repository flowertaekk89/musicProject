package serverMain;

import server.BasicPictureSending;
import server.Server;

/**
 * �Ǳ���
 * */
public class ServerMain {
	public static void main(String[] args) {
		new BasicPictureSending("����pic/noun_677172_cc.png");
		new BasicPictureSending("����pic/0.jpg");
		new BasicPictureSending("����pic/noun_870956_cc.png");
		new BasicPictureSending("����pic/noun_870956_cc-1.png");
		new Server();
	}//main
}//class
