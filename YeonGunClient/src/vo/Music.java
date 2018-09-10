package vo;

import java.io.Serializable;

/**
 * 박소연, 권근택
 * */
public class Music extends ListUpdate implements Serializable {
	private int id;
	private int genre_1;
	private int genre_2;
	private int genre_3;
	private int genre_count;

	// 생성자 1
	public Music(String singer, String title, String genre_1) {
		super(singer, title);
		this.id = 0;
		this.genre_count = 1;

		switch (genre_1) {
		case "신나는":
			this.genre_1 = Genre.DELIGHTFUL;
			break;

		case "새벽":
			this.genre_1 = Genre.DAWN;
			break;

		case "카페":
			this.genre_1 = Genre.CAFE;
			break;

		case "클래식":
			this.genre_1 = Genre.CLASSIC;
			break;

		case "휴식":
			this.genre_1 = Genre.REST;
			break;

		case "비오는날":
			this.genre_1 = Genre.RAINY;
			break;

		case "달달":
			this.genre_1 = Genre.SWEET;
			break;

		case "슬픈":
			this.genre_1 = Genre.SAD;
			break;

		case "어두운":
			this.genre_1 = Genre.DARK;
			break;
		}// switch
	}// cons_1

	// 생성자 2
	public Music(String singer, String title, String genre_1, String genre_2) {
		super(singer, title);
		this.id = 0;
		this.genre_count = 2;

		switch (genre_1) {
		case "신나는":
			this.genre_1 = Genre.DELIGHTFUL;
			break;

		case "새벽":
			this.genre_1 = Genre.DAWN;
			break;

		case "카페":
			this.genre_1 = Genre.CAFE;
			break;

		case "클래식":
			this.genre_1 = Genre.CLASSIC;
			break;

		case "휴식":
			this.genre_1 = Genre.REST;
			break;

		case "비오는날":
			this.genre_1 = Genre.RAINY;
			break;

		case "달달":
			this.genre_1 = Genre.SWEET;
			break;

		case "슬픈":
			this.genre_1 = Genre.SAD;
			break;

		case "어두운":
			this.genre_1 = Genre.DARK;
			break;
		}// switch

		switch (genre_2) {
		case "신나는":
			this.genre_2 = Genre.DELIGHTFUL;
			break;

		case "새벽":
			this.genre_2 = Genre.DAWN;
			break;

		case "카페":
			this.genre_2 = Genre.CAFE;
			break;

		case "클래식":
			this.genre_2 = Genre.CLASSIC;
			break;

		case "휴식":
			this.genre_2 = Genre.REST;
			break;

		case "비오는날":
			this.genre_2 = Genre.RAINY;
			break;

		case "달달":
			this.genre_2 = Genre.SWEET;
			break;

		case "슬픈":
			this.genre_2 = Genre.SAD;
			break;

		case "어두운":
			this.genre_2 = Genre.DARK;
			break;
		}// switch

	}// cons_2

	// 생성자 3
	public Music(String singer, String title, String genre_1, String genre_2, String genre_3) {
		super(singer, title);
		this.id = 0;
		this.genre_count = 3;

		switch (genre_1) {
		case "신나는":
			this.genre_1 = Genre.DELIGHTFUL;
			break;

		case "새벽":
			this.genre_1 = Genre.DAWN;
			break;

		case "카페":
			this.genre_1 = Genre.CAFE;
			break;

		case "클래식":
			this.genre_1 = Genre.CLASSIC;
			break;

		case "휴식":
			this.genre_1 = Genre.REST;
			break;

		case "비오는날":
			this.genre_1 = Genre.RAINY;
			break;

		case "달달":
			this.genre_1 = Genre.SWEET;
			break;

		case "슬픈":
			this.genre_1 = Genre.SAD;
			break;

		case "어두운":
			this.genre_1 = Genre.DARK;
			break;
		}// switch

		switch (genre_2) {
		case "신나는":
			this.genre_2 = Genre.DELIGHTFUL;
			break;

		case "새벽":
			this.genre_2 = Genre.DAWN;
			break;

		case "카페":
			this.genre_2 = Genre.CAFE;
			break;

		case "클래식":
			this.genre_2 = Genre.CLASSIC;
			break;

		case "휴식":
			this.genre_2 = Genre.REST;
			break;

		case "비오는날":
			this.genre_2 = Genre.RAINY;
			break;

		case "달달":
			this.genre_2 = Genre.SWEET;
			break;

		case "슬픈":
			this.genre_2 = Genre.SAD;
			break;

		case "어두운":
			this.genre_2 = Genre.DARK;
			break;
		}// switch

		switch (genre_3) {
		case "신나는":
			this.genre_3 = Genre.DELIGHTFUL;
			break;

		case "새벽":
			this.genre_3 = Genre.DAWN;
			break;

		case "카페":
			this.genre_3 = Genre.CAFE;
			break;

		case "클래식":
			this.genre_3 = Genre.CLASSIC;
			break;

		case "휴식":
			this.genre_3 = Genre.REST;
			break;

		case "비오는날":
			this.genre_3 = Genre.RAINY;
			break;

		case "달달":
			this.genre_3 = Genre.SWEET;
			break;

		case "슬픈":
			this.genre_3 = Genre.SAD;
			break;

		case "어두운":
			this.genre_3 = Genre.DARK;
			break;
		}// switch
	}// cons_3

	public int getGenre_1() {
		return genre_1;
	}

	public void setGenre_1(int genre_1) {
		this.genre_1 = genre_1;
	}

	public int getGenre_2() {
		return genre_2;
	}

	public void setGenre_2(int genre_2) {
		this.genre_2 = genre_2;
	}

	public int getGenre_3() {
		return genre_3;
	}

	public void setGenre_3(int genre_3) {
		this.genre_3 = genre_3;
	}

	public int getGenre_count() {
		return genre_count;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return super.toString() + String.format(", 장르: %d, %d, %d", genre_1, genre_2, genre_3);
	}

}// class
