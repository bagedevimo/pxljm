package goldeneagle.state;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import goldeneagle.ResourceCache;
import goldeneagle.entities.BackgroundImageEntity;
import goldeneagle.entities.ButtonEntity;
import goldeneagle.scene.Camera;
import goldeneagle.scene.Scene;
import goldeneagle.scene.SceneManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class MenuState extends GameState {

	Scene scene;
	Camera camera;
	
	double fadeIn = 0;
	double fadeInDelta = 0.01;
	private int n = 3;
	Color ambient = Color.BLACK;
	@Override
	protected void init() {
		scene = new Scene(getClock());
		scene.setAmbient(ambient);
		
		camera = new Camera(scene.getRoot());
		
		double ratio = Display.getWidth() / Display.getHeight();
		if(ratio > 1)
			camera.setRadius(540);
		else
			camera.setRadius(960);

		BackgroundImageEntity bg = new BackgroundImageEntity(scene.getRoot(), "assets/menu/bg.jpg");
		scene.AddEntity(bg);
		
		ButtonEntity startButton = new ButtonEntity(scene.getRoot(), 256, 256, "assets/menu/start.png", "assets/menu/start_hover.png");
		scene.AddEntity(startButton);
	}

	@Override
	protected void update(double deltaTime) {
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			 this.nextState = new LoadGameState();
		
		if(ambient != Color.WHITE && n < 0) {
			n = 3;
			ambient = ambient.brighter();
		}
		else if(ambient != Color.WHITE)
			n--;
		scene.setAmbient(ambient);
	}

	@Override
	protected void draw() {
		SceneManager.doFrame(scene, camera);
	}

}
