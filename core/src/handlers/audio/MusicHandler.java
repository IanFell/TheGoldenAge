package handlers.audio;

import com.badlogic.gdx.audio.Music;

import gameobjects.gamecharacters.enemies.Boss;
import gameobjects.gamecharacters.players.Player;
import handlers.enemies.BossHandler;
import helpers.GameAttributeHelper;
import loaders.audio.MusicLoader;
import loaders.bossloader.BossLoader;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import mixer.Mixer;
import physics.Lighting.Fire;
import physics.Weather.NightAndDayCycle;
import physics.Weather.WeatherHandler;
import screens.Screens;

/**
 * Handles game music.
 * 
 * @author Fabulous Fellini
 *
 */
public class MusicHandler {

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

	/**
	 * 
	 * @param MusicLoader musicLoader
	 */
	public void handleMusic(MusicLoader musicLoader) {
		if (GameAttributeHelper.gameState == Screens.GAME_SCREEN) {
			// Dont let invincible music play during a boss battle because it will interfere with battle music.
			if (Player.isInvincible && !bossBattleIsInProgress) {
				handleInvincibleAudio(musicLoader);
			} else {
				if (musicLoader.buff.isPlaying()) {
					musicLoader.buff.stop();
				}
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
				musicLoader.ambientMusic.play();
				if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
					musicLoader.ambientMusic.pause();
				}
			}
			handleBossBattleMusic(musicLoader);
			handleBossExplosionMusic(musicLoader);
			handleMissionMusic(musicLoader);
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
		} else {
			bossBattleIsInProgress = false;
			if (musicLoader.bossBattleMusic.isPlaying()) {
				musicLoader.bossBattleMusic.stop();
			}
		}
		if (GameAttributeHelper.gamePlayState == GameAttributeHelper.STATE_PAUSE) {
			musicLoader.bossBattleMusic.pause();
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
			}
			bossExplosionTimer++;
			if (bossExplosionTimer > EXPLOSION_TIMER_MAX_VALUE) {
				Boss.shouldPlayExplosionMusic = false;
				bossExplosionTimer = 0;
				musicLoader.bossDeafeatedMusic.stop();
			}
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
				!MissionRawBar.phasesAreInProgress
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
