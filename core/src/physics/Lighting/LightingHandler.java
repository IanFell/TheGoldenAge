package physics.Lighting;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.weapons.Gun;
import loaders.GameObjectLoader;
import missions.MissionRawBar;

/**
 * Handles ALL in game lighting.
 * 
 * @author Fabulous Fellini
 *
 */
public class LightingHandler {

	public LightHandler lightHandler;
	public PlayerShadowHandler shadowHandler;

	/**
	 * Constructor.
	 */
	public LightingHandler() {
		lightHandler  = new LightHandler();
		shadowHandler = new PlayerShadowHandler();
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void renderShadows(MyGame myGame) {
		/**
		 * If we want shadows to dissapear from players at night time, use this if statement.
		 * However, the shadows help the player to know they are jumping. 
		 */
		//if (NightAndDayCycle.isDayTime()) {
		if (GameObjectLoader.gameObjectList.contains(myGame.getGameObject(GameObject.PLAYER_ONE))) {
			shadowHandler.renderLighting(
					myGame.renderer.batch, 
					myGame.imageLoader, 
					myGame.getGameObject(GameObject.PLAYER_ONE)
					);
		}
		if (Player.lifeState == Player.LIFE_STATE_ONE || Player.lifeState == Player.LIFE_STATE_TWO) {
			if (GameObjectLoader.gameObjectList.contains(myGame.getGameObject(GameObject.PLAYER_TWO))) {
				if (Gun.hasBeenCollected) {
					shadowHandler.renderLighting(
							myGame.renderer.batch, 
							myGame.imageLoader, 
							myGame.getGameObject(GameObject.PLAYER_TWO)
							);
				}
			}
		}
		if (Player.lifeState == Player.LIFE_STATE_ONE) {
			if (GameObjectLoader.gameObjectList.contains(myGame.getGameObject(GameObject.PLAYER_THREE))) {
				if (MissionRawBar.rawBarMissionComplete) {
					shadowHandler.renderLighting(
							myGame.renderer.batch, 
							myGame.imageLoader, 
							myGame.getGameObject(GameObject.PLAYER_THREE)
							);
				}
			}
		}
		//}
	}
}
