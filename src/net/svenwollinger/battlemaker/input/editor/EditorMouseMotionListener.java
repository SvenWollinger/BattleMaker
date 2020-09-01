package net.svenwollinger.battlemaker.input.editor;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import net.svenwollinger.battlemaker.utils.Texture;
import net.svenwollinger.battlemaker.utils.Tool;
import net.svenwollinger.battlemaker.windows.editor.EditorWindowMain;

public class EditorMouseMotionListener implements MouseMotionListener{

	EditorWindowMain wndInstance;
	
	public EditorMouseMotionListener(EditorWindowMain _instance) {
		wndInstance = _instance;
	}
	
	public Point lastPoint;
	public Point mousePos;
	
	boolean dragEnabled = false;
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(dragEnabled && arg0.isShiftDown()) {
			
			if(lastPoint == null) lastPoint = arg0.getPoint();
			
			float x = (float) (lastPoint.getX() - arg0.getPoint().getX()) / (wndInstance.getWidth() / wndInstance.editorInstance.tiles);
			float y =  (float) (lastPoint.getY() - arg0.getPoint().getY()) / (wndInstance.getHeight() / (wndInstance.getContentPane().getHeight() / wndInstance.editorInstance.getTileSize()));
	
			wndInstance.editorInstance.cameraPos.x -= x;
			wndInstance.editorInstance.cameraPos.y -= y;
			wndInstance.repaint();
			lastPoint = arg0.getPoint();
		} else {
			if(wndInstance.editorInstance.selectedTool == Tool.TILE) {
				Texture txt = null;
				if(SwingUtilities.isLeftMouseButton(arg0))
					txt = wndInstance.editorInstance.selectedTexture;
				else if(SwingUtilities.isRightMouseButton(arg0))
					txt = null;
				if(!SwingUtilities.isMiddleMouseButton(arg0)) wndInstance.editorInstance.currentMap.set(arg0.getPoint(),  txt);
			} else if(wndInstance.editorInstance.selectedTool == Tool.SELECT) {
				if(SwingUtilities.isLeftMouseButton(arg0)) {
					if(wndInstance.editorInstance.selectedEntity != null) {
						wndInstance.editorInstance.selectedEntity.position = wndInstance.editorInstance.currentMap.screenToWorldPos(arg0.getPoint());
						wndInstance.editorInstance.currentMapIsSaved = false;
						wndInstance.repaint();
					}
				}
			}
		}
		
		
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		mousePos = arg0.getPoint();
	}

}
