package net.svenwollinger.battlemaker.windows.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.svenwollinger.battlemaker.utils.LayerButton;
import net.svenwollinger.battlemaker.utils.LayerViewer;
import net.svenwollinger.battlemaker.utils.Tool;
import net.svenwollinger.battlemaker.utils.ToolButton;

public class Toolbar extends JPanel{
	private static final long serialVersionUID = -1508396539941790776L;
	
	public EditorWindowMain wndInstance;
	
	ToolButton selectButton;
	ToolButton tileButton;
	ToolButton entityButton;

	JButton layerDownBtn;
	JButton layerUpBtn;
	
	LayerViewer layerViewer;
	
	public Toolbar(EditorWindowMain _instance) {
		wndInstance = _instance;
		
		selectButton = new ToolButton("Select", this, Tool.SELECT);
		tileButton = new ToolButton("Tile Tool", this, Tool.TILE);
		entityButton = new ToolButton("Entity Tool", this, Tool.ENTITY);
		
		layerDownBtn = new LayerButton(-1, wndInstance);
		layerUpBtn = new LayerButton(1, wndInstance);
		layerViewer = new LayerViewer(wndInstance);
		
		this.add(selectButton);
		this.add(tileButton);
		this.add(entityButton);
		
		this.add(layerDownBtn);
		this.add(layerUpBtn);
		this.add(layerViewer);
		
		this.setLayout(null);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjust();
			}
		});
		
		adjust();
	}
	
	public void repaintButtons() {
		selectButton.repaint();
		tileButton.repaint();
		entityButton.repaint();
		layerViewer.repaint();
	}

	
	
	public void adjust() {
		int size = this.getHeight();
		selectButton.setBounds(0,0,size,size);
		tileButton.setBounds(size,0,size,size);
		entityButton.setBounds(size*2,0,size,size);
		
		layerDownBtn.setBounds(wndInstance.getContentPane().getWidth() - size*3,0,size,size);
		layerViewer.setBounds(wndInstance.getContentPane().getWidth() - size*2,0,size,size);
		layerUpBtn.setBounds(wndInstance.getContentPane().getWidth() - size,0,size,size);
		
		layerViewer.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
}
