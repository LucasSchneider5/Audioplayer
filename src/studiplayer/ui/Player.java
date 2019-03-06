package studiplayer.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

@SuppressWarnings("serial")
public class Player extends JFrame implements ActionListener{
	
	private PlayList playList;
	
	public static final String DEFAULT_songDescription = "no current song";
	public static final String DEFAULT_playTime = "--:--";
	public static final String DEFAULT_TITLE = "Studiplayer: empty play list";
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	public static final String POSITION_DAUER = "00:00";
	public static final String TITEL_PRAEFIX = "Current song: ";
	
	private JLabel songDescription = new JLabel();
	private JLabel playTime = new JLabel(POSITION_DAUER);;
	private JButton bplay = new JButton(new ImageIcon("icons/play.png"));
	private JButton bpause = new JButton(new ImageIcon("icons/pause.png"));
	private JButton bstop = new JButton(new ImageIcon("icons/stop.png"));
	
	private volatile boolean stopped = false;
	
	private PlayListEditor playListEditor;
	private boolean editorVisible;
	
	public Player(PlayList playList) {
		
		this.playList = playList;
		
		playListEditor = new PlayListEditor(this, this.playList);
		editorVisible = false;
		
		JPanel buttons = new JPanel();
		bplay.setActionCommand("AC_PLAY");
		bplay.addActionListener(this);
		buttons.add(bplay);
		bplay.setEnabled(true);
		bpause.setActionCommand("AC_PAUSE");
		bpause.addActionListener(this);
		bpause.setEnabled(false);
		buttons.add(bpause);
		bstop.setActionCommand("AC_STOP");
		bstop.addActionListener(this);
		bstop.setEnabled(false);
		buttons.add(bstop);
		JButton bnext = new JButton(new ImageIcon("icons/next.png"));
		bnext.setActionCommand("AC_NEXT");
		bnext.addActionListener(this);
		buttons.add(bnext);
		bnext.setEnabled(true);
		JButton beditor = new JButton(new ImageIcon("icons/pl_editor.png"));
		beditor.setActionCommand("AC_EDITOR");
		beditor.addActionListener(this);
		buttons.add(beditor);
		beditor.setEnabled(true);
		
		updateSongInfo(playList.getCurrentAudioFile());
		
		this.add(playTime, BorderLayout.WEST);
		this.add(buttons, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.pack();
		this.setVisible(true);
	}
	
	private class TimerThread extends Thread {
		
		public void run() {
			while(stopped == false && playList.getCurrentAudioFile() != null) {
				playTime.setText(playList.getCurrentAudioFile().getFormattedPosition());
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class PlayerThread extends Thread {
		
		public void run() {
			while(stopped == false && playList.getCurrentAudioFile() != null) {
				try {
					playList.getCurrentAudioFile().play();
				} catch (NotPlayableException e) {
					e.printStackTrace();
				}
				if(stopped == false) {
					playList.changeCurrent();
					updateSongInfo(playList.getCurrentAudioFile());
				}
			}
		}
	}
	
	private void updateSongInfo(AudioFile af) {
		if(af != null) {
			songDescription.setText(playList.getCurrentAudioFile().toString());
			this.add(songDescription, BorderLayout.NORTH);
			this.setTitle(TITEL_PRAEFIX + playList.getCurrentAudioFile());
		}
		else {
			songDescription = new JLabel(DEFAULT_songDescription);
			this.add(songDescription, BorderLayout.NORTH);
			this.setTitle(DEFAULT_songDescription);
			playTime.setText(DEFAULT_playTime);
			this.add(playTime, BorderLayout.WEST);
		}
	}
	
	private void playCurrentSong() {
		stopped = false;
		updateSongInfo(playList.getCurrentAudioFile());
		if(playList != null) {
			(new TimerThread()).start();
			(new PlayerThread()).start();
		}
	}
	
	private void stopCurrentSong() {
		stopped = true;
		if(playList != null) {
			playTime.setText(POSITION_DAUER);
			playList.getCurrentAudioFile().stop();
		}
		updateSongInfo(playList.getCurrentAudioFile());
	}
	
	public void actionPerformed(ActionEvent e) {
		AudioFile af;
		String cmd = e.getActionCommand();
		stopped = false;
		
		if(cmd.equals("AC_PLAY")) {
			playCurrentSong();
			bpause.setEnabled(true);
			bstop.setEnabled(true);
			bplay.setEnabled(false);
		}
		else if(cmd.equals("AC_PAUSE")) {
			af = playList.getCurrentAudioFile();
			bpause.setEnabled(true);
			bstop.setEnabled(true);
			bplay.setEnabled(false);
			if(playList != null) {
				playList.getCurrentAudioFile().togglePause();
			}
		}
		else if(cmd.equals("AC_STOP")) {
			stopCurrentSong();
			bplay.setEnabled(true);
			bpause.setEnabled(false);
			bstop.setEnabled(false);
		}
		else if(cmd.equals("AC_NEXT")) {
			bplay.setEnabled(false);
			bpause.setEnabled(true);
			bstop.setEnabled(true);
			if(!stopped) {
				stopCurrentSong();
			}
			playList.changeCurrent();
			playCurrentSong();
			af = playList.getCurrentAudioFile();
			if(af != null) {
				System.out.println("Switched to next audio file ");
			}
			else {
				System.out.println("PlayList is empty");
			}
			System.out.println("");
		}
		else if(cmd.equals("AC_EDITOR")) {
			if(editorVisible) {
				editorVisible = false;
			}
			else {
				editorVisible = true;
			}
			playListEditor.setVisible(editorVisible);
		}
	}
	
	public static void main(String[] args) {
		PlayList pl = new PlayList();
		if(args.length == 0) {
			pl.loadFromM3U(DEFAULT_PLAYLIST);
		}
		else {
			for(int i = 0; i < args.length; i++) {
				pl.loadFromM3U(args[i]);
			}
		}
		new Player(pl);
	}
}
