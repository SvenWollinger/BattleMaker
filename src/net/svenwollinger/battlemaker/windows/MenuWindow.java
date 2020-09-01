package net.svenwollinger.battlemaker.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import net.svenwollinger.battlemaker.Main;

public class MenuWindow extends JFrame{

	private static final long serialVersionUID = 1L;

	MenuWindow instance;
	
	JLabel titleText = new JLabel("BattleMaker", SwingConstants.CENTER);
	JLabel versionText = new JLabel("Version: " + Main.version, SwingConstants.CENTER);
	
	JButton startEditorBtn = new JButton("Start your battle!");
	JButton aboutBtn = new JButton("About");
	JButton exitBtn = new JButton("Exit");
	
	public MenuWindow() {
		instance = this;
		init();
	}
	
	public void adjustContent() {
		int w = this.getWidth();
		titleText.setFont(new Font("TimesRoman", Font.PLAIN, this.getHeight()/10));

		int buttonSize = this.getHeight()/5;
		int offset = 10;
		
		titleText.setBounds(0,0,w,buttonSize);
		startEditorBtn.setBounds(w/4, buttonSize + offset, w/2, buttonSize);
		aboutBtn.setBounds(w/4, buttonSize*2 + offset*2, w/2, buttonSize);
		exitBtn.setBounds(w/4, buttonSize*3 + offset*3, w/2, buttonSize);
		versionText.setBounds(w/4, buttonSize*4 + offset*4, w/2, buttonSize/5);
	}
	
	public void init() {
		this.setSize(512,512);
		this.setTitle("BattleMaker Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setLayout(null);
		this.setResizable(false);
		
		this.add(titleText);
		this.add(startEditorBtn);
		this.add(aboutBtn);
		this.add(exitBtn);
		this.add(versionText);
		
		adjustContent();

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustContent();
			}
		});
		
		startEditorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Editor();
				instance.dispose();
			}
		});
		
		aboutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(instance, "BattleMaker Version " + Main.version + " by Sven Wollinger");
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
}
