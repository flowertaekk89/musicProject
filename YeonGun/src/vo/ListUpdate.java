package vo;

import java.io.Serializable;

/**
 * �Ǳ���. �ڼҿ�
 * */
public class ListUpdate implements Serializable{

	private String singer;
	private String title;

	public ListUpdate(String singer, String title) {
		this.singer = singer;
		this.title = title;
	}//cons

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return String.format("���� : %s, �� : %s", singer, title);
	}
	
}//class
