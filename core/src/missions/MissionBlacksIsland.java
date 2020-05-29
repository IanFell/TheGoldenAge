package missions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.Treasure;
import gameobjects.gamecharacters.players.Player;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MissionBlacksIsland extends Mission {

	public static boolean missionBlacksIslandComplete = false;

	private Treasure treasure;

	/**
	 * Constructor.
	 */
	public MissionBlacksIsland() {
		treasure = new Treasure(GameAttributeHelper.CHUNK_FIVE_X_POSITION_START + 3, GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START - 6);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void updateMission(MyGame myGame) {
		treasure.update(myGame.getGameObject(Player.PLAYER_ONE));
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderMission(SpriteBatch batch, ImageLoader imageLoader) {
		treasure.renderObject(batch, imageLoader);
	}
}
