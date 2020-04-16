package handlers.audio;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.collectibles.Ammo;
import gameobjects.collectibles.Heart;
import gameobjects.collectibles.Rum;
import gameobjects.gamecharacters.Boss;
import gameobjects.gamecharacters.Giant;
import gameobjects.gamecharacters.Player;
import gameobjects.nature.Feather;
import gameobjects.weapons.Gun;
import gameobjects.weapons.LegendSword;
import gameobjects.weapons.MagicPearl;
import handlers.collectibles.AmmoHandler;
import handlers.enemies.GiantHandler;
import helpers.GameAttributeHelper;
import inventory.Inventory;
import loaders.SoundLoader;
import loaders.cannonballloader.CannonBallLoader;
import loaders.cannonloader.CannonLoader;
import loaders.chestloader.ChestLoader;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import mixer.Mixer;
import screens.Screens;
import ui.LocationMarker;

/**
 * Handles game sounds.
 * 
 * @author Fabulous Fellini
 *
 */
public class SoundHandler {

	private final int QUICK_SAND_LOOPING_VALUE = 20;

	private boolean startLandingAudio = false;

	private int attackTimer    = GameAttributeHelper.TIMER_START_VALUE;
	private int inventoryTimer = GameAttributeHelper.TIMER_START_VALUE;
	private int jumpTimer      = GameAttributeHelper.TIMER_START_VALUE;
	private int quickSandTimer = GameAttributeHelper.TIMER_START_VALUE;

	/**
	 * 
	 * @param SoundLoader soundLoader
	 * @param MyGame      myGame
	 */
	public void handleSound(SoundLoader soundLoader, MyGame myGame) {
		if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {
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
			if (MissionRawBar.playCollectionSound) {
				soundLoader.bubbleSound.play(Mixer.BUBBLE_VOLUME);
				MissionRawBar.playCollectionSound = false;
			}

			handleEnemyDeathSound(myGame, soundLoader);

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

			attackTimer++;
			if (attackTimer > 2) {
				attackTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
			if (Player.playerIsPerformingAttack) {
				if (attackTimer > 1) {
					if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof LegendSword) {
						soundLoader.swordSound.play(Mixer.SWORD_ATTACK_VOLUME);
					} else if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Gun) {
						if (AmmoHandler.ammoCount > 0) {
							soundLoader.pistolSound.play(Mixer.GUN_ATTACK_VOLUME);
						}
					} else {
						soundLoader.bubbleSound.play(Mixer.BUBBLE_ATTACK_VOLUME);
					}
				}
			}

			// Collectibles.
			if (Heart.playSound) {
				soundLoader.heartSound.play(Mixer.HEART_COLLECT_VOLUME);
				Heart.playSound = false;
			}
			if (Ammo.playSound) {
				soundLoader.pickUpGunSound.play(Mixer.HEART_COLLECT_VOLUME);
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
				soundLoader.pickUpSwordSound.play(Mixer.FEATHER_COLLECT_VOLUME);
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

			// Click sound when choosing different inventory objects.
			inventoryTimer++;
			if (inventoryTimer > 2) {
				inventoryTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
			if (Inventory.playClickSound) {
				if (inventoryTimer < 1) {
					soundLoader.clickSound.play(Mixer.CLICK_VOLUME);
					Inventory.playClickSound = false;
					inventoryTimer           = GameAttributeHelper.TIMER_START_VALUE;
				}
			}
			handleJumpingAudio(soundLoader);
			handleLandingAudio(soundLoader);
			handleQuickSandAudio(soundLoader);

			if (LocationMarker.playSound) {
				soundLoader.locationMarkerSound.play(Mixer.LOCATION_MARKER_HIT_VOLUME);
				LocationMarker.playSound = false;
			}

			if (LocationMarker.playBeepSound) {
				soundLoader.locatorBeep.play(Mixer.LOCATION_MARKER_BEEP_VOLUME);
				LocationMarker.playBeepSound = false;
			}
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
					soundLoader.enemyDeathSound.play(Mixer.ENEMY_DEATH_VOLUME);
					myGame.gameScreen.enemyHandler.enemySpawner[i].enemies.get(k).setPlaySound(false);
				}
			}
		}
		for (int i = 0; i < myGame.gameScreen.gruntHandler.gruntSpawner.length; i++) {
			for (int k = 0; k < myGame.gameScreen.gruntHandler.gruntSpawner[i].grunts.size(); k++) {
				// For now make the same sound for every grunt killing.  
				if (myGame.gameScreen.gruntHandler.gruntSpawner[i].grunts.get(k).getPlaySound()) {
					soundLoader.enemyDeathSound.play(Mixer.ENEMY_DEATH_VOLUME);
					myGame.gameScreen.gruntHandler.gruntSpawner[i].grunts.get(k).setPlaySound(false);
				}
			}
		}
		for (int i = 0; i < GiantHandler.giants.length; i++) {
			if (GiantHandler.giants[i].getPlaySound()) {
				soundLoader.enemyDeathSound.play(Mixer.ENEMY_DEATH_VOLUME);
				GiantHandler.giants[i].setPlaySound(false);
			}
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
		if (Player.isJumping || MissionStumpHole.jumpSoundShouldPlay) {
			if (jumpTimer < 1) {
				if (!Player.isInWater) { 
					soundLoader.jumpSound.play(Mixer.JUMP_VOLUME);
				}
			}
			jumpTimer++;
			if (jumpTimer > 50) {
				jumpTimer = GameAttributeHelper.TIMER_START_VALUE;
			}
		} else {
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
