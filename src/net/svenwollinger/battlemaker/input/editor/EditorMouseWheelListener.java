package net.svenwollinger.battlemaker.input.editor;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.svenwollinger.battlemaker.windows.editor.EditorWindowMain;

public class EditorMouseWheelListener implements MouseWheelListener{

EditorWindowMain wndInstance;
	
	public EditorMouseWheelListener(EditorWindowMain _instance) {
		wndInstance = _instance;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		wndInstance.zoom(arg0.getWheelRotation(), arg0.getPoint());
	}
	
}
