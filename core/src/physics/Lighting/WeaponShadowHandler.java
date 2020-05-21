package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.MagicPearl;
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
			batch.draw(imageLoader.oysterShadow, magicPearl.getX(), magicPearl.getY() + 1.0f, magicPearl.getWidth(), magicPearl.getHeight());
		}

		GameObject birdWeapon = myGame.getGameScreen().birdWeapon; 
		// Draw shadow under stationary bird before player has collected it.
		if (!birdWeapon.hasBeenCollected && MissionStumpHole.stumpHoleMissionComplete) {
			batch.draw(imageLoader.oysterShadow, birdWeapon.getX(), birdWeapon.getY() + 1.0f, birdWeapon.getWidth(), birdWeapon.getHeight());
		}
		// Player has collected bird.  Render shadow if he's using it as a weapon.
		if (
				birdWeapon.hasBeenCollected &&
				myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof BirdWeapon) {
			batch.draw(imageLoader.oysterShadow, birdWeapon.getX(), birdWeapon.getY() + 1.0f, birdWeapon.getWidth(), birdWeapon.getHeight());
		}
	}
}
