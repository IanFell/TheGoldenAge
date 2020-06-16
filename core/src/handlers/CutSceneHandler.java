package handlers;

import com.mygdx.mygame.MyGame;

import cutscenes.CutSceneBird;
import cutscenes.CutSceneCauldron;
import cutscenes.CutSceneCutthroat;
import cutscenes.CutSceneFarzenplank;
import cutscenes.CutSceneJollyRoger;
import cutscenes.CutSceneMap;
import gameobjects.weapons.Gun;
import handlers.enemies.BossHandler;
import loaders.bossloader.BossLoader;
import missions.MissionCauldron;
import missions.MissionRawBar;
import missions.MissionThePoint;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneHandler {

	private CutSceneJollyRoger cutSceneJollyRoger;
	private CutSceneCutthroat cutSceneCutthroat;
	private CutSceneFarzenplank cutSceneFarzenplank;
	private CutSceneBird cutSceneBird;
	private CutSceneCauldron cutSceneCauldron;
	private CutSceneMap cutSceneMap;

	public void initializeCutScenes() {
		cutSceneJollyRoger  = new CutSceneJollyRoger("Cutscene Jolly Roger");
		cutSceneCutthroat   = new CutSceneCutthroat("Cutscene Cutthroat");
		cutSceneFarzenplank = new CutSceneFarzenplank("Cutscene Farzenplank");
		cutSceneBird        = new CutSceneBird("Cutscene Bird");
		cutSceneCauldron    = new CutSceneCauldron("Cutscene Cauldron");
		cutSceneMap         = new CutSceneMap("Cutscene Map");
	}

	/**
	 * 
	 * @return CutSceneJollyRoger
	 */
	public CutSceneJollyRoger getCutSceneJollyRoger() {
		return cutSceneJollyRoger;
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void updateCutScenes(MyGame myGame) {
		if (cutSceneJollyRoger.isSelectedCutSceneInProgress()) {
			cutSceneJollyRoger.updateCutScene(myGame);
		}
		if (Gun.hasBeenCollected) {
			cutSceneCutthroat.updateCutScene(myGame);
		}
		if (MissionRawBar.rawBarMissionComplete) {
			cutSceneFarzenplank.updateCutScene(myGame);
		}
		if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead()) {
			cutSceneBird.updateCutScene(myGame);
		}
		if (MissionCauldron.missionCauldronComplete) {
			cutSceneCauldron.updateCutScene(myGame);
		}
		if (MissionThePoint.missionThePointComplete) {
			cutSceneMap.updateCutScene(myGame);
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void renderCutScenes(MyGame myGame) {
		if (cutSceneJollyRoger.isSelectedCutSceneInProgress()) {
			cutSceneJollyRoger.renderCutScene(myGame.renderer.batch,  myGame.imageLoader, myGame);
		}
		if (Gun.hasBeenCollected) {
			cutSceneCutthroat.renderCutScene(myGame);
		}
		if (MissionRawBar.rawBarMissionComplete) {
			cutSceneFarzenplank.renderCutScene(myGame);
		}
		if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead()) {
			cutSceneBird.renderCutScene(myGame);
		}
		if (MissionCauldron.missionCauldronComplete) {
			cutSceneCauldron.renderCutScene(myGame);
		}
		if (MissionThePoint.missionThePointComplete) {
			cutSceneMap.renderCutScene(myGame);
		}
	}
}
