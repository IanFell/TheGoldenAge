package handlers.collectibles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.Ammo;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class AmmoHandler extends CollectibleHandler {

	public static int ammoCount       = 0;
	public final static int ammoValue = 5;

	public static final int MAX_AMOUNT_AMMO_ALLOWED_IN_WORLD_TO_COLLECT = 200;

	public static final int MAX_AMOUNT_AMMO_PLAYER_CAN_CARRY = 99;

	public static ArrayList <Ammo> ammo = new ArrayList<Ammo>();

	public void init() {
		for (int i = 0; i < MAX_AMOUNT_AMMO_ALLOWED_IN_WORLD_TO_COLLECT; i++) {
			addRandomlyPlacedAmmo();
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateAmmo(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < ammo.size(); i++) {
			ammo.get(i).updateObject(myGame, mapHandler);
			if (ammo.get(i).getY() + ammo.get(i).getHeight() < 0) {
				ammo.remove(i);
			}
			if (ammo.size() < MAX_AMOUNT_AMMO_ALLOWED_IN_WORLD_TO_COLLECT) {
				addRandomlyPlacedAmmo();
			}
		}
		// Don't let ammo get above max amount player can carry so UI value doesn't roll over.
		if (ammoCount >= MAX_AMOUNT_AMMO_PLAYER_CAN_CARRY) {
			ammoCount = MAX_AMOUNT_AMMO_PLAYER_CAN_CARRY;
		}
	}

	private void addRandomlyPlacedAmmo() {
		int startX = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60);
		int startY = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 50);
		ammo.add(new Ammo(
				startX, 
				startY
				));
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	public void renderAmmo(SpriteBatch batch, ImageLoader imageLoader) {
		for (int i = 0; i < ammo.size(); i++) {
			if (gameObjectIsWithinScreenBounds(ammo.get(i))) {
				ammo.get(i).renderObject(batch, imageLoader);
			}
		}
	}
}
