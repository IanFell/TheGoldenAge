package input.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.mygdx.mygame.MyGame;

import cutscenes.CutScene;
import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.weapons.Weapon;
import helpers.ControllerInputHelper;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import input.Input;
import inventory.Inventory;
import maps.MapInformationHolder;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import screens.PauseScreen;
import screens.Screens;
import screens.TitleScreen;
import store.Store;
import ui.ControlsUi;
import ui.GameOver;
import ui.InventoryUi;
import ui.MapUi;
import ui.UserInterface;
import ui.Win;

/**
 * Parent class for all GamePads.
 * 
 * @author Cero from JGO - I did change it a little bit to reflect my needs.
 *
 */
public class ControllerInput extends Input {

	// This is a patch for switch inventory slower.
	private int intventorySwitchTimer = 0;

	private boolean canPressTrigger = true;
	private int triggerTimer        = 0;

	private boolean canSelectDifferentOption = true;
	private int selectOptionTimer            = 0;

	public static int storeObjectNumber = 0;

	private final int CLICK_TIMER_MAX_VALUE = 5;

	// Make sure inventory button if held down is not being hit infinite times.
	protected float clickUiTimer    = GameAttributeHelper.TIMER_START_VALUE;
	private int clickTimer          = GameAttributeHelper.TIMER_START_VALUE;
	protected boolean canClick      = true;

	protected Controller controller;

	/**
	 * Determine whether a controller has been detected.
	 */
	private boolean hasControllers = true;

	protected String controllerName;

	/**
	 * Represents the dead zone for joysticks.  
	 * In the deadzone will register as 0 (non movement).
	 * This is used because controller is very sensitive and we
	 * dont want an accidental movement to trigger.
	 */
	protected float deadZone = 0.2f;

	/**
	 * Controller buttons.
	 */
	protected int BUTTON_X;
	protected int BUTTON_Y;
	protected int BUTTON_A;
	protected int BUTTON_B;
	protected int BUTTON_LB;
	protected int BUTTON_L3;
	protected int BUTTON_RB;
	protected int BUTTON_R3;
	protected int BUTTON_BACK;
	protected int BUTTON_START;

	/**
	 * Arcade buttons.
	 */
	protected int BUTTON_ATTACK;
	protected int BUTTON_JUMP;
	protected int BUTTON_UI;
	protected int BUTTON_SELECT;

	/**
	 * Controller D-Pad.
	 */
	protected PovDirection BUTTON_DPAD_UP    = PovDirection.north;
	protected PovDirection BUTTON_DPAD_DOWN  = PovDirection.south;
	protected PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
	protected PovDirection BUTTON_DPAD_LEFT  = PovDirection.west;

	/**
	 * Controller sticks.
	 */
	protected int AXIS_LEFT_X;  // -1 is left | +1 is right
	protected int AXIS_LEFT_Y;  // -1 is up | +1 is down
	protected int AXIS_RIGHT_X; // -1 is left | +1 is right
	protected int AXIS_RIGHT_Y; // -1 is up | +1 is down

	private boolean canPollTriggers  = true;
	private int pollTriggerTimer     = 0;

	public static void resetGame() {
		storeObjectNumber = 0;
	}

	/**
	 * If a controller is found, set the name.
	 */
	public void init() {
		if(Controllers.getControllers().size == 0) {
			hasControllers = false;
		} else {
			controller     = ControllerInputHelper.getFirstController();
			controllerName = ControllerInputHelper.getControllerName();
		}
	}

	private void handleTriggerPollTimer() {
		if (!canPollTriggers) {
			pollTriggerTimer++;
			if (pollTriggerTimer > 30) {
				pollTriggerTimer = 0;
				canPollTriggers = true;
			}
		}
	}

	private void handleClickTimer() {
		// Use timer so we can't change between inventory objects too quickly.
		clickTimer++;
		if (clickTimer > CLICK_TIMER_MAX_VALUE) {
			clickTimer = GameAttributeHelper.TIMER_START_VALUE;
			canClick = true;
		}
	}

