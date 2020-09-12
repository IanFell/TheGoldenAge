package store;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.stationarygameobjects.buildings.Building;
import handlers.CollisionHandler;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Market extends Building {

	/**
	 * Constructor.
	 * 
	 * @param int     x
	 * @param int     y
	 * @param int     width
	 * @param int     height
	 * @param Texture texture
	 */
	public Market(int x, int y, int width, int height, Texture texture) {
		super(x, y, width, height, texture);
		rectangle.width  = width;
		rectangle.height = height;
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		CollisionHandler.checkIfPlayerHasCollidedWithStructureOutsideOfTown(
				myGame.getGameObject(GameObject.PLAYER_ONE),
				this,
				myGame
				); 
		
		if (Store.playerIsShortOnLootMessageShouldRender) {
			if (Store.shortOnLootTimer < 1) {
				Store.playBuzzerAudio = true;
			}
			Store.shortOnLootTimer++;
			if (Store.shortOnLootTimer >= Store.SHORT_ON_LOOT_MAX_DISPLAY_VALUE) {
				Store.shortOnLootTimer                       = 0;
				Store.playerIsShortOnLootMessageShouldRender = false;
			}
		}
	}
	
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		super.renderObject(batch, imageLoader);
		if (Store.playerIsShortOnLootMessageShouldRender) {
			batch.draw(
					imageLoader.objectiveNotEnoughLoot, 
					x, 
					y, 
					width, 
					-height / 2
					);
		}
	}
}
