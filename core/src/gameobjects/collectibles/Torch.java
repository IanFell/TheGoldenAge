package gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Fire;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Torch extends GameObject {

	private Fire fire;

	private float stickWidth  = 0.1f;
	private float stickHeight = 0.5f;

	/**
	 * 
	 * @param float x
	 * @param float y
	 */
	public Torch(float x, float y) {
		this.x      = x;
		this.y      = y;
		this.width  = 0.5f;
		this.height = 1.5f;
		fire        = new Fire(x, y, width, height, "Player", false);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
		case Player.DIRECTION_RIGHT:
			fire.setX(PlayerController.getCurrentPlayer(myGame).getX() + 1);
			fire.setY(PlayerController.getCurrentPlayer(myGame).getY() - 0.5f);
			break;
		case Player.DIRECTION_LEFT:
			fire.setX(PlayerController.getCurrentPlayer(myGame).getX() - 0.5f);
			fire.setY(PlayerController.getCurrentPlayer(myGame).getY() - 0.5f);
			break;
		case Player.DIRECTION_DOWN:
			fire.setX(PlayerController.getCurrentPlayer(myGame).getX());
			fire.setY(PlayerController.getCurrentPlayer(myGame).getY() - 0.5f);
			break;
		case Player.DIRECTION_UP:
			fire.setX(PlayerController.getCurrentPlayer(myGame).getX());
			fire.setY(PlayerController.getCurrentPlayer(myGame).getY() - 1.0f);
			break;
		}
		fire.updateObject(myGame, mapHandler);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.blackSquare, fire.getX() + 0.2f, fire.getY(), stickWidth, stickHeight);
		fire.renderObject(batch, imageLoader);
	}
}
