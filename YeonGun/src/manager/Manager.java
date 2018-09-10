package manager;

import java.util.ArrayList;

import vo.ListUpdate;
import vo.Music;

/**
 * 권근택, 박소연
 * */
public interface Manager {
	
	/**
	 * @return ArrayList<Music> DB 소유하고 있는 모든 곡 목록을 반환한다.
	 * */
	ArrayList<ListUpdate> select_AllSongs();
	
	void currTitle(String title);
	
	void sendingPic(String title);

	/**
	 * @param Music m 등록할 Music 객체를 받는다.
	 * */
	boolean addMusic(Music m, String youtube);
	
	/**
	 * @param 좋아요를 누른 노래 객체를 받는다.
	 * */
	int likedMusic(ListUpdate m);
	
	int currLikedCount(String title);
	
	/**
	 * @param Music m 유튜브와 연동할 노래객체를 받아온다.
	 * */
	String youtube(ListUpdate m);
	
	
	/**
	 * @param String title 제목으로 검색할 곡명을 받아온다.
	 * @return ArrayList<Music> 매개변수로 받은 곡명과 동일한 곡을 ArrayList에 넣어서 반환한다.
	 * */
	ArrayList<Music> searchByTitle(String title);
	
	
	/**
	 * @param String singer 검색할 가수명을 받아온다
	 * @return ArrayList<Music> 매개변수로 받은 가수명과 동일한 곡을 ArrayList에 넣어서 반환한다.
	 * */
	ArrayList<Music> searchBySinger(String singer);
	
	
	/**
	 * 사용자가 몇곡을 추천받을지 입력하게 한 후 그 개수에 맞게 반환
	 * @param String genre 검색할 장르를 받아온다
	 * @param int Count 반환할 곡의 개수
	 * @return ArrayList<Music> 매개변수로 받은 장르와 동일한 곡을 ArrayList에 넣어서 반환한다.
	 * */
	ArrayList<ListUpdate> searchByGenre(String genre);
	
	
	/**
	 * 랜덤한 곡 반환
	 * */
	ArrayList<ListUpdate> randomPicked();
	
	
}//interface
