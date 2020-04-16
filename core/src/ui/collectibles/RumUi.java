package ui.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import handlers.collectibles.RumHandler;
import loaders.ImageLoader;
import ui.TextBasedUiParent;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class RumUi extends TextBasedUiParent {

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 * @param GameOjbect  player
	 */
	public void renderUi(
			SpriteBatch batch, 
			ImageLoader imageLoader, 
			MyGame myGame, 
			GameObject player
			) {
		float xPos = player.getX() - 12.5f;
		float yPos = player.getY() - 2.0f;
		batch.draw(
				imageLoader.rum, 
				xPos,
				yPos - 1,
				iconSize, 
				-iconSize
				); 
		super.renderUi(batch, imageLoader, myGame, player, xPos + 2, yPos - 1, RumHandler.rumCount);
	}	
}
