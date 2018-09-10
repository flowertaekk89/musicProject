package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import exception.FileNotFoundExceptionByMe;
import manager.Manager;
import vo.Genre;
import vo.ListUpdate;
import vo.Music;

/**
 * �Ǳ���
 * */
public class ServerManager implements Manager {

	private String selectedTitle;
	private final String DEFAULT_FILE_NAME = "����pic/default.jpg";

	/**
	 * ��ü��� ��!!
	 */
	@Override
	public ArrayList<ListUpdate> select_AllSongs() {
		ArrayList<ListUpdate> musicList = new ArrayList<>();
		ArrayList<Music> music = new ArrayList<>();

		Connection conn = ConnectionManager.getConnection();
		String sql = "SELECT s.name, t.title, g.genre_no " + "FROM singer s, title t, genre g "
				+ "WHERE s.singer_id = t.singer_no " + "AND t.song_no = g.song_no " + "ORDER BY s.name ASC";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			loop: while (rs.next()) {
				String name = rs.getString("name");
				String title = rs.getString("title");
				String genre = genreParsing(rs.getInt("genre_no"));

				for (int i = 0; i < music.size(); i++) {
					Music m = music.get(i); // �̹� ArrayList�� ����Ǿ� �ִ� Music ��ü�� �ϳ��� ����.
					if (m.getSinger().equalsIgnoreCase(name) && m.getTitle().equalsIgnoreCase(title)) {

						String genre_1 = null;
						String genre_2 = null;
						// �ϳ��� � �������� �帣�� �����Ǿ� ���� �� �帣�� Music��ü�� �߰��� �� ���� ���� �ӽ� ��ü
						Music addingGenre = null;
						switch (m.getGenre_count()) {
						case 1:
							genre_1 = genreParsing(m.getGenre_1());
							addingGenre = new Music(name, title, genre_1, genre);
							music.set(i, addingGenre);
							break;

						case 2:
							genre_1 = genreParsing(m.getGenre_1());
							genre_2 = genreParsing(m.getGenre_2());
							addingGenre = new Music(name, title, genre_1, genre_2, genre);
							music.set(i, addingGenre);
							break;
						}// switch
						continue loop;
					} // if
				} // for
				music.add(new Music(name, title, genre));
			} // while

			for (Music m : music) {
				musicList.add(new ListUpdate(m.getSinger(), m.getTitle()));
			} // for
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionManager.closeConnection(conn);
		return musicList;
	}// select_AllSongs

	/**
	 * �帣 int -> String ��ȯ ��!
	 */
	public String genreParsing(int genre) {
		String result = null;

		switch (genre) {
		case Genre.DELIGHTFUL:
			result = "�ų���";
			break;

		case Genre.DAWN:
			result = "����";
			break;

		case Genre.CAFE:
			result = "ī��";
			break;

		case Genre.CLASSIC:
			result = "Ŭ����";
			break;

		case Genre.REST:
			result = "�޽�";
			break;

		case Genre.RAINY:
			result = "����³�";
			break;

		case Genre.SWEET:
			result = "�޴�";
			break;

		case Genre.SAD:
			result = "����";
			break;

		case Genre.DARK:
			result = "��ο�";
			break;
		}// switch

		return result;
	}// genreParsing

