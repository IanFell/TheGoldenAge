package screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import cutscenes.CutSceneIntro;
import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Gun;
import gameobjects.weapons.MagicPearl;
import handlers.MissionHandler;
import handlers.TownHandler;
import handlers.WeaponHandler;
import handlers.arrowhandler.ArrowHandler;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.HeartHandler;
import handlers.collectibles.RumHandler;
import handlers.enemies.EnemyHandler;
import handlers.enemies.GiantHandler;
import handlers.enemies.GruntHandler;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import input.Input;
import inventory.Inventory;
import loaders.GameObjectLoader;
import loaders.GameWorld;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
import maps.MapLoader;
import maps.MapRenderer;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import physics.Lighting.WeaponShadowHandler;
import physics.Lighting.CollectibleShadowHandler;
import physics.Lighting.LightingHandler;
import physics.Lighting.StructureShadowHandler;
import physics.Weather.LightningBoltHandler;
import physics.Weather.WeatherHandler;
import store.Store;
import ui.MapUi;
import ui.Pause;
import ui.UserInterface;

/**
 * Screen of the game while in play.
 * 
 * @author Fabulous Fellini
 *
 */
public class GameScreen extends Screens {

	private Pause pause = new Pause(0, 0);

	// Map when user pauses game.
	private MapUi mapUi;

	/**
	 * User interface that is always on screen.
	 * This includes, hearts, loot, etc.
	 */
	private UserInterface userInterface;
	//private GuiScreen guiScreen = new GuiScreen(myGame);

	public static int cameraWidth = 10;

	/**
	 * Keeps track if screen has been initialized.
	 */
	private boolean gameScreenHasBeenInitialized;

	/**
	 * Class to render our level maps.
	 */
	private MapRenderer mapRenderer = new MapRenderer();

	/**
	 * Class to draw our level maps.
	 */
	private MapHandler mapHandler = new MapHandler();

	/**
	 * Class to load up the tiles for our level maps.
	 */
	private MapLoader mapLoader = new MapLoader();

	private LightingHandler lightingHandler = new LightingHandler();

	private WeatherHandler weatherHandler = new WeatherHandler();

	/**
	 * Used for transparancy to render clouds.
	 */
	//private ScreenShader screenShader = new ScreenShader(myGame);

	/**
	 * When this screen fades, gameplay starts.
	 */
	//private ScreenShader screenShaderPostIntro = new ScreenShader(myGame);

	/**
	 *  Screen fades in during transitions.
	 */
	//private TransitionScreen transitionScreen = new TransitionScreen(myGame);

	/**
	 * Handles all game missions.
	 */
	private MissionHandler missionHandler;

	/**
	 * We need this to instantiate weapons, however all other 
	 * calls to the weapon handler will deal with static variables.
	 */
	private WeaponHandler weaponHandler = new WeaponHandler();

	public Gun gun;
	public MagicPearl magicPearl;
	public BirdWeapon birdWeapon;

	public EnemyHandler enemyHandler = new EnemyHandler();
	public GruntHandler gruntHandler = new GruntHandler();
	public GiantHandler giantHandler = new GiantHandler();

	public ArrowHandler arrowHandler = new ArrowHandler();

	public TownHandler townHandler = new TownHandler();

	private HeartHandler heartHandler = new HeartHandler();
	private RumHandler rumHandler     = new RumHandler();
	private AmmoHandler ammoHandler   = new AmmoHandler();

	private StructureShadowHandler structureShadowHandler;
	private CollectibleShadowHandler collectibleShadowHandler;
	private WeaponShadowHandler arrowShadowHandler;

	private CutSceneIntro cutSceneIntro;

	private Store store = new Store();

	/**
	 * 
	 * @param MyGame myGame
	 */
	public GameScreen(final MyGame myGame) {
		super(myGame);
		GameAttributeHelper.gameState = Screens.GAME_SCREEN;
		gameScreenHasBeenInitialized  = false;
		cutSceneIntro                 = new CutSceneIntro("Intro");
	}

