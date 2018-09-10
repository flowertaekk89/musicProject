package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ±Ç±ÙÅÃ
 * */
public class BasicPictureSending {
	public BasicPictureSending(String pic) {
		try {
			ServerSocket ss = new ServerSocket(7777);
			
			Socket client = ss.accept();
			
			BufferedOutputStream bos = new BufferedOutputStream(new DataOutputStream(client.getOutputStream()));
			BufferedInputStream bis = bis = new BufferedInputStream(new FileInputStream(pic));
			
				byte [] arr = new byte[4000];
				
				int p = 0;
				while((p = bis.read(arr)) > -1){
					bos.write(arr);
				}//while

			bos.close();
			bis.close();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}//cons
}//class
