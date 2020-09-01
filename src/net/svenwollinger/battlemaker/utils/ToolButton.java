package net.svenwollinger.battlemaker.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.svenwollinger.battlemaker.windows.editor.Toolbar;

public class ToolButton extends JButton{
	private static final long serialVersionUID = 7102395166776038642L;

	String name;
	Toolbar toolbarInstance;
	Tool tool;
	
	@SuppressWarnings("deprecation") //why does eclipse tell me this is not used? setname doesnt work
	public ToolButton(String _name, Toolbar _toolbar, Tool _tool) {
		name = _name;
		toolbarInstance = _toolbar;
		tool = _tool;
		this.setLabel(name);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tool == Tool.TILE) toolbarInstance.wndInstance.editorInstance.textureViewer.requestFocus();
				
				toolbarInstance.wndInstance.editorInstance.selectedTool = tool;
				//If we dont do this the keylistener breaks, cuz hes not in focus.
				toolbarInstance.wndInstance.requestFocus();
			}
		});
	}
	
}
