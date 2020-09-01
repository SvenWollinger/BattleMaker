package net.svenwollinger.battlemaker.utils;

import java.io.Serializable;

public class Texture implements Serializable{
	private static final long serialVersionUID = 3568668705120677185L;
	
	public int texturePackID;
	public String texture;
	
	public Texture(int _tpID, String _texture) {
		texturePackID = _tpID;
		texture = _texture;
	}
}
