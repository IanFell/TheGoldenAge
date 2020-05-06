package handlers.holehandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.hole.Hole;
import handlers.TownHandler;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import maps.MapHandler;
import towns.Town;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class HoleHandler {

	private Hole hole;

	public static boolean playerIsInHole = false;

	private int timer = 0;

	private final int TUNNEL_TRAVEL_VALUE = 100;

	private Rectangle recOne;
	private Rectangle recTwo;

	private TownHandler townHandler = new TownHandler();

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

		recOne = new Rectangle(0, 0, 20, 10);
		recTwo = new Rectangle(0, 0, 20, 10);
	}

	/**
	 * This only renders the tunnel after player has entered the hole.
	 * The actual holes are rendered from the game object list.
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderTunnel(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (playerIsInHole) {
			GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
			int size = 300;
			batch.draw(
					imageLoader.blackSquare,
					player.getX() - size / 2,
					player.getY() - size / 2,
					size,
					size
					);
			batch.draw(imageLoader.tunnel, recOne.x, recOne.y, recOne.width, recOne.height);
			batch.draw(imageLoader.tunnel, recTwo.x, recTwo.y, recTwo.width, recTwo.height);

			myGame.getGameObject(Player.PLAYER_ONE).renderObject(batch, imageLoader);
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateHoles(MyGame myGame, MapHandler mapHandler) {
		hole.updateObject(myGame, mapHandler);

		GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
		if (playerIsInHole) {

			// Deal with the teleport tunnel images.
			recOne.x--;
			recTwo.x--;
			int offset       = 8;
			float resetValue = player.getX() - offset;
			if (recOne.x + recOne.width < resetValue) {
				recOne.setX(player.getX() + offset);
			}
			if (recTwo.x + recTwo.width < resetValue) {
				recTwo.setX(player.getX() + offset);
			}

			// Deal with tunnel timing.
			timer++;
			if (timer > TUNNEL_TRAVEL_VALUE) {
				timer            = 0;
				playerIsInHole   = false;
				Player.isJumping = true;
				setRandomLandingLocation(player);
			}
		} else {
			int yPos = 5;
			recOne.setX(player.getX() + 15);
			recOne.setY(player.getY() - yPos);
			recTwo.setX(recOne.getX() + recOne.getWidth() / 2);
			recTwo.setY(player.getY() - yPos);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	private void setRandomLandingLocation(GameObject player) {
		int town = RandomNumberGenerator.generateRandomInteger(townHandler.getTownLength());
		switch (town) {
		case Town.MEXICO_BEACH:
			player.setX(GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 43);
			player.setY(GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 10);
			break;
		case Town.PORT_ST_JOE:
			player.setX(GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 1);
			player.setY(GameAttributeHelper.CHUNK_THREE_Y_POSITION_START - 6);
			break;
		case Town.THE_POINT:
			player.setX(GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 10); 
			player.setY(GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START - 100);
			break;
		case Town.WEWA:
			player.setX(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60);
			player.setY(3);
			break;
		case Town.APALACHICOLA:
			player.setX(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 35);
			player.setY(GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 43);
			break;
		case Town.ST_GEORGE:
			player.setX(GameAttributeHelper.CHUNK_SEVEN_X_POSITION_START + 5);
			player.setY(GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 3);
			break;
		case Town.CAPE_SAN_BLAS:
			player.setX(GameAttributeHelper.CHUNK_THREE_X_POSITION_START - 30); 
			player.setY(GameAttributeHelper.CHUNK_SIX_Y_POSITION_START - 40);
			break;
		case Town.STUMP_HOLE:
			player.setX(GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 12); 
			player.setY(GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 45);
			break;
		}
	}
}
