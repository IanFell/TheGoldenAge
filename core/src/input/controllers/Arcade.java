package input.controllers;

import com.mygdx.mygame.MyGame;

import controllers.GameStateController;
import cutscenes.CutScene;
import debugging.Debugger;
import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.Stump;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.Gun;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Weapon;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.CollectibleHandler;
import handlers.collectibles.RumHandler;
import helpers.GameAttributeHelper;
import inventory.Inventory;
import loaders.GameObjectLoader;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import screens.Screens;
import screens.TitleScreen;
import store.Store;
import ui.AddedToInventory;
import ui.InventoryUi;
import ui.MapUi;

public class Arcade extends ControllerInput {

	/**
	 * Constructor.
	 */
	public Arcade() {
		super();
		this.BUTTON_ATTACK = 0;
		this.BUTTON_JUMP   = 1;
		this.BUTTON_UI     = 2;
		this.BUTTON_SELECT = 3;
		this.BUTTON_START  = 4;
		this.AXIS_LEFT_X   = 3; // -1 is left | +1 is right
		this.AXIS_LEFT_Y   = 2; // -1 is up | +1 is down
		this.AXIS_RIGHT_X  = 1; // -1 is left | +1 is right
		this.AXIS_RIGHT_Y  = 0; // -1 is up | +1 is down

		//System.exit(0);
	}

	/**
	 * Handles controller input.
	 * Polling is broken up into sections of buttons for better readability. 
	 * 
	 * @param GameObject player
	 * @param MyGame     myGame
	 */
	@Override
	public void handleInput(GameObject player, MyGame myGame) {
		if (
				!Inventory.allInventoryShouldBeRendered && 
				!MapUi.mapShouldBeRendered && 
				!CutScene.gameShouldPause && 
				!Store.playerWantsToEnterStore
				) {
			pollSticks(player);
		} else {
			pollSticksForUi(player, myGame);
		}
		pollAllArcadeButtons(player, myGame);
	}

	private void pollSticksForUi(GameObject player, MyGame myGame) {


		if (stickIsMoved(AXIS_LEFT_X)) {
			System.out.print("LEFT STICK X pressed \n");
			if (controller.getAxis(AXIS_LEFT_X) < 0) {
				if (Inventory.currentlySelectedInventoryObject > 0) {
					if (InventoryUi.clickedObject > 0) {
						selectAlternateInventoryObject(Inventory.currentlySelectedInventoryObject, false, player);
						InventoryUi.clickedObject--;
						canClick                           = false;
						Weapon.shouldPlaySwitchWeaponAudio = true;
					}
				}
				if (Store.playerWantsToEnterStore) {
					//if (canClick) {
						selectStoreObject(myGame, GameObject.DIRECTION_LEFT);
						Weapon.shouldPlaySwitchWeaponAudio = true;
					//}
				}
			} else if (controller.getAxis(AXIS_LEFT_X) > 0) {
				if (Inventory.currentlySelectedInventoryObject < 11) {
					if (InventoryUi.clickedObject < player.getInventory().inventory.size() - 1) {
						selectAlternateInventoryObject(Inventory.currentlySelectedInventoryObject, true, player);
						InventoryUi.clickedObject++;
						Weapon.shouldPlaySwitchWeaponAudio = true;
					}
				}
				if (Store.playerWantsToEnterStore) {
					//if (canClick) {
						selectStoreObject(myGame, GameObject.DIRECTION_RIGHT);
						Weapon.shouldPlaySwitchWeaponAudio = true;
					//}
				}
			} 
		} 
	}

	@Override
	protected void pollTriggers(GameObject player) {
		//System.exit(0);
	}

	/**
	 * 
	 * @param GameObject player
	 */
	protected void pollAllArcadeButtons(GameObject player, MyGame myGame) {

		if (controller.getButton(BUTTON_START)) {
			if (GameAttributeHelper.gameState == Screens.TITLE_SCREEN) {
				GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
				Weapon.shouldPlaySwitchWeaponAudio = true;
			}
		}

		if (controller.getButton(BUTTON_ATTACK)) {
			switch (GameAttributeHelper.gameState) {
			case Screens.GAME_SCREEN:
				// Skip Intro.
				if (!Debugger.skipIntroCutscene) {
					Debugger.skipIntroCutscene = true;
				}
			}
		}

		if(controller.getButton(BUTTON_JUMP)) {
			if (!Inventory.allInventoryShouldBeRendered) {
				Player.isJumping = true;
			}
		}

		if (controller.getButton(BUTTON_UI)) {
			//if (Inventory.allInventoryShouldBeRendered) {
			if (clickUiTimer < 1) {
				Inventory.playClickSound = true;
				if (Inventory.allInventoryShouldBeRendered || MapUi.mapShouldBeRendered) {
					Inventory.allInventoryShouldBeRendered = false;
					MapUi.mapShouldBeRendered              = false;
				} else {
					// If we press start and UI is not open, open inventory screen.
					// We can navigate through inventory screen by pressing RB.
					Inventory.allInventoryShouldBeRendered = !Inventory.allInventoryShouldBeRendered;
				}
			//}
			} /*else {
				// Enter store.
				if (canClick) {
					if (Store.storeIsUnlocked) {
						Store.playerWantsToEnterStore = !Store.playerWantsToEnterStore;
						canClick = false;
						Weapon.shouldPlaySwitchWeaponAudio = true;
					}
				}
			}*/
			clickUiTimer++;
			if (clickUiTimer > 5) {
				clickUiTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
			//}
		}

		if (controller.getButton(BUTTON_SELECT)) {
			if (!Store.playerWantsToEnterStore) {
				if (Inventory.allInventoryShouldBeRendered) {
					selectAlternateInventoryObject(
							//Inventory.currentlySelectedInventoryObject,
							InventoryUi.clickedObject,
							player
							);
					Inventory.allInventoryShouldBeRendered = false;
				}
			} 
			
			// TODO THIS NEEDS TO SELECT THE WEAPON LIKE INVENTORY AND CLOSE THE STORE.
			if (Store.shouldDisplayEnterStoreMessage) {
				if (canClick) {
					if (Store.storeIsUnlocked) {
						Store.playerWantsToEnterStore = !Store.playerWantsToEnterStore;
						canClick = false;
						Weapon.shouldPlaySwitchWeaponAudio = true;
					}
				}
			}
		}
	}
}
