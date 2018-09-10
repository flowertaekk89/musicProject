package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import vo.ListUpdate;

/**
 * 박소연
 * */
public class ClientUI extends JFrame {

	private JPanel background_panel;
	private JPanel foot_panel;
	private JPanel panel_2;
	private JLabel Lb_Title;
	private JLabel Lb_Singer;
	private JLabel Lb_Count;
	private BufferedImage img2;
	private JLabel AlbumPhoto;
	private JButton Bt_Loveit;


	/**
	 * Create the frame.
	 */
	public ClientUI() {
		ClientManager cm = new ClientManager();
		setIconImage(
				Toolkit.getDefaultToolkit().getImage("C:\\Users\\SCMaster\\Desktop\\1st Project\\noun_677172_cc.png"));
		// 가장 큰 창
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 560);
		setResizable(false);
		background_panel = new JPanel();
		background_panel.setBackground(new Color(204, 204, 255));
		background_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(background_panel);
		background_panel.setLayout(new MigLayout("", "[422px]", "[643px]"));
		setUndecorated(true);
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		background_panel.add(panel, "cell 0 0,grow");

		// Header Panel
		JPanel header_panel = new JPanel();
		header_panel.setBounds(0, 0, 422, 141);
		String Line = "\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013\u3013";
		panel.setLayout(null);
		header_panel.setBackground(new Color(204, 204, 255));
		header_panel.setLayout(new GridLayout(5, 1));
		JLabel Blank1 = new JLabel();
		JLabel Header_Gunlon = new JLabel("DJ YeonGeun");
		JLabel Header_Line = new JLabel(Line);
		Header_Line.setFont(Header_Line.getFont().deriveFont(Header_Line.getFont().getStyle() | Font.BOLD));
		Header_Line.setHorizontalAlignment(SwingConstants.CENTER);
		Header_Line.setForeground(Color.WHITE);
		Header_Gunlon.setForeground(Color.WHITE);
		Header_Gunlon.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 19));
		Header_Gunlon.setHorizontalAlignment(SwingConstants.CENTER);
		header_panel.add(Blank1);
		header_panel.add(Header_Gunlon);
		header_panel.add(Header_Line);

		panel.add(header_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		header_panel.add(panel_1);
				panel_1.setLayout(null);
		
				Lb_Title = new JLabel("Title");
				Lb_Title.setBounds(83, 5, 293, 18);
				panel_1.add(Lb_Title);
				Lb_Title.setFont(new Font("배달의민족 주아", Font.BOLD | Font.ITALIC, 15));
				Lb_Title.setForeground(Color.WHITE);
				Lb_Title.setHorizontalAlignment(SwingConstants.LEFT);
		
		panel_2 = new JPanel();
		panel_2.setOpaque(false);
		header_panel.add(panel_2);
		panel_2.setLayout(null);
		
				Lb_Singer = new JLabel("Singer");
				Lb_Singer.setBounds(99, 0, 309, 18);
				panel_2.add(Lb_Singer);
				Lb_Singer.setFont(new Font("배달의민족 주아", Font.BOLD | Font.ITALIC, 15));
				Lb_Singer.setForeground(Color.WHITE);
				Lb_Singer.setHorizontalAlignment(SwingConstants.LEFT);

		// Body Panel
		MusicListUI musicListUI = new MusicListUI(this);
		
		JPanel body_panel = new JPanel();
		body_panel.setBackground(new Color(204, 204, 255));
		body_panel.setBounds(0, 140, 422, 283);
		
			//앨범 이미지
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("C:/연근pic12/0.jpg"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image Album = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		AlbumPhoto = new JLabel("");
		body_panel.add(AlbumPhoto);
		AlbumPhoto.setHorizontalAlignment(SwingConstants.CENTER);
		AlbumPhoto.setIcon(new ImageIcon(Album));
		panel.add(body_panel);

		// 곡 정보
		foot_panel = new JPanel();
		foot_panel.setBackground(new Color(204, 204, 255));
		foot_panel.setBounds(0, 422, 422, 114);

		// loveit 버튼 이미지 삽입
		img2 = null;
		Bt_Loveit = new JButton();

		try {
			img2 = ImageIO.read(new File("C:/연근pic12/noun_870956_cc.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Bt_Loveit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//좋아요 버튼
				try {
					img2 = ImageIO.read(new File("C:\\연근pic12\\noun_870956_cc-1.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image Icon = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				Bt_Loveit.setIcon(new ImageIcon(Icon));
				String liked = cm.likedMusic(musicListUI.getSelectedValue())+"";
				Lb_Count.setText(liked);
			}//actionPerformed
		});
		Image Icon = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		foot_panel.setLayout(null);
		panel.add(foot_panel);

		JLabel Lb_Line1 = new JLabel(
				"\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
		Lb_Line1.setForeground(Color.WHITE);
		Lb_Line1.setBounds(28, -1, 371, 18);
		foot_panel.add(Lb_Line1);
		Bt_Loveit.setBounds(325, 12, 74, 35);
		foot_panel.add(Bt_Loveit);
		Bt_Loveit.setFocusPainted(true);
		Bt_Loveit.setOpaque(false);
		Bt_Loveit.setContentAreaFilled(false);
		Bt_Loveit.setMargin(new Insets(0, 0, 0, 0));
		Bt_Loveit.setIcon(new ImageIcon(Icon));
		Bt_Loveit.setBorder(BorderFactory.createEmptyBorder());

		Lb_Count = new JLabel("Count");
		Lb_Count.setForeground(Color.WHITE);
		Lb_Count.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 15));
		Lb_Count.setBounds(289, 12, 53, 35);
		foot_panel.add(Lb_Count);
		

		// Listen 버튼
		JButton Bt_Listen = new JButton("LISTEN");
		Bt_Listen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//LISTEN 버튼
				ListUpdate selectedValue = musicListUI.getSelectedValue();
				String youtube = cm.youtube(selectedValue);
				if(youtube == null){
					JOptionPane.showMessageDialog(musicListUI, "등록된 YouTube 주소가 없습니다.");
				}else 
					new YouTubeScreen(youtube);
			}//actionPerformed
		});
		Bt_Listen.setOpaque(false);
		Bt_Listen.setFocusPainted(true);
		Bt_Listen.setContentAreaFilled(false);
		Bt_Listen.setForeground(Color.WHITE);
		Bt_Listen.setFont(new Font("Stencil", Font.BOLD, 19));
		Bt_Listen.setBounds(157, 41, 105, 35);
		Bt_Listen.setBorder(BorderFactory.createEmptyBorder());
		foot_panel.add(Bt_Listen);

		JLabel label = new JLabel(
				"\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
		label.setForeground(Color.WHITE);
		label.setBounds(28, 89, 371, 18);
		foot_panel.add(label);
		setVisible(true);
	}// cons
	
	public JLabel getLb_Title() {
		return Lb_Title;
	}
	public JLabel getLb_Singer() {
		return Lb_Singer;
	}
	public JLabel getLb_Count() {
		return Lb_Count;
	}
	public JLabel getLb_Album(){
		return AlbumPhoto;
	}
	public JButton getBt_Loveit() {
		return Bt_Loveit;
	}
}// class