	private void handleTriggerTimer() {
		if (!canPressTrigger) {
			triggerTimer++;
		}
		if (triggerTimer > 1) {
			triggerTimer    = 0;
			canPressTrigger = true;
		} 
	}

	private void handleOptionTimer() {
		if (!canSelectDifferentOption) {
			selectOptionTimer++;
			if (selectOptionTimer > 20) {
				selectOptionTimer = 0;
				canSelectDifferentOption = true;
			}
		}
	}

	/**
	 * Handles controller input.
	 * Polling is broken up into sections of buttons for better readability. 
	 * 
	 * @param GameObject player
	 * @param MyGame     myGame
	 */
	public void handleInput(GameObject player, MyGame myGame) {
		if (hasControllers) {  
			// Don't poll these if UI is open.
			if (!Inventory.allInventoryShouldBeRendered && !MapUi.mapShouldBeRendered && !CutScene.gameShouldPause) {
				pollSticks(player);
			}
			pollMainFourButtons(player, myGame);
			pollTriggers(player);
			pollStartSection();
			pollDPad(player, myGame);
			handleClickTimer();
			handleTriggerPollTimer();
			handleTriggerTimer();
			handleOptionTimer();
		}
	}

	/**
	 * Polls controller for A, B, X, and Y.
	 * 
	 * @param GameObject player
	 * @param MyGame     myGame
	 */
	protected void pollMainFourButtons(GameObject player, MyGame myGame) {
		//if(controller.getButton(BUTTON_X)) {}
	}

	/**
	 * Polls controller for LB, RB.  
	 * This method is overridden differently for different 
	 * controllers because the triggers are registered differently.
	 * 
	 * @param GameObject player
	 */
	protected void pollTriggers(GameObject player) {
		if (Inventory.allInventoryShouldBeRendered) {
			if (canClick) {
				if(controller.getButton(BUTTON_LB)) {
					if (UserInterface.userInterfaceOption > 0) {
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
					}
				}
				if(controller.getButton(BUTTON_RB)) {
					if (UserInterface.userInterfaceOption < UserInterface.userInterfaceMaxOptionValue) {
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
					}
				}
				canClick = false;
			}
		}
	}

