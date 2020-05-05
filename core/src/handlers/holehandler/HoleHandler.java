package handlers.holehandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.nature.hole.Hole;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class HoleHandler {

	private Hole hole;

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void init(MyGame myGame) {
		hole = new Hole(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 63, 
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START
				);
		GameObjectLoader.gameObjectList.add(hole);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderHoles(SpriteBatch batch, ImageLoader imageLoader) {
		hole.renderObject(batch, imageLoader);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateHoles(MyGame myGame, MapHandler mapHandler) {
		hole.updateObject(myGame, mapHandler);
	}
}
