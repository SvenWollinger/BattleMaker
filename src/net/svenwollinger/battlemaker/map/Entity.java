package net.svenwollinger.battlemaker.map;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

import net.svenwollinger.battlemaker.utils.Texture;
import net.svenwollinger.battlemaker.windows.Editor;

public class Entity implements Serializable{
	private static final long serialVersionUID = 3165447622811964586L;
	
	public WorldPosition position;
	public Texture texture;
	public float sizeX = 1F;
	public float sizeY = 1F;
	
	public Entity(float _x, float _y, Texture _texture, float _sizeX, float _sizeY) {
		position = new WorldPosition(_x, _y);
		this.texture = _texture;
		this.sizeX = _sizeX;
		this.sizeY = _sizeY;
	}
	
	/*
	 * Easy function to draw entitys, pass the graphics and the tileSize and boom. Entity.
	 * TODO: add the selection outline again, when entity is selected
	 */
	public void draw(Graphics g, final int tileSize, final Editor _editor) {
		final int addX = (int) (tileSize - (sizeX * tileSize)) /2;
		final int addY = (int) (tileSize - (sizeY * tileSize)) /2;
		
		final Texture txt = texture;
		Image imgTemp = _editor.res.missingEntTexture.getImage();
	
		if(txt != null) {
			if(_editor.currentMap.texturePacks.get(txt.texturePackID) != null) {
				imgTemp = _editor.currentMap.texturePacks.get(txt.texturePackID).getImage(txt.texture);
			}
			g.drawImage(imgTemp, (int)(position.x * tileSize) + addX, (int)(position.y * tileSize) + addY, (int)(tileSize * sizeX), (int)(tileSize * sizeY), null);
		}
	}
	
}
