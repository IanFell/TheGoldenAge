package handlers;

import com.mygdx.mygame.MyGame;

import cutscenes.CutSceneCutthroat;
import cutscenes.CutSceneFarzenplank;
import cutscenes.CutSceneJollyRoger;
import gameobjects.weapons.Gun;
import missions.MissionRawBar;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneHandler {

	private CutSceneJollyRoger cutSceneJollyRoger;
	private CutSceneCutthroat cutSceneCutthroat;
	private CutSceneFarzenplank cutSceneFarzenplank;

	public void initializeCutScenes() {
		cutSceneJollyRoger  = new CutSceneJollyRoger("Cutscene Jolly Roger");
		cutSceneCutthroat   = new CutSceneCutthroat("Cutscene Cutthroat");
		cutSceneFarzenplank = new CutSceneFarzenplank("Cutscen Farzenplank");
	}

	/**
	 * 
	 * @return CutSceneJollyRoger
	 */
	public CutSceneJollyRoger getCutSceneJollyRoger() {
		return cutSceneJollyRoger;
	}

	public void updateCutScenes() {
		if (cutSceneJollyRoger.isSelectedCutSceneInProgress()) {
			cutSceneJollyRoger.updateCutScene();
		}
		if (Gun.hasBeenCollected) {
			cutSceneCutthroat.updateCutScene();
		}
		if (MissionRawBar.rawBarMissionComplete) {
			cutSceneFarzenplank.updateCutScene();
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void renderCutScenes(MyGame myGame) {
		if (cutSceneJollyRoger.isSelectedCutSceneInProgress()) {
			cutSceneJollyRoger.renderCutScene(myGame.renderer.batch,  myGame.imageLoader);
		}
		if (Gun.hasBeenCollected) {
			cutSceneCutthroat.renderCutScene(myGame);
		}
		if (MissionRawBar.rawBarMissionComplete) {
			cutSceneFarzenplank.renderCutScene(myGame);
		}
	}
}
