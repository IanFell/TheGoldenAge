package com.mygdx.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import cutscenes.CutScene;
import cutscenes.CutSceneBird;
import cutscenes.CutSceneCutthroat;
import debugging.Debugger;
import factories.GameObjectFactory;
import gameobjects.GameObject;
import gameobjects.PoisonPlant;
import gameobjects.collectibles.Ammo;
import gameobjects.collectibles.Heart;
import gameobjects.collectibles.Rum;
import gameobjects.collectibles.TenHearts;
import gameobjects.gamecharacters.enemies.Boss;
import gameobjects.gamecharacters.enemies.Enemy;
import gameobjects.gamecharacters.enemies.Giant;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.Gun;
import handlers.CutSceneHandler;
import handlers.InputHandler;
import handlers.MissionHandler;
import handlers.audio.AudioHandler;
import handlers.audio.SoundHandler;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.RumHandler;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import render.Render;
import screens.ControlsScreen;
import screens.GameScreen;
import screens.Screens;
import screens.TitleScreen;
import store.Store;
import ui.BossHealthUi;
import ui.GameOver;
import ui.LocationMarker;
import ui.ObjectiveUi;
import ui.Win;

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
	public ControlsScreen controlsScreen;

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

	/**
	 * 
	 * @return ControlsScreen
	 */
	public ControlsScreen getControlsScreen() {
		return controlsScreen;
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
		controlsScreen      = new ControlsScreen(this);
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

		if (GamePlayHelper.gameOver) {
			setUpNewGame();
		}
	}

	private void setUpNewGame() {
		audioHandler.dispose();
		//imageLoader.dispose();
		controlsScreen.dispose();
		gameScreen.dispose();
		titleScreen.dispose();
		renderer.dispose();
		GamePlayHelper.gameOver       = false;
		GameOver.triggerGameOver      = false;
		Win.triggerWin                = false;
		GameAttributeHelper.gameState = Screens.TITLE_SCREEN;
		PlayerOne.lives               = 0;
		GameObjectLoader.gameObjectList.clear(); 
		ObjectiveUi.resetGame();
		MissionHandler.resetMissions();
		Store.resetStore();
		
		// TODO THESE ARE NEW THINGS ADDED
		CutScene.resetGame();
		CutSceneBird.resetGame();
		CutSceneCutthroat.resetGame();
		Debugger.resetGame();
		PoisonPlant.resetGame();
		Ammo.resetGame();
		Heart.resetGame();
		Rum.resetGame();
		TenHearts.resetGame();
		Boss.resetGame();
		Enemy.resetGame();
		Giant.resetGame();
		
		// TODO NEW THINGS ADDED STOP

		Gun.hasBeenCollected       = false;
		TradingPost.hasBeenEntered = false;

		getGameScreen().getBirdWeapon().hasBeenCollected = false;

		for (int i = 0; i < BossLoader.boss.length; i++) {
			BossLoader.boss[i].setIsDead(false);
		}

		BossHealthUi.shouldDisplay = false;

		AmmoHandler.ammoCount = 0;
		RumHandler.rumCount   = 0;

		LocationMarker.resetGame();

		CutSceneHandler.resetIntroCutscene();

		SoundHandler.gameOverDeathHasPlayed = false;
		
		getGameObject(Player.PLAYER_ONE).getInventory().inventory.clear();

		this.create();
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
