package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import vo.Command;
import vo.ListUpdate;
import vo.Music;

/**
 * 권근택
 * */
public class ServerThread implements Runnable {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ServerManager sm;
	private Socket socket;

	public ServerThread(ObjectInputStream ois, ObjectOutputStream oos, Socket socket) {
		this.ois = ois;
		this.oos = oos;
		this.socket = socket;
		sm = new ServerManager();
	}//cons

	@Override
	public void run() {
		
		try {
			while(!socket.isClosed()){
				Object [] obj = (Object[]) ois.readObject();
				int command = (int) obj[0];
				Object value = obj[1];
				
				switch(command){
				case Command.SELECT_ALL_SONGS:
					oos.writeObject(sm.select_AllSongs());
					break;
					
				case Command.SENDING_PIC:
					sm.sendingPic(null);
					oos.writeObject(null);
					break;
					
				case Command.CURR_TITLE:
					sm.currTitle((String) value);
					oos.writeObject(null);
					break;
				case Command.ADD_MUSIC:
					if(obj.length == 2){ // youtube id가 입력 됐을 시 아닐 시 구분
						oos.writeObject(sm.addMusic((Music)value, null));
					}else
						oos.writeObject(sm.addMusic((Music)value, (String)obj[2]));
					break;
					
				case Command.LIKED_MUSIC:
					oos.writeObject(sm.likedMusic((ListUpdate) value));
					break;
					
				case Command.RANDOM_PICKED:
					oos.writeObject(sm.randomPicked());
					break;
					
				case Command.SEARCH_BY_GENRE:
					oos.writeObject(sm.searchByGenre((String) value));
					break;
					
				case Command.SEARCH_BY_SINGER:
					oos.writeObject(sm.searchBySinger((String) value));
					break;
					
				case Command.SEARCH_BY_TITLE:
					oos.writeObject(sm.searchByTitle((String) value));
					break;
					
				case Command.YOUTUBE:
					oos.writeObject(sm.youtube((ListUpdate) value));
					break;
					
				case Command.CURR_LIKED_COUNT:
					oos.writeObject(sm.currLikedCount((String) value));
					break;
				}//switch
			}//while
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//run
}//class
