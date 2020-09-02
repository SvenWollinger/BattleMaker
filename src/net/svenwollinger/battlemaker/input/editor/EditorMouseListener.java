package net.svenwollinger.battlemaker.input.editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.svenwollinger.battlemaker.map.Entity;
import net.svenwollinger.battlemaker.windows.Editor;
import net.svenwollinger.battlemaker.windows.editor.EditorWindow;

public class EditorMouseListener implements MouseListener{

	EditorWindow wndInstance;
	
	public EditorMouseListener(EditorWindow _instance) {
		wndInstance = _instance;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Editor editor = wndInstance.editorInstance;
		if(arg0.getButton() == MouseEvent.BUTTON1) {
			switch(editor.selectedTool) {
				case TILE:
					editor.currentMap.set(arg0.getPoint(), editor.selectedTexture);
					break;
				case ENTITY:
					editor.currentMap.addEntity(arg0.getPoint());
					break;
				default:
					break;
			}
		} else if(arg0.getButton() == MouseEvent.BUTTON3) {
			switch(editor.selectedTool) {
			case ENTITY:
				editor.currentMap.set(arg0.getPoint(), null);
				break;
			default:
				break;
		}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1)
			wndInstance.mouseMotionListener.dragEnabled = true;
		
		Entity ent = wndInstance.editorInstance.currentMap.getEntity(arg0.getPoint());
		if(ent != null)
			wndInstance.editorInstance.selectedEntity = ent;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1)
			wndInstance.mouseMotionListener.dragEnabled = false;
		
		wndInstance.mouseMotionListener.lastPoint = null;
		wndInstance.editorInstance.selectedEntity = null;
	}

}
