package input.controllers;

import com.mygdx.mygame.MyGame;

import controllers.GameStateController;
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
import screens.TitleScreen;
import store.Store;
import ui.AddedToInventory;
import ui.ConfidenceUi;
import ui.InventoryUi;
import ui.MapUi;

/**
 * Logitech F310 GamePad.
 * 
 * @author Fabulous Fellini
 *
 */
public class LogitechF310 extends ControllerInput {

	private boolean inventoryHasClosedAndPlayerIsAllowedToJump = true;
	private int inventoryJumpingTimer                          = 0;
	private final int INVENTORY_JUMP_TIMER_VALUE               = 10;

	/**
	 * Trigger buttons.
	 */
	protected int BUTTON_LT;
	protected int BUTTON_RT;

	/**
	 * Constructor.
	 */
	public LogitechF310() {
		super();
		this.BUTTON_X     = 0;
		this.BUTTON_Y     = 3;
		this.BUTTON_A     = 1;
		this.BUTTON_B     = 2;
		this.BUTTON_BACK  = 8;
		this.BUTTON_START = 9;
		this.BUTTON_LB    = 4;
		this.BUTTON_L3    = 10;
		this.BUTTON_RB    = 5;
		this.BUTTON_R3    = 11;
		this.BUTTON_LT    = 6;
		this.BUTTON_RT    = 7;
		this.AXIS_LEFT_X  = 3; // -1 is left | +1 is right
		this.AXIS_LEFT_Y  = 2; // -1 is up | +1 is down
		this.AXIS_RIGHT_X = 1; // -1 is left | +1 is right
		this.AXIS_RIGHT_Y = 0; // -1 is up | +1 is down
	}

