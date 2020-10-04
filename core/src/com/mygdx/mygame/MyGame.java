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
import gameobjects.nature.Feather;
import gameobjects.nature.Stump;
import gameobjects.nature.shockplant.ShockPlant;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Dagger;
import gameobjects.weapons.Gun;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
import gameobjects.weapons.Weapon;
import handlers.CollisionHandler;
import handlers.CutSceneHandler;
import handlers.InputHandler;
import handlers.MissionHandler;
import handlers.arrowhandler.ArrowHandler;
import handlers.audio.AudioHandler;
import handlers.audio.MusicHandler;
import handlers.audio.SoundHandler;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.RumHandler;
import handlers.enemies.BossHandler;
import handlers.holehandler.HoleHandler;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import input.controllers.ControllerInput;
import inventory.Inventory;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import physics.Lighting.Fire;
import physics.Lighting.LightHandler;
import physics.Weather.LightningBoltHandler;
import physics.Weather.NightAndDayCycle;
import physics.Weather.RainHandler;
import physics.Weather.WeatherHandler;
import render.Render;
import screens.ControlsScreen;
import screens.CreditsScreen;
import screens.GameScreen;
import screens.Screens;
import screens.SplashScreen;
import screens.TitleScreen;
import store.Store;
import ui.AddedToInventory;
import ui.BossHealthUi;
import ui.ConfidenceUi;
import ui.DeathUi;
import ui.GameOver;
import ui.InventoryUi;
import ui.LocationMarker;
import ui.ObjectiveUi;
import ui.OutOfAmmo;
import ui.UnlockUi;
import ui.UserInterface;
import ui.Win;
import ui.collectibles.HealthUi;

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
	public CreditsScreen creditsScreen;
	public SplashScreen splashScreen;

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
	 * @return CreditsScreen
	 */
	public CreditsScreen getCreditsScreen() {
		return creditsScreen;
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
		if (InputHandler.inputType == InputHandler.INPUT_ARCADE || InputHandler.inputType == InputHandler.INPUT_CONTROLLER) {
			Gdx.input.setCursorCatched(true);
		}
		gameAttributeHelper = new GameAttributeHelper();

		// TODO KEEP THESE IN THIS ORDER SO GameAttributeHelper.gameState doesn't get set to the incorrect value.
		creditsScreen       = new CreditsScreen(this);
		controlsScreen      = new ControlsScreen(this);
		gameScreen          = new GameScreen(this);
		titleScreen         = new TitleScreen(this);
		splashScreen        = new SplashScreen(this);
		this.setScreen(splashScreen);
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
		splashScreen.dispose();
		renderer.dispose();
		GamePlayHelper.gameOver       = false;
		GameOver.triggerGameOver      = false;
		Win.triggerWin                = false;
		GameAttributeHelper.gameState = Screens.SPLASH_SCREEN;
		GameObjectLoader.gameObjectList.clear(); 
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
		Player.resetGame();
		PlayerOne.resetGame();
		Feather.resetGame();
		Stump.resetGame();
		ShockPlant.resetGame();
		BirdWeapon.resetGame();
		Dagger.resetGame();
		Gun.resetGame();
		MagicPearl.resetGame();
		Paw.resetGame();
		Weapon.resetGame();
		CollisionHandler.resetGame();
		ArrowHandler.resetGame();
		MusicHandler.resetGame();
		SoundHandler.resetGame();
		AmmoHandler.resetGame();
		RumHandler.resetGame();
		BossHandler.resetGame();
		HoleHandler.resetGame();
		// TODO RESET INPUT BASE CLASS IF THINGS ARE MESSED UP
		ControllerInput.resetGame();
		Inventory.resetGame();
		Fire.resetGame();
		LightHandler.resetGame();
		LightningBoltHandler.resetGame();
		NightAndDayCycle.resetGame();
		RainHandler.resetGame();
		WeatherHandler.resetGame();
		AddedToInventory.resetGame();
		BossHealthUi.resetGame();
		ConfidenceUi.resetGame();
		InventoryUi.resetGame();
		OutOfAmmo.resetGame();
		UnlockUi.resetGame();
		UserInterface.resetGame();
		HealthUi.resetGame();
		ObjectiveUi.resetGame();
		DeathUi.resetGame();
		// TODO NEW THINGS ADDED STOP

		TradingPost.hasBeenEntered = false;

		getGameScreen().getBirdWeapon().hasBeenCollected = false;

		for (int i = 0; i < BossLoader.boss.length; i++) {
			BossLoader.boss[i].setIsDead(false);
		}

		LocationMarker.resetGame();

		CutSceneHandler.resetIntroCutscene();

		SoundHandler.gameOverDeathHasPlayed = false;

		GameAttributeHelper.playerHasStartedGame = false;

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
