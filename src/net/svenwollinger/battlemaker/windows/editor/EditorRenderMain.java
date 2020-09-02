package net.svenwollinger.battlemaker.windows.editor;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

import net.svenwollinger.battlemaker.map.Map;
import net.svenwollinger.battlemaker.map.WorldPosition;
import net.svenwollinger.battlemaker.utils.Texture;

public class EditorRenderMain extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	EditorWindowMain wndInstance;
	
	public EditorRenderMain(EditorWindowMain _instance) {
		this.wndInstance = _instance;
	}

	@Override
	public void paint(Graphics g) {
		if(wndInstance.editorInstance.currentMap != null) {
			String loadedMap = "New Map";
			String loadedMapIsSaved = "*";
			if(wndInstance.editorInstance.currentMapIsSaved) loadedMapIsSaved = "";
			if(wndInstance.editorInstance.currentMapSavePath != null) loadedMap = wndInstance.editorInstance.currentMapSavePath;
			wndInstance.setTitle("BattleMaker - " + loadedMapIsSaved + loadedMap);
			
			final Map cMap = wndInstance.editorInstance.currentMap;
			
			final int tileSize = wndInstance.editorInstance.getTileSize();

			final int transX = wndInstance.editorInstance.getTransX();
			final int transY = wndInstance.editorInstance.getTransY();
		
			g.translate(transX, transY);
			
			float tileAmountTemp = (wndInstance.editorInstance.tiles + wndInstance.editorInstance.getTilesSafety()) / 2;
			
			if(tileAmountTemp <= 5) //This prevents us from not seeing stuff when we are too far zoomed in
				tileAmountTemp = 5;
			
			float tileAmountX = tileAmountTemp;
			float tileAmountY = tileAmountTemp;
			
			/*
			 * If the window is close to being a cube, we dont change the tileAmount.
			 * If thats not the case we either modify the x or y value which defines how many tiles are drawn on either axis
			 * this prevents us from rendering stuff the user cant see.
			 */
			final float aspectRatio = (float) this.getHeight() / (float) this.getWidth();
			if(aspectRatio >= 1.2F || aspectRatio < 0.7F) {
				if(this.getHeight() > this.getWidth())
					tileAmountX /= 1.5F;
				else
					tileAmountY /= 1.5F;
			}
			
			final int cPosX = (int) wndInstance.editorInstance.cameraPos.x;
			final int cPosY = (int) wndInstance.editorInstance.cameraPos.y;
			for(int y = (int) -tileAmountY; y < tileAmountY; y++) {
				for(int x = (int) -tileAmountX; x < tileAmountX; x++) {
					
					final int tileX = -cPosX + x;
					final int tileY = -cPosY + y;
					
					if(tileX >= 0 && tileY >= 0 && tileX < cMap.getWidth() && tileY < cMap.getHeight()) {
						for(int i = 0; i < cMap.layers.size(); i++) {
							final Texture txt = cMap.layers.get(i)[tileX][tileY];
							Image img = wndInstance.editorInstance.res.missingTexture.getImage();
						
							if(txt != null) {
								if(cMap.texturePacks.get(txt.texturePackID) != null) {
									if(cMap.texturePacks.get(txt.texturePackID).getImage(txt.texture) != null)
										img = cMap.texturePacks.get(txt.texturePackID).getImage(txt.texture);
								}
								g.drawImage(img, tileX * tileSize, tileY * tileSize, tileSize, tileSize, null);
							}
						}
					}
						
				}		
			}
			
			for(int i = 0; i < cMap.entities.size(); i++) {
				cMap.entities.get(i).draw(g, tileSize, wndInstance.editorInstance);
			}
			
			Graphics2D g2 = (Graphics2D) g;
			int thick = (int)(wndInstance.editorInstance.gridSize / wndInstance.editorInstance.tiles);
			g2.setStroke(new BasicStroke(thick));	
			if(wndInstance.editorInstance.showGrid) {
				for(int x = (int) -tileAmountX; x < tileAmountX; x++) {
					final int tileX = -cPosX + x;
					if(tileX >= 0 && tileX < cMap.getWidth() + 1)
						g.drawLine(tileX * tileSize, 0, tileX * tileSize, cMap.getHeight() * tileSize);
				}
				for(int y = (int) -tileAmountY; y < tileAmountY; y++) {
					final int tileY = -cPosY + y;
					if(tileY >= 0 && tileY < cMap.getHeight() + 1)
						g.drawLine(0, tileY * tileSize, cMap.getWidth() * tileSize, tileY * tileSize);
				}
			} else {
				g2.drawRect(0,0,cMap.getWidth() * tileSize, cMap.getHeight() * tileSize);
			}
			
			
			if(wndInstance.editorInstance.showDebug) {
				final WorldPosition mousePos = cMap.screenToWorldPos(wndInstance.mouseMotionListener.mousePos);
				g.setFont(new Font("TimesRoman", Font.PLAIN, this.getHeight()/15)); 
				final String debugString = "CameraX: " + -cPosX + "\n" +
							         "CameraY: " + -cPosY + "\n" +
							         "Zoom: " + wndInstance.editorInstance.tiles + "\n" +
							         "Mouse X: " + mousePos.x + "\n" +
							         "Mouse Y: " + mousePos.y + "\n" +
							         "Layer: " + wndInstance.editorInstance.currentLayer;
				drawString(g, debugString, -transX, -transY);
			}
			if(wndInstance.editorInstance.showDebug) wndInstance.repaint();// ->Enable this in case of debugging, so that the thing always renders
		}
	}
	
	void drawString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

}
