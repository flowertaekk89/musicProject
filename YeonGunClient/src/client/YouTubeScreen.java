package client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

/**
 * 박소연, 권근택
 * */
public class YouTubeScreen extends JFrame {
	
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	public YouTubeScreen(String youtube) {
		NativeInterface.open();
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            JFrame frame = new JFrame("DJ 연근");
	            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	            frame.getContentPane().add(getBrowserPanel(youtube), BorderLayout.CENTER);
	            frame.setSize(WIDTH, HEIGHT);
	            frame.setLocationByPlatform(true);
	            frame.setAlwaysOnTop(true);
	            frame.setVisible(true);
	        }
	    });
	    
	    if(!NativeInterface.isOpen())
	    	NativeInterface.runEventPump();
	    
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        @Override
	        public void run() {
	            NativeInterface.close();
	        }//run
	    }));
	}//cons

	public JPanel getBrowserPanel(String youtube) {
	    JPanel webBrowserPanel = new JPanel(new BorderLayout());
	    JWebBrowser webBrowser = new JWebBrowser();
	    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
	    webBrowser.setBarsVisible(false);
	    webBrowser.navigate(youtube);
	    
	    return webBrowserPanel;
	}//getBrowserPanel

	
}//class
