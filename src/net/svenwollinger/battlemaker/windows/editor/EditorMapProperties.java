package net.svenwollinger.battlemaker.windows.editor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import net.svenwollinger.battlemaker.windows.Editor;

public class EditorMapProperties extends JFrame  implements WindowListener {

	private static final long serialVersionUID = 1L;

	Editor editorInstance;
	
	public EditorMapProperties(Editor _instance) {
		editorInstance = _instance;
		this.setTitle("Map Properties");
		this.setSize(512,512);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.addWindowListener(this);
	}
	
	public void kill() {
		this.dispose();
		editorInstance.propertiesWindow = null;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {	 }

	@Override
	public void windowClosed(WindowEvent arg0) {
		kill();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		kill();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) { }

	@Override
	public void windowDeiconified(WindowEvent arg0) { }

	@Override
	public void windowIconified(WindowEvent arg0) { }

	@Override
	public void windowOpened(WindowEvent arg0) { }
		
}
