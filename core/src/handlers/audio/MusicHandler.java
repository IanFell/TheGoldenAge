package handlers.audio;

import com.badlogic.gdx.audio.Music;
import com.mygdx.mygame.MyGame;

import cutscenes.CutScene;
import cutscenes.CutSceneBird;
import gameobjects.PoisonPlant;
import gameobjects.gamecharacters.enemies.Boss;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import gameobjects.weapons.Paw;
import handlers.collectibles.HeartHandler;
import handlers.enemies.BossHandler;
import helpers.GameAttributeHelper;
import inventory.Inventory;
import loaders.audio.MusicLoader;
import loaders.bossloader.BossLoader;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import mixer.Mixer;
import physics.Lighting.Fire;
import physics.Weather.NightAndDayCycle;
import physics.Weather.WeatherHandler;
import screens.Screens;
import store.Store;
import ui.GameOver;
import ui.Win;

/**
 * Handles game music.
 * 
 * @author Fabulous Fellini
 *
 */
public class MusicHandler {

	// The monkey SFX are really long, so we only play it for 3 seconds.
	private final int MONKEY_SCREAM_TIME = 3;

	private boolean startDayTimeAmbientAudio   = true;
	private boolean startNightTimeAmbientAudio = true;
	private boolean startStormAudio            = true;
	private boolean startFootstepsAudio        = true;
	private boolean startOceanAudio            = true;

	private int bossExplosionTimer              = 0;
	private final int EXPLOSION_TIMER_MAX_VALUE = 110;

	private boolean bossBattleIsInProgress = false;

	/**
	 * Mission audio consists of three parts:
	 * 1. Intro.
	 * 2. Loop that builds up.
	 * 3. Stinger when player has won.
	 * These variables control the flow of those three audio tracks.
	 */
	private boolean missionIntroCompletionListenerHasBeenSet = false;
	private boolean missionLoopIsPlaying                     = false;
	public static boolean playerHasBeatMission               = false;
	private boolean missionRawBarStingerHasPlayed            = false;
	private boolean missionStumpHoleStingerHasPlayed         = false;

	private boolean shouldPlayBuffStinger                      = false;
	private int buffStingerTimer                               = 0;
	private boolean shouldContinueAmbientMusicAfterBuffStinger = true;
	private boolean isPlayingBuff                              = false;
	private final int BUFF_DELAY                               = 100;

	private boolean shouldPlayBossStinger                      = false;
	private int bossStingerTimer                               = 0;
	private boolean shouldContinueAmbientMusicAfterBossStinger = true;
	private boolean isPlayingBoss                              = false;
	private final int BOSS_DELAY                               = 100;

	public static void resetGame() {
		playerHasBeatMission = false;
	}

	private void handleBuffStingerTiming() {
		if (isPlayingBuff) {
			buffStingerTimer++;
			if (buffStingerTimer > BUFF_DELAY) {
				buffStingerTimer                           = 0;
				isPlayingBuff                              = false;
				shouldContinueAmbientMusicAfterBuffStinger = true;
			}
		}
	}

