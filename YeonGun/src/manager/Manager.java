package manager;

import java.util.ArrayList;

import vo.ListUpdate;
import vo.Music;

/**
 * �Ǳ���, �ڼҿ�
 * */
public interface Manager {
	
	/**
	 * @return ArrayList<Music> DB �����ϰ� �ִ� ��� �� ����� ��ȯ�Ѵ�.
	 * */
	ArrayList<ListUpdate> select_AllSongs();
	
	void currTitle(String title);
	
	void sendingPic(String title);

	/**
	 * @param Music m ����� Music ��ü�� �޴´�.
	 * */
	boolean addMusic(Music m, String youtube);
	
	/**
	 * @param ���ƿ並 ���� �뷡 ��ü�� �޴´�.
	 * */
	int likedMusic(ListUpdate m);
	
	int currLikedCount(String title);
	
	/**
	 * @param Music m ��Ʃ��� ������ �뷡��ü�� �޾ƿ´�.
	 * */
	String youtube(ListUpdate m);
	
	
	/**
	 * @param String title �������� �˻��� ����� �޾ƿ´�.
	 * @return ArrayList<Music> �Ű������� ���� ���� ������ ���� ArrayList�� �־ ��ȯ�Ѵ�.
	 * */
	ArrayList<Music> searchByTitle(String title);
	
	
	/**
	 * @param String singer �˻��� �������� �޾ƿ´�
	 * @return ArrayList<Music> �Ű������� ���� ������� ������ ���� ArrayList�� �־ ��ȯ�Ѵ�.
	 * */
	ArrayList<Music> searchBySinger(String singer);
	
	
	/**
	 * ����ڰ� ����� ��õ������ �Է��ϰ� �� �� �� ������ �°� ��ȯ
	 * @param String genre �˻��� �帣�� �޾ƿ´�
	 * @param int Count ��ȯ�� ���� ����
	 * @return ArrayList<Music> �Ű������� ���� �帣�� ������ ���� ArrayList�� �־ ��ȯ�Ѵ�.
	 * */
	ArrayList<ListUpdate> searchByGenre(String genre);
	
	
	/**
	 * ������ �� ��ȯ
	 * */
	ArrayList<ListUpdate> randomPicked();
	
	
}//interface
