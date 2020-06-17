package com.mygdx.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import factories.GameObjectFactory;
import gameobjects.GameObject;
import handlers.InputHandler;
import handlers.audio.AudioHandler;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import render.Render;
import screens.GameScreen;
import screens.TitleScreen;

/**
 * Main game class.
 * 
 * @author Fabulous Fellini
 *
 */
public class MyGame extends Game {

	/**
	 * Game renderer.
	 */
	public Render renderer = new Render();

	/**
	 * Factory for creating game objects.
	 */
	public GameObjectFactory gameObjectFactory = new GameObjectFactory();

	/**
	 * Creates and initializes game objects.
	 */
	public GameObjectLoader gameObjectLoader = new GameObjectLoader();

	/**
	 * Holds game attributes such as screen size, game state, etc.
	 */
	public GameAttributeHelper gameAttributeHelper;

	private AudioHandler audioHandler = new AudioHandler();

	/**
	 * Loads and holds all images.
	 */
	public ImageLoader imageLoader = new ImageLoader();

	/**
	 * Computer or a game pad handler.
	 */
	public InputHandler inputHandler = new InputHandler();

	public GameScreen gameScreen;
	public TitleScreen titleScreen;

	/**
	 * 
	 * @return GameScreen
	 */
	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * 
	 * @return TitleScreen
	 */
	public TitleScreen getTitleScreen() {
		return titleScreen;
	}

	@Override
	public void create () {
		Gdx.graphics.setContinuousRendering(true);
		Gdx.graphics.setResizable(false);
		imageLoader.init();
		renderer.init();
		gameObjectLoader.createObjects(this);
		audioHandler.init();
		inputHandler.init();
		gameAttributeHelper = new GameAttributeHelper();

		// TODO KEEP THESE IN THIS ORDER SO GameAttributeHelper.gameState doesn't get set to the incorrect value.
		gameScreen          = new GameScreen(this);
		titleScreen         = new TitleScreen(this);
		this.setScreen(titleScreen);
		//this.setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
		gameLoop();
		//GameAttributeHelper.printFramesPerSecond();
	}

	@Override
	public void dispose () {
		renderer.dispose();
		imageLoader.dispose(); 
		audioHandler.dispose();
	}

	/**
	 * 30 FPS.
	 */
	private void gameLoop() {
		inputHandler.handleInput(this);
		audioHandler.handleAudio(this);
	}

	/**
	 * 
	 * @param int object
	 * @return GameObject
	 */
	public GameObject getGameObject(int object) {
		GameObject desiredObject = null;
		switch (object) {
		case GameObject.PLAYER_ONE:
			desiredObject = gameObjectLoader.playerOne;
			break;
		case GameObject.PLAYER_TWO:
			desiredObject = gameObjectLoader.playerTwo;
			break;
		case GameObject.PLAYER_THREE:
			desiredObject = gameObjectLoader.playerThree;
			break;
		}
		return desiredObject;
	}
}
