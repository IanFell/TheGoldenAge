package ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import loaders.ImageLoader;

/**
 * Since we are now using images to draw player names, this most likely doesn't have to extend a parent class anymore.
 * However, we may want to change this back later, so we will leave it for now.
 * 
 * @author Fabulous Fellini
 *
 */
public class PlayerNameUi {

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 * @param GameOjbect  player
	 * @param float       xOffset
	 * @param float       yOffset
	 */
	public void renderUi(
			SpriteBatch batch, 
			ImageLoader imageLoader, 
			MyGame myGame, 
			GameObject player, 
			float xOffset, 
			float yOffset
			) {
		
		batch.draw(
				imageLoader.objectiveBackground,
				player.getX() - xOffset + 0.5f,
				player.getY() + yOffset,
				9, 
				-2
				); 
		
		batch.draw(
				getCurrentPlayerNameImage(getCurrentPlayerNameToDisplayOnUi(), imageLoader), 
				player.getX() - xOffset + 0.5f, 
				player.getY() + yOffset, 
				9, 
				-2
				); 
	}

	/**
	 * 
	 * @param String      name
	 * @param ImageLoader imageLoader
	 * @return Texture
	 */
	private Texture getCurrentPlayerNameImage(String name, ImageLoader imageLoader) {
		if (name.equalsIgnoreCase(Player.JOLLY_ROGER)) {
			return imageLoader.jollyRogerUiNameLabel;
		}
		if (name.equalsIgnoreCase(Player.BLACK_BEARD)) {
			return imageLoader.blackBeardUiNameLabel;
		}
		return imageLoader.pegLegUiNameLabel;
	}

	/**
	 * 
	 * @return String
	 */
	public String getCurrentPlayerNameToDisplayOnUi() {
		if (Player.lifeState == Player.LIFE_STATE_ONE) {
			return Player.JOLLY_ROGER;
		}
		if (Player.lifeState == Player.LIFE_STATE_TWO) {
			return Player.BLACK_BEARD;
		}
		return Player.PEG_LEG;
	}
}
