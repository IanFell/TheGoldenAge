package handlers.audio;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.mygame.MyGame;

import cutscenes.CutScene;
import gameobjects.GameObject;
import gameobjects.collectibles.Ammo;
import gameobjects.collectibles.Heart;
import gameobjects.collectibles.Rum;
import gameobjects.collectibles.TenHearts;
import gameobjects.gamecharacters.enemies.Boss;
import gameobjects.gamecharacters.enemies.Giant;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import gameobjects.nature.Feather;
import gameobjects.nature.shockplant.ShockPlant;
import gameobjects.weapons.Arrow;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Dagger;
import gameobjects.weapons.Gun;
import gameobjects.weapons.LegendSword;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
import gameobjects.weapons.Weapon;
import handlers.CutSceneHandler;
import handlers.arrowhandler.ArrowHandler;
import handlers.collectibles.AmmoHandler;
import handlers.enemies.BossHandler;
import handlers.holehandler.HoleHandler;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import inventory.Inventory;
import loaders.audio.SoundLoader;
import loaders.bossloader.BossLoader;
import loaders.cannonballloader.CannonBallLoader;
import loaders.cannonloader.CannonLoader;
import loaders.chestloader.ChestLoader;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import mixer.Mixer;
import screens.PauseScreen;
import screens.Screens;
import store.Store;
import ui.GameOver;
import ui.LocationMarker;
import ui.ObjectiveUi;

/**
 * Handles game sounds.
 * 
 * @author Fabulous Fellini
 *
 */
public class SoundHandler {

	public static boolean gameOverDeathHasPlayed = false;

	public static boolean playRockDropAudio = false;

	private boolean enemyDeathSoundCanPlay = true;
	private int enemyDeathTimer            = 0;

	private final int QUICK_SAND_LOOPING_VALUE = 20;

	private boolean startLandingAudio = false;

	private int attackTimer    = GameAttributeHelper.TIMER_START_VALUE;
	private int inventoryTimer = GameAttributeHelper.TIMER_START_VALUE;
	private int jumpTimer      = GameAttributeHelper.TIMER_START_VALUE;
	private int quickSandTimer = GameAttributeHelper.TIMER_START_VALUE;

	private boolean stumpHoleBirdSFXArePlaying = false;

	private final int OAR_SPLASH_TIMER_MAX = 50;
	private int oarSplashTimer             = 0;

	public static void resetGame() {
		gameOverDeathHasPlayed = false;
	}

	/**
	 * 
	 * @param SoundLoader soundLoader
	 */
	private void handleOarSplashAudio(SoundLoader soundLoader) {
		if (Player.isInWater && Player.playerIsMoving && !CutScene.gameShouldPause && !Inventory.allInventoryShouldBeRendered) {
			if (oarSplashTimer < 1) {
				soundLoader.splash.play(Mixer.SPLASH_VOLUME);
			}
			oarSplashTimer++;
			if (oarSplashTimer > OAR_SPLASH_TIMER_MAX) {
				oarSplashTimer = 0;
			}
		} else {
			oarSplashTimer = 0;
		}
	}

	/**
	 * 
	 * @param SoundLoader soundLoader
	 */
	private void handleCollectibleAudio(SoundLoader soundLoader) {
		if (LegendSword.playSound) {
			soundLoader.pickUpSwordSound.play(Mixer.PICK_UP_SWORD_VOLUME);
			LegendSword.playSound = false;
		}
		if (Gun.playCollectionSound) {
			soundLoader.pickUpGunSound.play(Mixer.PICK_UP_GUN_VOLUME);
			Gun.playCollectionSound = false;
		}
		if (MagicPearl.playCollectionSound) {
			soundLoader.bubbleSound.play(Mixer.BUBBLE_VOLUME);
			MagicPearl.playCollectionSound = false;
		}
		if (BirdWeapon.playCollectionSound) {
			soundLoader.bird.play(Mixer.PICK_UP_BIRD_VOLUME);
			BirdWeapon.playCollectionSound = false;
		}
		if (Paw.playCollectionSound && !Player.isInvincible) {
			soundLoader.monkey.play(Mixer.PICK_UP_MONKEY_VOLUME);
		}
		if (Dagger.playCollectionSound) {
			soundLoader.dagger.play(Mixer.PICK_UP_DAGGER_VOLUME);
			Dagger.playCollectionSound = false;
		}
		if (MissionRawBar.playCollectionSound) {
			soundLoader.bubbleSound.play(Mixer.BUBBLE_VOLUME);
			MissionRawBar.playCollectionSound = false;
		}
	}

