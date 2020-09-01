package net.svenwollinger.battlemaker.windows.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.svenwollinger.battlemaker.utils.Tool;
import net.svenwollinger.battlemaker.utils.ToolButton;

public class Toolbar extends JPanel{
	private static final long serialVersionUID = -1508396539941790776L;
	
	public EditorWindowMain wndInstance;
	
	ToolButton selectButton = new ToolButton("Select", this, Tool.SELECT);
	ToolButton tileButton = new ToolButton("Tile Tool", this, Tool.TILE);
	ToolButton entityButton = new ToolButton("Entity Tool", this, Tool.ENTITY);

	JButton layerDownBtn = new JButton("-");
	JButton layerUpBtn = new JButton("+");
	
	JPanel layerViewer = new JPanel();
	JLabel viewerText = new JLabel();
	
	public Toolbar(EditorWindowMain _instance) {
		wndInstance = _instance;
		this.setLayout(null);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjust();
			}
		});
		this.add(selectButton);
		this.add(tileButton);
		this.add(entityButton);
		
		this.add(layerDownBtn);
		
	    layerViewer.add(viewerText);
		this.add(layerViewer);
		
		this.add(layerUpBtn);
		
		layerDownBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				wndInstance.editorInstance.changeLayer(-1);
				wndInstance.requestFocus();
			}
		});
		
		layerUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				wndInstance.editorInstance.changeLayer(1);
				wndInstance.requestFocus();
			}
		});
		
		adjust();
	}

	public void setLayer(int layer) {
		viewerText.setText("" + layer);
		viewerText.setFont(new Font("Arial", 0, (int)(this.getHeight()/1.25F)));
	}
	
	public void adjust() {
		int size = this.getHeight();
		selectButton.setBounds(0,0,size,size);
		tileButton.setBounds(size,0,size,size);
		entityButton.setBounds(size*2,0,size,size);
		
		layerDownBtn.setBounds(wndInstance.getContentPane().getWidth() - size*3,0,size,size);
		layerViewer.setBounds(wndInstance.getContentPane().getWidth() - size*2,0,size,size);
		layerUpBtn.setBounds(wndInstance.getContentPane().getWidth() - size,0,size,size);
		
		setLayer(wndInstance.editorInstance.currentLayer);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
}
