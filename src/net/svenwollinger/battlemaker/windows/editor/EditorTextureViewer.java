package net.svenwollinger.battlemaker.windows.editor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import net.svenwollinger.battlemaker.utils.Texture;
import net.svenwollinger.battlemaker.utils.TextureButton;
import net.svenwollinger.battlemaker.windows.Editor;

public class EditorTextureViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	Editor editorInstance;
	
	JPanel btnContainer = new JPanel();
	JScrollPane scrPane;
	public int texturesPerRow = 4;
	
	int btnSize = 128;
	
	public EditorTextureViewer(Editor _instance) {
		editorInstance = _instance;
		this.setSize(512,512);
		this.setTitle("Textures");

		scrPane = new JScrollPane(btnContainer);
		scrPane.getVerticalScrollBar().setUnitIncrement(16);
		scrPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrPane);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		btnContainer.setLayout(null);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				texturesPerRow = editorInstance.textureViewer.getWidth() / btnSize;
				adjustButtons();
			}
		});
		updateButtons();
	}
	
	public void adjustButtons() {
		final int mX = texturesPerRow -1;
		int cX = 0;
		int cY = 0;

		final int scrollBarSize = (this.getWidth() - this.getContentPane().getWidth());
		final int size = (this.getWidth() - scrollBarSize* 2) / (mX + 1);
		for (Component c : btnContainer.getComponents()) {
	        if(c instanceof TextureButton) {
	        	c.setBounds(cX * size, cY*size,size,size);	
				if(cX >= mX) {
					cX = 0;
					cY++;
				} else {
					cX++; 
				}
	        }
		}
		btnContainer.setPreferredSize(new Dimension((mX + 1) * size, cY*size));
    }
	
	public void updateButtons() {
		this.setVisible(false);
		cleanse(btnContainer);
		
		if(!editorInstance.currentMap.textureList.isEmpty()) {
			for(int i = 0; i < editorInstance.currentMap.textureList.size(); i++) {
				final Texture cTxt = editorInstance.currentMap.textureList.get(i);
				btnContainer.add(new TextureButton(cTxt, editorInstance));
			}
		}
		
		this.setVisible(true);
		adjustButtons();
		scrPane.revalidate();
	}
	
	public void cleanse(Container parent) {
	    for (Component c : parent.getComponents()) {
	        if(c instanceof TextureButton)
	        	parent.remove(c);
	        if (c instanceof Container)
	        	cleanse((Container)c);
	    }
	}

}
