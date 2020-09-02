package net.svenwollinger.battlemaker.windows;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.svenwollinger.battlemaker.StaticResources;
import net.svenwollinger.battlemaker.map.Entity;
import net.svenwollinger.battlemaker.map.Map;
import net.svenwollinger.battlemaker.map.MapSave;
import net.svenwollinger.battlemaker.map.WorldPosition;
import net.svenwollinger.battlemaker.utils.Texture;
import net.svenwollinger.battlemaker.utils.Tool;
import net.svenwollinger.battlemaker.windows.editor.EditorMapProperties;
import net.svenwollinger.battlemaker.windows.editor.EditorTextureViewer;
import net.svenwollinger.battlemaker.windows.editor.EditorWindowMain;

public class Editor {
	public Map currentMap = null;
	public String currentMapSavePath = null; //Used to quickly save the map
	public boolean currentMapIsSaved = false;
	public float tiles = 32;
	public Tool selectedTool = Tool.SELECT;
	
	public WorldPosition cameraPos = new WorldPosition(0,0);
	
	public float zoomSpeed = 3F;
	
	public boolean showDebug = false;
	public boolean showGrid = true;
	public int gridSize = 10;
	
	public Texture selectedTexture = null;
	public Entity selectedEntity = null;
	
	public EditorWindowMain mainWindow;
	public EditorTextureViewer textureViewer;
	public EditorMapProperties propertiesWindow = null;
	
	public int currentLayer = 0;
	
	Editor instance;
	
	public StaticResources res = new StaticResources();
	
	public Editor() {
		final int w = 32, h = 32;
		
		mainWindow = new EditorWindowMain(this);
		currentMap = new Map(w,h, this, 3);
		textureViewer = new EditorTextureViewer(this);
		textureViewer.setLocation(mainWindow.getWidth(), 0);
		cameraPos.x = -(w/2);
		cameraPos.y = -(h/2);

		instance = this;
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu map = new JMenu("Map");
		
		JMenuItem newMap = new JMenuItem("New");
		newMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int w = Integer.parseInt(JOptionPane.showInputDialog("Width:"));
				int h = Integer.parseInt(JOptionPane.showInputDialog("Height:"));
				initMap(new Map(w,h, instance, 3));
			}
		});
		
		JMenuItem openMap = new JMenuItem("Open");
		openMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openMap();
			}
		});
		
		JMenuItem saveMap = new JMenuItem("Save");
		saveMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMap(false);
			}
		});
		
		JMenuItem saveMapAs = new JMenuItem("Save as");
		saveMapAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMap(true);
			}
		});
		
		JMenuItem mapProperties = new JMenuItem("Map Properties");
		mapProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(propertiesWindow == null)
					propertiesWindow = new EditorMapProperties(instance);
				else
					propertiesWindow.requestFocus();
			}
		});
		
		JMenuItem loadTP = new JMenuItem("Load Texturepack");
		loadTP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(new File("").getAbsolutePath() + "\\BattleMaker\\texturepacks\\"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP files", "zip");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(instance.mainWindow);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						instance.currentMap.loadTexturePack(chooser.getSelectedFile().getPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		JMenuItem exportBtn = new JMenuItem("Export as PNG");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage img = currentMap.render(128);
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify a file to save");   
					 
					int userSelection = fileChooser.showSaveDialog(instance.mainWindow);
					 
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						String path = fileChooser.getSelectedFile().getAbsolutePath();
						if(!path.endsWith(".png")) path += ".png";
					    File fileToSave = new File(path);
					    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
					    ImageIO.write(img, "png", fileToSave);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JMenuItem exitBtn = new JMenuItem("Exit");
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		file.add(newMap);
		file.add(openMap);
		file.add(saveMap);
		file.add(saveMapAs);
		map.add(mapProperties);
		map.add(loadTP);
		map.add(exportBtn);
		file.add(exitBtn);
		menuBar.add(file);
		menuBar.add(map);
		mainWindow.setJMenuBar(menuBar);
	}
	
	public int getTilesSafety() {
		return (int)(tiles/4);
	}
	
	public int getTileSize() {
		int pixl = mainWindow.render.getWidth();
		if(pixl < mainWindow.render.getHeight())
			pixl = mainWindow.render.getHeight();
		return (int)( pixl / tiles);
	}

	public int getTransX() {
		int transX = (int)(cameraPos.x * getTileSize());
       	transX += mainWindow.render.getWidth() / 2;
       	return transX;
	}
	
	public int getTransY() {
		int returnVal = (int) (cameraPos.y * getTileSize());
		returnVal += mainWindow.render.getHeight() / 2;
		return returnVal;
	}
	
	public Texture copyTexture(Point _point) {
		Texture texture;
		Entity ent = currentMap.getEntity(_point);
		if(ent != null) {
			texture = ent.texture;
		} else {
			WorldPosition wp = currentMap.screenToWorldPos(_point);
			texture = currentMap.layers.get(currentLayer)[(int) (wp.x + 0.5F)][(int) (wp.y + 0.5F)];
		}
		return texture;
	}
	
	public void saveMapObject(Object serObj, String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("Map " + filepath + " saved!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public Object loadMapObject(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            System.out.println("Map " + filepath + " loaded!");
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	public void initMap(Map _map) {
		currentMap = _map;
		currentMapSavePath = null;
		cameraPos.x = -((float)_map.getWidth()/2);
		cameraPos.y = -((float)_map.getHeight()/2);
		currentMapIsSaved = false;
		textureViewer.updateButtons();
		textureViewer.repaint();
		mainWindow.repaint();
	}
	
	private void saveDialog(String filepath) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		fileChooser.setCurrentDirectory(new File(filepath));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BattleMaker Map files", "bmap");
		fileChooser.setFileFilter(filter);
		int userSelection = fileChooser.showSaveDialog(instance.mainWindow);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    String path = fileChooser.getSelectedFile().getAbsolutePath();
		    if(!path.endsWith(".bmap")) path += ".bmap";
	    	currentMapSavePath = path;
		    saveMapObject(new MapSave(currentMap), path);
		}
	}
	
	private void openDialog(String filepath) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BattleMaker Map files", "bmap");
		chooser.setCurrentDirectory(new File(filepath));
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(instance.mainWindow);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			MapSave save = (MapSave) loadMapObject(chooser.getSelectedFile().getPath());
			initMap(save.convert(instance));
			currentMapSavePath = chooser.getSelectedFile().getPath();
		}
	}
	
	public void openMap() {
		openDialog(new File("").getAbsolutePath() + "\\BattleMaker\\maps\\");
		mainWindow.repaint();
		textureViewer.updateButtons();
		textureViewer.repaint();
		currentMapIsSaved = true; //Shows that map is saved
	}
	
	public void changeLayer(int direction) {
		switch (direction) {
			case 1:
				if(currentLayer < (currentMap.layers.size()-1)) currentLayer++;
				break;
			case -1:
				if(currentLayer > 0) currentLayer--;
				break;
		}
		mainWindow.toolbar.repaintButtons();
	}
	
	public void saveMap(boolean forceDialog) {
		if(currentMapSavePath == null || forceDialog) {
			saveDialog(new File("").getAbsolutePath() + "\\BattleMaker\\maps\\");
		} else {
			saveMapObject(new MapSave(currentMap),currentMapSavePath);
		}
		currentMapIsSaved = true; //Shows that map is saved
	    mainWindow.repaint();
	}
	
}
