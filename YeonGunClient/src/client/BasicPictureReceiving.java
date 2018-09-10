package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 박소연
 * */
public class BasicPictureReceiving {
	public BasicPictureReceiving(String pic) {
	File file = new File("C:/연근pic12");
		
		if(!file.exists())
			file.mkdirs();
		
		try {
			Socket socket = new Socket("localhost", 7777);
			BufferedInputStream bis = new BufferedInputStream(new DataInputStream(socket.getInputStream()));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(pic));
			
			int p = 0;
			byte [] arr = new byte[4000];
			while((p = bis.read(arr)) > -1){
				bos.write(arr);
			}//while
			
			bos.close();
			bis.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//cons
}//class
