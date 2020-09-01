package net.svenwollinger.battlemaker.utils;

import java.awt.Image;
import java.util.HashMap;

public class TexturePack {

	public Image icon;
	public String name;
	public String author;
	public int id;
	public String location;
	
	private HashMap<String, Image> images = new HashMap<String, Image>();
	
	public TexturePack() { }
	
	public void addImage(Image _image, String _key) {
		System.out.println("Adding image " + _key);
		images.put(_key, _image);
	}
	
	public Image getImage(String _key) {
		return images.get(_key);
	}
}
