package loaders;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import handlers.enemies.BossHandler;
import loaders.barloader.BarLoader;
import loaders.bossloader.BossLoader;
import loaders.boulderloader.BoulderLoader;
import loaders.cannonloader.CannonLoader;
import loaders.chestloader.ChestLoader;
import loaders.dockloader.DockLoader;
import loaders.fireloader.FireLoader;
import loaders.floatingboatloader.FloatingBoatLoader;
import loaders.flowerloader.FlowerLoader;
import loaders.flyingbirdloader.FlyingBirdLoader;
import loaders.hangingloader.HangingLoader;
import loaders.lighthouseloader.LightHouseLoader;
import loaders.logloader.LogLoader;
import loaders.marketloader.MarketLoader;
import loaders.pigglywigglyloader.PigglyWigglyLoader;
import loaders.plantloaders.PlantLoader;
import loaders.poisonplantloader.PoisonPlantLoader;
import loaders.quicksandloader.QuickSandLoader;
import loaders.rawbarloader.RawBarLoader;
import loaders.roastloader.RoastLoader;
import loaders.rockloader.RockLoader;
import loaders.scallopcoveloader.ScallopCoveLoader;
import loaders.shockplantloader.ShockPlantLoader;
import loaders.signloader.SignLoader;
import loaders.stationaryboatsloader.StationaryBoatsLoader;
import loaders.stumploader.StumpLoader;
import loaders.teepeeloader.TeePeeLoader;
import loaders.tradingpostloader.TradingPostLoader;
import loaders.treeloaders.DrSuessTreeLoader;
import loaders.treeloaders.TreeLoader;
import maps.MapHandler;
import missions.MissionStumpHole;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class GameWorld {

	private static FloatingBoatLoader floatingBoatLoader;
	private StationaryBoatsLoader stationaryBoatsLoader;
	private MarketLoader marketLoader;
	private RoastLoader roastLoader;
	private HangingLoader hangingLoader;
	private BarLoader barLoader;
	private static FlyingBirdLoader flyingBirdLoader;
	private PoisonPlantLoader poisonPlantLoader;
	private ShockPlantLoader shockPlantLoader;
	private LogLoader logLoader;
	private TreeLoader treeLoader;
	private DrSuessTreeLoader drSuessTreeLoader;
	private PlantLoader plantLoader;
	private ChestLoader chestLoader;
	private TeePeeLoader teePeeLoader;
	private RawBarLoader rawBarLoader;
	private TradingPostLoader tradingPostLoader;
	private LightHouseLoader lightHouseLoader;
	private FireLoader fireLoader;
	private PigglyWigglyLoader pigglyWigglyLoader;
	private RockLoader rockLoader;
	private BoulderLoader boulderLoader;
	private FlowerLoader flowerLoader;
	private SignLoader signLoader;
	private DockLoader dockLoader;
	private CannonLoader cannonLoader;
	private StumpLoader stumpLoader;
	private QuickSandLoader quickSandLoader;
	private BossLoader bossLoader;
	private ScallopCoveLoader scallopCoveLoader;

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public GameWorld(MyGame myGame) {
		floatingBoatLoader    = new FloatingBoatLoader();
		stationaryBoatsLoader = new StationaryBoatsLoader();
		marketLoader          = new MarketLoader();
		roastLoader           = new RoastLoader();
		flyingBirdLoader      = new FlyingBirdLoader();
		poisonPlantLoader     = new PoisonPlantLoader();
		shockPlantLoader      = new ShockPlantLoader();
		logLoader             = new LogLoader();
		treeLoader            = new TreeLoader();
		drSuessTreeLoader     = new DrSuessTreeLoader();
		plantLoader           = new PlantLoader();
		chestLoader           = new ChestLoader();
		teePeeLoader          = new TeePeeLoader();
		rawBarLoader          = new RawBarLoader();
		lightHouseLoader      = new LightHouseLoader();
		fireLoader            = new FireLoader();
		pigglyWigglyLoader    = new PigglyWigglyLoader();
		rockLoader            = new RockLoader();
		boulderLoader         = new BoulderLoader();
		flowerLoader          = new FlowerLoader();
		signLoader            = new SignLoader();
		dockLoader            = new DockLoader();
		tradingPostLoader     = new TradingPostLoader();
		cannonLoader          = new CannonLoader();
		stumpLoader           = new StumpLoader();
		quickSandLoader       = new QuickSandLoader();
		bossLoader            = new BossLoader();
		barLoader             = new BarLoader();
		hangingLoader         = new HangingLoader();
		scallopCoveLoader     = new ScallopCoveLoader();
		loadGameWorld(myGame);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void loadGameWorld(MyGame myGame) {
		floatingBoatLoader.loadFloatingBoats();
		stationaryBoatsLoader.loadStationaryBoats(myGame);
		marketLoader.loadMarket(myGame);
		roastLoader.loadRoastLoader();
		poisonPlantLoader.loadPoisonPlants();
		shockPlantLoader.loadShockPlants();
		logLoader.loadLogs();
		treeLoader.loadTrees();
		drSuessTreeLoader.loadTrees();
		plantLoader.loadPlants();
		rockLoader.loadRocks();
		boulderLoader.loadBoulders();
		flowerLoader.loadFlowers();
		chestLoader.loadChests();
		teePeeLoader.loadTeePees(myGame);
		rawBarLoader.loadRawBar(myGame);
		lightHouseLoader.loadLightHouse(myGame);
		fireLoader.loadFire();
		pigglyWigglyLoader.loadPigglyWiggly(myGame);
		signLoader.loadSigns();
		dockLoader.loadDocks(myGame);
		tradingPostLoader.loadTradingPost(myGame);
		cannonLoader.loadCannons(myGame);
		stumpLoader.loadStumps();
		quickSandLoader.loadQuickSand();
		bossLoader.loadBosses();
		barLoader.loadBar(myGame);
		hangingLoader.loadHangingLoader();
		scallopCoveLoader.loadScallopCove(myGame);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public static void updateGameWorld(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < ChestLoader.chests.length; i++) {
			ChestLoader.chests[i].updateObject(myGame, mapHandler);
		}
		for (int i = 0; i < TeePeeLoader.teePees.length; i++) {
			TeePeeLoader.teePees[i].updateObject(myGame, mapHandler);
		}
		for (int i = 0; i < FireLoader.fires.length; i++) {
			FireLoader.fires[i].updateObject(myGame, mapHandler);
		}
		for (int i = 0; i < FireLoader.fires.length; i++) {
			FireLoader.fires[i].updateObject(myGame, mapHandler);
		}
		for (int i = 0; i < QuickSandLoader.quickSand.length; i++) {
			QuickSandLoader.quickSand[i].updateObject(myGame, mapHandler);
		}
		GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
		for (int i = 0; i < ShockPlantLoader.AMOUNT_OF_SHOCK_PLANTS; i++) {
			ShockPlantLoader.updateShockPlants(player);
		}
		for (int i = 0; i < PoisonPlantLoader.AMOUNT_OF_POISON_PLANTS; i++) {
			PoisonPlantLoader.updatePoisonPlants(player);
		}
		for (int i = 0; i < RoastLoader.AMOUNT_OF_ROASTS; i++) {
			RoastLoader.updateRoasts(myGame, mapHandler);
		}

		if (!MissionStumpHole.missionIsActive) {
			for(int i = 0; i < CannonLoader.cannons.length; i++) {	
				CannonLoader.cannons[i].updateObject(myGame, mapHandler);
			}
		}

		/**
		 * Since the stump hole mission is drawn directly over these game world stumps,
		 * do not update game world stumps during stump hole mission.  
		 * This is so stump hole player cannot get stuck on these stumps which will take them above the screen
		 * and soft crash the game.
		 */
		for(int i = 0; i < StumpLoader.stumps.size(); i++) {
			if (!MissionStumpHole.missionIsActive) {
				StumpLoader.stumps.get(i).updateObject(myGame, mapHandler);
			}
		}
		RawBarLoader.rawbar.updateObject(myGame, mapHandler);
		BarLoader.bar.updateObject(myGame, mapHandler);
		PigglyWigglyLoader.pigglyWiggly.updateObject(myGame, mapHandler);
		TradingPostLoader.tradingPost.updateObject(myGame, mapHandler);
		ScallopCoveLoader.scallopCove.updateObject(myGame, mapHandler);

		// Update bosses.
		BossHandler.handleBosses(myGame, mapHandler);

		flyingBirdLoader.updateFlyingBirds();

		MarketLoader.market.updateObject(myGame, mapHandler);

		floatingBoatLoader.updateFloatingBoats(myGame, mapHandler);
	}
}
