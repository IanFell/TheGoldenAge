package input.computer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.mygame.MyGame;

import controllers.GameStateController;
import controllers.PlayerController;
import cutscenes.CutScene;
import debugging.Debugger;
import gameobjects.GameObject;
import gameobjects.collectibles.Rum;
import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.Stump;
import gameobjects.weapons.Weapon;
import handlers.InputHandler;
import handlers.collectibles.RumHandler;
import handlers.holehandler.HoleHandler;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import inventory.Inventory;
import loaders.GameObjectLoader;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import screens.PauseScreen;
import screens.Screens;
import screens.TitleScreen;
import store.Store;
import ui.ConfidenceUi;
import ui.GameOver;
import ui.MapUi;
import ui.Win;

/**
 * Handles keyboard input.
 * 
 * @author Fabulous Fellini
 *
 */
public class Keyboard extends ComputerInput {

	private int weaponElement = 0;

	//private int pauseTimer = 0;

	/**
	 * 
	 * @param MyGame myGame
	 */
	@Override
	public void handleInput(MyGame myGame) {
		GameObject player = PlayerController.getCurrentPlayer(myGame);
		switch (GameAttributeHelper.gameState) {

		case Screens.TITLE_SCREEN:
			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){ 
				if (TitleScreen.titleScreenHover == TitleScreen.PRESS_START) {
					GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN); 
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				GameStateController.switchGameStates(myGame, Screens.CREDITS_SCREEN); 
			}
			break;

		case Screens.CONTROLS_SCREEN:
			if (GameAttributeHelper.playerHasStartedGame && InputHandler.inputType == InputHandler.INPUT_COMPUTER) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
					GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
					GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
					GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
				}
			} else {
				if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
					GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
					GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
				}
			}
			break;

		case Screens.CREDITS_SCREEN:
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			break;

		case Screens.GAME_SCREEN:	
			if ( !Win.triggerWin && !GameOver.triggerGameOver) {
				// Skip intro cut scene.
				if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
					//Debugger.skipIntroCutscene = true;
				}

				if (
						!Inventory.allInventoryShouldBeRendered && 
						!MapUi.mapShouldBeRendered && 
						GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PLAY &&
						!HoleHandler.playerIsInHole &&
						!CutScene.gameShouldPause &&
						!ConfidenceUi.confidenceUiShouldBeRendered
						) {
					handleKeyboardDirectionalButtons(myGame, "wasd", player);
				}

				if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
					// Stump hole mission uses a different player than the game world player.
					if (MissionStumpHole.missionIsActive) {
						if (Stump.playerIsOnStump) {
							Stump.playerIsOnStump                = false;
							MissionStumpHole.playerIsJumping     = true;
							MissionStumpHole.jumpSoundShouldPlay = true;
						}
					} else {
						Player.isJumping = true;
					}
				} else {
					// If we have released space bar.
					if (MissionStumpHole.missionIsActive) {
						MissionStumpHole.jumpSoundShouldPlay = false;
					}
				}

				if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
					if (player.getInventory().getInventoryIsEquipped()) {
						if (player.getInventory().inventory.size() > 0) {
							// Don't switch if the weapon isn't available.
							weaponElement = Inventory.currentlySelectedInventoryObject;
							if (weaponElement < player.getInventory().inventory.size() - 1) {
								Inventory.currentlySelectedInventoryObject = Inventory.currentlySelectedInventoryObject + 1;
								Weapon.shouldPlaySwitchWeaponAudio         = true;
							}
						}
					}
				}

				if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
					if (player.getInventory().getInventoryIsEquipped()) {
						if (player.getInventory().inventory.size() > 0) {
							// Don't switch if the weapon isn't available.
							if (Inventory.currentlySelectedInventoryObject > 0) {
								Inventory.currentlySelectedInventoryObject = Inventory.currentlySelectedInventoryObject - 1;
								Weapon.shouldPlaySwitchWeaponAudio         = true;
							}
						}
					}
				}

				// Display all inventory.
				if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
					if (!Store.playerWantsToEnterStore) {
						Inventory.allInventoryShouldBeRendered = !Inventory.allInventoryShouldBeRendered;
						MapUi.mapShouldBeRendered              = false;
						Inventory.playClickSound               = true;
					}
				} 

				if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
					if (!Store.playerWantsToEnterStore) {
						MapUi.mapShouldBeRendered = !MapUi.mapShouldBeRendered;
						Inventory.allInventoryShouldBeRendered = false;
					}
				}

				// If player has rum, decrease rum count and make player invinvible.
				if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
					if (RumHandler.rumCount > 0 && !Player.isInvincible) {
						RumHandler.rumCount--;
						Player.isInvincible                       = true;
						Player.invincibilityTimer                 = 0;
						ConfidenceUi.confidenceUiShouldBeRendered = true;
						Rum.playDrinkingSound                     = true;
					}
				}

				if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
					if (Store.storeIsUnlocked) {
						Store.playerWantsToEnterStore = !Store.playerWantsToEnterStore;
						Inventory.playClickSound                   = true;
					}
				}

				if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
					if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {
						GameStateController.switchGameStates(myGame, Screens.CONTROLS_SCREEN);
					}
				}

				if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
					if (!CutScene.gameShouldPause) {
						if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PLAY) {
							GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PAUSE;
						} else {
							GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PLAY;
						}
						PauseScreen.playSound = true;
					}
				}

				if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
					if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
						GamePlayHelper.gameOver           = true;
						GameOver.triggerGameOver          = true;
						Win.triggerWin                    = true;
						GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PLAY;
					}
					if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
						System.exit(0);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	//@Override
	public void handleInputOld(MyGame myGame) {
		GameObject player = PlayerController.getCurrentPlayer(myGame);
		switch (GameAttributeHelper.gameState) {
		case Screens.SPLASH_SCREEN:
			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){ 
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			break;

		case Screens.TITLE_SCREEN:
			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){ 
				if (TitleScreen.titleScreenHover == TitleScreen.PRESS_START) {
					GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN); 
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				TitleScreen.titleScreenHover--;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				TitleScreen.titleScreenHover++;
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				GameStateController.switchGameStates(myGame, Screens.CREDITS_SCREEN); 
			}

			break;

		case Screens.CONTROLS_SCREEN:
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			break;

		case Screens.CREDITS_SCREEN:
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
			}
			break;

		case Screens.GAME_SCREEN:	

			// Skip intro cut scene.
			if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
				Debugger.skipIntroCutscene = true;
			}

			if (
					!Inventory.allInventoryShouldBeRendered && 
					!MapUi.mapShouldBeRendered && 
					GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PLAY &&
					!HoleHandler.playerIsInHole &&
					!CutScene.gameShouldPause
					) {
				//handleKeyboardDirectionalButtons(myGame, "arrows", player);
				handleKeyboardDirectionalButtons(myGame, "wasd", player);
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				// Stump hole mission uses a different player than the game world player.
				if (MissionStumpHole.missionIsActive) {
					if (Stump.playerIsOnStump) {
						Stump.playerIsOnStump                = false;
						MissionStumpHole.playerIsJumping     = true;
						MissionStumpHole.jumpSoundShouldPlay = true;
					}
				} else {
					Player.isJumping = true;
				}
			} else {
				// If we have released space bar.
				if (MissionStumpHole.missionIsActive) {
					MissionStumpHole.jumpSoundShouldPlay = false;
				}
			}

			// Execute screenshake.
			/*
			if (Gdx.input.isKeyPressed(Input.Keys.Z)) { 
				GameScreen.screenShake.shake(0.3f, 3);
			} */

			/**
			 * Perform operations on lighting.  
			 * This will make the light texture grow,
			 * then shrink back to normal size when key is released.
			 */
			/*
			if (Gdx.input.isKeyPressed(Input.Keys.L)) { 
				LightHandler.isGrowing = true;
			} else {
				LightHandler.isGrowing = false;
			} */

			/*
		float cameraZoomAmount = 1.0f;
		// Zoom camera out.
		if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && Gdx.input.isKeyPressed(Input.Keys.O)) {
			GameScreen.camera.zoom += cameraZoomAmount;
		}

		// Zoom camera in.
		if (Gdx.input.isKeyPressed(Input.Keys.Z) && Gdx.input.isKeyPressed(Input.Keys.I)) {
			GameScreen.camera.zoom -= cameraZoomAmount;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			Player.hasTorch = !Player.hasTorch;
		}*/


			if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
				if (player.getInventory().getInventoryIsEquipped()) {
					if (player.getInventory().inventory.size() > 0) {
						// Don't switch if the weapon isn't available.
						weaponElement = Inventory.currentlySelectedInventoryObject;
						if (weaponElement < player.getInventory().inventory.size() - 1) {
							Inventory.currentlySelectedInventoryObject = Inventory.currentlySelectedInventoryObject + 1;
							Weapon.shouldPlaySwitchWeaponAudio         = true;
						}
					}
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
				if (player.getInventory().getInventoryIsEquipped()) {
					if (player.getInventory().inventory.size() > 0) {
						// Don't switch if the weapon isn't available.
						if (Inventory.currentlySelectedInventoryObject > 0) {
							Inventory.currentlySelectedInventoryObject = Inventory.currentlySelectedInventoryObject - 1;
							Weapon.shouldPlaySwitchWeaponAudio         = true;
						}
					}
				}
			}

			// Display all inventory.
			if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
				//if (!startClickTimer) {
				//startClickTimer                        = true;
				if (!Store.playerWantsToEnterStore) {
					Inventory.allInventoryShouldBeRendered = !Inventory.allInventoryShouldBeRendered;
					MapUi.mapShouldBeRendered              = false;
				}
				//} else {
				// Make sure inventory button is only hit once.
				//inventoryTimer++;
				//if (inventoryTimer > 1) {
				//	resetClickTimer();
				//}
				//}
			} 

			/*
		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			Inventory.inventoryIsEquipped = false;
		}*/

			if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
				if (!Store.playerWantsToEnterStore) {
					MapUi.mapShouldBeRendered = !MapUi.mapShouldBeRendered;
					Inventory.allInventoryShouldBeRendered = false;
				}

				/*
				if (!startClickTimer) {
					startClickTimer                        = true;
					MapUi.mapShouldBeRendered              = !MapUi.mapShouldBeRendered;
					Inventory.allInventoryShouldBeRendered = false;
				} else {
					// Make sure inventory button is only hit once.
					inventoryTimer++;
					if (inventoryTimer > 1) {
						resetClickTimer();
					}
				} */
			}

			//if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			//BulletLoader.createBullet(myGame);
			//} 

			// If player has rum, decrease rum count and make player invinvible.
			if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
				if (RumHandler.rumCount > 0 && !Player.isInvincible) {
					RumHandler.rumCount--;
					Player.isInvincible                       = true;
					Player.invincibilityTimer                 = 0;
					ConfidenceUi.confidenceUiShouldBeRendered = true;
					Rum.playDrinkingSound                     = true;
				}
			}

			/*
		if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
			Store.storeShouldBeRendered = !Store.storeShouldBeRendered;
		}*/

			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				if (Store.storeIsUnlocked) {
					Store.playerWantsToEnterStore = !Store.playerWantsToEnterStore;
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
				if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {
					GameStateController.switchGameStates(myGame, Screens.CONTROLS_SCREEN);
				}
			}

			/*
		// Fill your collectibles for debugging.
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
			myGame.getGameObject(Player.PLAYER_ONE).updatePlayerLoot(20);
			RumHandler.rumCount   = 99;
			AmmoHandler.ammoCount = 99;
		}*/

			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				//if (pauseTimer % 5 == 0) {
				if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PLAY) {
					GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PAUSE;
				} else {
					GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PLAY;
				}
				PauseScreen.playSound = true;
				//}
			}

			if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
					GamePlayHelper.gameOver           = true;
					GameOver.triggerGameOver          = true;
					Win.triggerWin                    = true;
					GameAttributeHelper.gamePlayState = GameAttributeHelper.STATE_PLAY;
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
					System.exit(0);
				}
			}
			//pauseTimer++;
			//if (pauseTimer > 50) {
			//	pauseTimer = 0;
			//}
			/*
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			System.exit(0);
		}*/
		}
	}


	/**
	 * Handles arrows and WASD.
	 * 
	 * @param GameObject player
	 * @param String     directions
	 */
	private void handleKeyboardDirectionalButtons(MyGame myGame, String directions, GameObject player) {

		// If user presses the T button to use turbo.
		int turboSpeed    = 0;
		float playerSpeed = Player.PLAYER_SPEED;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			playerSpeed = Player.PLAYER_SPEED * turboSpeed;
		}

		int up;
		int down;
		int left;
		int right;

		if (directions.equalsIgnoreCase("arrows")) {
			up    = Input.Keys.UP;
			down  = Input.Keys.DOWN;
			left  = Input.Keys.LEFT;
			right = Input.Keys.RIGHT;
		} else {
			up    = Input.Keys.W;
			down  = Input.Keys.S;
			left  = Input.Keys.A;
			right = Input.Keys.D;
		}

		if(GameObjectLoader.gameObjectList.contains(player)) {
			// RawBar Mission uses a different player than normal since it's kind of like a mini game.
			if (MissionRawBar.phasesAreInProgress && !MissionRawBar.rawBarMissionComplete) { 
				if (Gdx.input.isKeyPressed(left)) {
					MissionRawBar.playerX -= MissionRawBar.MISSION_RAW_BAR_SPEED;
					player.setDirection(Player.DIRECTION_LEFT);
				}
				else if (Gdx.input.isKeyPressed(right)) {
					MissionRawBar.playerX += MissionRawBar.MISSION_RAW_BAR_SPEED;
					player.setDirection(Player.DIRECTION_RIGHT);
				}
				else if (Gdx.input.isKeyPressed(up)) {
					MissionRawBar.playerY -= MissionRawBar.MISSION_RAW_BAR_SPEED;
					player.setDirection(Player.DIRECTION_UP);
				}
				else if (Gdx.input.isKeyPressed(down)) {
					MissionRawBar.playerY += MissionRawBar.MISSION_RAW_BAR_SPEED;
					player.setDirection(Player.DIRECTION_DOWN);
				}
			} else if (MissionStumpHole.missionIsActive) { 
				// Stump Hole Mission uses a different player than normal since it's kind of like a mini game.
				if (Gdx.input.isKeyPressed(left)) {
					MissionStumpHole.player.setX(MissionStumpHole.player.getX() - MissionStumpHole.playerDx);
					MissionStumpHole.playerDirection = MissionStumpHole.DIRECTION_LEFT;
				}
				else if (Gdx.input.isKeyPressed(right)) {
					MissionStumpHole.player.setX(MissionStumpHole.player.getX() + MissionStumpHole.playerDx);
					MissionStumpHole.playerDirection = MissionStumpHole.DIRECTION_RIGHT;
				}
			} else { 
				// Use normal player.
				if (Gdx.input.isKeyPressed(left)) {
					((Player) player).moveLeft(playerSpeed);
				}
				else if (Gdx.input.isKeyPressed(right)) {
					((Player) player).moveRight(playerSpeed);
				}
				else if (Gdx.input.isKeyPressed(up)) {
					((Player) player).moveUp(playerSpeed);
				}
				else if (Gdx.input.isKeyPressed(down)) {
					((Player) player).moveDown(playerSpeed);
				}
				else {
					Player.playerIsMoving = false;
				}
			}
		}
	}
}
