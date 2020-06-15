package handlers;

import com.mygdx.mygame.MyGame;

import cutscenes.CutSceneCutthroat;
import cutscenes.CutSceneJollyRoger;
import gameobjects.weapons.Gun;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneHandler {

	private CutSceneJollyRoger cutSceneJollyRoger;
	private CutSceneCutthroat cutSceneCutthroat;

	public void initializeCutScenes() {
		cutSceneJollyRoger = new CutSceneJollyRoger("Cutscene Jolly Roger");
		cutSceneCutthroat  = new CutSceneCutthroat("Cutscene Cutthroat");
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
	}
}
