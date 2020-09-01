package net.svenwollinger.battlemaker.windows.editor;

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import net.svenwollinger.battlemaker.input.editor.EditorKeyListener;
import net.svenwollinger.battlemaker.input.editor.EditorMouseListener;
import net.svenwollinger.battlemaker.input.editor.EditorMouseMotionListener;
import net.svenwollinger.battlemaker.input.editor.EditorMouseWheelListener;
import net.svenwollinger.battlemaker.windows.Editor;

public class EditorWindowMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public EditorRenderMain render = new EditorRenderMain(this); 
	public Toolbar toolbar;
	public EditorMouseMotionListener mouseMotionListener = new EditorMouseMotionListener(this);
	
	public Editor editorInstance;
	
	public EditorWindowMain(Editor _editor) {
		editorInstance = _editor;
		init();
	}
	
	void init() {
		this.setSize(512, 512);
		this.setTitle("BattleMaker");
		
		this.addKeyListener(new EditorKeyListener(this));
		this.render.addMouseMotionListener(mouseMotionListener);
		this.render.addMouseWheelListener(new EditorMouseWheelListener(this));
		this.render.addMouseListener(new EditorMouseListener(this));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(render);
		toolbar = new Toolbar(this);
		this.add(toolbar);
		this.setVisible(true);
		this.setLayout(null); //TODO: miiight be used for toolbar
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjust();
			}
		});
		this.setFocusable(true);
		this.setAutoRequestFocus(true);
		adjust();
	}
	
	public void adjust() {
		render.setBounds(0, this.getHeight()/12, this.getWidth(), this.getHeight());
		toolbar.setBounds(0,0, this.getWidth(), this.getHeight()/12);
	}
	
	public void zoom(int zoom, Point _mousePos) {
		final int maxZoomOut = 10;
		
		float h = render.getHeight();
		float w = render.getWidth();

		Point mousePos;
		
		if(_mousePos == null)
			mousePos = new Point((int)(w/2), (int)(h/2));
		else
			mousePos = _mousePos;

		/*
		 * Calculating if we should move up or down.
		 * We do this by halfing the screen, we then substract the mouse position from that
		 * Its then either - or +. Depending on up or down. We then half it by the height so that its not too strong 
		 */
		float halfY = h / 2;
		float distanceY = halfY - (float) mousePos.getY();
		float addY = distanceY/h;
		
		float halfX = w / 2;
		float distanceX = halfX - (float) mousePos.getX();
		float addX = distanceX/w;
		
		float zoomAmount = editorInstance.zoomSpeed;
		
		if(zoom == -1) {
			if(editorInstance.tiles >= editorInstance.zoomSpeed) {
				editorInstance.cameraPos.x += addX * zoomAmount;
				editorInstance.cameraPos.y += addY * zoomAmount;	
				editorInstance.tiles -= zoomAmount;
			}
		} else if(zoom == 1) {
			if((editorInstance.getTileSize() >= maxZoomOut)) {
				//Uncomment this if you want directional zooming out
				/*editorInstance.cameraPosX -= addX * zoomAmount;
				editorInstance.cameraPosY -= addY * zoomAmount;	*/
				editorInstance.tiles += zoomAmount;
			}
		}
	
		this.repaint();
	}
}
