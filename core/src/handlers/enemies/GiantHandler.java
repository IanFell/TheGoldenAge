package handlers.enemies;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.enemies.Giant;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class GiantHandler {

	private int GIANT_WEWA         = 0;
	private int GIANT_APALACHICOLA = 1;
	private int GIANT_PORT_ST_JOE  = 2;

	public static Giant[] giants = new Giant[3];

	private int size = 5;

	/**
	 * 
	 * @param ImageLoader imageLoader
	 */
	public void init(ImageLoader imageLoader) {
		giants[GIANT_WEWA] = new Giant(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60, 
				3, 
				size, 
				size, 
				GameObject.DIRECTION_LEFT
				);
		giants[GIANT_APALACHICOLA] = new Giant(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 43, 
				size, 
				size, 
				GameObject.DIRECTION_LEFT
				);
		giants[GIANT_PORT_ST_JOE] = new Giant(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 1, 
				GameAttributeHelper.CHUNK_THREE_Y_POSITION_START - 6, 
				size, 
				size, 
				GameObject.DIRECTION_RIGHT
				);

		for (int i = 0; i < giants.length; i++) {
			GameObjectLoader.gameObjectList.add(giants[i]);
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateGiants(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < giants.length; i++) {
			giants[i].updateObject(myGame, mapHandler);
		}
		/**
		 * This giant will eventually be off the map.
		 * This patch makes that not happen.
		 */
		if (giants[GIANT_WEWA].getY() < 0) {
			giants[GIANT_WEWA].setY(3);
		}
	}

	/**
	 * Sets position near player for final fight.
	 * 
	 * @param GameObject player
	 */
	public void setGiantsToPlayer(GameObject player) {
		float spinAngle  = 0;
		int radius       = 7;
		float angleValue = 2f;
		spinAngle += angleValue;
		giants[GIANT_WEWA].setX((float) (player.getX() - Math.cos(spinAngle) * radius));
		giants[GIANT_WEWA].setY((float) (player.getY() + Math.sin(spinAngle) * radius));
		giants[GIANT_WEWA].setIsDead(false);
		spinAngle += angleValue;
		giants[GIANT_APALACHICOLA].setX((float) (player.getX() - Math.cos(spinAngle) * radius));
		giants[GIANT_APALACHICOLA].setY((float) (player.getY() + Math.sin(spinAngle) * radius));
		giants[GIANT_APALACHICOLA].setIsDead(false);
		spinAngle += angleValue;
		giants[GIANT_PORT_ST_JOE].setX((float) (player.getX() - Math.cos(spinAngle) * radius));
		giants[GIANT_PORT_ST_JOE].setY((float) (player.getY() + Math.sin(spinAngle) * radius));
		giants[GIANT_PORT_ST_JOE].setIsDead(false);
	}
}
