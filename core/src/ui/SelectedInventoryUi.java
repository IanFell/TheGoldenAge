package ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Dagger;
import gameobjects.weapons.Gun;
import gameobjects.weapons.LegendSword;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
import handlers.InputHandler;
import inventory.Inventory;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class SelectedInventoryUi {

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 * @param GameObject  player
	 */
	public void renderSelectedInventoryUi(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame, GameObject player) {
		int size = 2;

		batch.draw(
				imageLoader.objectiveBackground,
				player.getX() - 12.0f,
				player.getY() + 6.0f,
				size, 
				-size
				); 

		// Only display inventory object on screen if it is equpped.
		if (player.getInventory().getInventoryIsEquipped()) {

			// First, create an object and image to hold display of inventory object on in game UI.
			if (Inventory.currentlySelectedInventoryObject < player.getInventory().inventory.size()) {
				GameObject object = player.getInventory().inventory.get(Inventory.currentlySelectedInventoryObject);
				Texture image     = null;

				// Next, find the inventory object we have selected.
				if (object instanceof Gun) {
					image = imageLoader.gunRight;
				} else if (object instanceof MagicPearl) {
					image = imageLoader.magicPearl;
				} else if (object instanceof LegendSword) {
					image = imageLoader.legendSwordRainbow;
				} else if (object instanceof BirdWeapon) {
					// This doesn't actually do anything, but we need it so we don't throw a null pointer on the image variable.
					image = imageLoader.attackBird;
				} else if (object instanceof Paw) {
					image = imageLoader.paw;
				} else if (object instanceof Dagger) {
					image = imageLoader.daggerUp;
				}

				// Compensate for the fucked up gun image not being drawn correctly on the y-axis.
				float yPos = player.getY() + 5.525f;
				if (object instanceof Gun) {
					yPos = player.getY() + 5.525f + 0.3f;
				}

				// Lastly, draw correct inventory object.
				batch.draw(
						image,
						player.getX() - 11.5f,
						yPos,
						1, 
						-1 /* * 1.3f*/
						);
				
				if (object instanceof MagicPearl) {
					if (MagicPearl.isAttacking) {
						Texture texture = imageLoader.pearlLt;
						
						if (InputHandler.inputType == InputHandler.INPUT_ARCADE) {
							texture = imageLoader.pearlStart;
						}
						
						batch.draw(
								imageLoader.objectiveBackground,
								player.getX() + 4.5f,
								player.getY() - 2,
								8, 
								-3 /* * 1.3f*/
								);
						batch.draw(
								texture,
								player.getX() + 4.5f,
								player.getY() - 2,
								8, 
								-3 /* * 1.3f*/
								);
					}
				}
			}
		}
	}
}
