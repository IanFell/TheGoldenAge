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
	private final int PURCHASE_BUTTON_GUN   = 3;

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
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			break;
		case Screens.TITLE_SCREEN:
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
			}
			break;
		case Screens.GAME_SCREEN:
			if (Inventory.allInventoryShouldBeRendered) {
				// Inventory menu buttons.
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					// Skip the raw bar mission intro if we click on anywhere on the screen.
					if (MissionRawBar.missionIsActive && !MissionRawBar.introHasCompleted) {
						MissionRawBar.introHasCompleted = true;
					}
					// Dont throw exception if inventory is not equipped.
					if (Inventory.inventoryIsEquipped) {
						Player.playerIsPerformingAttack = true;
						if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
							MagicPearl.isAttacking     = true;
							MagicPearl.isMovingForward = true;
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
						if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
							MagicPearl.isMovingForward = false;
						}
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
		storeTimer++;
		if (storeTimer > 50) {
			storeTimer = 0;
		}
		if (storeTimer % 10 == 0) {
			if (Store.storeShouldBeRendered) {
				for (int i = 0; i < purchasingButtons.length; i++) {
					if (purchasingButtons[PURCHASE_BUTTON_HEART].contains(Gdx.input.getX(), Gdx.input.getY())) {
						player.setHealth(player.getHealth() + 1);
						closeStore();
						break;
					}
					else if (purchasingButtons[PURCHASE_BUTTON_RUM].contains(Gdx.input.getX(), Gdx.input.getY())) {
						RumHandler.rumCount++;
						closeStore();
						break;
					}
					else if (purchasingButtons[PURCHASE_BUTTON_GUN].contains(Gdx.input.getX(), Gdx.input.getY())) {
						/*if (player.getPlayerLoot() >= Gun.LOOT_NEEDED_TO_BUY_GUN && TradingPost.hasBeenEntered) { */
						GameObject gun = myGame.getGameScreen().gun;
						((Player) player).getInventory().addObjectToInventory(gun);
						Inventory.inventoryHasStartedCollection = true;
						Gun.hasBeenCollected                    = true;
						Gun.playCollectionSound                 = true;
						GameObjectLoader.gameObjectList.add(gun);

						// Remove loot (player has bought gun).
						player.updatePlayerLoot(-Gun.LOOT_NEEDED_TO_BUY_GUN);

						// Close the store.
						Store.gunHasBeenPurchasedAtStore = true;
						TradingPost.hasBeenEntered       = true;
						closeStore();
						break;
						//}
					}
					//else if (purchasingButtons[3].contains(Gdx.input.getX(), Gdx.input.getY())) {
					//	closeStore();
					//System.exit(0);
					/*GameObject pearl = myGame.gameScreen.magicPearl;
						((Player) player).getInventory().addObjectToInventory(pearl);
						Inventory.inventoryHasStartedCollection = true;
						pearl.hasBeenCollected                  = true;
						MagicPearl.playCollectionSound          = true;
						GameObjectLoader.gameObjectList.add(pearl);

						// Close the store.
						//Store.gunHasBeenPurchasedAtStore     = true;
						Store.storeShouldBeRendered          = false;
						Store.playerWantsToEnterStore        = false;
						Store.shouldDisplayEnterStoreMessage = false;*/
					//}
				}
			} 
		}
	}
}