	/**
	 * 
	 * @param SoundLoader soundLoader
	 * @param MyGame      myGame
	 */
	public void handleSound(SoundLoader soundLoader, MyGame myGame) {
		if (GameAttributeHelper.gameState == Screens.TITLE_SCREEN || GameAttributeHelper.gameState == Screens.CONTROLS_SCREEN) {
			// Player is switching options on the title screen.  Just use this audio since it sounds good and already in the game.
			if (Weapon.shouldPlaySwitchWeaponAudio) {
				soundLoader.switchWeapons.play(Mixer.SWITCH_WEAPON_VOLUME);
				Weapon.shouldPlaySwitchWeaponAudio = false;
			}
		}

		if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {

			handleOarSplashAudio(soundLoader);

			if (CutScene.shouldPlayIntroJingle) {
				soundLoader.cutscene.play(Mixer.CUTSCENE_INTRO_JINGLE_VOLUME);
				CutScene.shouldPlayIntroJingle = false;
			}

			handleCollectibleAudio(soundLoader);

			// This will throw a null pointer if started from the title screen without the if statement.
			if (myGame.gameScreen.enemyHandler.enemySpawner[0] != null) {
				handleEnemyDeathSound(myGame, soundLoader);
			}

			if (myGame.getGameObject(Player.PLAYER_ONE).getPlaySound()) {
				soundLoader.playerHit.play(Mixer.PLAYER_HIT_VOLUME);
				myGame.getGameObject(Player.PLAYER_ONE).setPlaySound(false);
			}

			handleCannonSound(myGame, soundLoader);

			// Handle the landing/explosion of the cannon balls.
			for (int i = 0; i < CannonBallLoader.cannonballs.size(); i++) {
				if (CannonBallLoader.cannonballs.get(i).getAttackBoundary().overlaps(myGame.getGameObject(Player.PLAYER_ONE).rectangle)) {
					if (CannonBallLoader.cannonballs.get(i).isPlayLandSound()) {
						soundLoader.bombSound.play(Mixer.BOMB_VOLUME);
						CannonBallLoader.cannonballs.get(i).setPlayLandSound(false);
					}
				}
			}

			if (ArrowHandler.playSound && !MissionStumpHole.missionIsActive) {
				soundLoader.bow.play(Mixer.ARROW_VOLUME);
				ArrowHandler.playSound = false;
			}

			attackTimer++;
			if (attackTimer > 5) {
				attackTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
			if (
					Player.playerIsPerformingAttack && 
					!CutScene.gameShouldPause &&
					!Inventory.allInventoryShouldBeRendered &&
					!Store.playerWantsToEnterStore &&
					!Store.shouldDisplayEnterStoreMessage &&
					!Store.shouldDisplayEnterStoreMessageAlternate &&
					!MissionRawBar.phasesAreInProgress &&
					!MissionStumpHole.missionIsActive &&
					attackTimer < 3
					//attackTimer < 2
					) {
				if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.size() > 0) {
					if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof LegendSword) {
						soundLoader.swordSound.play(Mixer.SWORD_ATTACK_VOLUME);
					} else if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Gun) {
						if (AmmoHandler.ammoCount > 0) {
							soundLoader.pistolSound.play(Mixer.GUN_ATTACK_VOLUME);
						} else {
							soundLoader.buzzer.play(Mixer.BUZZER_VOLUME);
						}
					} else if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Paw) {
						if (Paw.playAttackSound && !Player.isInvincible) {
							soundLoader.bombSound.play(Mixer.PAW_BOMB_VOLUME);
						}
					} else if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Dagger) {
						soundLoader.dagger.play(Mixer.DAGGER_ATTACK_VOLUME);
					} else {
						soundLoader.bubbleSound.play(Mixer.BUBBLE_ATTACK_VOLUME);
					}
				}
			}

			if (BirdWeapon.birdIsAttacking && BirdWeapon.playAttackSound) {
				soundLoader.birdTwo.loop(Mixer.BIRD_STUMP_HOLE_MISSION_VOLUME);
				BirdWeapon.playAttackSound = false;
			} else if (!BirdWeapon.birdIsAttacking) {
				soundLoader.birdTwo.stop();
			}

			// Collectibles.
			if (Heart.playSound) {
				soundLoader.heartSound.play(Mixer.HEART_COLLECT_VOLUME);
				Heart.playSound = false;
			}
			if (TenHearts.playSound) {
				soundLoader.trumpet.play(Mixer.TEN_HEARTS_COLLECT_VOLUME);
				TenHearts.playSound = false;
			}
			if (Ammo.playSound) {
				soundLoader.pickUpGunSound.play(Mixer.AMMO_COLLECT_VOLUME);
				Ammo.playSound = false;
			}
			if (Rum.playSound) {
				soundLoader.rumSound.play(Mixer.RUM_COLLECT_VOLUME);
				Rum.playSound = false;
			}
			for (int i = 0; i < ChestLoader.chests.length; i++) {
				if (ChestLoader.chests[i].getPlaySound()) {
					soundLoader.chestSound.play(Mixer.CHEST_COLLECT_VOLUME);
					ChestLoader.chests[i].setPlaySound(false);
				}
			}
			if (Feather.playSound) {
				soundLoader.arrow.play(Mixer.FEATHER_COLLECT_VOLUME);
				Feather.playSound = false;
			}

			if (Giant.playLandingSound) {
				soundLoader.giantLandingSound.play(Mixer.GIANT_LANDING_VOLUME);
				Giant.playLandingSound = false;
			}

			if (Boss.playGruntSound) {
				soundLoader.bossGrunt.play(Mixer.BOSS_GRUNT_VOLUME);
				Boss.playGruntSound = false;
			}

			handleBossAudio(soundLoader);

			// Click sound when choosing different inventory objects.
			inventoryTimer++;
			if (inventoryTimer > 2) {
				inventoryTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
			if (Inventory.playClickSound) {
				if (inventoryTimer < 1) {
					soundLoader.switchWeapons.play(Mixer.SWITCH_WEAPON_VOLUME);
					Inventory.playClickSound = false;
					inventoryTimer           = GameAttributeHelper.TIMER_START_VALUE;
				}
			}
			// Player is switching weapons without the inventory UI.
			if (Weapon.shouldPlaySwitchWeaponAudio) {
				soundLoader.switchWeapons.play(Mixer.SWITCH_WEAPON_VOLUME);
				Weapon.shouldPlaySwitchWeaponAudio = false;
			}

			if (ShockPlant.playSparkAudio) {
				soundLoader.spark.play(Mixer.SPARK_VOLUME);
				ShockPlant.playSparkAudio = false;
			}

			if (!CutScene.gameShouldPause) {
				if (CutSceneHandler.playerCanJump) {
					handleJumpingAudio(soundLoader);
					handleLandingAudio(soundLoader);
				}
				handleQuickSandAudio(soundLoader);
			}

			if (LocationMarker.playSound) {
				soundLoader.locationMarkerSound.play(Mixer.LOCATION_MARKER_HIT_VOLUME);
				LocationMarker.playSound = false;
			}

			if (LocationMarker.playBeepSound) {
				//soundLoader.locatorBeep.play(Mixer.LOCATION_MARKER_BEEP_VOLUME);
				LocationMarker.playBeepSound = false;
			}

			handleObjectiveChange(soundLoader);

			if (Store.playSound) {
				soundLoader.register.play(Mixer.REGISTER_VOLUME);
				Store.playSound = false;
			}

			if (Store.playBuzzerAudio) {
				soundLoader.buzzer.play(Mixer.BUZZER_VOLUME);
				Store.playBuzzerAudio = false;
			}

			/*
			 * This is now handled by shooting the gun, not here.
			if (OutOfAmmo.playBuzzerAudio) {
				soundLoader.buzzer.play(Mixer.BUZZER_VOLUME);
				OutOfAmmo.playBuzzerAudio = false;
			} */

			if (HoleHandler.playerIsInHole) {
				if (HoleHandler.playSound) {
					soundLoader.tunnel.loop(Mixer.TUNNEL_VOLUME);
					HoleHandler.playSound = false;
				}
			} else {
				soundLoader.tunnel.stop();
			}

			if (PlayerOne.playDeathSound) {
				// Play female death scream.
				soundLoader.femaleScream.play(Mixer.DEATH_VOLUME);

				// Now we will have a male player following so play both death screams.
				if (Store.gunHasBeenPurchasedAtStore) {
					soundLoader.death.play(Mixer.DEATH_VOLUME);
				}

				// Now we have two males following.
				if (MissionRawBar.rawBarMissionComplete) {
					soundLoader.maleScream.play(Mixer.DEATH_VOLUME_SHIVER);
				}

				PlayerOne.playDeathSound = false;
			}

			if (Rum.playDrinkingSound) {
				soundLoader.drink.play(Mixer.DRINK_VOLUME);
				Rum.playDrinkingSound = false;
			}

			// Take this out for now because it's really annoying.
			//handleStumpHoleMissionBirdSFX(soundLoader);

			if (PauseScreen.playSound) {
				soundLoader.pause.play(Mixer.PAUSE_VOLUME);
				PauseScreen.playSound = false;
			}

			if (GameOver.triggerGameOver && !gameOverDeathHasPlayed) {
				soundLoader.death.play(Mixer.DEATH_VOLUME);
				soundLoader.femaleScream.play(Mixer.DEATH_VOLUME);
				soundLoader.gameOver.play(Mixer.PIRATE_VOICE_GAME_OVER_VOLUME);
				gameOverDeathHasPlayed = true;
			}

			if (MissionStumpHole.missionIsActive) {
				if (playRockDropAudio) {
					soundLoader.laserFall.play(Mixer.LASER_FALL_VOLUME);
					playRockDropAudio = false;
				} 
			} else {
				soundLoader.laserFall.stop();
			}

			if (Arrow.playArrowSwooshAudio) {
				soundLoader.arrowSwoosh.play(Mixer.ARROW_SWOOSH_VOLUME);
				Arrow.playArrowSwooshAudio = false;
			}

			if (MissionStumpHole.playSplashSound) {
				soundLoader.splash.play(Mixer.SPLASH_VOLUME);
				MissionStumpHole.playSplashSound = false;
			}
		}
	}

	private void handleBossAudio(SoundLoader soundLoader) {
		for (int i = 0; i < BossLoader.boss.length; i++) {
			if (BossHandler.shouldPlayLaughSound[i]) {

				Sound bossMessage = soundLoader.myTreasure;
				switch (i) {
				case BossHandler.TRADIN_POST:
					bossMessage = soundLoader.looking;
					break;
				case BossHandler.APALACHICOLA:
					bossMessage = soundLoader.hey;
					break;
				case BossHandler.STUMP_HOLE:
					bossMessage = soundLoader.looking;
					break;
				case BossHandler.WEWA:
					bossMessage = soundLoader.you;
					break;
				case BossHandler.THE_POINT:
					bossMessage = soundLoader.myTreasure;
					break;
				}
				bossMessage.play(Mixer.BOSS_LAUGH_VOLUME);

				BossHandler.shouldPlayLaughSound[i] = false;
			}
		}
		if (Boss.playBashAudio) {
			soundLoader.bossAttackBash.play(Mixer.BOSS_ATTACK_BASH_VOLUME);
			Boss.playBashAudio = false;
		}
	}

	/**
	 * 
	 * @param SoundLoader soundLoader
	 */
	private void handleObjectiveChange(SoundLoader soundLoader) {
		if (ObjectiveUi.playObjectiveChangeGoToTradingPost) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeGoToTradingPost = false;
		}
		if (ObjectiveUi.playObjectiveChangeEnterTradingPost) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeEnterTradingPost = false;
		}
		if (ObjectiveUi.playObjectiveChangeBuyTheGun) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeBuyTheGun = false;
		}
		if (ObjectiveUi.playObjectiveChangeGoToRawBar) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeGoToRawBar = false;
		}
		if (ObjectiveUi.playObjectiveChangeCollectOysters) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeCollectOysters = false;
		}
		if (ObjectiveUi.playObjectiveChangeGoToStumpHole) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeGoToStumpHole = false;
		}
		if (ObjectiveUi.playObjectiveChangeCollectFeathers) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeCollectFeathers = false;
		}
		if (ObjectiveUi.playObjectiveChangeCollectTheBird) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeCollectTheBird = false;
		}
		if (ObjectiveUi.playObjectiveChangeGoToWewa) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeGoToWewa = false;
		}
		if (ObjectiveUi.playObjectiveChangeFindTheCauldron) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeFindTheCauldron = false;
		}
		if (ObjectiveUi.playObjectiveChangeThePoint) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeThePoint = false;
		}
		if (ObjectiveUi.playObjectiveChangeTreasure) {
			soundLoader.objectiveChange.play(Mixer.OBJECTIVE_CHANGE_VOLUME);
			ObjectiveUi.playObjectiveChangeTreasure = false;
		}
	}

	/**
	 * 
	 * @param SoundLoader soundLoader
	 */
	private void handleStumpHoleMissionBirdSFX(SoundLoader soundLoader) {
		if (MissionStumpHole.missionIsActive) {
			if (!stumpHoleBirdSFXArePlaying) {
				soundLoader.birdTwo.loop(Mixer.BIRD_STUMP_HOLE_MISSION_VOLUME);
				stumpHoleBirdSFXArePlaying = true;
			}
		}
		if (MissionStumpHole.stumpHoleMissionComplete) {
			soundLoader.birdTwo.stop();
		}
	}

	/**
	 * 
	 * @param SoundLoader soundLoader
	 */
	private void handleQuickSandAudio(SoundLoader soundLoader) {
		if (Player.isInQuickSand) {
			if (quickSandTimer == 0) {
				soundLoader.quickSand.play(Mixer.QUICK_SAND_VOLUME);
			}
			quickSandTimer++;
			if (quickSandTimer > QUICK_SAND_LOOPING_VALUE) {
				quickSandTimer = 0;
			}
		} 
	}

	/**
	 * Handles sound when cannon fires or knight dies.
	 * 
	 * @param MyGame      myGame
	 * @param SoundLoader soundLoader
	 */
	private void handleCannonSound(MyGame myGame, SoundLoader soundLoader) {
		for (int i = 0; i < CannonLoader.cannons.length; i++) {
			if (CannonLoader.cannons[i].isPlayBlastSound()) {
				GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
				if (CannonLoader.cannons[i].getAttackBoundary().overlaps(player.rectangle)) {
					soundLoader.cannonFire.play(Mixer.CANNON_FIRE_VOLUME);

				}
				CannonLoader.cannons[i].setPlayBlastSound(false);

				// If the knight dies.
				if (CannonLoader.cannons[i].knight.getPlaySound()) {
					soundLoader.enemyDeathSound.play(Mixer.ENEMY_DEATH_VOLUME);
					CannonLoader.cannons[i].knight.setPlaySound(false);
				}
			}
		}
	}

	/**
	 * 
	 * @param MyGame      myGame
	 * @param SoundLoader soundLoader
	 */
	private void handleEnemyDeathSound(MyGame myGame, SoundLoader soundLoader) {
		for (int i = 0; i < myGame.gameScreen.enemyHandler.enemySpawner.length; i++) {
			for (int k = 0; k < myGame.gameScreen.enemyHandler.enemySpawner[i].enemies.size(); k++) {
				// For now make the same sound for every enemy killing.  
				if (myGame.gameScreen.enemyHandler.enemySpawner[i].enemies.get(k).getPlaySound()) {
					if (enemyDeathSoundCanPlay) {
						int randomSound = RandomNumberGenerator.generateRandomInteger(100);
						if (randomSound < 50) {
							soundLoader.enemyDeathSound.play(Mixer.ENEMY_DEATH_VOLUME);
						} else {
							soundLoader.enemyHurt.play(Mixer.ENEMY_DEATH_VOLUME_ALTERNATE);
						}
					}
					myGame.gameScreen.enemyHandler.enemySpawner[i].enemies.get(k).setPlaySound(false);
				}
			}
		}
		for (int i = 0; i < myGame.gameScreen.gruntHandler.gruntSpawner.length; i++) {
			for (int k = 0; k < myGame.gameScreen.gruntHandler.gruntSpawner[i].grunts.size(); k++) {
				// For now make the same sound for every grunt killing.  
				if (myGame.gameScreen.gruntHandler.gruntSpawner[i].grunts.get(k).getPlaySound()) {
					if (enemyDeathSoundCanPlay) {
						soundLoader.enemyDeathSound.play(Mixer.ENEMY_DEATH_VOLUME);
					}
					myGame.gameScreen.gruntHandler.gruntSpawner[i].grunts.get(k).setPlaySound(false);
				}
			}
		}

		if (Giant.playGiantDeathSound) {
			soundLoader.giantGrunt.play(Mixer.GIANT_DEATH_VOLUME);
			Giant.playGiantDeathSound = false;
		} 

		enemyDeathTimer++;
		if (enemyDeathTimer > 20) {
			enemyDeathTimer        = 0;
			enemyDeathSoundCanPlay = true;
		}
	}

	/**
	 * This also handles jumping audio in Stump Hole mission.
	 * We use a different jumping variable in Stump Hole mission so the camera doesn't
	 * move everytime the player jumps in that mission.
	 * 
	 * @param SoundLoader soundLoader
	 */
	private void handleJumpingAudio(SoundLoader soundLoader) {
		if (Player.isJumping && !MissionStumpHole.missionIsActive) {
			if (jumpTimer < 1) {
				if (!Player.isInWater) { 
					soundLoader.jumpSound.play(Mixer.JUMP_VOLUME);
				}
			}
			jumpTimer++;
			if (jumpTimer > 50) {
				jumpTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
		} /*else if (MissionStumpHole.missionIsActive && MissionStumpHole.jumpSoundShouldPlay) {
			if (jumpTimer < 1) {
				soundLoader.jumpSound.play(Mixer.JUMP_VOLUME);
			}
			jumpTimer++;
			if (jumpTimer > 50) {
				jumpTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
		}*/ else {
			/*
			if (MissionStumpHole.missionIsActive && MissionStumpHole.jumpSoundShouldPlay) {
				if (jumpTimer < 1) {
					soundLoader.jumpSound.play(Mixer.JUMP_VOLUME);
				}
				jumpTimer++;
				if (jumpTimer > 50) {
					jumpTimer = GameAttributeHelper.TIMER_START_VALUE;
				}
			} */
			jumpTimer = GameAttributeHelper.TIMER_START_VALUE;
		} 
	}

	/**
	 * 
	 * @param SoundLoader soundLoader
	 */
	private void handleLandingAudio(SoundLoader soundLoader) {
		if (startLandingAudio && Player.jumpingAction == Player.ON_GROUND) {
			if (!Player.isInWater) { 
				soundLoader.landSound.play(Mixer.LAND_VOLUME);
			}
			startLandingAudio = false;
		}
		if (Player.isJumping) {
			startLandingAudio = true;
		}
	}
}
