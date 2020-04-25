package handlers.arrowhandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;
import maps.MapHandler;
import spawners.ArrowSpawner;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowHandler {

	private ArrowSpawner arrowSpawner;

	private final int MEXICO_BEACH = 0;

	public void init() {
		arrowSpawner = new ArrowSpawner(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 82,
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 40,
				MEXICO_BEACH,
				GameObject.DIRECTION_RIGHT
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderArrowHandler(SpriteBatch batch, ImageLoader imageLoader) {
		arrowSpawner.renderArrowSpawner(batch, imageLoader);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateArrowHandler(MyGame myGame, MapHandler mapHandler) {
		arrowSpawner.updateArrowSpawner(myGame, mapHandler);
	}
}
