package net.svenwollinger.battlemaker.utils;

public class Vector2Int {
	private int x;
	private int y;
	
	public Vector2Int(int _x, int _y) {
		x = _x;
		y = _y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void add(Vector2Int _vec2Int) {
		this.x += _vec2Int.x;
		this.y += _vec2Int.y;
	}
	
	public void substract(Vector2Int _vec2Int) {
		this.x -= _vec2Int.x;
		this.y -= _vec2Int.y;
	}
}
