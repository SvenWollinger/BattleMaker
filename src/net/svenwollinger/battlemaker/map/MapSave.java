package net.svenwollinger.battlemaker.map;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import net.svenwollinger.battlemaker.utils.Texture;
import net.svenwollinger.battlemaker.utils.TexturePack;
import net.svenwollinger.battlemaker.windows.Editor;


public class MapSave implements Serializable{

	private static final long serialVersionUID = 7990599930961299650L;

	public int width;
	public int height;

	//public Texture[][] tiles;
	public ArrayList<Texture[][]> layers = new ArrayList<Texture[][]>();
	
	public ArrayList<Entity> entities;
	
	public ArrayList<String> loadedTexturePacks = new ArrayList<String>();
	
	public MapSave(Map _map) {
		width = _map.getWidth();
		height = _map.getHeight();
		layers = _map.layers;
		entities = _map.entities;
		for(TexturePack tp : _map.texturePacks.values()) {
			loadedTexturePacks.add(tp.location);
		}
	}

	public Map convert(Editor _editorInstance) {
		Map newMap = new Map(width, height, _editorInstance, layers.size());
		newMap.layers = layers;
		newMap.entities = entities;
		
		//Attempt to load texture packs
		if(loadedTexturePacks != null) {
			for(int i = 0; i < loadedTexturePacks.size(); i++) {
				if(new File(loadedTexturePacks.get(i)).exists()) {
					try {
						newMap.loadTexturePack(loadedTexturePacks.get(i));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Texturepack couldnt be loaded: " + loadedTexturePacks.get(i));
				}
			}
		}
		
		return newMap;
	}
	
}
