package net.svenwollinger.battlemaker.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.svenwollinger.battlemaker.windows.Editor;
import net.svenwollinger.battlemaker.windows.editor.Toolbar;

public class ToolButton extends JButton{
	private static final long serialVersionUID = 7102395166776038642L;

	String name;
	Toolbar toolbarInstance;
	Tool tool;
	
	Editor editorInstance;
	Image img;
	
	public ToolButton(String _name, Toolbar _toolbar, Tool _tool) {
		name = _name;
		toolbarInstance = _toolbar;
		tool = _tool;
		
		editorInstance = toolbarInstance.wndInstance.editorInstance;
		
		switch (tool) {
			case SELECT:
				img = editorInstance.res.pointer.getImage();
				break;
			case ENTITY:
				img = editorInstance.res.entity.getImage();
				break;
			case TILE:
				img = editorInstance.res.pencil.getImage();
				break;
			default:
				img = editorInstance.res.missingEntTexture.getImage();
				break;
		}
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tool == Tool.TILE) toolbarInstance.wndInstance.editorInstance.textureViewer.requestFocus();
				toolbarInstance.wndInstance.editorInstance.selectedTool = tool;
				//If we don't do this the KeyListener breaks, because he is not in focus.
				toolbarInstance.wndInstance.requestFocus();
				toolbarInstance.repaintButtons();
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
	
		if(editorInstance.selectedTool == tool) {
			g.drawImage(img, offset,offset, this.getWidth() - offset*2,this.getHeight() - offset*2, null);
			g.setColor(Color.BLACK);
			g.drawRect(offset/2, offset/2, this.getWidth() - (offset*2)/2, this.getHeight() - (offset*2)/2);
		} else {
			g.drawImage(img, 0,0,this.getWidth(),this.getHeight(), null);
		}
	}
	
}
