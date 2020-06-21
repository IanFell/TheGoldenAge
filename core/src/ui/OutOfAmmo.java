package ui;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class OutOfAmmo extends GameObject {

	public static boolean shouldRender = false;

	public static int timer = 0;

	private final int DISPLAY_VALUE = 50;

	/**
	 * Constructor.
	 */
	public OutOfAmmo() {
		this.width  = 9;
		this.height = 2;
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void render(MyGame myGame) {
		if (shouldRender) {
			GameObject player = myGame.getGameObject(Player.PLAYER_ONE); 
			myGame.renderer.batch.draw(
					myGame.imageLoader.outOfAmmo, 
					player.getX() , 
					player.getY() , 
					width, 
					-height
					);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void update(GameObject player) {
		if (shouldRender) {
			timer++;
			if (timer > DISPLAY_VALUE) {
				shouldRender             = false;
				timer                    = 0;
			}
		}
	}
}