	private void handleBossStingerTiming() {
		if (isPlayingBoss) {
			bossStingerTimer++;
			if (bossStingerTimer > BOSS_DELAY) {
				bossStingerTimer                           = 0;
				isPlayingBoss                              = false;
				shouldContinueAmbientMusicAfterBossStinger = true;
			}
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleBuffStinger(MusicLoader musicLoader) {
		if (shouldPlayBuffStinger) {
			musicLoader.buffOut.setVolume(Mixer.INVINCIBLE_MUSIC_VOLUME);
			musicLoader.buffOut.play();
			shouldPlayBuffStinger                      = false;
			isPlayingBuff                              = true;
			shouldContinueAmbientMusicAfterBuffStinger = false;
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleBossStinger(MusicLoader musicLoader) {
		if (shouldPlayBossStinger) {
			musicLoader.bossStinger.setVolume(Mixer.BOSS_BATTLE_MUSIC_VOLUME);
			musicLoader.bossStinger.play();
			shouldPlayBossStinger                      = false;
			isPlayingBoss                              = true;
			shouldContinueAmbientMusicAfterBossStinger = false;
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 * @param MyGame      myGame
	 */
	public void handleMusic(MusicLoader musicLoader, MyGame myGame) {
		if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {
			// Dont let invincible music play during a boss battle because it will interfere with battle music.
			if (Player.isInvincible && !bossBattleIsInProgress && !CutScene.gameShouldPause) {
				handleInvincibleAudio(musicLoader);
			} else {
				if (musicLoader.buff.isPlaying()) {
					musicLoader.buff.stop();
					handleBuffStinger(musicLoader);
				}
				handleBuffStingerTiming();
				/*
				if (musicLoader.bossBattleMusic.isPlaying()) {
					musicLoader.bossBattleMusic.stop();
					handleBossStinger(musicLoader);
				}
				handleBossStingerTiming();*/

				if (NightAndDayCycle.isDayTime()) {
					handleDayTimeAudio(musicLoader);
				} else {
					handleNightTimeAudio(musicLoader);
				}
				handleFootstepsAudio(musicLoader);
				handleFireAudio(musicLoader);
				handleOceanAudio(musicLoader);

				musicLoader.ambientMusic.setVolume(Mixer.AMBIENT_MUSIC_VOLUME);
				musicLoader.ambientMusic.setLooping(true);

				handleCursedPawWeaponAudio(musicLoader);
				handleCursedPawCollectionAudio(musicLoader);

				if (!CutScene.gameShouldPause) {
					if (shouldContinueAmbientMusicAfterBuffStinger && 
							!isPlayingBuff // && 
							// shouldContinueAmbientMusicAfterBossStinger
							) {
						musicLoader.ambientMusic.play();
					}
				} else {
					musicLoader.ambientMusic.pause();
				}
				if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
					musicLoader.ambientMusic.pause();
				}
			}

			if (!CutScene.gameShouldPause) {
				handleBossBattleMusic(musicLoader);
				handleBossExplosionMusic(musicLoader);
			}
			handleMissionMusic(musicLoader);
			handleCutsceneMusic(musicLoader);
			handlePoisonAudio(musicLoader);

			handleLowHealthBeep(musicLoader, myGame);

			if (musicLoader.theme.isPlaying() && !GameOver.triggerGameOver) {
				musicLoader.theme.stop();
			}

			if (GameOver.triggerGameOver || Win.triggerWin) {
				handleEndOfGame(musicLoader);
			}
		} else if (GameAttributeHelper.gameState == Screens.TITLE_SCREEN) {
			musicLoader.theme.setVolume(Mixer.TITLE_SCREEN_VOLUME);
			musicLoader.theme.setLooping(true);
			musicLoader.theme.play();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 * @param MyGame      myGame
	 */
	private void handleLowHealthBeep(MusicLoader musicLoader, MyGame myGame) {
		float heartSize = myGame.getGameObject(Player.PLAYER_ONE).getHealth();
		if (heartSize > 0 && heartSize < 5) {
			musicLoader.lowHealthBeep.setVolume(Mixer.LOW_HEALTH_BEEP_VOLUME);
			musicLoader.lowHealthBeep.setLooping(true);
			musicLoader.lowHealthBeep.play();
		} else {
			musicLoader.lowHealthBeep.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleEndOfGame(MusicLoader musicLoader) {
		// Game over screen should only be playing the ocean and day/night/storm SFX.
		musicLoader.footsteps.stop();
		musicLoader.ambientMusic.stop();
		musicLoader.poison.stop();
		musicLoader.buff.stop();
		musicLoader.missionLoop.stop();
		if (Win.triggerWin) {
			// Winning screen plays the theme.
			musicLoader.theme.setVolume(Mixer.TITLE_SCREEN_VOLUME);
			musicLoader.theme.setLooping(true);
			musicLoader.theme.play();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handlePoisonAudio(MusicLoader musicLoader) {
		if (PoisonPlant.playPoisonSound) {
			musicLoader.poison.setVolume(Mixer.POISON_VOLUME);
			musicLoader.poison.play();
			PoisonPlant.playPoisonSound = false;
		}

		if (!PlayerOne.isPoisoned) {
			musicLoader.poison.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleCursedPawWeaponAudio(MusicLoader musicLoader) {
		if (Player.playerIsPerformingAttack && !CutScene.gameShouldPause && !Player.isInvincible) {
			if (Paw.playAttackSound) {
				musicLoader.monkey.setVolume(Mixer.PAW_ATTACK_VOLUME);
				musicLoader.monkey.play();
				Paw.playAttackSound = false;
			}
		}
		if (musicLoader.monkey.getPosition() > MONKEY_SCREAM_TIME) {
			musicLoader.monkey.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleCursedPawCollectionAudio(MusicLoader musicLoader) {
		if (Paw.playCollectionSound && !Player.isInvincible) {
			musicLoader.monkey.setVolume(Mixer.PAW_COLLECT_VOLUME);
			musicLoader.monkey.play();
			Paw.playCollectionSound = false;
		}
		if (musicLoader.monkey.getPosition() > MONKEY_SCREAM_TIME) {
			musicLoader.monkey.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleCutsceneMusic(MusicLoader musicLoader) {
		if (CutScene.gameShouldPause) {
			stopMusicForCutscene(musicLoader);
			musicLoader.ocean.setVolume(Mixer.CUTSCENE_MUSIC_VOLUME);
			musicLoader.ocean.setLooping(true);
			musicLoader.ocean.play();

			musicLoader.typewriter.setVolume(Mixer.TYPEWRITER_VOLUME);
			musicLoader.typewriter.play();
		} else {
			musicLoader.ocean.stop();
			musicLoader.typewriter.stop();
			musicLoader.theme.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void stopMusicForCutscene(MusicLoader musicLoader) {
		if (musicLoader.poison.isPlaying()) {
			musicLoader.poison.stop();
		}
		if (musicLoader.footsteps.isPlaying()) {
			musicLoader.footsteps.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleMissionMusic(MusicLoader musicLoader) {
		if (MissionRawBar.phasesAreInProgress || MissionStumpHole.missionIsActive) {
			if (musicLoader.ambientMusic.isPlaying()) {
				musicLoader.ambientMusic.pause();

				if (!missionIntroCompletionListenerHasBeenSet) {
					musicLoader.missionIntro.setOnCompletionListener(new Music.OnCompletionListener() {
						@Override
						public void onCompletion(Music music) {  
							missionLoopIsPlaying = true;
						}
					});
					missionIntroCompletionListenerHasBeenSet = true;
					playerHasBeatMission                     = true;

					musicLoader.missionIntro.setVolume(Mixer.MISSION_MUSIC_VOLUME);
					musicLoader.missionIntro.play();
				}

				if (missionLoopIsPlaying) {
					musicLoader.missionLoop.setVolume(Mixer.MISSION_MUSIC_VOLUME);
					musicLoader.missionLoop.setLooping(true);
					musicLoader.missionLoop.play();
					missionLoopIsPlaying = false;
				}
			}
		} else {
			if (musicLoader.missionIntro.isPlaying()) {
				musicLoader.missionIntro.stop();
			}
			if (musicLoader.missionLoop.isPlaying()) {
				musicLoader.missionLoop.stop();
			}
			missionLoopIsPlaying                     = false;
			missionIntroCompletionListenerHasBeenSet = false;
		}
		handleMissionCompleteStinger(musicLoader);
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleMissionCompleteStinger(MusicLoader musicLoader) {
		if (MissionRawBar.rawBarMissionComplete && !missionRawBarStingerHasPlayed) {
			executeStinger(musicLoader);
			missionRawBarStingerHasPlayed = true;
		} else if (MissionStumpHole.stumpHoleMissionComplete && !missionStumpHoleStingerHasPlayed) {
			executeStinger(musicLoader);
			missionStumpHoleStingerHasPlayed = true;
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void executeStinger(MusicLoader musicLoader) {
		musicLoader.missionWin.setVolume(Mixer.MISSION_MUSIC_VOLUME);
		musicLoader.missionWin.play();
		playerHasBeatMission = false;
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleBossBattleMusic(MusicLoader musicLoader) {
		if (BossLoader.boss[BossHandler.TRADIN_POST].isBattleMusicHasStarted() && !BossLoader.boss[BossHandler.TRADIN_POST].isDead()) {
			bossBattleIsInProgress = true;
			if (musicLoader.ambientMusic.isPlaying()) {
				musicLoader.ambientMusic.stop();
			}
			musicLoader.bossBattleMusic.setVolume(Mixer.BOSS_BATTLE_MUSIC_VOLUME);
			musicLoader.bossBattleMusic.setLooping(true);
			musicLoader.bossBattleMusic.play();
			BossLoader.boss[BossHandler.TRADIN_POST].setBattleMusicHasStarted(false);
		} else if (BossLoader.boss[BossHandler.APALACHICOLA].isBattleMusicHasStarted() && !BossLoader.boss[BossHandler.APALACHICOLA].isDead()) { 
			bossBattleIsInProgress = true;
			if (musicLoader.ambientMusic.isPlaying()) {
				musicLoader.ambientMusic.stop();
			}
			musicLoader.bossBattleMusic.setVolume(Mixer.BOSS_BATTLE_MUSIC_VOLUME);
			musicLoader.bossBattleMusic.setLooping(true);
			musicLoader.bossBattleMusic.play();
			BossLoader.boss[BossHandler.APALACHICOLA].setBattleMusicHasStarted(false);
		} else if (BossLoader.boss[BossHandler.STUMP_HOLE].isBattleMusicHasStarted() && !BossLoader.boss[BossHandler.STUMP_HOLE].isDead()) { 
			bossBattleIsInProgress = true;
			if (musicLoader.ambientMusic.isPlaying()) {
				musicLoader.ambientMusic.stop();
			}
			musicLoader.bossBattleMusic.setVolume(Mixer.BOSS_BATTLE_MUSIC_VOLUME);
			musicLoader.bossBattleMusic.setLooping(true);
			musicLoader.bossBattleMusic.play();
			BossLoader.boss[BossHandler.STUMP_HOLE].setBattleMusicHasStarted(false);
		} else if (BossLoader.boss[BossHandler.WEWA].isBattleMusicHasStarted() && !BossLoader.boss[BossHandler.WEWA].isDead()) { 
			bossBattleIsInProgress = true;
			if (musicLoader.ambientMusic.isPlaying()) {
				musicLoader.ambientMusic.stop();
			}
			musicLoader.bossBattleMusic.setVolume(Mixer.BOSS_BATTLE_MUSIC_VOLUME);
			musicLoader.bossBattleMusic.setLooping(true);
			musicLoader.bossBattleMusic.play();
			BossLoader.boss[BossHandler.WEWA].setBattleMusicHasStarted(false);
		} else if (BossLoader.boss[BossHandler.THE_POINT].isBattleMusicHasStarted() && !BossLoader.boss[BossHandler.THE_POINT].isDead()) { 
			bossBattleIsInProgress = true;
			if (musicLoader.ambientMusic.isPlaying()) {
				musicLoader.ambientMusic.stop();
			}
			musicLoader.bossBattleMusic.setVolume(Mixer.BOSS_BATTLE_MUSIC_VOLUME);
			musicLoader.bossBattleMusic.setLooping(true);
			musicLoader.bossBattleMusic.play();
			BossLoader.boss[BossHandler.THE_POINT].setBattleMusicHasStarted(false);
		} else {
			bossBattleIsInProgress = false;
			if (musicLoader.bossBattleMusic.isPlaying()) {
				musicLoader.bossBattleMusic.stop();
			}
		}
		if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
			musicLoader.bossBattleMusic.pause();
		}

		if (Boss.playSpinAudio) {
			musicLoader.spinAudio.setVolume(Mixer.BOSS_ATTACK_SPIN_VOLUME);
			musicLoader.spinAudio.play();
			Boss.playSpinAudio = false;
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleBossExplosionMusic(MusicLoader musicLoader) {
		if (Boss.shouldPlayExplosionMusic) {
			if (bossExplosionTimer == 0) {
				musicLoader.bossDeafeatedMusic.setVolume(Mixer.MAX_VOLUME);
				musicLoader.bossDeafeatedMusic.play();
				musicLoader.bossStinger.setVolume(Mixer.BOSS_BATTLE_MUSIC_VOLUME);
				musicLoader.bossStinger.play();
			}
			bossExplosionTimer++;
			if (bossExplosionTimer > EXPLOSION_TIMER_MAX_VALUE) {
				Boss.shouldPlayExplosionMusic = false;
				bossExplosionTimer = 0;
				musicLoader.bossDeafeatedMusic.stop();
				shouldContinueAmbientMusicAfterBossStinger = true;
			}
			shouldContinueAmbientMusicAfterBossStinger = false;
		} 

		if (CutScene.gameShouldPause || CutSceneBird.anyCutSceneIsInProgress) {
			musicLoader.bossDeafeatedMusic.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleInvincibleAudio(MusicLoader musicLoader) {
		if (musicLoader.ambientMusic.isPlaying()) {
			musicLoader.ambientMusic.stop();
		}
		musicLoader.buff.setVolume(Mixer.INVINCIBLE_MUSIC_VOLUME);
		musicLoader.buff.setLooping(true);
		musicLoader.buff.play();
		if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
			musicLoader.buff.pause();
		}
		shouldPlayBuffStinger = true;
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleDayTimeAudio(MusicLoader musicLoader) {
		if (musicLoader.nightTimeAmbientNoise.isPlaying()) {
			musicLoader.nightTimeAmbientNoise.stop();
			startNightTimeAmbientAudio = true;
		}
		if (startDayTimeAmbientAudio) {
			musicLoader.dayTimeAmbientNoise.setVolume(Mixer.DAY_TIME_AMBIENT_VOLUME);
			musicLoader.dayTimeAmbientNoise.setLooping(true);
			musicLoader.dayTimeAmbientNoise.play();
			startDayTimeAmbientAudio = false;
		}
		handleStormAudio(musicLoader);
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleStormAudio(MusicLoader musicLoader) {
		if (WeatherHandler.isStorming() && startStormAudio) {
			musicLoader.rainAndThunder.setVolume(Mixer.STORM_VOLUME);
			musicLoader.rainAndThunder.setLooping(true);
			musicLoader.rainAndThunder.play();
			musicLoader.dayTimeAmbientNoise.stop();
			startStormAudio = false;
		} else if (!WeatherHandler.isStorming()) {
			if (musicLoader.rainAndThunder.isPlaying()) {
				musicLoader.rainAndThunder.stop();
				startStormAudio = true;
			}
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleNightTimeAudio(MusicLoader musicLoader) {
		if (startNightTimeAmbientAudio) {
			musicLoader.nightTimeAmbientNoise.setVolume(Mixer.NIGHT_TIME_AMBIENT_VOLUME);
			musicLoader.nightTimeAmbientNoise.setLooping(true);
			musicLoader.nightTimeAmbientNoise.play();
			startNightTimeAmbientAudio = false;

			if (musicLoader.dayTimeAmbientNoise.isPlaying()) {
				musicLoader.dayTimeAmbientNoise.stop();
				startDayTimeAmbientAudio = true;
			}
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleFireAudio(MusicLoader musicLoader) {
		if (Fire.playSound) {
			musicLoader.fire.setVolume(Mixer.FIRE_VOLUME);
			musicLoader.fire.play();
			Fire.playSound = false;
		} else {
			musicLoader.fire.stop();
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleOceanAudio(MusicLoader musicLoader) {
		// Play this audio if player is in water.
		if (Player.isInWater || MissionRawBar.phasesAreInProgress) {
			startOceanAudio = true;
		} else {
			musicLoader.ocean.stop();
		}
		if (startOceanAudio) {
			musicLoader.ocean.setVolume(Mixer.OCEAN_VOLUME);
			musicLoader.ocean.setLooping(true);
			musicLoader.ocean.play();
			startOceanAudio = false;
		}
	}

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	private void handleFootstepsAudio(MusicLoader musicLoader) {
		if (
				Player.playerIsMoving && 
				Player.jumpingAction == Player.ON_GROUND && 
				!Player.isInWater && 
				!MissionRawBar.phasesAreInProgress &&
				!Inventory.allInventoryShouldBeRendered &&
				!Store.playerWantsToEnterStore &&
				!Store.shouldDisplayEnterStoreMessage &&
				!CutScene.gameShouldPause
				) {
			startFootstepsAudio = true;
		} else {
			musicLoader.footsteps.stop();
		}
		if (startFootstepsAudio) {
			musicLoader.footsteps.setVolume(Mixer.FOOTSTEPS_VOLUME);
			musicLoader.footsteps.setLooping(true);
			musicLoader.footsteps.play();
			startFootstepsAudio = false;
		}
	}
}