	/**
	 * Polls controller for LB, RB, LT, RT.
	 * 
	 * @param GameObject player
	 */
	@Override
	protected void pollTriggers(GameObject player) {

		if (!MissionStumpHole.missionIsActive && !MissionRawBar.phasesAreInProgress) {

			super.pollTriggers(player);

			// If player presses both triggers on pause screen, exit game.
			if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
				if(controller.getButton(BUTTON_LT) && controller.getButton(BUTTON_RT)) {
					System.exit(0);
				}
			}

			// I can't get the other trigger to function correctly, so lets just use this one.
			if(controller.getButton(BUTTON_LT)) {
				MagicPearl.isAttacking     = false;
				MagicPearl.isMovingForward = false;
			} 

			if(controller.getButton(BUTTON_RT) && player.getInventory().inventory.size() > 0) {
				if (canClick) {
					Player.playerIsPerformingAttack = true;
				}
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
						Paw.haveKilledEnemies   = false;
					}
				}
				canClick = false;
			} else {
				if (Inventory.inventoryIsEquipped) {
					if (!Store.storeShouldBeRendered) {
						if (Inventory.currentlySelectedInventoryObject < player.getInventory().inventory.size()) {
							if (player.getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof MagicPearl) {
								MagicPearl.isMovingForward = false;
							}
						}
					}
				}
				Player.playerIsPerformingAttack = false;
			}
		}
	}

	private void handleInventoryJumpingTimer() {
		if (!inventoryHasClosedAndPlayerIsAllowedToJump) {
			inventoryJumpingTimer++;
			if (inventoryJumpingTimer > INVENTORY_JUMP_TIMER_VALUE) {
				inventoryJumpingTimer                      = 0;
				inventoryHasClosedAndPlayerIsAllowedToJump = true;
			}
		}
	}

	/**
	 * Polls controller for A, B, X, and Y.
	 * 
	 * @param GameObject player
	 */
	@Override
	protected void pollMainFourButtons(GameObject player, MyGame myGame) {
		super.pollMainFourButtons(player, myGame);
		handleInventoryJumpingTimer();
		if(controller.getButton(BUTTON_Y)) {
			// Enter store.
			if (canClick) {
				if (Store.storeIsUnlocked /*&& Store.storeShouldBeRendered*/) {
					//Store.playerWantsToEnterStore = !Store.playerWantsToEnterStore;
					Store.playerWantsToEnterStore          = true;
					canClick                               = false;
					Weapon.shouldPlaySwitchWeaponAudio     = true;
					// Inventory.allInventoryShouldBeRendered = false;
				}
			}
		}
		if(controller.getButton(BUTTON_A)) {
			switch (GameAttributeHelper.gameState) {
			case Screens.SPLASH_SCREEN:
				//GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
				break;
			case Screens.TITLE_SCREEN:
				if (TitleScreen.titleScreenHover == TitleScreen.PRESS_START) {
					GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
					Weapon.shouldPlaySwitchWeaponAudio = true;
				}
				else if (TitleScreen.titleScreenHover == TitleScreen.CONTROLS) {
					GameStateController.switchGameStates(myGame, Screens.CONTROLS_SCREEN);
					Weapon.shouldPlaySwitchWeaponAudio = true; 
				}
				else if (TitleScreen.titleScreenHover == TitleScreen.CREDITS) {
					GameStateController.switchGameStates(myGame, Screens.CREDITS_SCREEN);
					Weapon.shouldPlaySwitchWeaponAudio = true; 
				}
				break;
			case Screens.GAME_SCREEN:

				// Skip Intro.
				Debugger.skipIntroCutscene = true;

				// Stump hole mission uses a different player than the game world player.
				if (MissionStumpHole.missionIsActive) {
					if (Stump.playerIsOnStump) {
						Stump.playerIsOnStump                = false;
						MissionStumpHole.playerIsJumping     = true;
						MissionStumpHole.jumpSoundShouldPlay = true;
					}
				} else {
					if (!Inventory.allInventoryShouldBeRendered) {
						if (inventoryHasClosedAndPlayerIsAllowedToJump) {
							Player.isJumping = true;
						}
					}
				}

				/*
				 * A button will initially select the first inventory object.
				 * Player can cycle through inventory after this using D-Pad.
				 * 
				 * The above is now false.  If we want it true, switch the commented out line below.
				 * Now, it will start wherever the player left off on the inventory.
				 */
				if (!Store.playerWantsToEnterStore) {
					if (Inventory.allInventoryShouldBeRendered) {
						selectAlternateInventoryObject(
								//Inventory.currentlySelectedInventoryObject,
								InventoryUi.clickedObject,
								player
								);
						Inventory.allInventoryShouldBeRendered     = false;
						MapUi.mapShouldBeRendered                  = false;
						inventoryHasClosedAndPlayerIsAllowedToJump = false;
					} else {
						// TODO DO I NEED THIS?
						if (!MissionRawBar.introHasCompleted && MissionRawBar.missionIsActive) {
							MissionRawBar.introHasCompleted = true;
						} else {
							// Only allow player to jump if UI is not open.
							if (inventoryHasClosedAndPlayerIsAllowedToJump) {
								Player.isJumping = true;
							}
						}
					}
					break;
				} else if (Store.storeShouldBeRendered) {
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
					inventoryHasClosedAndPlayerIsAllowedToJump = false;
				}
			}
		} else {
			if (Inventory.allInventoryShouldBeRendered) {
				Inventory.mouseIsClickingOnInventoryObject = false;
				for (int i = 0; i < inventoryButtonIsPressed.length; i++) {
					inventoryButtonIsPressed[0] = false;
				}
			}
		}

		if(controller.getButton(BUTTON_B)) {
			switch (GameAttributeHelper.gameState) {
			case Screens.CREDITS_SCREEN:
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
				Weapon.shouldPlaySwitchWeaponAudio = true;
				break;
			case Screens.CONTROLS_SCREEN:
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
				Weapon.shouldPlaySwitchWeaponAudio = true;
				break;
			case Screens.GAME_SCREEN:	
				Store.playerWantsToEnterStore = false;
				//givePlayerEverything(myGame);
				break;
			}
		} 

		if(controller.getButton(BUTTON_X)) {
			if (RumHandler.rumCount > 0 && !Player.isInvincible) {
				RumHandler.rumCount--;
				Player.isInvincible                       = true;
				Player.invincibilityTimer                 = 0;
				ConfidenceUi.confidenceUiShouldBeRendered = true;
				Rum.playDrinkingSound                     = true;
			}
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void givePlayerEverything(MyGame myGame) {
		myGame.getGameObject(Player.PLAYER_ONE).updatePlayerLoot(20);
		myGame.getGameObject(Player.PLAYER_ONE).setHealth(30);
		RumHandler.rumCount   = 99;
		AmmoHandler.ammoCount = 99;
	}
}
