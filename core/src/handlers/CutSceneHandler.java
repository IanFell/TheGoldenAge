package handlers;

import com.mygdx.mygame.MyGame;

import cutscenes.CutSceneBird;
import cutscenes.CutSceneCauldron;
import cutscenes.CutSceneCutthroat;
import cutscenes.CutSceneEnemy;
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

	private int ENEMY_CUTSCENE_DELAY = 500;

	private static CutSceneJollyRoger cutSceneJollyRoger;
	private CutSceneCutthroat cutSceneCutthroat;
	private CutSceneFarzenplank cutSceneFarzenplank;
	private CutSceneBird cutSceneBird;
	private CutSceneCauldron cutSceneCauldron;
	private CutSceneMap cutSceneMap;
	private CutSceneEnemy cutSceneEnemy;

	private int cutSceneEnemyStartTimer = 0;

	public void initializeCutScenes() {
		cutSceneEnemy       = new CutSceneEnemy("Cutscene Enemy");
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
		if (cutSceneJollyRoger.isCutSceneConcluded() && cutSceneEnemyStartTimer < ENEMY_CUTSCENE_DELAY) {
			cutSceneEnemyStartTimer++;
		}

		if (cutSceneJollyRoger.isCutSceneConcluded() && cutSceneEnemyStartTimer == ENEMY_CUTSCENE_DELAY) {
			cutSceneEnemy.updateCutScene(myGame);
		}
		if (Gun.hasBeenCollected) {
			cutSceneCutthroat.updateCutScene(myGame);
		}
		if (MissionRawBar.rawBarMissionComplete) {
			cutSceneFarzenplank.updateCutScene(myGame);
		}
		if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead() && myGame.getGameScreen().getBirdWeapon().hasBeenCollected) {
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
		if (cutSceneJollyRoger.isCutSceneConcluded() && cutSceneEnemyStartTimer == ENEMY_CUTSCENE_DELAY) {
			cutSceneEnemy.renderCutScene(myGame);
		}
		if (Gun.hasBeenCollected) {
			cutSceneCutthroat.renderCutScene(myGame);
		}
		if (MissionRawBar.rawBarMissionComplete) {
			cutSceneFarzenplank.renderCutScene(myGame);
		}
		if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead()  && myGame.getGameScreen().getBirdWeapon().hasBeenCollected) {
			cutSceneBird.renderCutScene(myGame);
		}
		if (MissionCauldron.missionCauldronComplete) {
			cutSceneCauldron.renderCutScene(myGame);
		}
		if (MissionThePoint.missionThePointComplete) {
			cutSceneMap.renderCutScene(myGame);
		}
	}
	
	public static void resetIntroCutscene() {
		cutSceneJollyRoger.setSelectedCutSceneIsInProgress(false);
	}
}
