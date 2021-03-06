package handlers;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import cutscenes.CutScene;
import gameobjects.GameObject;
import gameobjects.PoisonPlant;
import gameobjects.collectibles.Ammo;
import gameobjects.collectibles.Heart;
import gameobjects.collectibles.Rum;
import gameobjects.collectibles.TenHearts;
import gameobjects.gamecharacters.enemies.Boss;
import gameobjects.gamecharacters.enemies.Enemy;
import gameobjects.gamecharacters.enemies.Giant;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import gameobjects.nature.Feather;
import gameobjects.nature.QuickSand;
import gameobjects.nature.hole.Hole;
import gameobjects.nature.shockplant.ShockPlant;
import gameobjects.stationarygameobjects.Chest;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.Arrow;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.CannonBall;
import gameobjects.weapons.Dagger;
import gameobjects.weapons.Gun;
import gameobjects.weapons.LegendSword;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
import gameobjects.weapons.RockDrop;
import gameobjects.weapons.Weapon;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.CollectibleHandler;
import handlers.collectibles.RumHandler;
import handlers.holehandler.HoleHandler;
import inventory.Inventory;
import loaders.GameObjectLoader;
import maps.MapHandler;
import missions.MissionChests;
import missions.MissionLegendOfTheSevenSwords;
import missions.MissionStumpHole;
import store.Store;
import tiles.Tile;
import ui.AddedToInventory;
import ui.BossHealthUi;
import ui.LocationMarker;
import ui.collectibles.HealthUi;

/**
 * Handles collisions between game objects.
 * 
 * @author Fabulous Fellini
 *
 */
public class CollisionHandler {

	// Keep track of enemy collision timing for player health.
	//private static int enemyTimer = 0;

	// Keep track of quick sand collision timing for player health.
	private static int quickSandTimer = 0;

	private final static int HEALTH_TIMER_MAX = 25;

	// Times the removal of hearts so they don't dissapear really fast and kill the player.
	private final static int HEALTH_TIMER_TRIGGER = 24;

	public static void resetGame() {
		quickSandTimer = 0;
	}

