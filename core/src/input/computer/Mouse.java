package input.computer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.mygame.MyGame;

import controllers.GameStateController;
import controllers.PlayerController;
import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Gun;
import gameobjects.weapons.MagicPearl;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.CollectibleHandler;
import handlers.collectibles.RumHandler;
import helpers.GameAttributeHelper;
import inventory.Inventory;
import loaders.GameObjectLoader;
import missions.MissionRawBar;
import screens.Screens;
import store.Store;

/**
 * Handles mouse input.
 * 
 * @author Fabulous Fellini
 *
 */
public class Mouse extends ComputerInput {

	private final int PURCHASE_BUTTON_HEART = 0;
	private final int PURCHASE_BUTTON_RUM   = 1;
	private final int PURCHASE_BUTTON_GUN   = 2;
	private final int PURCHASE_BUTTON_PEARL = 3;
	private final int PURCHASE_BUTTON_AMMO  = 4;
	private final int PURCHASE_BUTTON_NULL  = 5;

	// Use this so we can't click on purchasing buttons really fast.
	private int storeTimer = 0;

	/**
	 * Constructor.
	 */
	public Mouse() {
		super();
	}

	private void closeStore() {
		Store.storeShouldBeRendered          = false;
		Store.playerWantsToEnterStore        = false;
		Store.shouldDisplayEnterStoreMessage = false;
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	@Override
	public void handleInput(MyGame myGame) {

		//System.out.println("Mouse Coordinate X: " + Gdx.input.getX());
		//System.out.println("Mouse Coordinate Y: " + Gdx.input.getY());

		GameObject player = PlayerController.getCurrentPlayer(myGame);
		switch (GameAttributeHelper.gameState) {
		case Screens.SPLASH_SCREEN:
			if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
				//GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			break;
		case Screens.TITLE_SCREEN:
			if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
				GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
			}
			break;
		case Screens.GAME_SCREEN:
			if (Inventory.allInventoryShouldBeRendered) {
				// Inventory menu buttons.
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
					for (int i = 0; i < ((Player) player).getInventory().inventory.size(); i++) {
						if (inventoryButtons[i].contains(Gdx.input.getX(), Gdx.input.getY())) {
							selectAlternateInventoryObject(i, player);
						}
					}
				} else {
					Inventory.mouseIsClickingOnInventoryObject = false;
					for (int i = 0; i < inventoryButtonIsPressed.length; i++) {
						inventoryButtonIsPressed[i] = false;
					}
				}
			} else {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
					// Skip the raw bar mission intro if we click on anywhere on the screen.
					if (MissionRawBar.missionIsActive && !MissionRawBar.introHasCompleted) {
						MissionRawBar.introHasCompleted = true;
					}
					// Dont throw exception if inventory is not equipped.
					if (Inventory.inventoryIsEquipped && !Store.playerWantsToEnterStore) {
						Player.playerIsPerformingAttack = true;
						if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
							MagicPearl.isAttacking     = !MagicPearl.isAttacking;
							MagicPearl.isMovingForward = !MagicPearl.isMovingForward;
						}
						if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof BirdWeapon) {
							BirdWeapon.birdIsAttacking = true;
						}
					}
					handleStore(player, myGame);
				} else {
					Player.playerIsPerformingAttack = false;
					// Dont throw exception if inventory is not equipped.
					if (Inventory.inventoryIsEquipped) {
						//if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
						//MagicPearl.isMovingForward = false;
						//}
						if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof BirdWeapon) {
							BirdWeapon.birdIsAttacking = false;
						}
					}
					// Turn off store stuff.
					Store.mouseIsClickingOnPurchasingObject = false;
					for (int i = 0; i < purchasingButtonIsPressed.length; i++) {
						purchasingButtonIsPressed[i] = false;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param MyGae      myGame
	 */
	private void handleStore(GameObject player, MyGame myGame) {
		//storeTimer++;
		//if (storeTimer > 50) {
		//	storeTimer = 0;
		//}
		//if (storeTimer % 5 == 0) {
		if (Store.storeShouldBeRendered) {
			for (int i = 0; i < purchasingButtons.length; i++) {
				if (purchasingButtons[PURCHASE_BUTTON_HEART].contains(Gdx.input.getX(), Gdx.input.getY())) {
					if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_HEART) {
						player.setHealth(player.getHealth() + 1);
						closeStore();
						Store.playSound = true;
						// Remove loot (player has bought gun).
						player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_HEART);
						break;
					} else {
						Store.playerIsShortOnLootMessageShouldRender = true;
						break;
					}
				}
				else if (purchasingButtons[PURCHASE_BUTTON_RUM].contains(Gdx.input.getX(), Gdx.input.getY())) {
					if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_RUM) {
						RumHandler.rumCount++;
						closeStore();
						Store.playSound = true;
						// Remove loot (player has bought gun).
						player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_RUM);
						break;
					} else {
						Store.playerIsShortOnLootMessageShouldRender = true;
						break;
					}
				}
				else if (purchasingButtons[PURCHASE_BUTTON_GUN].contains(Gdx.input.getX(), Gdx.input.getY())) {
					if (!Store.gunPurchased) {
						if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_GUN && TradingPost.hasBeenEntered) { 
							GameObject gun = myGame.getGameScreen().gun;
							((Player) player).getInventory().addObjectToInventory(gun);
							Inventory.inventoryHasStartedCollection = true;
							Gun.hasBeenCollected                    = true;
							Gun.playCollectionSound                 = true;
							GameObjectLoader.gameObjectList.add(gun);

							// Remove loot (player has bought gun).
							player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_GUN);

							// Close the store.
							Store.gunHasBeenPurchasedAtStore = true;
							TradingPost.hasBeenEntered       = true;
							closeStore();
							Store.playSound    = true;
							Store.gunPurchased = true;
							break;
						} else {
							Store.playerIsShortOnLootMessageShouldRender = true;
							break;
						}
					}
				}
				else if (purchasingButtons[PURCHASE_BUTTON_PEARL].contains(Gdx.input.getX(), Gdx.input.getY())) {
					if (!Store.pearlPurchased && Store.pearlUnlocked) {
						if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_PEARL) {
							GameObject pearl = myGame.gameScreen.magicPearl;
							((Player) player).getInventory().addObjectToInventory(pearl);
							Inventory.inventoryHasStartedCollection = true;
							pearl.hasBeenCollected                  = true;
							MagicPearl.playCollectionSound          = true;
							GameObjectLoader.gameObjectList.add(pearl);
							closeStore();
							Store.playSound      = true;
							Store.pearlPurchased = true;
							// Remove loot (player has bought gun).
							player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_PEARL);
							break;
						} else {
							Store.playerIsShortOnLootMessageShouldRender = true;
							break;
						}
					}
				}
				else if (purchasingButtons[PURCHASE_BUTTON_AMMO].contains(Gdx.input.getX(), Gdx.input.getY())) {
					if (Store.ammoUnlocked) {
						if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_AMMO) {
							if (AmmoHandler.ammoCount < AmmoHandler.MAX_AMOUNT_AMMO_PLAYER_CAN_CARRY) {
								AmmoHandler.ammoCount += AmmoHandler.ammoValue;
								closeStore();
								Store.playSound = true;
								player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_AMMO);
								break;
							} 
						} else {
							Store.playerIsShortOnLootMessageShouldRender = true;
							break;
						}
					}
				}
				else if (purchasingButtons[PURCHASE_BUTTON_NULL].contains(Gdx.input.getX(), Gdx.input.getY())) {
					closeStore();
					Store.playSound = true;
					break;
				}
			}
		} 
		//}
	}
}
