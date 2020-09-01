package net.svenwollinger.battlemaker.utils;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.svenwollinger.battlemaker.windows.Editor;

public class TextureButton extends JButton{
	private static final long serialVersionUID = -352553643825461771L;
	
	public Texture texture;
	private Editor editorInstance;
	
	public TextureButton (Texture _texture, Editor _instance) {
		texture = _texture;
		editorInstance = _instance;
		this.setName(_texture.texture);
		this.setToolTipText(_texture.texture.replace("textures/", ""));
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editorInstance.selectedTexture = texture;
				editorInstance.textureViewer.repaint();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		this.setPreferredSize(new Dimension(64,64));
		
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(editorInstance.currentMap.texturePacks.get(texture.texturePackID).getImage(texture.texture), 0,0,this.getWidth(),this.getHeight(),null);
		
		
		int stroke = 1;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(stroke));
		if(editorInstance.selectedTexture != null)
			if(editorInstance.selectedTexture.texture.equals(texture.texture) && editorInstance.selectedTexture.texturePackID == texture.texturePackID) {
				stroke = 4;
				g2.setStroke(new BasicStroke(stroke));
			}
				//g2.drawRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.drawRect(stroke, stroke, this.getWidth() - stroke*2, this.getHeight() - stroke*2);
		g2.setStroke(oldStroke);
	}
}
