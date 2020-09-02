package net.svenwollinger.battlemaker.input.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.svenwollinger.battlemaker.map.Map;
import net.svenwollinger.battlemaker.windows.editor.EditorWindow;

public class EditorKeyListener implements KeyListener{

	EditorWindow wndInstance;
	
	public EditorKeyListener(EditorWindow _instance) {
		this.wndInstance = _instance;
	}
	
	boolean ctrlPressed = false;
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		float speed = 0.2F;
		
		if(ctrlPressed && arg0.getKeyCode() == KeyEvent.VK_S) {
			wndInstance.editorInstance.saveMap(false);
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
			ctrlPressed = true;
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_F) {
			Map cMap = wndInstance.editorInstance.currentMap;
			for(int y = 0; y < cMap.getHeight(); y++)
				for(int x = 0; x < cMap.getWidth(); x++)
					cMap.layers.get(wndInstance.editorInstance.currentLayer)[x][y] = wndInstance.editorInstance.selectedTexture;
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_C) {
			wndInstance.editorInstance.selectedTexture = wndInstance.editorInstance.copyTexture(wndInstance.mouseMotionListener.mousePos);
			wndInstance.editorInstance.textureViewer.repaint();
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_T) {
			wndInstance.editorInstance.currentMap.removeEntity(wndInstance.mouseMotionListener.mousePos);
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_Z) {
			wndInstance.editorInstance.showGrid = !wndInstance.editorInstance.showGrid;
			wndInstance.repaint();
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_D) {
			wndInstance.editorInstance.showDebug = !wndInstance.editorInstance.showDebug;
			wndInstance.repaint();
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_ADD) {
			//wndInstance.zoom(-1, null);
			wndInstance.editorInstance.changeLayer(1);
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			//wndInstance.zoom(1, null);
			wndInstance.editorInstance.changeLayer(-1);
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_L) {
			wndInstance.editorInstance.currentMap.addLayer();
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_K) {
			wndInstance.editorInstance.currentMap.removeLayer();
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			wndInstance.editorInstance.cameraPos.y = wndInstance.editorInstance.cameraPos.y - speed;
			wndInstance.repaint();
		}
		if(arg0.getKeyCode() == KeyEvent.VK_UP) {
			wndInstance.editorInstance.cameraPos.y = wndInstance.editorInstance.cameraPos.y + speed;
			wndInstance.repaint();
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			wndInstance.editorInstance.cameraPos.x = wndInstance.editorInstance.cameraPos.x - speed;
			wndInstance.repaint();
		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			wndInstance.editorInstance.cameraPos.x = wndInstance.editorInstance.cameraPos.x + speed;
			wndInstance.repaint();
		}
	
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
			ctrlPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) { }

	
	
}