	/**
	 * Polls controller for DPad.
	 * 
	 * @param GameObject player
	 * @param MyGame     myGame
	 */
	private void pollDPad(GameObject player, MyGame myGame) {
		intventorySwitchTimer++;
		if (controller.getPov(0) == BUTTON_DPAD_UP) {
			if (GameAttributeHelper.gameState == Screens.TITLE_SCREEN) {
				if (canSelectDifferentOption) {
					if (TitleScreen.titleScreenHover > 0) {
						TitleScreen.titleScreenHover--;
						Weapon.shouldPlaySwitchWeaponAudio = true;
						canSelectDifferentOption           = false;
					}
				}
			} else {
				Player.hasTorch = !Player.hasTorch;
			}
		} else if (controller.getPov(0) == BUTTON_DPAD_DOWN) {
			if (GameAttributeHelper.gameState == Screens.TITLE_SCREEN) {
				if (canSelectDifferentOption) {
					if (TitleScreen.titleScreenHover < TitleScreen.titleScreenOptionsMax - 1) {
						TitleScreen.titleScreenHover++;
						Weapon.shouldPlaySwitchWeaponAudio = true;
						canSelectDifferentOption           = false;
					}
				}
			}
		} else if (controller.getPov(0) == BUTTON_DPAD_LEFT) {
			if (Inventory.currentlySelectedInventoryObject > 0 && intventorySwitchTimer % 5 == 0) {
				if (InventoryUi.clickedObject > 0) {
					selectAlternateInventoryObject(Inventory.currentlySelectedInventoryObject, false, player);
					InventoryUi.clickedObject--;
					canClick                           = false;
					Weapon.shouldPlaySwitchWeaponAudio = true;
				}
			}
			if (Store.storeShouldBeRendered) {
				if (canClick) {
					selectStoreObject(myGame, GameObject.DIRECTION_LEFT);
					Weapon.shouldPlaySwitchWeaponAudio = true;
				}
			}
		} else if (controller.getPov(0) == BUTTON_DPAD_RIGHT) {
			if (Inventory.currentlySelectedInventoryObject < 11 && intventorySwitchTimer % 5 == 0) {
				if (InventoryUi.clickedObject < player.getInventory().inventory.size() - 1) {
					selectAlternateInventoryObject(Inventory.currentlySelectedInventoryObject, true, player);
					InventoryUi.clickedObject++;
					Weapon.shouldPlaySwitchWeaponAudio = true;
				}
			}
			if (Store.storeShouldBeRendered) {
				if (canClick) {
					selectStoreObject(myGame, GameObject.DIRECTION_RIGHT);
					Weapon.shouldPlaySwitchWeaponAudio = true;
				}
			}
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 * @param int    direction
	 */
	protected void selectStoreObject(MyGame myGame, int direction) {
		if (direction == GameObject.DIRECTION_LEFT) {
			if (storeObjectNumber > 0) {
				storeObjectNumber--;
			}
		} else {
			if (storeObjectNumber < 5) {
				storeObjectNumber++;
			}
		}
		canClick = false;
	}

	/**
	 * This method uses the D-Pad to cycle through inventory, different than the method in the Input class.
	 * 
	 * @param int        currentlySelectedInventory
	 * @param boolean    isAdding
	 * @param GameObject player
	 */
	protected void selectAlternateInventoryObject(int currentlySelectedInventory, boolean isAdding, GameObject player) {
		int increment = 1;
		inventoryButtonIsPressed[Inventory.currentlySelectedInventoryObject] = false;
		if (isAdding) {
			inventoryButtonIsPressed[Inventory.currentlySelectedInventoryObject + increment] = true;
			Inventory.currentlySelectedInventoryObject = Inventory.currentlySelectedInventoryObject + increment;
		} else {
			inventoryButtonIsPressed[Inventory.currentlySelectedInventoryObject - increment] = true;
			Inventory.currentlySelectedInventoryObject = Inventory.currentlySelectedInventoryObject - increment;
		}
		Inventory.mouseIsClickingOnInventoryObject = true;
		Inventory.playClickSound                   = true;
		((Player) player).getInventory().setInventoryIsEquipped(true);
	}

	/**
	 * Polls controller for analog sticks.
	 * 
	 * @param GameObject player
	 */
	protected void pollSticks(GameObject player) {
		if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PLAY) {
			float playerSpeed = Player.PLAYER_SPEED - 0.1f;
			int turboSpeed    = 0; // TODO CHANGE THIS TO 0 FOR REAL GAME.
			if(controller.getButton(BUTTON_L3)) {
				//System.out.print("L3 button pressed \n");
				//System.out.println("Player is using turbo!  Going fast!");
				playerSpeed = Player.PLAYER_SPEED * turboSpeed;
			}
			Player.playerIsMoving = false;

			// RawBar Mission uses a different player than normal since it's kind of like a mini game.
			if (MissionRawBar.phasesAreInProgress) {
				if (stickIsMoved(AXIS_LEFT_X)) {
					if (controller.getAxis(AXIS_LEFT_X) < 0) {
						MissionRawBar.playerX -= MissionRawBar.MISSION_RAW_BAR_SPEED;
						player.setDirection(Player.DIRECTION_LEFT);
					} else if (controller.getAxis(AXIS_LEFT_X) > 0) {
						MissionRawBar.playerX += MissionRawBar.MISSION_RAW_BAR_SPEED;
						player.setDirection(Player.DIRECTION_RIGHT);
					} 
				} 
				if (stickIsMoved(AXIS_LEFT_Y)) {
					if (controller.getAxis(AXIS_LEFT_Y) < deadZone) {
						MissionRawBar.playerY -= MissionRawBar.MISSION_RAW_BAR_SPEED;
						player.setDirection(Player.DIRECTION_UP);
					} else if (controller.getAxis(AXIS_LEFT_Y) > deadZone) {
						MissionRawBar.playerY += MissionRawBar.MISSION_RAW_BAR_SPEED;
						player.setDirection(Player.DIRECTION_DOWN);
					} 
				}
			} else if (MissionStumpHole.missionIsActive) { 
				// Stump Hole Mission uses a different player than normal since it's kind of like a mini game.
				if (stickIsMoved(AXIS_LEFT_X)) {
					if (controller.getAxis(AXIS_LEFT_X) < 0) {
						MissionStumpHole.player.setX(MissionStumpHole.player.getX() - MissionStumpHole.playerDx);
						MissionStumpHole.playerDirection = MissionStumpHole.DIRECTION_LEFT;
					} else if (controller.getAxis(AXIS_LEFT_X) > 0) {
						MissionStumpHole.player.setX(MissionStumpHole.player.getX() + MissionStumpHole.playerDx);
						MissionStumpHole.playerDirection = MissionStumpHole.DIRECTION_RIGHT;
					}
				}
			}  else {
				// Left stick.
				if (stickIsMoved(AXIS_LEFT_X)) {
					//System.out.print("LEFT STICK X pressed \n");
					if (controller.getAxis(AXIS_LEFT_X) < 0) {
						if (player.getX() > 0) {
							((Player) player).moveLeft(playerSpeed);
						}
					} /*else*/ if (controller.getAxis(AXIS_LEFT_X) > 0) {
						if (player.getX() < GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + MapInformationHolder.CHUNK_WIDTH - 1) {
							((Player) player).moveRight(playerSpeed);
						}
					} 
				} 
				if (stickIsMoved(AXIS_LEFT_Y)) {
					//System.out.print("LEFT STICK Y pressed \n");
					if (controller.getAxis(AXIS_LEFT_Y) < deadZone) {
						if (player.getY() > 0) {
							((Player) player).moveUp(playerSpeed);
						}
					} /*else*/ if (controller.getAxis(AXIS_LEFT_Y) > deadZone) {
						if (player.getY() < GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + MapInformationHolder.CHUNK_HEIGHT - 1) {
							((Player) player).moveDown(playerSpeed);
						}
					} 
				} 
			}

			/**
			 * We don't use the right stick yet, so don't even check it.
			 */
			/*
		if (stickIsMoved(AXIS_RIGHT_X)) {
			System.out.print("RIGHT STICK X pressed \n");
		}
		if (stickIsMoved(AXIS_RIGHT_Y)) {
			System.out.print("RIGHT STICK Y pressed \n");
		}
		if(controller.getButton(BUTTON_R3)) {
			System.out.print("R3 button pressed \n");
		} 
			 */
		}
	}

	/**
	 * Polls controller for start, back/select buttons.
	 */
	protected void pollStartSection() {
		if (!CutScene.gameShouldPause) {

			if(controller.getButton(BUTTON_BACK)) {
				if (canClick) {
					//System.out.print("BACK button pressed \n");
					if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PLAY) {
						GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PAUSE;
					} else {
						GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PLAY;
					}
					PauseScreen.playSound = true;
					canClick = false;
				}
			}

			if(controller.getButton(BUTTON_START)) {
				/*
				if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
					GamePlayHelper.gameOver  = true;
					GameOver.triggerGameOver = true;
					Win.triggerWin           = true;
				} */
				
				if (!MissionStumpHole.missionIsActive && !MissionRawBar.phasesAreInProgress && !Store.playerWantsToEnterStore) {
					// If we press start and UI is open, close it.
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
					}
					clickUiTimer++;
					if (clickUiTimer > 5) {
						clickUiTimer = GameAttributeHelper.TIMER_START_VALUE;
					}
				}
			}
		}
	}

	/**
	 * Determines if either joystick has been moved.
	 * 
	 * @param int axis
	 * @return boolean
	 */
	protected boolean stickIsMoved(int axis) {
		if (controller.getAxis(axis) > deadZone  || controller.getAxis(axis) < -deadZone) {
			return true;
		}
		return false;
	}
}
