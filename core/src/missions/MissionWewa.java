package missions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import handlers.CollisionHandler;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;
import ui.LocationMarker;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MissionWewa extends Mission {

	private LocationMarker locationMarker;

	public static boolean wewaMissionComplete = false;

	/**
	 * Constructor.
	 */
	public MissionWewa() {
		locationMarker = new LocationMarker(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 63, 11);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void updateMission(MyGame myGame) {
		locationMarker.updateObject();
		if (CollisionHandler.playerHasCollidedWithLocationMarker(myGame.getGameObject(Player.PLAYER_ONE), locationMarker)) {
			wewaMissionComplete = true;
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderMission(SpriteBatch batch, ImageLoader imageLoader) {
		if (locationMarker.timerValuesAreCorrectToFlash()) {
			locationMarker.renderObject(batch, imageLoader);
		}
	}

	public static void resetGame() {
		wewaMissionComplete = false;
		missionComplete     = false;
	}
}
