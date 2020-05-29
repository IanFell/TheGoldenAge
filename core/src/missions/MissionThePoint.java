package missions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.TreasureMap;
import gameobjects.gamecharacters.players.Player;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MissionThePoint extends Mission {

	private TreasureMap treasureMap;

	public static boolean missionThePointComplete = false;

	/**
	 * Constructor.
	 */
	public MissionThePoint() {
		treasureMap = new TreasureMap(GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 12, GameAttributeHelper.CHUNK_TWO_Y_POSITION_START + 34);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void updateMission(MyGame myGame) {
		treasureMap.update(myGame.getGameObject(Player.PLAYER_ONE));
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderMission(SpriteBatch batch, ImageLoader imageLoader) {
		treasureMap.renderObject(batch, imageLoader);
	}
}
