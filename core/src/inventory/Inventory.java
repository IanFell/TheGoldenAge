package inventory;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Dagger;
import gameobjects.weapons.Gun;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
import gameobjects.weapons.Weapon;
import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Fire;
import screens.Screens;
import ui.InventoryUi;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Inventory extends Screens {

	public static boolean mouseIsClickingOnInventoryObject;
	public static Rectangle rectangle;
	public static boolean playClickSound;
	private Fire fire;
	public ArrayList <GameObject> inventory;
	public static boolean inventoryIsEquipped;
	public static boolean allInventoryShouldBeRendered;
	public static int currentlySelectedInventoryObject;
	private static InventoryUi inventoryUi;
	public static boolean inventoryHasStartedCollection   = false;
	public static boolean inventoryObjectHasBeenCollected = false;
	
	public static void resetGame() {
		inventoryHasStartedCollection   = false;
		inventoryObjectHasBeenCollected = false;
	}

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public Inventory(final MyGame myGame) {
		super(myGame);
		inventory                        = new ArrayList<GameObject>();
		inventoryIsEquipped              = false;
		allInventoryShouldBeRendered     = false;
		currentlySelectedInventoryObject = 0;
		rectangle                        = new Rectangle(0, 0, 4, 4);
		mouseIsClickingOnInventoryObject = false;
		fire                             = new Fire(0, 0, 0, 0, "Inventory", false);
		playClickSound                   = false;
		inventoryUi                      = new InventoryUi(myGame);
		inventory.clear();
	}

	/**
	 * 
	 * @param boolean shouldRenderAllInventory
	 */
	public void setAllInventoryShouldBeRendered(boolean shouldRenderAllInventory) {
		allInventoryShouldBeRendered = shouldRenderAllInventory;
	}

	/**
	 * 
	 * @param GameObject object
	 */
	public void addObjectToInventory(GameObject object) {
		inventory.add(object);
	}

	/**
	 * 
	 * @param float      x
	 * @param float      y
	 * @param MapHandler mapHandler
	 */
	public void updateInventory(float x, float y, MapHandler mapHandler) {
		// Set all inventory to follow player.
		float xPosition = 0;
		float yPosition = 0;
		int objectType  = Weapon.WEAPON_TYPE_SWORD;
		if (inventory.size() > 0) {
			if (currentlySelectedInventoryObject < inventory.size()) {
				if (inventory.get(currentlySelectedInventoryObject) instanceof Gun) {
					objectType = Weapon.WEAPON_TYPE_GUN;
				} else if (inventory.get(currentlySelectedInventoryObject) instanceof MagicPearl) {
					objectType = Weapon.WEAPON_TYPE_MAGIC_PEARL;
				} else if (inventory.get(currentlySelectedInventoryObject) instanceof BirdWeapon) {
					objectType = Weapon.WEAPON_TYPE_BIRD;
				} else if (inventory.get(currentlySelectedInventoryObject) instanceof Paw) {
					objectType = Weapon.WEAPON_TYPE_PAW;
				} else if (inventory.get(currentlySelectedInventoryObject) instanceof Dagger) {
					objectType = Weapon.WEAPON_TYPE_DAGGER;
				}
				for (int i = 0; i < inventory.size(); i++) {
					if (objectType == Weapon.WEAPON_TYPE_SWORD) {
						updateSword(i, xPosition, yPosition, x, y);
					} else if (objectType == Weapon.WEAPON_TYPE_GUN){
						updateGun(i, xPosition, yPosition, x, y);
					} else if (objectType == Weapon.WEAPON_TYPE_MAGIC_PEARL) {
						updateMagicPearl(i, xPosition, yPosition, x, y);
					} else if (objectType == Weapon.WEAPON_TYPE_BIRD) {
						updateBirdWeapon(i, xPosition, yPosition, x, y);
					} else if (objectType == Weapon.WEAPON_TYPE_PAW) {
						updatePawWeapon(i, xPosition, yPosition, x, y);
					} else if (objectType == Weapon.WEAPON_TYPE_DAGGER) {
						updateDagger(i, xPosition, yPosition, x, y);
					}
					if (Inventory.allInventoryShouldBeRendered) {
						fire.updateObject(myGame, mapHandler);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param int   selectedInventory
	 * @param float xPosition
	 * @param float yPosition
	 * @param float x
	 * @param float y
	 */
	private void updateDagger(int selectedInventory, float xPosition, float yPosition, float x, float y) {
		float inventoryHeight = inventory.get(selectedInventory).getHeight();
		if (Player.isInWater) {
			switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
			case Player.DIRECTION_RIGHT:
				xPosition = x + 3;
				yPosition = y - 0.5f;
				break;
			case Player.DIRECTION_LEFT:
				xPosition = x - 3.5f;
				yPosition = y - 0.5f;
				break;
			case Player.DIRECTION_DOWN:
				xPosition = x - 0.3f;
				yPosition = y + inventoryHeight + 1;
				break;
			case Player.DIRECTION_UP:
				xPosition = x - 0.3f;
				yPosition = y - inventoryHeight - 2;
				break;
			}
		} else {
			switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
			case Player.DIRECTION_RIGHT:
				xPosition = x + 3.6f;
				yPosition = y - 1.5f;
				// Compensate for player being larger if he's invincible.
				if (Player.isInvincible) {
					xPosition = x + 4.3f;
				}
				break;
			case Player.DIRECTION_LEFT:
				xPosition = x - 3.5f;
				yPosition = y - 1.5f;
				break;
			case Player.DIRECTION_DOWN:
				xPosition = x - 0.3f;
				yPosition = y + inventoryHeight;
				break;
			case Player.DIRECTION_UP:
				xPosition = x - 0.3f;
				yPosition = y - inventoryHeight - 2.7f;
				break;
			}
		}
		inventory.get(selectedInventory).setX(xPosition);
		inventory.get(selectedInventory).setY(yPosition);
	}

	/**
	 * 
	 * @param int   selectedInventory
	 * @param float xPosition
	 * @param float yPosition
	 * @param float x
	 * @param float y
	 */
	private void updatePawWeapon(int selectedInventory, float xPosition, float yPosition, float x, float y) {
		switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
		case Player.DIRECTION_RIGHT:
			xPosition = x;
			yPosition = y - 1;
			break;
		case Player.DIRECTION_LEFT:
			xPosition = x;
			yPosition = y - 1;
			break;
		case Player.DIRECTION_DOWN:
			xPosition = x - 1;
			yPosition = y;
			break;
		case Player.DIRECTION_UP:
			xPosition = x - 1;
			yPosition = y - 1;
			break;
		}	
		inventory.get(selectedInventory).setX(xPosition);
		inventory.get(selectedInventory).setY(yPosition);
	}

	/**
	 * 
	 * @param int   selectedInventory
	 * @param float xPosition
	 * @param float yPosition
	 * @param float x
	 * @param float y
	 */
	private void updateBirdWeapon(int selectedInventory, float xPosition, float yPosition, float x, float y) {
		if (!MagicPearl.isAttacking && !BirdWeapon.birdIsAttacking) {
			switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
			case Player.DIRECTION_RIGHT:
				xPosition = x;
				yPosition = y - 1;
				break;
			case Player.DIRECTION_LEFT:
				xPosition = x;
				yPosition = y - 1;
				break;
			case Player.DIRECTION_DOWN:
				xPosition = x - 1;
				yPosition = y;
				break;
			case Player.DIRECTION_UP:
				xPosition = x - 1;
				yPosition = y - 1;
				break;
			}	
			inventory.get(selectedInventory).setX(xPosition);
			inventory.get(selectedInventory).setY(yPosition);
		}
	}

	/**
	 * TODO This doesn't appear to be doing anything.  The pearl seems to be controlled in it's own class.
	 * 
	 * @param int   selectedInventory
	 * @param float xPosition
	 * @param float yPosition
	 * @param float x
	 * @param float y
	 */
	private void updateMagicPearl(int selectedInventory, float xPosition, float yPosition, float x, float y) {
		if (!MagicPearl.isAttacking) {
			switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
			case Player.DIRECTION_RIGHT:
				xPosition = x + 2;
				yPosition = y - 1;
				break;
			case Player.DIRECTION_LEFT:
				xPosition = x - 2;
				yPosition = y - 1;
				break;
			case Player.DIRECTION_DOWN:
				xPosition = x - 1;
				yPosition = y;
				break;
			case Player.DIRECTION_UP:
				xPosition = x + 1;
				yPosition = y - 2;
				break;
			}	
			inventory.get(selectedInventory).setX(xPosition);
			inventory.get(selectedInventory).setY(yPosition);
		}
	}

	/**
	 * 
	 * @param int   selectedInventory
	 * @param float xPosition
	 * @param float yPosition
	 * @param float x
	 * @param float y
	 */
	private void updateGun(int selectedInventory, float xPosition, float yPosition, float x, float y) {
		float inventoryHeight = inventory.get(selectedInventory).getHeight();
		switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
		case Player.DIRECTION_RIGHT:
			xPosition = x + 1;
			yPosition = y;
			break;
		case Player.DIRECTION_LEFT:
			xPosition = x - 1.0f;
			yPosition = y;
			break;
		case Player.DIRECTION_DOWN:
			xPosition = x;
			yPosition = y + 1.0f;
			break;
		case Player.DIRECTION_UP:
			xPosition = x;
			yPosition = y - inventoryHeight;
			break;
		}	
		inventory.get(selectedInventory).setX(xPosition);
		inventory.get(selectedInventory).setY(yPosition);
	}

	/**
	 * 
	 * @param int   selectedInventory
	 * @param float xPosition
	 * @param float yPosition
	 * @param float x
	 * @param float y
	 */
	private void updateSword(int selectedInventory, float xPosition, float yPosition, float x, float y) {
		float inventoryHeight = inventory.get(selectedInventory).getHeight();
		if (Player.playerIsPerformingAttack) {
			if (Player.isInWater) {
				switch (Player.direction) {
				case Player.DIRECTION_RIGHT:
					xPosition = x + 4.5f;
					yPosition = y - 0.5f;
					break;
				case Player.DIRECTION_LEFT:
					xPosition = x - 4.5f;
					yPosition = y - 0.5f;
					break;
				case Player.DIRECTION_DOWN:
					xPosition = x - 0.3f;
					yPosition = y + inventoryHeight + 2;
					break;
				case Player.DIRECTION_UP:
					xPosition = x - 0.3f;
					yPosition = y - inventoryHeight - 3;
					break;
				}
			} else {
				switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
				case Player.DIRECTION_RIGHT:
					xPosition = x + 4;
					yPosition = y - 1.5f;
					break;
				case Player.DIRECTION_LEFT:
					xPosition = x - 4.5f;
					yPosition = y - 1.5f;
					break;
				case Player.DIRECTION_DOWN:
					xPosition = x - 0.3f;
					yPosition = y + inventoryHeight + 1;
					break;
				case Player.DIRECTION_UP:
					xPosition = x - 0.3f;
					yPosition = y - inventoryHeight - 4;
					break;
				}
			}
		} else {
			if (Player.isInWater) {
				switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
				case Player.DIRECTION_RIGHT:
					xPosition = x + 3;
					yPosition = y - 0.5f;
					break;
				case Player.DIRECTION_LEFT:
					xPosition = x - 3.5f;
					yPosition = y - 0.5f;
					break;
				case Player.DIRECTION_DOWN:
					xPosition = x - 0.3f;
					yPosition = y + inventoryHeight + 1;
					break;
				case Player.DIRECTION_UP:
					xPosition = x - 0.3f;
					yPosition = y - inventoryHeight - 2;
					break;
				}
			} else {
				switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
				case Player.DIRECTION_RIGHT:
					xPosition = x + 3.6f;
					yPosition = y - 1.5f;
					// Compensate for player being larger if he's invincible.
					if (Player.isInvincible) {
						xPosition = x + 4.3f;
					}
					break;
				case Player.DIRECTION_LEFT:
					xPosition = x - 3.5f;
					yPosition = y - 1.5f;
					break;
				case Player.DIRECTION_DOWN:
					xPosition = x - 0.3f;
					yPosition = y + inventoryHeight;
					break;
				case Player.DIRECTION_UP:
					xPosition = x - 0.3f;
					yPosition = y - inventoryHeight - 2.7f;
					break;
				}
			}
		}
		inventory.get(selectedInventory).setX(xPosition);
		inventory.get(selectedInventory).setY(yPosition);
	}

	/**
	 * 
	 * @param boolean isEquipped
	 */
	public void setInventoryIsEquipped(boolean isEquipped) {
		inventoryIsEquipped = isEquipped;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean getInventoryIsEquipped() {
		return inventoryIsEquipped;
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	public void renderInventory(SpriteBatch batch, ImageLoader imageLoader) {
		//if (!Player.isInWater) { 
		inventoryUi.renderInventoryUi(
				batch, 
				imageLoader, 
				inventory, 
				inventoryIsEquipped, 
				currentlySelectedInventoryObject, 
				allInventoryShouldBeRendered,
				fire,
				mouseIsClickingOnInventoryObject,
				rectangle
				);
		//} 
	}
}