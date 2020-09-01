package net.svenwollinger.battlemaker.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import net.svenwollinger.battlemaker.utils.Texture;
import net.svenwollinger.battlemaker.utils.TexturePack;
import net.svenwollinger.battlemaker.utils.Vector2Int;
import net.svenwollinger.battlemaker.windows.Editor;

public class Map {
	private int width;
	private int height;
	
	public ArrayList<Texture[][]> layers = new ArrayList<Texture[][]>();
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	Editor editorInstance;
	
	public HashMap<Integer, TexturePack> texturePacks = new HashMap<Integer, TexturePack>();
	public ArrayList<Texture> textureList = new ArrayList<Texture>();
	
	public Map(int _width, int _height, Editor _instance, int layerCount) {
		this.width = _width;
		this.height = _height;
		for(int i = 0; i < layerCount; i++)
			layers.add(new Texture[_width][_height]);
		this.editorInstance = _instance;
	}
	
	/*
	 *	We get the total pixel position from a point on the screen
	 *	this is not a world position, but how many pixels rendered the point would be
	 */
	public Vector2Int getTotalPixelPosition(Point _point) {
		int transX = editorInstance.getTransX();
		int transY = editorInstance.getTransY();
		
		int pointX = (int) _point.getX();
		int pointY = (int) _point.getY();

		return new Vector2Int(-transX + pointX, -transY + pointY);
	}
	
	/*
	 * Simple really, calculate a world position from a point on the screen
	 */
	public WorldPosition screenToWorldPos(Point _point) {
		Vector2Int total = getTotalPixelPosition(_point);
		int tileSize = editorInstance.getTileSize();
		float x = ((float) total.getX() / (float) tileSize) -0.5F; //We add 0.5 so that its the actual middle pos of a grid, not the top left.
		float y = ((float) total.getY() / (float) tileSize) -0.5F; 
		return new WorldPosition(x,y);
	}
	
	//Find an entity by position on screen
	public Entity getEntity(Point pos) {
		for(int i = 0; i < entities.size(); i++) {
			Entity ent = entities.get(i);
			int tileSize = editorInstance.getTileSize();
			
			Vector2Int total = getTotalPixelPosition(pos);
			
			Point p = new Point(total.getX(), total.getY());
			
			int addX = (int) (tileSize - (ent.sizeX * tileSize)) /2; //we have to add this, in order to manage offset
			int addY = (int) (tileSize - (ent.sizeY * tileSize)) /2;
			
			Rectangle rect = new Rectangle((int)(ent.position.x * tileSize) + addX,(int)(ent.position.y * tileSize) + addY,(int)(tileSize * ent.sizeX),(int)(tileSize * ent.sizeY));
			if(rect.contains(p))
				return ent;
		}
		return null;
	}
	
	//Remove entity by position
	public void removeEntity(Point pos) {
		Entity ent = getEntity(pos);
		if(ent != null) entities.remove(ent);
		editorInstance.currentMapIsSaved = false;
		editorInstance.mainWindow.repaint();
	}
	
	//Set tile
	public void set(Point point, Texture value) {
		WorldPosition p = screenToWorldPos(point);
		int x = (int) (p.x + 0.5F);
		int y = (int) (p.y + 0.5F);
		if(x >= 0 && y >= 0 && x < width && y < height) {
			layers.get(editorInstance.currentLayer)[x][y] = value;
		}
		editorInstance.currentMapIsSaved = false;
		editorInstance.mainWindow.repaint();
	}
	
	//Add Entity
	public void addEntity(Point mousePos) {
		WorldPosition pos = screenToWorldPos(mousePos);
		float sX = Float.parseFloat(JOptionPane.showInputDialog("Width:"));
		float sY = Float.parseFloat(JOptionPane.showInputDialog("Height:"));
		//TODO: Allow clamping
		if(editorInstance.selectedTexture != null) {
			editorInstance.currentMap.entities.add(new Entity(pos.x,pos.y, editorInstance.selectedTexture, sX, sY));
			editorInstance.currentMapIsSaved = false;
		}
		editorInstance.mainWindow.repaint();
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public BufferedImage render(int tileSize) {
		BufferedImage img = new BufferedImage(width * tileSize, height * tileSize, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				for(int i = 0; i < layers.size(); i++) {
					Texture txt = layers.get(i)[x][y];
					if(txt != null) {
						Image imgTemp = editorInstance.missingTexture.getImage();
						if(texturePacks.get(txt.texturePackID) != null) {
							imgTemp = texturePacks.get(txt.texturePackID).getImage(txt.texture);
						}
						g.drawImage(imgTemp, x * tileSize, y * tileSize, tileSize, tileSize, null);
					}
				}
			}
		}
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g, tileSize, editorInstance);
		}
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		int thick = (int)(editorInstance.gridSize / editorInstance.tiles);
		g2.setStroke(new BasicStroke(thick));	
		if(editorInstance.showGrid) {
			for(int x = 0; x < width; x++) {
					g.drawLine(x * tileSize, 0, x * tileSize, height * tileSize);
			}
			for(int y = 0; y < height; y++) {
				g.drawLine(0, y * tileSize, width * tileSize, y * tileSize);
			}
		} else {
			g2.drawRect(0,0,width * tileSize, height * tileSize);
		}
		
		g.dispose();
		return img;
	}
	
	public void loadTexturePack(String location) throws IOException {
		TexturePack texturePack = new TexturePack();
		
		ZipFile zipFile = new ZipFile(location);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		texturePack.location = zipFile.getName();
		while(entries.hasMoreElements()){
		    ZipEntry entry = entries.nextElement();
		    if(!entry.isDirectory()){
		    	InputStream stream = zipFile.getInputStream(entry);
		    	
		    	if(entry.getName().equals("properties.txt")) {
			    	int ch;
			    	StringBuilder sb = new StringBuilder();
			    	while((ch = stream.read()) != -1)
			    	    sb.append((char)ch);
			    	
			    	String[] lines = sb.toString().split("\\n");
			    	int i = 0;
			    	for(String s: lines){
			    		switch(i) {
				    		case 0:
				    			texturePack.name = s;
				    			break;
				    		case 1:
				    			texturePack.author = s;
				    			break;
				    		case 2:
				    			texturePack.id = Integer.parseInt(s);
				    			break;
			    		}		
			    		i++;
			    	}
		    	} else if(entry.getName().equals("icon.png")) {
		    		texturePack.icon = ImageIO.read(stream);
		    	} else if(entry.getName().contains(".png") && !entry.getName().equals("icon.png")){
		    		texturePack.addImage(ImageIO.read(stream), entry.getName());
	    			textureList.add(new Texture(texturePack.id, entry.getName()));		
		    	}
		    	stream.close();
		    }
		    
		}
		zipFile.close();
		
		if(texturePacks.get(texturePack.id) != null) {
			JOptionPane.showMessageDialog(editorInstance.mainWindow, "Error: Texturepack already loaded!");
		} else {
			texturePacks.put(texturePack.id, texturePack);
			editorInstance.textureViewer.updateButtons();
			editorInstance.textureViewer.repaint();
			editorInstance.mainWindow.repaint();
		}
	}
	
	public void addLayer() {
		layers.add(new Texture[width][height]);
	}
	
	public void removeLayer() {
		if(layers.size() == 1) {
			JOptionPane.showMessageDialog(null, "You cant delete the last layer!");
		} else {
			layers.remove(editorInstance.currentLayer);
			if(editorInstance.currentLayer > layers.size()-1)
				editorInstance.changeLayer(-1);
		}
		editorInstance.mainWindow.repaint();
	}
	
}
