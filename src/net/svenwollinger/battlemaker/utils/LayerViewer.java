package net.svenwollinger.battlemaker.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;
import net.svenwollinger.battlemaker.Utils;
import net.svenwollinger.battlemaker.windows.editor.EditorWindow;

public class LayerViewer extends JPanel{
	private static final long serialVersionUID = 4070287462570677903L;

	EditorWindow wndInstance;
	
	public LayerViewer(EditorWindow _wndInstance) {
		wndInstance = _wndInstance;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.WHITE);
		Utils.drawCenteredString(g, wndInstance.editorInstance.currentLayer + "", new Rectangle(0,0,this.getWidth(),this.getHeight()), new Font("Monospaced", Font.BOLD, this.getHeight()));
	}
	
	
	
}
