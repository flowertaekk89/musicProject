package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sound.midi.Receiver;

import manager.Manager;
import vo.Command;
import vo.ListUpdate;
import vo.Music;

/**
 * 박소연
 * */
public class ClientManager implements Manager {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket socket;
	private final String HOST = "localhost";
	private final int PORT = 7799;
	
	public ClientManager() {
		
		try {
			socket = new Socket(HOST, PORT);
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}//cons

	@Override
	public ArrayList<ListUpdate> select_AllSongs() {
		ArrayList<ListUpdate> result = new ArrayList<>();
		Object values[] = {Command.SELECT_ALL_SONGS, null};
		try {
			oos.writeObject(values);
			result = (ArrayList<ListUpdate>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public boolean addMusic(Music m, String youtube) {
		boolean result = false;
		Object values[] = {Command.ADD_MUSIC, m, youtube};
		try {
			oos.writeObject(values);
			result = (boolean) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int likedMusic(ListUpdate m) {
		int result = 0;
		
		Object values[] = {Command.LIKED_MUSIC, m};
		try {
			oos.writeObject(values);
			result = (int) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}//likedMusic

	@Override
	public String youtube(ListUpdate m) {
		String result = null;
		Object values[] = {Command.YOUTUBE, m};
		try {
			oos.writeObject(values);
			result = (String) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<Music> searchByTitle(String title) {
		ArrayList<Music> result = new ArrayList<>();
		Object values[] = {Command.SEARCH_BY_TITLE, title};
		
		try {
			oos.writeObject(values);
			result = (ArrayList<Music>) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<Music> searchBySinger(String singer) {
		ArrayList<Music> result = new ArrayList<>();
		Object values[] = {Command.SEARCH_BY_SINGER, singer};
		try {
			oos.writeObject(values);
			result = (ArrayList<Music>) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<ListUpdate> searchByGenre(String genre) {
		ArrayList<ListUpdate> result= new ArrayList<>();
		Object values[] = {Command.SEARCH_BY_GENRE, genre};
		try {
			oos.writeObject(values);
			result = (ArrayList<ListUpdate>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<ListUpdate> randomPicked() {
		ArrayList<ListUpdate> result = null;
		Object values[] = {Command.RANDOM_PICKED, null};
		
		try {
			oos.writeObject(values);
			result = (ArrayList<ListUpdate>) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int currLikedCount(String title) {
		int result = 0;
		Object values[] = {Command.CURR_LIKED_COUNT, title};
		
		try {
			oos.writeObject(values);
			result = (int) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void currTitle(String title) {
		
		try {
			Object values[] = {Command.CURR_TITLE, title};
			oos.writeObject(values);
			ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}//sendingPic

	@Override
	public void sendingPic(String title) {
		try {
			Object value [] = {Command.SENDING_PIC, null};
			oos.writeObject(value);
			//
			try {
				Socket socket = new Socket(HOST, 5678);
				BufferedInputStream bis = new BufferedInputStream(new DataInputStream(socket.getInputStream()));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:/연근pic12/"+title+".jpg"));

				int i = 0;
				while((i = bis.read()) > -1){
					bos.write(i);
				}//while
				
				bos.close();
				bis.close();
				socket.close();
				
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			//
			ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}//sendingPic
	
}//class
