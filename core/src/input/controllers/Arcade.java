package input.controllers;

import com.mygdx.mygame.MyGame;

import controllers.GameStateController;
import cutscenes.CutScene;
import debugging.Debugger;
import gameobjects.GameObject;
import gameobjects.collectibles.Rum;
import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.Stump;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Gun;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
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
import store.Store;
import ui.AddedToInventory;
import ui.ConfidenceUi;
import ui.ControlsUi;
import ui.InventoryUi;
import ui.MapUi;
import ui.UserInterface;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Arcade extends ControllerInput {

	private final int SWITCH_TIME_LIMIT = 5;

	private int buyItemTimer = 0;

	private int attackTimer   = 0;
	private boolean canAttack = false;

	private int storeSwitchTimer   = 0;
	private boolean storeCanSwitch = false;

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

	/**
	 * 
	 * @param GameObject player
	 * @param MyGame     myGame
	 */
	private void pollSticksForUi(GameObject player, MyGame myGame) {
		if (!storeCanSwitch) {
			storeSwitchTimer++;
			if (storeSwitchTimer > SWITCH_TIME_LIMIT) {
				storeSwitchTimer = 0;
				storeCanSwitch   = true;
			}
		}

		if (stickIsMoved(AXIS_LEFT_X)) {
			System.out.print("LEFT STICK X pressed \n");
			if (controller.getAxis(AXIS_LEFT_X) < 0) {
				if (Inventory.currentlySelectedInventoryObject > 0) {
					if (InventoryUi.clickedObject > 0) {
						if (storeCanSwitch) {
							selectAlternateInventoryObject(Inventory.currentlySelectedInventoryObject, false, player);
							InventoryUi.clickedObject--;
							canClick                           = false;
							Weapon.shouldPlaySwitchWeaponAudio = true;
							storeCanSwitch                     = false;
						}
					}
				}
				if (Store.playerWantsToEnterStore) {
					if (storeCanSwitch) {
						selectStoreObject(myGame, GameObject.DIRECTION_LEFT);
						Weapon.shouldPlaySwitchWeaponAudio = true;
						storeCanSwitch                     = false;
					}
				}
			} else if (controller.getAxis(AXIS_LEFT_X) > 0) {
				if (Inventory.currentlySelectedInventoryObject < 11) {
					if (InventoryUi.clickedObject < player.getInventory().inventory.size() - 1) {
						if (storeCanSwitch) {
							selectAlternateInventoryObject(Inventory.currentlySelectedInventoryObject, true, player);
							InventoryUi.clickedObject++;
							Weapon.shouldPlaySwitchWeaponAudio = true;
							storeCanSwitch                     = false;
						}
					}
				}
				if (Store.playerWantsToEnterStore) {
					if (storeCanSwitch) {
						selectStoreObject(myGame, GameObject.DIRECTION_RIGHT);
						Weapon.shouldPlaySwitchWeaponAudio = true;
						storeCanSwitch                     = false;
					}
				}
			} 
		}

		if (stickIsMoved(AXIS_LEFT_Y)) {
			System.out.print("LEFT STICK Y pressed \n");
			if (controller.getAxis(AXIS_LEFT_Y) < deadZone) {
				if (UserInterface.userInterfaceOption < UserInterface.userInterfaceMaxOptionValue) {
					if (storeCanSwitch) {
						Weapon.shouldPlaySwitchWeaponAudio = true;
						if (UserInterface.userInterfaceOption == UserInterface.MAP_SCREEN) {
							UserInterface.userInterfaceOption      = UserInterface.CONTROLS_SCREEN;
							MapUi.mapShouldBeRendered              = false;
							ControlsUi.controlsShouldBeRendered    = true;
						}
						else if (UserInterface.userInterfaceOption == UserInterface.INVENTORY_SCREEN) {
							UserInterface.userInterfaceOption      = UserInterface.MAP_SCREEN;
							MapUi.mapShouldBeRendered              = true;
							ControlsUi.controlsShouldBeRendered    = false;
						} 
						storeCanSwitch = false;
					}
				}
			} else if (controller.getAxis(AXIS_LEFT_Y) > deadZone) {
				if (UserInterface.userInterfaceOption > 0) {
					if (storeCanSwitch) {
						Weapon.shouldPlaySwitchWeaponAudio = true;
						if (UserInterface.userInterfaceOption == UserInterface.MAP_SCREEN) {
							UserInterface.userInterfaceOption      = UserInterface.INVENTORY_SCREEN;
							MapUi.mapShouldBeRendered              = false;
							ControlsUi.controlsShouldBeRendered    = false;
						}
						else if (UserInterface.userInterfaceOption == UserInterface.CONTROLS_SCREEN) {
							UserInterface.userInterfaceOption      = UserInterface.MAP_SCREEN;
							MapUi.mapShouldBeRendered              = true;
							ControlsUi.controlsShouldBeRendered    = false;
						} 
						storeCanSwitch = false;
					}
				}
			} 
		} 
	}

	/**
	 * 
	 * @param GameObject player
	 */
	@Override
	protected void pollTriggers(GameObject player) {}

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

			if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {
				if (Store.shouldDisplayEnterStoreMessage && storeCanSwitch) {
					if (Store.storeIsUnlocked) {
						Store.playerWantsToEnterStore = !Store.playerWantsToEnterStore;
						Weapon.shouldPlaySwitchWeaponAudio = true;
						storeCanSwitch                     = false;
					}
				}
			}
		}

		if (controller.getButton(BUTTON_ATTACK)) {
			switch (GameAttributeHelper.gameState) {
			case Screens.GAME_SCREEN:
				if (!canAttack) {
					attackTimer++;
					if (attackTimer > SWITCH_TIME_LIMIT) {
						attackTimer = 0;
						canAttack   = true;
					}
				}

				if (canAttack) {
					Player.playerIsPerformingAttack = true;
					canAttack                       = false;
				}
				if (player.getInventory().inventory.size() > 0) {
					if (player.getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) != null) {
						if (player.getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
							MagicPearl.isAttacking     = true;
							MagicPearl.isMovingForward = true;
						}
						if (player.getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof BirdWeapon) {
							if (!BirdWeapon.birdIsAttacking) {
								BirdWeapon.birdIsAttacking = true;
								BirdWeapon.playAttackSound = true;
								BirdWeapon.shouldPlaySound = true;
							}
						}
						if (player.getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Paw && !Paw.hasBeenUsed) {
							Paw.hasBeenUsed         = true;
							Paw.playAttackSound     = true;
						}
					}
				}
			}
		} else {
			if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {
				if (Inventory.inventoryIsEquipped) {
					if (!Store.storeShouldBeRendered) {
						if (Inventory.currentlySelectedInventoryObject < player.getInventory().inventory.size()) {
							if (player.getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
								MagicPearl.isMovingForward = false;
							}
						}
					}
				}
			}
		}

		if(controller.getButton(BUTTON_JUMP)) {
			if (!Debugger.skipIntroCutscene) {
				Debugger.skipIntroCutscene = true;
			}

			// Stump hole mission uses a different player than the game world player.
			if (MissionStumpHole.missionIsActive) {
				if (Stump.playerIsOnStump) {
					Stump.playerIsOnStump                = false;
					MissionStumpHole.playerIsJumping     = true;
					MissionStumpHole.jumpSoundShouldPlay = true;
				}
			} else {
				if (!Inventory.allInventoryShouldBeRendered) {
					Player.isJumping = true;
				}
			}
		}

		if (controller.getButton(BUTTON_UI)) {
			if (!MissionStumpHole.missionIsActive && !MissionRawBar.phasesAreInProgress) {
				if (clickUiTimer < 1) {
					Inventory.playClickSound = true;
					if (Inventory.allInventoryShouldBeRendered || MapUi.mapShouldBeRendered) {
						Inventory.allInventoryShouldBeRendered = false;
						MapUi.mapShouldBeRendered              = false;
					} else {
						Inventory.allInventoryShouldBeRendered = !Inventory.allInventoryShouldBeRendered;
					}
				} 

				clickUiTimer++;
				if (clickUiTimer > SWITCH_TIME_LIMIT) {
					clickUiTimer = GameAttributeHelper.TIMER_START_VALUE;
				}
			}
		}

		if (controller.getButton(BUTTON_SELECT)) {
			if (Store.shouldDisplayEnterStoreMessage) {
				// TODO IF STORE DOESNT WORK LOOK HERE
				buyItemTimer++;
				if (buyItemTimer > SWITCH_TIME_LIMIT) {
					buyItemTimer = GameAttributeHelper.TIMER_START_VALUE;
				}
				if (buyItemTimer < 1) {
					switch(storeObjectNumber) {
					case PURCHASE_BUTTON_HEART:
						if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_HEART) {
							player.setHealth(player.getHealth() + 1);
							closeStore();
							Store.playSound = true;
							// Remove loot (player has bought health).
							player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_HEART);
							AddedToInventory.shouldRender        = true;
							AddedToInventory.shouldDisplayHealth = true;
							AddedToInventory.timer               = 0;
							break;
						} else {
							Store.playerIsShortOnLootMessageShouldRender = true;
						}
						break;
					case PURCHASE_BUTTON_RUM:
						if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_RUM) {
							RumHandler.rumCount++;
							closeStore();
							Store.playSound = true;
							// Remove loot (player has bought rum).
							player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_RUM);
							AddedToInventory.shouldRender     = true;
							AddedToInventory.shouldDisplayRum = true;
							AddedToInventory.timer            = 0;
							break;
						} else {
							Store.playerIsShortOnLootMessageShouldRender = true;
						}
						break;
					case PURCHASE_BUTTON_GUN:
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

								AddedToInventory.shouldRender     = true;
								AddedToInventory.shouldDisplayGun = true;
								AddedToInventory.timer            = 0;
							} else {
								Store.playerIsShortOnLootMessageShouldRender = true;
							}
						}
						break;
					case PURCHASE_BUTTON_PEARL: 
						if (!Store.pearlPurchased && Store.pearlUnlocked) {
							if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_PEARL) {
								GameObject pearl = myGame.gameScreen.magicPearl;
								((Player) player).getInventory().addObjectToInventory(pearl);
								//Inventory.inventoryHasStartedCollection = true;
								pearl.hasBeenCollected                  = true;
								MagicPearl.playCollectionSound          = true;
								GameObjectLoader.gameObjectList.add(pearl);
								closeStore();
								Store.playSound      = true;
								Store.pearlPurchased = true;
								// Remove loot (player has bought pearl).
								player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_PEARL);
								AddedToInventory.shouldRender            = true;
								AddedToInventory.shouldDisplayMagicPearl = true;
								AddedToInventory.timer                   = 0;
								break;
							} else {
								Store.playerIsShortOnLootMessageShouldRender = true;
								break;
							}
						}
						break;
					case PURCHASE_BUTTON_AMMO:
						if (Store.ammoUnlocked) {
							if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_AMMO) {
								if (AmmoHandler.ammoCount < AmmoHandler.MAX_AMOUNT_AMMO_PLAYER_CAN_CARRY) {
									AmmoHandler.ammoCount += AmmoHandler.ammoValue;
									closeStore();
									Store.playSound = true;
									player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_AMMO);
									AddedToInventory.shouldRender      = true;
									AddedToInventory.shouldDisplayAmmo = true;
									AddedToInventory.timer             = 0;
									break;
								} 
							} else {
								Store.playerIsShortOnLootMessageShouldRender = true;
								break;
							}
						}
						break;
					case PURCHASE_BUTTON_NULL:
						break;
					}
				}
			}
			if (!Store.playerWantsToEnterStore) {
				if (Inventory.allInventoryShouldBeRendered) {
					selectAlternateInventoryObject(
							InventoryUi.clickedObject,
							player
							);
					Inventory.allInventoryShouldBeRendered = false;
				} else {
					// Use this button to bring back magic pearl.
					if (MagicPearl.isMovingForward) {
						MagicPearl.isAttacking     = false;
						MagicPearl.isMovingForward = false;
					}

					if (RumHandler.rumCount > 0 && !Player.isInvincible) {
						RumHandler.rumCount--;
						Player.isInvincible                       = true;
						Player.invincibilityTimer                 = 0;
						ConfidenceUi.confidenceUiShouldBeRendered = true;
						Rum.playDrinkingSound                     = true;
					}
				}
			} 
		}
	}
}
