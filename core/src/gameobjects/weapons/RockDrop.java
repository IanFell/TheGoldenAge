package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import handlers.CollisionHandler;
import helpers.RandomNumberGenerator;
import loaders.ImageLoader;
import maps.MapHandler;
import missions.MissionStumpHole;

/**
 * TODO add plane sound when rock falls.
 * TODO make rock spin on the way down.
 * 
 * @author Fabulous Fellini
 *
 */
public class RockDrop extends GameObject {

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public RockDrop(float x, float y) {
		this.x                = x;
		this.y                = y;
		this.width            = 1;
		this.height           = 1;
		this.dy               = (float) RandomNumberGenerator.generateRandomDouble(0.1d, 0.4d);
		this.rectangle.width  = width;
		this.rectangle.height = height;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.rock, x, y, width, -height);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 * @param float      leftBoundary
	 * @param float      rightBoundary
	 */
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		if (MissionStumpHole.missionIsActive) {
			y += dy;
			rectangle.x = x;
			rectangle.y = y;
			CollisionHandler.checkIfPlayerHasCollidedWithRockDrop(
					MissionStumpHole.player, 
					this,
					myGame.getGameObject(Player.PLAYER_ONE)
					);
		}
	}
}
