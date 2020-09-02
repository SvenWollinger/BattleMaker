package net.svenwollinger.battlemaker.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.svenwollinger.battlemaker.windows.editor.EditorWindowMain;

public class LayerButton extends JButton{
	private static final long serialVersionUID = 4843305725405576790L;

	EditorWindowMain wndInstance;
	int dir = 0;
	
	Image img;
	
	public LayerButton(int _dir, EditorWindowMain _wndInstance) {
		dir = _dir;
		wndInstance = _wndInstance;
			
		switch(dir) {
			case 1:
				img = wndInstance.editorInstance.res.plus.getImage();
				break;
			case -1:
				img = wndInstance.editorInstance.res.minus.getImage();
				break;
		}
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				wndInstance.editorInstance.changeLayer(dir);
				wndInstance.requestFocus();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int offset = this.getWidth() / 8;
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(offset/2, offset/2, this.getWidth() - (offset*2)/2, this.getHeight() - (offset*2)/2);
	
		g.drawImage(img, 0,0,this.getWidth(),this.getHeight(), null);
		
	}
	
}
