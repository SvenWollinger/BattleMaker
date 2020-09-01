package net.svenwollinger.battlemaker.map;

import java.io.Serializable;

public class WorldPosition implements Serializable{
	private static final long serialVersionUID = 6031565734117977040L;
	
	public float x;
	public float y;
	
	public WorldPosition(float _x, float _y) {
		x = _x;
		y = _y;
	}
	
}