	/**
	 * �߰� ��!
	 */
	@Override
	public boolean addMusic(Music m, String youtube) {
		boolean result = false;

		Connection conn = ConnectionManager.getConnection();

		try {
			String sql_search = "SELECT singer_id FROM singer where name = ? ";// ������ ���������� Ȯ��
			PreparedStatement ps_search = conn.prepareStatement(sql_search);
			ps_search.setString(1, m.getSinger());
			ResultSet existing_info = ps_search.executeQuery();
			boolean isExisting = existing_info.next();

			String singer = null;
			int id = 0;
			if (isExisting) {
				id = existing_info.getInt("singer_id");
			} // if
			if (!isExisting) {
				String sql_singer = "INSERT INTO singer VALUES (SINGER_SEQ.NEXTVAL, ?)"; // id(PK), ����
				String sql_title = "INSERT INTO title VALUES (TITLE_SEQ.NEXTVAL, ?, SINGER_SEQ.CURRVAL, 0, ?)";// �뷡id(PK),
																												// ����,
																												// ����id(FK)
				String sql_genre = "INSERT INTO genre VALUES (TITLE_SEQ.CURRVAL, ?)";// �뷡id(FK), �帣

				PreparedStatement ps_singer = conn.prepareStatement(sql_singer);

				ps_singer.setString(1, m.getSinger());
				result = ps_singer.execute();

				PreparedStatement ps_title = conn.prepareStatement(sql_title);
				ps_title.setString(1, m.getTitle());
				ps_title.setString(2, youtube);
				result = ps_title.execute();

				PreparedStatement ps_genre = conn.prepareStatement(sql_genre);

				int genre_count = m.getGenre_count();
				switch (genre_count) {
				case 1:
					ps_genre.setInt(1, m.getGenre_1());
					result = ps_genre.execute();
					break;

				case 2:
					ps_genre.setInt(1, m.getGenre_1());
					result = ps_genre.execute();
					ps_genre.setInt(1, m.getGenre_2());
					result = ps_genre.execute();
					break;

				case 3:
					ps_genre.setInt(1, m.getGenre_1());
					result = ps_genre.execute();
					ps_genre.setInt(1, m.getGenre_2());
					result = ps_genre.execute();
					ps_genre.setInt(1, m.getGenre_3());
					result = ps_genre.execute();
					break;
				}// switch
			} else {
				String sql_title = "INSERT INTO title VALUES (TITLE_SEQ.NEXTVAL, ?, ?, 0, ?)";// �뷡id(PK),
																								// ����,
																								// ����id(FK)
				String sql_genre = "INSERT INTO genre VALUES (TITLE_SEQ.CURRVAL, ?)";// �뷡id(FK),
																						// �帣

				PreparedStatement ps_title = conn.prepareStatement(sql_title);
				ps_title.setString(1, m.getTitle());
				ps_title.setInt(2, id);
				ps_title.setString(3, youtube);
				result = ps_title.execute();

				PreparedStatement ps_genre = conn.prepareStatement(sql_genre);

				int genre_count = m.getGenre_count();
				switch (genre_count) {
				case 1:
					ps_genre.setInt(1, m.getGenre_1());
					result = ps_genre.execute();
					break;

				case 2:
					ps_genre.setInt(1, m.getGenre_1());
					result = ps_genre.execute();
					ps_genre.setInt(1, m.getGenre_2());
					result = ps_genre.execute();
					break;

				case 3:
					ps_genre.setInt(1, m.getGenre_1());
					result = ps_genre.execute();
					ps_genre.setInt(1, m.getGenre_2());
					result = ps_genre.execute();
					ps_genre.setInt(1, m.getGenre_3());
					result = ps_genre.execute();
					break;
				}// switch
			} // else

		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}

		ConnectionManager.closeConnection(conn);
		return result;
	}// addMusic

	/**
	 * ����ũ �߰� ��!
	 */
	@Override
	public int likedMusic(ListUpdate m) {
		int result = 0;
		String title = m.getTitle();

		Connection conn = ConnectionManager.getConnection();
		String sql = "UPDATE title SET liked = liked + 1 WHERE title = ?";
		String sql_return = "SELECT liked FROM title WHERE title = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();

			PreparedStatement ps_return = conn.prepareStatement(sql_return);
			ps_return.setString(1, title);
			ResultSet rs_return = ps_return.executeQuery();
			if (rs_return.next()) {
				result = rs_return.getInt("liked");
			} // if

		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionManager.closeConnection(conn);
		return result;
	}// likeMusic

