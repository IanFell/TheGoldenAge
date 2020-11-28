package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class LivesUi {

	private int lifeIconSize = 1;

	/**
	 * Note that the switch statement is "reversed" because the player actually starts
	 * with 0 lives and gains one with each loss.
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderLivesUi(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		float x = myGame.getGameObject(Player.PLAYER_ONE).getX() + 11.5f;
		float y = myGame.getGameObject(Player.PLAYER_ONE).getY() + 6.5f;

		switch (PlayerOne.lives) {
		case 2:
			batch.draw(imageLoader.playerRight, x, y, lifeIconSize, -lifeIconSize);
			break;
		case 1:
			batch.draw(imageLoader.playerRight, x, y, lifeIconSize, -lifeIconSize);
			batch.draw(imageLoader.playerRight, x - 1, y, lifeIconSize, -lifeIconSize);
			break;
		case 0:
			batch.draw(imageLoader.playerRight, x, y, lifeIconSize, -lifeIconSize);
			batch.draw(imageLoader.playerRight, x - 1, y, lifeIconSize, -lifeIconSize);
			batch.draw(imageLoader.playerRight, x - 2, y, lifeIconSize, -lifeIconSize);
			break;
		}	
	}
}
