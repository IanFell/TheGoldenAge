package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
import handlers.arrowhandler.ArrowHandler;
import handlers.enemies.BossHandler;
import inventory.Inventory;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import missions.MissionStumpHole;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class WeaponShadowHandler {

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	public void renderWeaponShadows(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		float offset = 1.3f;
		batch.draw(
				imageLoader.arrowShadowRight,
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getX() - offset,
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getY() + offset,
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getWidth(),
				ArrowHandler.arrows[ArrowHandler.MEXICO_BEACH].getHeight()
				);
		batch.draw(
				imageLoader.arrowShadowLeft,
				ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getX() + offset,
				ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getY() + offset,
				ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getWidth(),
				-ArrowHandler.arrows[ArrowHandler.APALACHICOLA].getHeight()
				);
		batch.draw(
				imageLoader.arrowShadowUp,
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getX() - offset,
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getY() + offset,
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getWidth(),
				ArrowHandler.arrows[ArrowHandler.STUMP_HOLE].getHeight()
				);

		GameObject magicPearl = myGame.getGameScreen().magicPearl; 
		if (
				magicPearl.hasBeenCollected &&
				myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
			batch.draw(imageLoader.shadow, magicPearl.getX(), magicPearl.getY() + 1.0f, magicPearl.getWidth(), magicPearl.getHeight());
		}

		GameObject birdWeapon = myGame.getGameScreen().birdWeapon; 
		// Draw shadow under stationary bird before player has collected it.
		if (!birdWeapon.hasBeenCollected && MissionStumpHole.stumpHoleMissionComplete) {
			// Only render shadow if boss is dead.
			int direction = myGame.getGameObject(Player.PLAYER_ONE).direction;
			float shadowX = birdWeapon.getX() + 1;
			if (direction == GameObject.DIRECTION_LEFT) {
				shadowX = birdWeapon.getX() - 1;
			}
			if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead()) {
				batch.draw(
						imageLoader.oysterShadow, 
						shadowX, 
						birdWeapon.getY() + 1.0f, 
						birdWeapon.getWidth(), 
						birdWeapon.getHeight()
						);
			}
		}
		// Player has collected bird.  Render shadow if he's using it as a weapon.
		if (
				birdWeapon.hasBeenCollected &&
				myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof BirdWeapon) {
			batch.draw(imageLoader.oysterShadow, birdWeapon.getX(), birdWeapon.getY() + 1.0f, birdWeapon.getWidth(), birdWeapon.getHeight());
		}

		GameObject paw = myGame.getGameScreen().paw; 
		if (!Paw.hasBeenUsed) {
			batch.draw(
					imageLoader.pawShadow, 
					paw.getX(), 
					paw.getY() + 1.0f, 
					paw.getWidth(), 
					-paw.getHeight()
					);
		}

		GameObject dagger = myGame.getGameScreen().dagger; 
		// Draw shadow under stationary bird before player has collected it.
		if (!dagger.hasBeenCollected) {
			batch.draw(
					imageLoader.daggerShadow, 
					dagger.getX(), 
					dagger.getY() + 1.0f, 
					dagger.getWidth(), 
					-dagger.getHeight()
					);
		}
	}
}
