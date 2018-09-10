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
 * 권근택
 * */
public class ServerManager implements Manager {

	private String selectedTitle;
	private final String DEFAULT_FILE_NAME = "연근pic/default.jpg";

	/**
	 * 전체출력 완!!
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
					Music m = music.get(i); // 이미 ArrayList에 저장되어 있는 Music 객체를 하나씩 받음.
					if (m.getSinger().equalsIgnoreCase(name) && m.getTitle().equalsIgnoreCase(title)) {

						String genre_1 = null;
						String genre_2 = null;
						// 하나의 곡에 여러개의 장르가 지정되어 있을 때 장르를 Music객체에 추가할 때 쓰기 위한 임시 객체
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
	 * 장르 int -> String 변환 완!
	 */
	public String genreParsing(int genre) {
		String result = null;

		switch (genre) {
		case Genre.DELIGHTFUL:
			result = "신나는";
			break;

		case Genre.DAWN:
			result = "새벽";
			break;

		case Genre.CAFE:
			result = "카페";
			break;

		case Genre.CLASSIC:
			result = "클래식";
			break;

		case Genre.REST:
			result = "휴식";
			break;

		case Genre.RAINY:
			result = "비오는날";
			break;

		case Genre.SWEET:
			result = "달달";
			break;

		case Genre.SAD:
			result = "슬픈";
			break;

		case Genre.DARK:
			result = "어두운";
			break;
		}// switch

		return result;
	}// genreParsing

	/**
	 * 추가 완!
	 */
	@Override
	public boolean addMusic(Music m, String youtube) {
		boolean result = false;

		Connection conn = ConnectionManager.getConnection();

		try {
			String sql_search = "SELECT singer_id FROM singer where name = ? ";// 동일한 가수명인지 확인
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
				String sql_singer = "INSERT INTO singer VALUES (SINGER_SEQ.NEXTVAL, ?)"; // id(PK), 가수
				String sql_title = "INSERT INTO title VALUES (TITLE_SEQ.NEXTVAL, ?, SINGER_SEQ.CURRVAL, 0, ?)";// 노래id(PK),
																												// 제목,
																												// 가수id(FK)
				String sql_genre = "INSERT INTO genre VALUES (TITLE_SEQ.CURRVAL, ?)";// 노래id(FK), 장르

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
				String sql_title = "INSERT INTO title VALUES (TITLE_SEQ.NEXTVAL, ?, ?, 0, ?)";// 노래id(PK),
																								// 제목,
																								// 가수id(FK)
				String sql_genre = "INSERT INTO genre VALUES (TITLE_SEQ.CURRVAL, ?)";// 노래id(FK),
																						// 장르

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
	 * 라이크 추가 완!
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
	 * 유튜브 연동 완!
	 */
	@Override
	public String youtube(ListUpdate m) {
		String youtube = null;
		Connection conn = ConnectionManager.getConnection();
		String sql_song_no = "SELECT song_no FROM title WHERE title = ?";
		String sql_youtube = "SELECT youtube FROM title WHERE song_no = ?";

		try {
			// 유튜브 주소를 받아오기 위한 song_no를 받아옴.
			PreparedStatement ps_song_no = conn.prepareStatement(sql_song_no);
			ps_song_no.setString(1, m.getTitle());
			ResultSet rs_song_no = ps_song_no.executeQuery();
			int song_no = 0;
			if (rs_song_no.next())
				song_no = rs_song_no.getInt("song_no");

			// song_no를 이용해서 youtube 주소를 받아옴
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
	 * 제목으로 찾기 완!
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
					Music m_arr = result.get(i); // 이미 ArrayList에 저장되어 있는 Music 객체를 하나씩 받음.
					if (m_arr.getSinger().equalsIgnoreCase(singer) && m_arr.getTitle().equalsIgnoreCase(title)) {

						String genre_1 = null;
						String genre_2 = null;
						// 하나의 곡에 여러개의 장르가 지정되어 있을 때 장르를 Music객체에 추가할 때 쓰기 위한 임시 객체
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
	 * 가수이름검색 완!
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
					Music m_arr = result.get(i); // 이미 ArrayList에 저장되어 있는 Music
													// 객체를 하나씩 받음.
					if (singer.equalsIgnoreCase(m_arr.getSinger()) && title.equalsIgnoreCase(m_arr.getTitle())) {

						String genre_1 = null;
						String genre_2 = null;
						// 하나의 곡에 여러개의 장르가 지정되어 있을 때 장르를 Music객체에 추가할 때 쓰기 위한 임시
						// 객체;
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
	 * 장르로 검색 완!
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
	 * String으로 받은 장르를 int로 변환
	 * */
	public int genreToInt(String genre){
		int result = 0;
		
		switch (genre) {
			case "신나는":
				result = Genre.DELIGHTFUL;
				break;
	
			case "새벽":
				result = Genre.DAWN;
				break;
	
			case "카페":
				result = Genre.CAFE;
				break;
	
			case "클래식":
				result = Genre.CLASSIC;
				break;
	
			case "휴식":
				result = Genre.REST;
				break;
	
			case "비오는날":
				result = Genre.RAINY;
				break;
	
			case "달달":
				result = Genre.SWEET;
				break;
	
			case "슬픈":
				result = Genre.SAD;
				break;
	
			case "어두운":
				result = Genre.DARK;
				break;
		}// switch
		return result;
	}//genreToInt

	/**
	 * 랜덤선택 완!
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
			File file = new File("연근pic/" + selectedTitle + ".jpg");
			if(!file.exists()){
				throw new FileNotFoundExceptionByMe(client);
			}//if
			
			bos = new BufferedOutputStream(new DataOutputStream(client.getOutputStream()));
			bis = new BufferedInputStream(
					new FileInputStream("연근pic/" + selectedTitle + ".jpg"));
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
