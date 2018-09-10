package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vo.ListUpdate;
import vo.Music;

/**
 * 박소연
 * */
public class MusicListUI extends JFrame {

	private JPanel panel;
	private JMenuItem Menu_Delight;
	private JTextField tf_search;
	private ClientManager cm = new ClientManager();
	private ListUpdate selectedValue;
	private File Albumfile;
	private BufferedImage image;
	
	public MusicListUI(ClientUI clientUI) {
		setTitle("DJ \uC5F0\uADFC");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\SCMaster\\Desktop\\1st Project\\noun_677172_cc.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(548, 100, 400, 562);
		
		panel = new JPanel();
		panel.setBackground(new Color(204, 204, 255));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 351, 484);
		panel.add(scrollPane);
		
		JList list = new JList();
		list.setForeground(Color.DARK_GRAY);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedValue = (ListUpdate) list.getSelectedValue();
				String title = selectedValue.getTitle();
				clientUI.getLb_Singer().setText(selectedValue.getSinger());
				clientUI.getLb_Title().setText(title);
				
				JButton Bt_Loveit = clientUI.getBt_Loveit();
				BufferedImage img2;
				try {
					img2 = ImageIO.read(new File("C:/연근pic12/noun_870956_cc.png"));
					Image Icon = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
					Bt_Loveit.setIcon(new ImageIcon(Icon));
				} catch (IOException e2) {
					e2.printStackTrace();
				}//catch
				
				cm.currTitle(title);
				cm.sendingPic(title);
				
				try {
					image = ImageIO.read(new File("C:\\연근pic12\\"+title+".jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image Album = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
				
				clientUI.getLb_Album().setIcon(new ImageIcon(Album));
				int liked = cm.currLikedCount(title);
				
				clientUI.getLb_Count().setText(liked+"");
			}//mouseClicked
		});
		DefaultListModel<ListUpdate> model = new DefaultListModel<>();
		list.setModel(model);
		list.setFont(new Font("맑은 고딕 Semilight", Font.BOLD | Font.ITALIC, 15));
		list.setBackground(new Color(204, 204, 255));
		scrollPane.setViewportView(list);
		
		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		JMenu Menu_Genre = new JMenu("\uC7A5\uB974");
		Menu_Genre.setBackground(Color.WHITE);
		menuBar.add(Menu_Genre);
		
		Menu_Delight = new JMenuItem("\uC2E0\uB098\uB294");
		Menu_Delight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("신나는");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}//actionPerformed
		});
		Menu_Genre.add(Menu_Delight);
		
		JMenuItem Menu_Dawn = new JMenuItem("\uC0C8\uBCBD");
		Menu_Dawn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("카페");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}//actionPerformed
		});
		Menu_Genre.add(Menu_Dawn);
		
		JMenuItem Menu_Cafe = new JMenuItem("\uCE74\uD398");
		Menu_Cafe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("새벽");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}//actionPerformed
		});
		Menu_Genre.add(Menu_Cafe);
		
		JMenuItem Menu_Classic = new JMenuItem("\uD074\uB798\uC2DD");
		Menu_Classic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("클래식");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}
		});
		Menu_Genre.add(Menu_Classic);
		
		JMenuItem Menu_Rest = new JMenuItem("\uD734\uC2DD");
		Menu_Rest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("휴식");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}
		});
		Menu_Genre.add(Menu_Rest);
		
		JMenuItem Menu_Rainy = new JMenuItem("\uBE44\uC624\uB294\uB0A0");
		Menu_Rainy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("비오는날");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}
		});
		Menu_Genre.add(Menu_Rainy);
		
		JMenuItem Menu_Sweet = new JMenuItem("\uB2EC\uB2EC");
		Menu_Sweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("달달");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}
		});
		Menu_Genre.add(Menu_Sweet);
		
		JMenuItem Menu_Sad = new JMenuItem("\uC2AC\uD508");
		Menu_Sad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("슬픈");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}
		});
		Menu_Genre.add(Menu_Sad);
		
		JMenuItem Menu_Dark = new JMenuItem("\uC5B4\uB450\uC6B4");
		Menu_Dark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ListUpdate> getList = cm.searchByGenre("어두운");
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}
		});
		Menu_Genre.add(Menu_Dark);
		
		JMenuItem menuItem = new JMenuItem("\uB79C\uB364");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.removeAllElements();
				model.addElement(cm.randomPicked().get(0));
			}
		});
		Menu_Genre.add(menuItem);
		
		tf_search = new JTextField();
		tf_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					String search = tf_search.getText();
					ArrayList<Music> music = cm.searchBySinger(search);
					ArrayList<ListUpdate> list = new ArrayList<>();
					for (Music m : music) {
						list.add(new ListUpdate(m.getSinger(), m.getTitle()));
					}//for
					model.removeAllElements();
					if(list.size() != 0){
						for (ListUpdate li : list) {
							model.addElement(li);
						}//for
					}else{
						JOptionPane.showMessageDialog(null, "해당 가수에 대한 정보가 없습니다.");
					}
					tf_search.setText("");
				}//if
				
			
			}//keyPressed
		});
		tf_search.setText("\"\uAC00\uC218 \uC785\uB825\"");
		menuBar.add(tf_search);
		tf_search.setColumns(10);
		
		JButton button = new JButton("\uD655\uC778");
		button.setBackground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String search = tf_search.getText();
				ArrayList<Music> music = cm.searchBySinger(search);
				ArrayList<ListUpdate> list = new ArrayList<>();
				for (Music m : music) {
					list.add(new ListUpdate(m.getSinger(), m.getTitle()));
				}//for
				model.removeAllElements();
				if(list.size() != 0){
					for (ListUpdate li : list) {
						model.addElement(li);
					}//for
				}else{
					JOptionPane.showMessageDialog(null, "해당 가수에 대한 정보가 없습니다.");
				}
				tf_search.setText("");
			}//actionPerformed
		});
		menuBar.add(button);
		
		JButton btnNewButton = new JButton("\uB178\uB798\uCD94\uAC00");
		btnNewButton.setBounds(188, 489, 189, 27);
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("HY그래픽M", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddSongUI addUI = new AddSongUI();
				
				
				
			}//actionPerformed
		});
		btnNewButton.setBackground(Color.WHITE);
		panel.add(btnNewButton);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(356, 5, 21, 484);
		scrollBar.setBackground(Color.WHITE);
		panel.add(scrollBar);
		
		JButton btnNewButton_1 = new JButton("\uC804\uCCB4 \uCD9C\uB825");
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ListUpdate> getList = cm.select_AllSongs();
				model.removeAllElements();
				for (ListUpdate m : getList) {
					model.addElement(m);
				}//for
			}
		});
		btnNewButton_1.setForeground(Color.DARK_GRAY);
		btnNewButton_1.setFont(new Font("HY그래픽M", Font.BOLD, 15));
		btnNewButton_1.setBounds(5, 489, 189, 27);
		panel.add(btnNewButton_1);
		setResizable(false);
		setVisible(true);
	}//cons
	
	public ListUpdate getSelectedValue() {
		return selectedValue;
	}//getSelectedValue
}