	/**
	 *
	 * @param float delta
	 */
	@Override
	public void render(float delta) {
		// If game screen has not been initialized, go ahead and initialize it.
		if (!gameScreenHasBeenInitialized) {
			initializeGameScreen();
			gameScreenHasBeenInitialized = !gameScreenHasBeenInitialized;
		}

		// TODO I dunno why I'm using the weatherHandler for this.
		clearScreenAndSetScreenColor(GameAttributeHelper.gameState, weatherHandler);

		// Screen only shakes when needed, but we must update it at all times just in case it needs to shake.
		screenShake.update(delta, camera, myGame.getGameObject(Player.PLAYER_ONE));
		updateCamera();

		// Draw SpriteBatch.
		myGame.renderer.batch.begin();
		renderObjectsOnGameScreenThatUseSpriteBatch();
		myGame.renderer.batch.end();

		// Comment this out for now.  Lets see if the game runs faster.
		// Draw ShapeRenderer.
		/*
		if (!TransitionScreen.isTransitionScreenIsComplete()) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		} 
		myGame.renderer.shapeRenderer.begin(ShapeType.Filled);
		renderObjectsOnGameScreenThatUseShapeRenderer();
		myGame.renderer.shapeRenderer.end(); */

		// If a screenshake happened, reset camera to it's original position before shake.
		if (!weatherHandler.lightningHandler.isLightningShouldBeRendered()) {
			resetCameraAfterScreenShake();
		}

		// Update objects associated with GameScreen.
		if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PLAY) {
			updateGameScreen();
		} 
	}

	/**
	 * Resets camera to it's original position before screenshake.
	 */
	public static void resetCameraAfterScreenShake() {
		camera.position.x           = cameraX;
		camera.position.y           = cameraY;
		ScreenShake.screenIsShaking = false;
	}

	public void initializeGameScreen() {
		mapLoader.loadMap(myGame, mapHandler);
		myGame.getGameObject(GameObject.PLAYER_ONE).init(myGame);
		myGame.getGameObject(GameObject.PLAYER_TWO).init(myGame);
		myGame.getGameObject(GameObject.PLAYER_THREE).init(myGame);
		enemyHandler.init(myGame.imageLoader);
		gruntHandler.init(myGame.imageLoader);
		giantHandler.init(myGame.imageLoader);
		arrowHandler.init();
		weatherHandler.init(myGame, this);
		LightningBoltHandler.init();
		missionHandler = new MissionHandler(myGame);
		mapUi          = new MapUi(myGame);
		userInterface  = new UserInterface();
		gun            = new Gun(
				GameAttributeHelper.CHUNK_THREE_X_POSITION_START - 48, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START - 63, 
				myGame.imageLoader
				);
		magicPearl     = new MagicPearl(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 35, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45
				);
		birdWeapon     = new BirdWeapon(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 12, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 46
				);
		heartHandler.init();
		rumHandler.init();
		ammoHandler.init();

		structureShadowHandler   = new StructureShadowHandler(myGame.imageLoader);
		collectibleShadowHandler = new CollectibleShadowHandler(myGame.imageLoader);
		arrowShadowHandler       = new WeaponShadowHandler();

		Input.initializeInventoryAndPurchasingUiForInput();

		/**
		 * This overlays the game screen and fades out from black.
		 * This makes the transition between screens much smoother.
		 */
		new TransitionScreen(myGame);
		initializeCamera();
	}

	/**
	 * Handle projection matrix and screen shake.
	 */
	@Override
	protected void updateCamera() {
		myGame.renderer.batch.setProjectionMatrix(camera.combined);
		//myGame.renderer.shapeRenderer.setProjectionMatrix(camera.combined);
		if (!ScreenShake.screenIsShaking) {
			if (cutSceneIntro.isSelectedCutSceneInProgress()) {
				camera.position.x = cutSceneIntro.getStartXPosition() + 5;
				camera.position.y = cutSceneIntro.getStartYPosition() + 1;
			} else {
				cameraFollowCurrentPlayer();
			}
		} 
		camera.update();
	}

	private void cameraFollowCurrentPlayer() {
		GameObject player = PlayerController.getCurrentPlayer(myGame);
		camera.position.x = player.getX();
		camera.position.y = player.getY();
	}

	private void initializeCamera() {
		camera = new OrthographicCamera(viewportWidth, verticalHeight);
		camera.setToOrtho(true, viewportWidth, verticalHeight);
		camera.position.x = myGame.getGameObject(GameObject.PLAYER_ONE).getX();
		camera.position.y = myGame.getGameObject(GameObject.PLAYER_ONE).getY();  
		camera.update();
	}

	private void updateGameScreen() {
		if (!TransitionScreen.isTransitionScreenIsComplete()) {
			TransitionScreen.updateObject();
		}
		lightingHandler.lightHandler.updateLighting(myGame.imageLoader);
		weatherHandler.update(myGame, this, mapHandler);
		myGame.getGameObject(GameObject.PLAYER_ONE).updateObject(myGame, mapHandler);
		myGame.getGameObject(GameObject.PLAYER_TWO).updateObject(myGame, mapHandler);
		myGame.getGameObject(GameObject.PLAYER_THREE).updateObject(myGame, mapHandler);
		enemyHandler.updateEnemies(myGame, mapHandler);
		gruntHandler.updateGrunts(myGame, mapHandler);
		giantHandler.updateGiants(myGame, mapHandler);
		arrowHandler.updateArrowHandler(myGame, mapHandler);
		GameWorld.updateGameWorld(myGame, mapHandler);
		mapUi.updateWorldMapUi();
		townHandler.updateTowns(myGame);
		//screenShader.updateObject();

		// Fade into gameplay after intro cutscene.
		/*
		if (cutSceneIntro.isCutSceneConcluded()) {
			screenShaderPostIntro.updateObject();
		} */

		// Start missions after intro cutscene.
		if (cutSceneIntro.isCutSceneConcluded()) {
			missionHandler.handleMissions(myGame, mapHandler);
		}

		WeaponHandler.updateWeapons(myGame, mapHandler);

		if (cutSceneIntro.isSelectedCutSceneInProgress()) {
			cutSceneIntro.updateCutScene();
		}

		heartHandler.updateHearts(myGame, mapHandler);
		rumHandler.updateRum(myGame, mapHandler);
		ammoHandler.updateAmmo(myGame, mapHandler);

		gun.updateObject(myGame, mapHandler);
		magicPearl.updateObject(myGame, mapHandler);
		birdWeapon.updateObject(myGame, mapHandler);

		userInterface.updateUserInterface();

		store.updateStore();

		pause.updateObject(myGame.getGameObject(Player.PLAYER_ONE));
	}

	/**
	 * 
	 * @return TownHandler
	 */
	public TownHandler getTownHandler() {
		return townHandler;
	}

	private void renderObjectsOnGameScreenThatUseSpriteBatch() {
		mapRenderer.renderMapOfChunks(myGame, mapHandler);
		lightingHandler.lightHandler.renderLighting(
				myGame.renderer.batch, 
				myGame.imageLoader, 
				myGame.getGameObject(GameObject.PLAYER_ONE)
				);
		weatherHandler.renderStormCycle(myGame, this);
		lightingHandler.renderShadows(myGame);

		/**
		 * We are not displaying the "inventory screen" here.
		 * This renderes weapons as player has them.
		 */
		myGame.getGameObject(Player.PLAYER_ONE).inventory.renderInventory(
				myGame.renderer.batch, 
				myGame.imageLoader
				);

		structureShadowHandler.renderStructureShadows(myGame.renderer.batch, myGame.imageLoader);
		collectibleShadowHandler.renderCollectibleShadows(myGame.renderer.batch, myGame.imageLoader);
		arrowShadowHandler.renderArrowShadows(myGame.renderer.batch, myGame.imageLoader, myGame);

		GamePlayHelper.sortAndRenderObjectsInYPositionOrder(
				GameObjectLoader.gameObjectList, 
				myGame.renderer.batch, 
				myGame.imageLoader
				);

		// These are not rendered in the game object list so they're not accidently rendered behind other objects.
		heartHandler.renderHearts(
				myGame.renderer.batch, 
				myGame.imageLoader
				);

		// These are not rendered in the game object list so they're not accidently rendered behind other objects.
		rumHandler.renderRum(
				myGame.renderer.batch, 
				myGame.imageLoader
				);

		// These are not rendered in the game object list so they're not accidently rendered behind other objects.
		ammoHandler.renderAmmo(
				myGame.renderer.batch, 
				myGame.imageLoader
				);

		// This shows the border of the towns strictly for debugging.
		//townHandler.renderTownBorders(myGame.renderer.batch, myGame.imageLoader);

		// Rain should be in front of all objects. 
		for (int i = 0; i < weatherHandler.rainHandler.length; i++) {
			weatherHandler.rainHandler[i].renderObject(
					myGame.renderer.batch, 
					myGame.imageLoader,
					this
					);
		}

		missionHandler.renderMissions(
				myGame.renderer.batch, 
				myGame.imageLoader,
				myGame
				);

		WeaponHandler.renderWeapons(
				myGame.renderer.batch, 
				myGame.imageLoader, 
				myGame
				);
		
		arrowHandler.renderArrows(myGame.renderer.batch, myGame.imageLoader);
		/*
		if (!cutSceneIntro.isCutSceneIsInProgress()) {
			guiScreen.render(myGame.renderer.batch, myGame.imageLoader);
		} */

		if (cutSceneIntro.isSelectedCutSceneInProgress()) {
			cutSceneIntro.renderCutScene(
					myGame.renderer.batch,  
					myGame.imageLoader
					);
		}

		store.renderStore(myGame.renderer.batch, myGame.imageLoader, myGame.getGameObject(Player.PLAYER_ONE));

		userInterface.renderUserInterface(
				myGame.renderer.batch,  
				myGame.imageLoader,
				myGame
				);

		/**
		 * The boss' health UI is attached to the boss object.
		 * So, render this here so the health UI isn't rendered behind other game objects
		 * so player can see it at all times.
		 */
		for (int i = 0; i < BossLoader.boss.length; i++) {
			if (!BossLoader.boss[i].isDead()) {
				BossLoader.boss[i].getBossHealthUi().renderBossHealthUi(myGame.renderer.batch, myGame.imageLoader, BossLoader.boss[i]);
			}
		}

		// Here we render the inventory screen if needed.
		if (Inventory.allInventoryShouldBeRendered) {
			myGame.getGameObject(Player.PLAYER_ONE).inventory.renderInventory(
					myGame.renderer.batch, 
					myGame.imageLoader
					);
		}

		mapUi.renderWorldMapUi(myGame.renderer.batch,  myGame.imageLoader, myGame);

		if (!MissionRawBar.phasesAreInProgress && !MissionStumpHole.missionIsActive && !MapUi.mapShouldBeRendered /*&& !Store.storeShouldBeRendered*/) {
			gun.renderObject(myGame.renderer.batch, myGame.imageLoader, myGame);
			magicPearl.renderObject(myGame.renderer.batch, myGame.imageLoader, myGame);
			birdWeapon.renderObject(myGame.renderer.batch, myGame.imageLoader, myGame);
		}

		if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
			pause.renderObject(myGame.renderer.batch, myGame.imageLoader);
		}

		weatherHandler.renderClouds(myGame);
	}

	/*
	private void renderObjectsOnGameScreenThatUseShapeRenderer() {
		screenShader.renderObject(myGame.renderer.shapeRenderer);

		// Fade screen in during transitions of screens.
		if (!TransitionScreen.isTransitionScreenIsComplete()) {
			transitionScreen.renderObject(myGame.renderer.shapeRenderer);
		}

		weatherHandler.renderClouds(myGame);
	} */

	/**
	 * 
	 * @return WeatherHandler
	 */
	public WeatherHandler getWeatherHandler() {
		return weatherHandler;
	}
}
