package vo;

import java.io.Serializable;

/**
 * �ڼҿ�, �Ǳ���
 * */
public class Music extends ListUpdate implements Serializable {
	private int id;
	private int genre_1;
	private int genre_2;
	private int genre_3;
	private int genre_count;

	// ������ 1
	public Music(String singer, String title, String genre_1) {
		super(singer, title);
		this.id = 0;
		this.genre_count = 1;

		switch (genre_1) {
		case "�ų���":
			this.genre_1 = Genre.DELIGHTFUL;
			break;

		case "����":
			this.genre_1 = Genre.DAWN;
			break;

		case "ī��":
			this.genre_1 = Genre.CAFE;
			break;

		case "Ŭ����":
			this.genre_1 = Genre.CLASSIC;
			break;

		case "�޽�":
			this.genre_1 = Genre.REST;
			break;

		case "����³�":
			this.genre_1 = Genre.RAINY;
			break;

		case "�޴�":
			this.genre_1 = Genre.SWEET;
			break;

		case "����":
			this.genre_1 = Genre.SAD;
			break;

		case "��ο�":
			this.genre_1 = Genre.DARK;
			break;
		}// switch
	}// cons_1

	// ������ 2
	public Music(String singer, String title, String genre_1, String genre_2) {
		super(singer, title);
		this.id = 0;
		this.genre_count = 2;

		switch (genre_1) {
		case "�ų���":
			this.genre_1 = Genre.DELIGHTFUL;
			break;

		case "����":
			this.genre_1 = Genre.DAWN;
			break;

		case "ī��":
			this.genre_1 = Genre.CAFE;
			break;

		case "Ŭ����":
			this.genre_1 = Genre.CLASSIC;
			break;

		case "�޽�":
			this.genre_1 = Genre.REST;
			break;

		case "����³�":
			this.genre_1 = Genre.RAINY;
			break;

		case "�޴�":
			this.genre_1 = Genre.SWEET;
			break;

		case "����":
			this.genre_1 = Genre.SAD;
			break;

		case "��ο�":
			this.genre_1 = Genre.DARK;
			break;
		}// switch

		switch (genre_2) {
		case "�ų���":
			this.genre_2 = Genre.DELIGHTFUL;
			break;

		case "����":
			this.genre_2 = Genre.DAWN;
			break;

		case "ī��":
			this.genre_2 = Genre.CAFE;
			break;

		case "Ŭ����":
			this.genre_2 = Genre.CLASSIC;
			break;

		case "�޽�":
			this.genre_2 = Genre.REST;
			break;

		case "����³�":
			this.genre_2 = Genre.RAINY;
			break;

		case "�޴�":
			this.genre_2 = Genre.SWEET;
			break;

		case "����":
			this.genre_2 = Genre.SAD;
			break;

		case "��ο�":
			this.genre_2 = Genre.DARK;
			break;
		}// switch

	}// cons_2

	// ������ 3
	public Music(String singer, String title, String genre_1, String genre_2, String genre_3) {
		super(singer, title);
		this.id = 0;
		this.genre_count = 3;

		switch (genre_1) {
		case "�ų���":
			this.genre_1 = Genre.DELIGHTFUL;
			break;

		case "����":
			this.genre_1 = Genre.DAWN;
			break;

		case "ī��":
			this.genre_1 = Genre.CAFE;
			break;

		case "Ŭ����":
			this.genre_1 = Genre.CLASSIC;
			break;

		case "�޽�":
			this.genre_1 = Genre.REST;
			break;

		case "����³�":
			this.genre_1 = Genre.RAINY;
			break;

		case "�޴�":
			this.genre_1 = Genre.SWEET;
			break;

		case "����":
			this.genre_1 = Genre.SAD;
			break;

		case "��ο�":
			this.genre_1 = Genre.DARK;
			break;
		}// switch

		switch (genre_2) {
		case "�ų���":
			this.genre_2 = Genre.DELIGHTFUL;
			break;

		case "����":
			this.genre_2 = Genre.DAWN;
			break;

		case "ī��":
			this.genre_2 = Genre.CAFE;
			break;

		case "Ŭ����":
			this.genre_2 = Genre.CLASSIC;
			break;

		case "�޽�":
			this.genre_2 = Genre.REST;
			break;

		case "����³�":
			this.genre_2 = Genre.RAINY;
			break;

		case "�޴�":
			this.genre_2 = Genre.SWEET;
			break;

		case "����":
			this.genre_2 = Genre.SAD;
			break;

		case "��ο�":
			this.genre_2 = Genre.DARK;
			break;
		}// switch

		switch (genre_3) {
		case "�ų���":
			this.genre_3 = Genre.DELIGHTFUL;
			break;

		case "����":
			this.genre_3 = Genre.DAWN;
			break;

		case "ī��":
			this.genre_3 = Genre.CAFE;
			break;

		case "Ŭ����":
			this.genre_3 = Genre.CLASSIC;
			break;

		case "�޽�":
			this.genre_3 = Genre.REST;
			break;

		case "����³�":
			this.genre_3 = Genre.RAINY;
			break;

		case "�޴�":
			this.genre_3 = Genre.SWEET;
			break;

		case "����":
			this.genre_3 = Genre.SAD;
			break;

		case "��ο�":
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
		return super.toString() + String.format(", �帣: %d, %d, %d", genre_1, genre_2, genre_3);
	}

}// class