	/**
	 * ��Ʃ�� ���� ��!
	 */
	@Override
	public String youtube(ListUpdate m) {
		String youtube = null;
		Connection conn = ConnectionManager.getConnection();
		String sql_song_no = "SELECT song_no FROM title WHERE title = ?";
		String sql_youtube = "SELECT youtube FROM title WHERE song_no = ?";

		try {
			// ��Ʃ�� �ּҸ� �޾ƿ��� ���� song_no�� �޾ƿ�.
			PreparedStatement ps_song_no = conn.prepareStatement(sql_song_no);
			ps_song_no.setString(1, m.getTitle());
			ResultSet rs_song_no = ps_song_no.executeQuery();
			int song_no = 0;
			if (rs_song_no.next())
				song_no = rs_song_no.getInt("song_no");

			// song_no�� �̿��ؼ� youtube �ּҸ� �޾ƿ�
			PreparedStatement ps_youtube = conn.prepareStatement(sql_youtube);
			ps_youtube.setInt(1, song_no);
			ResultSet rs_youtube = ps_youtube.executeQuery();
			if (rs_youtube.next())
				youtube = rs_youtube.getString("youtube");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionManager.closeConnection(conn);
		return youtube;
	}// youtube

	/**
	 * �������� ã�� ��!
	 */
	@Override
	public ArrayList<Music> searchByTitle(String title) {
		ArrayList<Music> result = new ArrayList<>();

		Connection conn = ConnectionManager.getConnection();
		String sql = "SELECT s.name, g.genre_no " + "FROM singer s, title t, genre g " + "WHERE title = ? "
				+ "AND g.song_no = t.song_no " + "AND t.singer_no = s.singer_id";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();

			loop: while (rs.next()) {
				String singer = rs.getString("name");
				String genre = genreParsing(rs.getInt("genre_no"));

				for (int i = 0; i < result.size(); i++) {
					Music m_arr = result.get(i); // �̹� ArrayList�� ����Ǿ� �ִ� Music ��ü�� �ϳ��� ����.
					if (m_arr.getSinger().equalsIgnoreCase(singer) && m_arr.getTitle().equalsIgnoreCase(title)) {

						String genre_1 = null;
						String genre_2 = null;
						// �ϳ��� � �������� �帣�� �����Ǿ� ���� �� �帣�� Music��ü�� �߰��� �� ���� ���� �ӽ� ��ü
						Music addingGenre = null;
						switch (m_arr.getGenre_count()) {
						case 1:
							genre_1 = genreParsing(m_arr.getGenre_1());
							addingGenre = new Music(singer, title, genre_1, genre);
							result.set(i, addingGenre);
							break;

						case 2:
							genre_1 = genreParsing(m_arr.getGenre_1());
							genre_2 = genreParsing(m_arr.getGenre_2());
							addingGenre = new Music(singer, title, genre_1, genre_2, genre);
							result.set(i, addingGenre);
							break;
						}// switch
						continue loop;
					} // if
				} // for
				result.add(new Music(singer, title, genre));
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionManager.closeConnection(conn);
		return result;
	}// searchBytitle

	/**
	 * �����̸��˻� ��!
	 */
	@Override
	public ArrayList<Music> searchBySinger(String singer) {
		ArrayList<Music> result = new ArrayList<>();

		Connection conn = ConnectionManager.getConnection();
		String sql = "SELECT t.title, g.genre_no " + "FROM singer s, title t, genre g " + "WHERE s.name = ? "
				+ "AND g.song_no = t.song_no " + "AND t.singer_no = s.singer_id";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, singer);
			ResultSet rs = ps.executeQuery();

			loop: while (rs.next()) {
				String title = rs.getString("title");
				String genre = genreParsing(rs.getInt("genre_no"));

				for (int i = 0; i < result.size(); i++) {
					Music m_arr = result.get(i); // �̹� ArrayList�� ����Ǿ� �ִ� Music
													// ��ü�� �ϳ��� ����.
					if (singer.equalsIgnoreCase(m_arr.getSinger()) && title.equalsIgnoreCase(m_arr.getTitle())) {

						String genre_1 = null;
						String genre_2 = null;
						// �ϳ��� � �������� �帣�� �����Ǿ� ���� �� �帣�� Music��ü�� �߰��� �� ���� ���� �ӽ�
						// ��ü;
						Music addingGenre = null;
						switch (m_arr.getGenre_count()) {
						case 1:
							genre_1 = genreParsing(m_arr.getGenre_1());
							addingGenre = new Music(singer, title, genre_1, genre);
							result.set(i, addingGenre);
							break;

						case 2:
							genre_1 = genreParsing(m_arr.getGenre_1());
							genre_2 = genreParsing(m_arr.getGenre_2());
							addingGenre = new Music(singer, title, genre_1, genre_2, genre);
							result.set(i, addingGenre);
							break;
						}// switch
						continue loop;
					} // if
				} // for
				result.add(new Music(singer, title, genre));
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionManager.closeConnection(conn);
		System.out.println(result);
		return result;
	}// searchBySinger

	/**
	 * �帣�� �˻� ��!
	 */
	@Override
	public ArrayList<ListUpdate> searchByGenre(String genre) {
		ArrayList<ListUpdate> result = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();

		String sql = "SELECT s.name, t.title " + "FROM singer s, title t, genre g " + "WHERE s.singer_id = t.singer_no "
				+ "AND t.song_no = g.song_no " + "AND g.genre_no = ?";

		int genre_int = genreToInt(genre);

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, genre_int);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String singer = rs.getString("name");
				String title = rs.getString("title");

				result.add(new ListUpdate(singer, title));
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionManager.closeConnection(conn);
		return result;
	}// searchByGenre
	
	/**
	 * String���� ���� �帣�� int�� ��ȯ
	 * */
	public int genreToInt(String genre){
		int result = 0;
		
		switch (genre) {
			case "�ų���":
				result = Genre.DELIGHTFUL;
				break;
	
			case "����":
				result = Genre.DAWN;
				break;
	
			case "ī��":
				result = Genre.CAFE;
				break;
	
			case "Ŭ����":
				result = Genre.CLASSIC;
				break;
	
			case "�޽�":
				result = Genre.REST;
				break;
	
			case "����³�":
				result = Genre.RAINY;
				break;
	
			case "�޴�":
				result = Genre.SWEET;
				break;
	
			case "����":
				result = Genre.SAD;
				break;
	
			case "��ο�":
				result = Genre.DARK;
				break;
		}// switch
		return result;
	}//genreToInt

	/**
	 * �������� ��!
	 */
	@Override
	public ArrayList<ListUpdate> randomPicked() {
		ArrayList<ListUpdate> picked = new ArrayList<>();
		Random r = new Random();

		Connection conn = ConnectionManager.getConnection();

		String sql = "SELECT s.name, t.title " + "FROM singer s, title t, genre g "
				+ "WHERE s.singer_id = t.singer_no " + "AND t.song_no = ?" + "AND t.song_no = g.song_no "
				+ "ORDER BY s.name ASC";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			while (picked.size() <= 1) {
				int today_pick = r.nextInt(300);
				ps.setInt(1, today_pick);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					String name = rs.getString("name");
					String title = rs.getString("title");

					ListUpdate m = new ListUpdate(name, title);

					picked.add(m);
					return picked;
				} // if
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionManager.closeConnection(conn);
		return picked;
	}// Randompicked

	@Override
	public int currLikedCount(String title) {
		int result = 0;

		Connection conn = ConnectionManager.getConnection();
		String sql = "SELECT liked FROM title WHERE title = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, title);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("liked");
			} // if
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ConnectionManager.closeConnection(conn);
		return result;
	}// currLikedCount

