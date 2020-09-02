package net.svenwollinger.battlemaker.input.editor;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.svenwollinger.battlemaker.windows.editor.EditorWindow;

public class EditorMouseWheelListener implements MouseWheelListener{

EditorWindow wndInstance;
	
	public EditorMouseWheelListener(EditorWindow _instance) {
		wndInstance = _instance;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		wndInstance.zoom(arg0.getWheelRotation(), arg0.getPoint());
	}
	
}
