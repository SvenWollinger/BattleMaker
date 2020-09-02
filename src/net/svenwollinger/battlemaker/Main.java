package net.svenwollinger.battlemaker;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.svenwollinger.battlemaker.windows.MenuWindow;

public class Main {

	public static String version = "20200902_1";
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new MenuWindow();
	}
}