	@Override
	public void currTitle(String title) {
		selectedTitle = title;
	}// sendingPic

	@Override
	public void sendingPic(String title) {
		ServerSocket ss = null;
		Socket client = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			ss = new ServerSocket(5678);
			client = ss.accept();
			File file = new File("����pic/" + selectedTitle + ".jpg");
			if(!file.exists()){
				throw new FileNotFoundExceptionByMe(client);
			}//if
			
			bos = new BufferedOutputStream(new DataOutputStream(client.getOutputStream()));
			bis = new BufferedInputStream(
					new FileInputStream("����pic/" + selectedTitle + ".jpg"));
			byte[] arr = new byte[4000];

			int i = 0;
			while ((i = bis.read()) > -1) {
				bos.write(i);
			} // while

			
		}catch(FileNotFoundExceptionByMe e){
			bos = null;
			bis = null;
			try {
				bos = new BufferedOutputStream(new DataOutputStream(client.getOutputStream()));
				bis = new BufferedInputStream(new FileInputStream(DEFAULT_FILE_NAME));
				
				int i = 0;
				while ((i = bis.read()) > -1) {
					bos.write(i);
				} // while
			} catch (IOException e1) {
				e1.printStackTrace();
			}finally{
					try {
						if(bos != null)
							bos.close();
						if(bis != null)
							bis.close();
						client.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
			}//finally
			System.err.println("[ERR] FileNotFound");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bos.close();
				bis.close();
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}//catch
		}//finally
	}// sendingPic
}// class
