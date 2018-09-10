package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextPane;

import vo.Music;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.nio.file.ClosedFileSystemException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;

/**
 * ¹Ú¼Ò¿¬
 */
public class AddSongUI extends JFrame {
	private JTextField tf_singer;
	private JTextField tf_title;
	private JPanel panel_1;
	private JTextField tf_youtube;
	private JComboBox genreBox_1;
	private String singer;
	private String title;
	private String genre;
	private String youtube;
	private final int WIDTH = 500;
	private final int HEIGHT = 300;

	public AddSongUI() {
		ClientManager cm = new ClientManager();
		
		setTitle("µî·Ï");
		setSize(WIDTH, HEIGHT);
		JPanel north = new JPanel();
		north.setBackground(new Color(204, 204, 255));
		getContentPane().add(north, BorderLayout.NORTH);
		north.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 204, 255));
		panel_1.setOpaque(false);
		north.add(panel_1);
		
		JTextPane txtpnAddSong = new JTextPane();
		txtpnAddSong.setForeground(Color.DARK_GRAY);
		panel_1.add(txtpnAddSong);
		txtpnAddSong.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		txtpnAddSong.setSize(100, 50);
		txtpnAddSong.setText("Add SONG");
		txtpnAddSong.setOpaque(false);
		
		JPanel center = new JPanel();
		center.setBackground(new Color(204, 204, 255));
		getContentPane().add(center, BorderLayout.CENTER);
		center.setLayout(null);
		
		JLabel AddingSInger = new JLabel("Singer");
		AddingSInger.setForeground(Color.DARK_GRAY);
		AddingSInger.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		AddingSInger.setBounds(57, 13, 68, 42);
		center.add(AddingSInger);
		
		tf_singer = new JTextField();
		tf_singer.setBounds(139, 18, 246, 32);
		center.add(tf_singer);
		tf_singer.setColumns(10);
		
		JLabel AddingGenre = new JLabel("Genre");
		AddingGenre.setForeground(Color.DARK_GRAY);
		AddingGenre.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		AddingGenre.setBounds(57, 64, 62, 27);
		center.add(AddingGenre);
		
		JLabel AddingTitle = new JLabel("Title");
		AddingTitle.setForeground(Color.DARK_GRAY);
		AddingTitle.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		AddingTitle.setBounds(57, 106, 68, 27);
		center.add(AddingTitle);
		
		tf_title = new JTextField();
		tf_title.setBounds(139, 103, 246, 32);
		center.add(tf_title);
		tf_title.setColumns(10);
		
		genreBox_1 = new JComboBox();
		genreBox_1.setBackground(Color.WHITE);
		genreBox_1.setForeground(Color.DARK_GRAY);
		genreBox_1.setFont(new Font("ÈÞ¸Õ¿¾Ã¼", Font.PLAIN, 15));
		genreBox_1.setModel(new DefaultComboBoxModel(new String[] {"\uC7A5\uB974", "\uC2E0\uB098\uB294", "\uC0C8\uBCBD", "\uCE74\uD398", "\uD074\uB798\uC2DD", "\uD734\uC2DD", "\uBE44\uC624\uB294\uB0A0", "\uB2EC\uB2EC", "\uC2AC\uD508", "\uC5B4\uB450\uC6B4"}));
		genreBox_1.setBounds(139, 61, 68, 32);
		center.add(genreBox_1);
		
		JLabel lblYoutubeAddr = new JLabel("Youtube addr");
		lblYoutubeAddr.setForeground(Color.DARK_GRAY);
		lblYoutubeAddr.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		lblYoutubeAddr.setBounds(0, 145, 144, 32);
		center.add(lblYoutubeAddr);
		
		tf_youtube = new JTextField();
		tf_youtube.setBounds(139, 147, 246, 32);
		center.add(tf_youtube);
		tf_youtube.setColumns(10);
		
		JComboBox genreBox_2 = new JComboBox();
		genreBox_2.setBackground(Color.WHITE);
		genreBox_2.setForeground(Color.DARK_GRAY);
		genreBox_2.setVisible(false);
		genreBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(genreBox_1.getSelectedItem().equals("Àå¸£"))
					genreBox_2.setVisible(false);
				else
					genreBox_2.setVisible(true);
			}//actionPerformed
		});
		genreBox_2.setFont(new Font("ÈÞ¸Õ¿¾Ã¼", Font.PLAIN, 15));
		genreBox_2.setModel(new DefaultComboBoxModel(new String[] {"\uC7A5\uB974", "\uC2E0\uB098\uB294", "\uC0C8\uBCBD", "\uCE74\uD398", "\uD074\uB798\uC2DD", "\uD734\uC2DD", "\uBE44\uC624\uB294\uB0A0", "\uB2EC\uB2EC", "\uC2AC\uD508", "\uC5B4\uB450\uC6B4"}));
		genreBox_2.setBounds(221, 61, 68, 32);
		center.add(genreBox_2);
		
		JComboBox genreBox_3 = new JComboBox();
		genreBox_3.setBackground(Color.WHITE);
		genreBox_3.setForeground(Color.DARK_GRAY);
		genreBox_3.setVisible(false);
		genreBox_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(genreBox_2.getSelectedItem().equals("Àå¸£"))
					genreBox_3.setVisible(false);
				else
					genreBox_3.setVisible(true);
			}//actionPerformed
		});
		genreBox_3.setFont(new Font("ÈÞ¸Õ¿¾Ã¼", Font.PLAIN, 15));
		genreBox_3.setModel(new DefaultComboBoxModel(new String[] {"\uC7A5\uB974", "\uC2E0\uB098\uB294", "\uC0C8\uBCBD", "\uCE74\uD398", "\uD074\uB798\uC2DD", "\uD734\uC2DD", "\uBE44\uC624\uB294\uB0A0", "\uB2EC\uB2EC", "\uC2AC\uD508", "\uC5B4\uB450\uC6B4"}));
		genreBox_3.setBounds(303, 61, 68, 32);
		center.add(genreBox_3);
		
		JPanel south = new JPanel();
		south.setBackground(new Color(204, 204, 255));
		getContentPane().add(south, BorderLayout.SOUTH);
		
		JButton AddingOK = new JButton("OK");
		AddingOK.setForeground(Color.DARK_GRAY);
		AddingOK.setBackground(Color.WHITE);
		AddingOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String singer = tf_singer.getText();
				String title = tf_title.getText();
				String getYoutube = tf_youtube.getText();
				String combo_1 = (String) genreBox_1.getSelectedItem();
				String combo_2 = (String) genreBox_2.getSelectedItem();
				String combo_3 = (String) genreBox_3.getSelectedItem();
				
				String genre_1 = combo_1.equals("Àå¸£") ? null : combo_1;
				String genre_2 = combo_2.equals("Àå¸£") ? null : combo_2;
				String genre_3 = combo_3.equals("Àå¸£") ? null : combo_3;
				youtube = getYoutube.replace("watch?v=", "v/");
						
				if(genre_1 != null){
					cm.addMusic(new Music(singer, title, genre_1), youtube);
				}else if(genre_2 != null){
					cm.addMusic(new Music(singer, title, genre_1, genre_2), youtube);
				}else if(genre_3 != null){
					cm.addMusic(new Music(singer, title, genre_1, genre_2, genre_3), youtube);
				}//else if
				
				tf_singer.setText("");
				tf_title.setText("");
				tf_youtube.setText("");
				genreBox_1.setSelectedItem("Àå¸£");
				genreBox_2.setSelectedItem("Àå¸£");
				genreBox_3.setSelectedItem("Àå¸£");
			}//actionPerformed
		});
		AddingOK.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		south.add(AddingOK);
		
		JButton AddingCancel = new JButton("Cancel");
		AddingCancel.setForeground(Color.DARK_GRAY);
		AddingCancel.setBackground(Color.WHITE);
		AddingCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}//actionPerformed
		});
		AddingCancel.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		south.add(AddingCancel);
	
		setVisible(true);
	}//cons
	
	public String getSinger() {
		return singer;
	}



	public String getTitle() {
		return title;
	}



	public String getGenre() {
		return genre;
	}



	public String getYoutube() {
		return youtube;
	}
}//class