	/**
	 * Only player can collide with solid tiles.
	 * NPCs can walk through them.
	 * 
	 * Currently player is set to be able to walk through a solid tile.
	 * 
	 * @param GameObject player
	 * @param MapHandler mapHandler
	 * @param Tile       tile
	 */
	public static void checkIfPlayerHasCollidedWithSolidTile(GameObject player, MapHandler mapHandler, Tile tile) {
		if (tile.isSolid()) {
			if (tile.getBoundingRectangle().overlaps(player.rectangle)) {
				// Move player so he is not longer overlapping tile bounds.
				switch (Player.direction) {
				case Player.DIRECTION_LEFT:
					//player.setX(player.getX() + Player.PLAYER_SPEED);
					break;
				case Player.DIRECTION_RIGHT:
					//player.setX(player.getX() - Player.PLAYER_SPEED);
					break;
				case Player.DIRECTION_UP:
					//player.setY(player.getY() + Player.PLAYER_SPEED);
					break;
				case Player.DIRECTION_DOWN:
					//player.setY(player.getY() - Player.PLAYER_SPEED);
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param MapHandler mapHandler
	 * @param Tile       tile
	 */
	public static void checkIfPlayerHasCollidedWithSandOrWaterTile(GameObject player, MapHandler mapHandler, Tile tile) {
		if (tile.getBoundingRectangle().overlaps(player.rectangle)) {
			if (tile.isWater()) {
				Player.isInWater = true;
			} else {
				Player.isInWater = false;
			} 
		}
	}

	/**
	 * 
	 * @param GameObject enemy
	 * @param Tile       tile
	 */
	public static boolean checkIfEnemyHasCollidedWithWaterTile(GameObject enemy, Tile tile) {
		if (tile.getBoundingRectangle().overlaps(enemy.rectangle)) {
			if (tile.isWater()) {
				return true;
			} 
		}
		return false;
	}

	/**
	 * 
	 * @param GameObject enemy
	 * @param Tile       tile
	 */
	public static boolean checkIfEnemyHasCollidedWithSandTile(GameObject enemy, Tile tile) {
		if (tile.getBoundingRectangle().overlaps(enemy.rectangle)) {
			if (!tile.isWater()) {
				return true;
			} 
		}
		return false;
	}

	/**
	 * 
	 * @param GameObject player
	 * @param MapHandler mapHandler
	 * @param Tile       tile
	 */
	public static void checkIfNPCHasCollidedWithSandOrWaterTile(Enemy enemy, MapHandler mapHandler, Tile tile) {
		if (tile.getBoundingRectangle().overlaps(enemy.rectangle)) {
			if (tile.isWater()) {
				switch(enemy.getEnemyDirection()) {
				case Enemy.DIRECTION_LEFT:
					enemy.moveRight(Player.PLAYER_SPEED);
					break;
				case Enemy.DIRECTION_RIGHT:
					enemy.moveLeft(Player.PLAYER_SPEED);
					break;
				case Enemy.DIRECTION_UP:
					enemy.moveDown(Player.PLAYER_SPEED);
					break;
				case Enemy.DIRECTION_DOWN:
					enemy.moveUp(Player.PLAYER_SPEED);
					break;
				}
			} 
		}
	}

	/**
	 * If player collides with chest, increase player score and play chest opening sound.
	 * 
	 * @param GameObject player
	 * @param Chest      chest
	 */
	public static void checkIfPlayerHasCollidedWithChest(GameObject player, Chest chest) {
		if (chest.rectangle.overlaps(player.rectangle) && player.getPlayerLoot() < Player.MAX_AMOUNT_LOOT_PLAYER_CAN_CARRY) {
			float offset = 0.5f;
			switch (Player.direction) {
			case Player.DIRECTION_LEFT:
				player.setX(player.getX() + Player.PLAYER_SPEED + offset);
				break;
			case Player.DIRECTION_RIGHT:
				player.setX(player.getX() - Player.PLAYER_SPEED - offset);
				break;
			case Player.DIRECTION_UP:
				player.setY(player.getY() + Player.PLAYER_SPEED + offset);
				break;
			case Player.DIRECTION_DOWN:
				player.setY(player.getY() - Player.PLAYER_SPEED - offset);
				break;
			} 
			if (chest.isClosed()) {
				if (((Player) player).getPlayerLoot() < Player.MAX_AMOUNT_LOOT_PLAYER_CAN_CARRY) {
					chest.setChestValuesAfterCollisionWithPlayer();
					((Player) player).updatePlayerLoot(Chest.LOOT_VALUE);

					AddedToInventory.shouldDisplayDagger = false;
					AddedToInventory.shouldRender        = true;
					AddedToInventory.shouldDisplayLoot   = true;
					AddedToInventory.timer               = 0;

					// Testing mission.  Later, this will be controlled.  Right now, it is always on.
					if (MissionChests.executeMission) {
						MissionChests.increaseNumberOfChestsOpened();
					}
				}
			}
		} 
	}

	/**
	 * 
	 * @param GameObject player
	 * @param GameObject structure
	 * @param String     structureName
	 * @param MyGame     myGame
	 */
	public static void checkIfPlayerHasCollidedWithStructure(
			GameObject player, 
			GameObject structure, 
			String structureName, 
			MyGame myGame
			) {
		if (structure.rectangle.overlaps(player.rectangle)) {
			if (structureName.equalsIgnoreCase("Raw Bar")) {
				setStoreRenderState();
			} else if (structureName.equalsIgnoreCase("Piggly Wiggly")) {
				setStoreRenderState();
			} else if (structureName.equalsIgnoreCase("Trading Post")) {
				/**
				 * Trading Post is where player is introduced to a "store" and must buy something to proceed.
				 * That is why this code differs from the other structure cases.
				 */
				if (Store.storeIsUnlocked) {
					Store.storeShouldBeRendered = true;
				} else {
					Store.storeIsUnlocked = true;
				}
			} 
			Store.shouldDisplayEnterStoreMessage = true;
		} else {
			Store.shouldDisplayEnterStoreMessage = false;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param GameObject structure
	 * @param MyGame     myGame
	 */
	public static void checkIfPlayerHasCollidedWithStructureOutsideOfTown(
			GameObject player, 
			GameObject structure, 
			MyGame myGame
			) {
		if (structure.rectangle.overlaps(player.rectangle)) {
			//setStoreRenderState();
			Store.shouldDisplayEnterStoreMessageAlternate = true;
		} else {
			//Store.shouldDisplayEnterStoreMessageAlternate = false;
		}
	}

	private static void setStoreRenderState() {
		if (Store.storeIsUnlocked) {
			Store.storeShouldBeRendered = true;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param GameObject fire
	 */
	public static void checkIfPlayerHasCollidedWithFire(GameObject player, GameObject fire) {
		if (fire.rectangle.overlaps(player.rectangle)) {}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param GameObject legendSword
	 */
	public static void checkIfPlayerHasCollidedWithLegendSword(GameObject player, GameObject legendSword) {
		if (legendSword.rectangle.overlaps(player.rectangle)) {
			MissionLegendOfTheSevenSwords.swordsCollected++;
			MissionLegendOfTheSevenSwords.legendSwordCollection.add(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			AddedToInventory.shouldRender             = true;
			AddedToInventory.shouldDisplayLegendSword = true;
			AddedToInventory.timer                    = 0;

			// Uncomment this to fill up inventory.
			/*
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword);
			((Player) player).getInventory().addObjectToInventory(legendSword); */

			legendSword.hasBeenCollected            = true;
			LegendSword.playSound                   = true;
			Inventory.inventoryHasStartedCollection = true;
		}
	}

	/**
	 * Currently this method is not used, because player must BUY the gun from the Trading Post.
	 * Player cannot just pick the gun up as a collectible.
	 * Keep this method for now, just in case this is changed in the future.
	 * 
	 * @param GameObject player
	 * @param GameObject gun
	 */
	public static void checkIfPlayerHasCollidedWithGun(GameObject player, GameObject gun) {
		// Only collect gun if we have enough loot.
		if (player.getPlayerLoot() >= CollectibleHandler.LOOT_NEEDED_TO_BUY_GUN && TradingPost.hasBeenEntered) {
			if (gun.rectangle.overlaps(player.rectangle)) {
				((Player) player).getInventory().addObjectToInventory(gun);
				Inventory.inventoryHasStartedCollection = true;
				Gun.hasBeenCollected                    = true;
				Gun.playCollectionSound                 = true;
				GameObjectLoader.gameObjectList.add(gun);
				// Remove loot (player has bought gun).
				player.updatePlayerLoot(-CollectibleHandler.LOOT_NEEDED_TO_BUY_GUN);
			}
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param GameObject gun
	 */
	public static void checkIfPlayerHasCollidedWithMagicPearl(GameObject player, GameObject pearl) {
		if (pearl.rectangle.overlaps(player.rectangle)) {
			((Player) player).getInventory().addObjectToInventory(pearl);
			Inventory.inventoryHasStartedCollection = true;
			pearl.hasBeenCollected                  = true;
			MagicPearl.playCollectionSound          = true;
			GameObjectLoader.gameObjectList.add(pearl);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param GameObject birdWeapon
	 */
	public static void checkIfPlayerHasCollidedWithBirdWeapon(GameObject player, GameObject birdWeapon) {
		if (birdWeapon.rectangle.overlaps(player.rectangle)) {
			((Player) player).getInventory().addObjectToInventory(birdWeapon);
			Inventory.inventoryHasStartedCollection = true;
			birdWeapon.hasBeenCollected             = true;
			GameObjectLoader.gameObjectList.add(birdWeapon);
			BirdWeapon.playCollectionSound          = true;
			AddedToInventory.shouldRender           = true;
			AddedToInventory.shouldDisplayWoody     = true;
			AddedToInventory.timer                  = 0;
		}
	}

	/**
	 * 
	 * @param Enemy  enemy
	 * @param Weapon weapon
	 */
	public static void checkIfWeaponHasCollidedWithEnemy(Enemy enemy, Weapon weapon) {
		if (weapon instanceof LegendSword) {
			//if (/*Player.playerIsPerformingAttack &&*/ Inventory.inventoryIsEquipped) {
			// Checking if dead is false keeps the sound from playing repeatedly.
			if (enemy.rectangle.overlaps(weapon.rectangle) && !enemy.isDead() && Player.playerIsPerformingAttack) {
				handleEnemyDeath(enemy);
			}
			//}
		}
	}

	/**
	 * 
	 * @param Enemy  enemy
	 * @param Weapon weapon
	 */
	public static void checkIfBirdWeaponHasCollidedWithEnemy(Enemy enemy, Weapon weapon) {
		if (weapon instanceof BirdWeapon) {
			if (/*Player.playerIsPerformingAttack &&*/ Inventory.inventoryIsEquipped) {
				// Checking if dead is false keeps the sound from playing repeatedly.
				if (enemy.rectangle.overlaps(weapon.rectangle) && !enemy.isDead()) {
					handleEnemyDeath(enemy);
				}
			}
		}
	}

	/**
	 * 
	 * @param Enemy  enemy
	 * @param Dagger dagger
	 */
	public static void checkIfDaggerHasCollidedWithEnemy(Enemy enemy, Dagger dagger) {
		if (dagger instanceof Dagger) {
			//if (/*Player.playerIsPerformingAttack &&*/ Inventory.inventoryIsEquipped) {
			// Checking if dead is false keeps the sound from playing repeatedly.
			if (enemy.rectangle.overlaps(dagger.rectangle) && !enemy.isDead() && Player.playerIsPerformingAttack) {
				handleEnemyDeath(enemy);
			}
			//}
		}
	}

	/**
	 * 
	 * @param Boss   boss
	 * @param Weapon weapon
	 */
	public static void checkIfWeaponHasCollidedWithBoss(Boss boss, Weapon weapon) {
		if (boss.rectangle.overlaps(weapon.rectangle) && !boss.isDead() && Player.playerIsPerformingAttack) {
			boss.setBossHealth(boss.getBossHealth() - Boss.BOSS_DAMAGE_TAKEN_FROM_PLAYER);
			Boss.playGruntSound = true;
			BossHealthUi.alpha += boss.getPercentToChangeAlphaEachHit();
		}
	}

	/**
	 * 
	 * @param Boss   boss
	 * @param Dagger dagger
	 */
	public static void checkIfDaggerHasCollidedWithBoss(Boss boss, Dagger dagger) {
		if (boss.rectangle.overlaps(dagger.rectangle) && !boss.isDead() && Player.playerIsPerformingAttack) {
			boss.setBossHealth(boss.getBossHealth() - Boss.BOSS_DAMAGE_TAKEN_FROM_PLAYER);
			Boss.playGruntSound = true;
			BossHealthUi.alpha += boss.getPercentToChangeAlphaEachHit();
		}
	}

	/**
	 * 
	 * @param Enemy  enemy
	 * @param Weapon weapon
	 */
	public static void checkIfProjectileHasCollidedWithEnemy(Enemy enemy, Weapon weapon)  {
		if (Inventory.inventoryIsEquipped) {  
			// Checking if dead is false keeps the sound from playing repeatedly.
			if (enemy.rectangle.overlaps(weapon.rectangle) /*&& !enemy.isDead()*/) {
				handleEnemyDeath(enemy);
			}
		}
	}

	/**
	 * 
	 * @param Boss   boss
	 * @param Weapon weapon
	 */
	public static void checkIfProjectileHasCollidedWithBoss(Boss boss, Weapon weapon)  {
		if (/*Player.playerIsPerformingAttack &&*/ Inventory.inventoryIsEquipped) {
			if (boss.rectangle.overlaps(weapon.rectangle) && !boss.isDead()) {
				boss.setBossHealth(boss.getBossHealth() - Boss.BOSS_DAMAGE_TAKEN_FROM_PLAYER);
				Boss.playGruntSound = true;
				BossHealthUi.alpha += boss.getPercentToChangeAlphaEachHit();
			}
		}
	}

	/**
	 * 
	 * @param Player     player
	 * @param CannonBall cannonBall
	 */
	public static void checkIfCannonBallHasCollidedWithPlayer(Player player, CannonBall cannonBall)  {
		if (player.rectangle.overlaps(cannonBall.rectangle) && !cannonBall.isCannonBallHasHitPlayer()) {
			if (!Player.isInvincible) {
				player.setHealth(player.getHealth() - 1);
				HealthUi.heartsShouldFlashWhite = true;
				player.setPlaySound(true);
				((Player) player).setBouncingBack(true);
			}
			cannonBall.setCannonBallHasHitPlayer(true);
			// Make cannonball stop and hit player and explode instead of going through him.
			cannonBall.setDx(0);
		}
	}

	/**
	 * 
	 * @param Enemy  enemy
	 * @param Player player
	 */
	public static void checkIfEnemyHasCollidedWithPlayer(Enemy enemy, Player player) {
		if (enemy.rectangle.overlaps(player.rectangle) && !enemy.isDead()) {
			// Kill enemy if he is overlapping with player while player is performing attack.
			if (Player.jumpingAction == Player.DESCENDING_JUMP) {
				handleEnemyDeath(enemy);
				// Player should bounce back upon attacking an enemy with a jump.
				player.setBouncingBack(true);
			} else {
				// Lets try this instead of the above method to see how it works out.
				if (!CutScene.anyCutSceneIsInProgress) {
					if (!Player.isInvincible) {
						player.setHealth(player.getHealth() - 0.1f);
						player.setPlaySound(true);
						player.setBouncingBack(true);
						HealthUi.heartsShouldFlashWhite = true;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param Enemy enemy
	 */
	private static void handleEnemyDeath(Enemy enemy) {
		enemy.setIsDead(true);
		enemy.setPlaySound(true);

		//if (enemy instanceof Giant) {
		//	Giant.playGiantDeathSound = true;
		//}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Heart      heart
	 */
	public static void checkIfPlayerCollidedWithHeart(GameObject player, Heart heart) {
		if (player.rectangle.overlaps(heart.rectangle)) {
			heart.setHasBeenCollected(true);
			((Player) player).setHealth(player.getHealth() + Heart.HEALTH);
			Heart.playSound                      = true;
			AddedToInventory.shouldRender        = true;
			AddedToInventory.shouldDisplayHealth = true;
			AddedToInventory.timer               = 0;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param TenHearts  tenHearts
	 */
	public static void checkIfPlayerCollidedWithTenHearts(GameObject player, TenHearts tenHearts) {
		if (player.rectangle.overlaps(tenHearts.rectangle)) {
			tenHearts.hasBeenCollected = true;
			((Player) player).setHealth(player.getHealth() + TenHearts.HEALTH);
			TenHearts.playSound                  = true;
			AddedToInventory.shouldRender        = true;
			AddedToInventory.shouldDisplayHealth = true;
			AddedToInventory.timer               = 0;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Ammo       ammo
	 */
	public static void checkIfPlayerCollidedWithAmmo(GameObject player, Ammo ammo) {
		if (player.rectangle.overlaps(ammo.rectangle)) {
			if (AmmoHandler.ammoCount < AmmoHandler.MAX_AMOUNT_AMMO_PLAYER_CAN_CARRY) {
				ammo.setHasBeenCollected(true);
				AmmoHandler.ammoCount += AmmoHandler.ammoValue;
				Ammo.playSound = true;
				AddedToInventory.shouldRender      = true;
				AddedToInventory.shouldDisplayAmmo = true;
				AddedToInventory.timer             = 0;
			} 
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param QuickSand  quickSand
	 */
	public static void checkIfPlayerCollidedWithQuickSand(GameObject player, QuickSand quickSand) {
		if (player.rectangle.overlaps(quickSand.rectangle)) {
			// Use this so quick sand doesn't drain player's health really quick.
			quickSandTimer++;
			if (quickSandTimer > HEALTH_TIMER_MAX) {
				quickSandTimer = 0;
			}
			// Comment this out to prevent death.
			if (quickSandTimer > HEALTH_TIMER_TRIGGER) {
				player.setHealth(player.getHealth() - Heart.HEALTH);
				HealthUi.heartsShouldFlashWhite = true;
			}
			Player.quickSandTimer = 0;
			Player.isInQuickSand  = true;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Rum        rum
	 */
	public static void checkIfPlayerCollidedWithRum(GameObject player, Rum rum) {
		if (player.rectangle.overlaps(rum.rectangle)) {
			if (RumHandler.rumCount < RumHandler.MAX_AMOUNT_RUM_PLAYER_CAN_CARRY) {
				rum.setHasBeenCollected(true);
				Rum.playSound = true;
				RumHandler.rumCount++;
				AddedToInventory.shouldRender     = true;
				AddedToInventory.shouldDisplayRum = true;
				AddedToInventory.timer            = 0;
			}
		}
	}

	/**
	 * Collision with a stump starts the mission.
	 * 
	 * @param GameObject player
	 * @param Stump      stump
	 */
	/*
	public static void checkIfPlayerCollidedWithStump(GameObject player, Stump stump) {
		if (player.rectangle.overlaps(stump.rectangle) && MissionRawBar.rawBarMissionComplete) {
			MissionStumpHole.missionIsActive = true;
		}
	} */

	/**
	 * This method does not use the regular player.
	 * Instead, it uses the unique rectangle (player) from MissionStumpHole.
	 * 
	 * @param Rectangle player
	 * @param Feather   feather
	 */
	public static void checkIfPlayerHasCollidedWithFeather(Rectangle player, Feather feather) {
		if (player.overlaps(feather.rectangle)) {
			if (!feather.hasBeenCollected) {
				MissionStumpHole.playerFeatherScore += MissionStumpHole.FEATHER_VALUE;
				feather.hasBeenCollected = true;
				Feather.playSound        = true;
				MissionStumpHole.alpha += MissionStumpHole.percentToChangeAlphaEachHit; 
			}
		}
	}

	/**
	 * Attack bird is in Stump Hole mission.
	 * 
	 * @param GameObject player
	 * @param Rectangle  birdWeapon
	 * @param Rectangle  missionPlayerRectangle
	 */
	public static void checkIfPlayerHasCollidedWithAttackBird(GameObject player, Rectangle birdWeapon, Rectangle missionPlayerRectangle) {
		if (birdWeapon.overlaps(missionPlayerRectangle)) {
			if (!Player.isInvincible) {
				player.setHealth(player.getHealth() - 0.1f);
				player.setPlaySound(true);
				HealthUi.heartsShouldFlashWhite = true;
				// Handle bounce back.
				switch (MissionStumpHole.playerDirection) {
				case GameObject.DIRECTION_LEFT:
					missionPlayerRectangle.setX(missionPlayerRectangle.getX() + 0.2f);
					break;
				case GameObject.DIRECTION_RIGHT:
					missionPlayerRectangle.setX(missionPlayerRectangle.getX() - 0.2f);
					break;
				} 
			}
		}
	}

	/**
	 * 
	 * @param GameObject     player
	 * @param LocationMarker locationMarker
	 * @return boolean
	 */
	public static boolean playerHasCollidedWithLocationMarker(GameObject player, LocationMarker locationMarker) {
		// This checks if we should play the beeping sound to alert player he is close to a locator.
		int soundBoundaryOffset = 17;
		if (
				player.getX() > locationMarker.getLocator().getX() - soundBoundaryOffset && 
				player.getX() < locationMarker.getLocator().getX() - soundBoundaryOffset + locationMarker.getLocator().getWidth() + soundBoundaryOffset * 2 &&
				player.getY() > locationMarker.getLocator().getY() - soundBoundaryOffset && 
				player.getY() < locationMarker.getLocator().getY() - soundBoundaryOffset + locationMarker.getLocator().getHeight() + soundBoundaryOffset * 2

				) {
			LocationMarker.playerIsWithinSoundBounds = true;
		} else {
			LocationMarker.playerIsWithinSoundBounds = false;
		}
		// This checks if player has actually hit the location marker itself.
		if (player.rectangle.overlaps(locationMarker.getLocator())) {
			LocationMarker.playSound = true;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Rectangle  landingSoundBoundary
	 * @return boolean
	 */
	public static boolean playerIsWithinSoundBoundsOfGiant(GameObject player, Rectangle landingSoundBoundary) {
		if (landingSoundBoundary.overlaps(player.rectangle)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Boss       boss
	 */
	public static void checkIfPlayerCollidedWithBoss(GameObject player, Boss boss) {
		if (player.rectangle.overlaps(boss.rectangle) && !Player.isInvincible) {
			boss.setX(boss.getX() + 5);
			((Player) player).setBouncingBack(true);
			player.setHealth(player.getHealth() - 0.5f);
			HealthUi.heartsShouldFlashWhite = true;
			player.setPlaySound(true);

			// Make bash audio every time boss bashes.
			Boss.bashAudioHasBeenPlayed = false;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Arrow      arrow
	 */
	public static void checkIfArrowHasCollidedWithPlayer(GameObject player, Arrow arrow) {
		if (player.rectangle.overlaps(arrow.rectangle)) {
			if (!CutScene.anyCutSceneIsInProgress) {
				if (!Player.isInvincible) {
					player.setHealth(player.getHealth() - 0.1f);
					HealthUi.heartsShouldFlashWhite = true;
					player.setPlaySound(true);
					((Player) player).setBouncingBack(true);
				}
			}
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Hole       hole
	 */
	public static void checkIfPlayerHasCollidedWithHole(GameObject player, Hole hole) {
		if (player.rectangle.overlaps(hole.rectangle)) {
			HoleHandler.playerIsInHole = true;
			HoleHandler.playSound      = true;
			// Move player away from hole so he's not constantly hitting it, triggering the audio.
			player.setY(player.getY() + 1);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param ShockPlant shockPlant
	 */
	public static void checkIfPlayerHasCollidedWithShockPlant(GameObject player, ShockPlant shockPlant) {
		if (player.rectangle.overlaps(shockPlant.rectangle)) {
			player.setHealth(player.getHealth() - 0.1f);
			HealthUi.heartsShouldFlashWhite = true;
			player.setPlaySound(true);
			((Player) player).setBouncingBack(true);
			ShockPlant.playSparkAudio = true;
		}
	}

	/**
	 * 
	 * @param GameObject  player
	 * @param PoisonPlant poisonPlant
	 */
	public static void checkIfPlayerHasCollidedWithPoisonPlant(GameObject player, PoisonPlant poisonPlant) {
		if (player.rectangle.overlaps(poisonPlant.rectangle)) {
			player.setHealth(player.getHealth() - 0.1f);
			player.setPlaySound(true);
			((Player) player).setBouncingBack(true);
			PlayerOne.isPoisoned            = true;
			PoisonPlant.playPoisonSound     = true;
			HealthUi.heartsShouldFlashWhite = true;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Paw        paw
	 */
	public static void checkIfPlayerHasCollidedWithPaw(GameObject player, Paw paw) {
		if (paw.rectangle.overlaps(player.rectangle)) {
			//if (!Paw.pawHasBeenCollected) {
			((Player) player).getInventory().addObjectToInventory(paw);
			Inventory.inventoryHasStartedCollection = true;
			Paw.pawHasBeenCollected                 = true;
			Paw.playCollectionSound                 = true;
			GameObjectLoader.gameObjectList.add(paw);
			AddedToInventory.shouldRender           = true;
			AddedToInventory.shouldDisplayPaw       = true;
			AddedToInventory.timer                  = 0;
			//}
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Dagger     dagger
	 */
	public static void checkIfPlayerHasCollidedWithDagger(GameObject player, Dagger dagger) {
		if (!dagger.hasBeenCollected) {
			if (dagger.rectangle.overlaps(player.rectangle)) {
				((Player) player).getInventory().addObjectToInventory(dagger);
				Inventory.inventoryHasStartedCollection = true;
				dagger.hasBeenCollected                 = true;
				Dagger.playCollectionSound              = true;
				GameObjectLoader.gameObjectList.add(dagger);
				AddedToInventory.shouldRender           = true;
				AddedToInventory.shouldDisplayDagger    = true;
				AddedToInventory.timer                  = 0;
			}
		}
	}

	/**
	 * 
	 * @param Rectangle  stumpHolePlayer
	 * @param RockDrop   rockDrop
	 * @param GameObject realPlayer
	 */
	public static void checkIfPlayerHasCollidedWithRockDrop(Rectangle stumpHolePlayer, RockDrop rockDrop, GameObject realPlayer) {
		if (stumpHolePlayer.overlaps(rockDrop.rectangle)) {
			if (!Player.isInvincible) {
				realPlayer.setHealth(realPlayer.getHealth() - 0.1f);
				HealthUi.heartsShouldFlashWhite = true;
				realPlayer.setPlaySound(true);
				//player.setBounceBack(true);
			}
		}
	}

	/**
	 * 
	 * @param Giant       giant
	 * @param LegendSword legendSword
	 */
	public static void checkIfWeaponHasCollidedWithGiant(Giant giant, LegendSword legendSword) {
		if (giant.rectangle.overlaps(legendSword.rectangle) && Player.playerIsPerformingAttack) {
			giant.setIsDead(true);
		}
	}

	/**
	 * 
	 * @param Giant  giant
	 * @param Dagger dagger
	 */
	public static void checkIfDaggerHasCollidedWithGiant(Giant giant, Dagger dagger) {
		if (giant.rectangle.overlaps(dagger.rectangle) && Player.playerIsPerformingAttack) {
			giant.setIsDead(true);
		}
	}

	/**
	 * 
	 * @param Giant  giant
	 * @param Weapon weapon
	 */
	public static void checkIfProjectileHasCollidedWithGiant(Giant giant, Weapon weapon) {
		if (giant.rectangle.overlaps(weapon.rectangle) /*&& Player.playerIsPerformingAttack*/) {
			giant.setIsDead(true);
		}
	}

	/**
	 * 
	 * @param Giant      giant
	 * @param BirdWeapon birdWeapon
	 */
	public static void checkIfBirdWeaponHasCollidedWithGiant(Giant giant, BirdWeapon birdWeapon) {
		if (giant.rectangle.overlaps(birdWeapon.rectangle) /*&& Player.playerIsPerformingAttack*/) {
			giant.setIsDead(true);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param Giant      giant
	 */
	public static void checkIfPlayerhasCollidedWithGiant(GameObject player, Giant giant) {
		if (giant.rectangle.overlaps(player.rectangle) && !giant.isDead()) {
			if (!CutScene.anyCutSceneIsInProgress) {
				if (!Player.isInvincible) {
					player.setHealth(player.getHealth() - 0.1f);
					player.setPlaySound(true);
					((Player) player).setBouncingBack(true);
					HealthUi.heartsShouldFlashWhite = true;
				}
			}
		}
	}
}
