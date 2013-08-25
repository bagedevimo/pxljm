package goldeneagle.state;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import goldeneagle.ResourceCache;
import goldeneagle.Vec3;
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
	ButtonEntity startButton, quitButton;
	
	int currentlySelected = 0;
	
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
		
		ButtonEntity logo = new ButtonEntity(scene.getRoot(), 256, 256, "assets/menu/once.png", "assets/menu/once.png");
		logo.setLinear(new Vec3(-Display.getWidth() + 100, -Display.getHeight() + 100, 0), Vec3.zero);
		scene.AddEntity(logo);
		
		startButton = new ButtonEntity(scene.getRoot(), 256, 256, "assets/menu/start.png", "assets/menu/start_hover.png");
		startButton.setLinear(new Vec3(-Display.getWidth() + 512 + 150 , -Display.getHeight() + 100, 0), Vec3.zero);
		scene.AddEntity(startButton);
		startButton.setActive(true);
		
		quitButton = new ButtonEntity(scene.getRoot(), 256, 256, "assets/menu/exit.png", "assets/menu/exit_hover.png");
		quitButton.setLinear(new Vec3(-Display.getWidth() + 512 + 150 + 256 , -Display.getHeight() + 100, 0), Vec3.zero);
		scene.AddEntity(quitButton);
		startButton.setActive(false);
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
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			currentlySelected = 0;
			startButton.setActive(true);
			quitButton.setActive(false);
		} else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			currentlySelected = 1;
			startButton.setActive(false);
			quitButton.setActive(true);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			if(currentlySelected == 0)
				this.nextState = new LoadGameState();
			else
				System.exit(0);
		}
	}

	@Override
	protected void draw() {
		SceneManager.doFrame(scene, camera);
	}

}
