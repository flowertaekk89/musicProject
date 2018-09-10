package exception;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 권근택
 * */
public class FileNotFoundExceptionByMe extends Exception {
	
	public FileNotFoundExceptionByMe(Socket client) {
		super("Picture not found!");
	}//cons
	
	public FileNotFoundExceptionByMe(String message, Socket client){
		super(message);
		
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new DataOutputStream(client.getOutputStream()));
			bis = new BufferedInputStream(new FileInputStream("C:\\Users\\SCMaster\\Desktop\\연근pic\\default.jpg"));
			
			byte[] arr = new byte[4000];
			
			int i = 0;
			while ((i = bis.read()) > -1) {
				bos.write(i);
			} // while
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(bos != null)
						bos.close();
					if(bis != null)
						bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}//finally
		
	}//cons
}//class
