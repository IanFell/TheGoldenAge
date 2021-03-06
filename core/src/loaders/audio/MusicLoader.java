package loaders.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Loads music.
 * 
 * @author Fabulous Fellini.
 *
 */
public class MusicLoader {

	public Music winMusic;
	public Music lowHealthBeep;
	public Music bossStinger;
	public Music spinAudio;
	public Music theme;
	public Music poison;
	public Music dayTimeAmbientNoise;
	public Music nightTimeAmbientNoise;
	public Music rainAndThunder;
	public Music fire;
	public Music ocean;
	public Music ambientMusic;
	public Music invincibleMusic;
	public Music bossDeafeatedMusic;
	public Music bossBattleMusic;
	public Music buff;
	public Music buffOut;
	public Music missionLoop;
	public Music missionIntro;
	public Music missionWin;
	public Music typewriter;
	public Music monkey;

	/**
	 * This is currently a music type because it is a long file of footsteps.
	 * (It's not just one footstep sound).
	 */
	public Music footsteps;

	public void init() {
		winMusic              = Gdx.audio.newMusic(Gdx.files.internal("audio/music/WinAudio.ogg"));
		lowHealthBeep         = Gdx.audio.newMusic(Gdx.files.internal("audio/music/LowHealthBeep.ogg"));
		bossStinger           = Gdx.audio.newMusic(Gdx.files.internal("audio/music/CombatOut.ogg"));
		spinAudio             = Gdx.audio.newMusic(Gdx.files.internal("audio/music/SpinAudio.ogg"));
		theme                 = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Theme.ogg"));
		poison                = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Slime.ogg"));
		monkey                = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Monkey.ogg"));
		ocean                 = Gdx.audio.newMusic(Gdx.files.internal("audio/music/ocean.ogg"));
		dayTimeAmbientNoise   = Gdx.audio.newMusic(Gdx.files.internal("audio/music/daytimeambience.ogg"));
		footsteps             = Gdx.audio.newMusic(Gdx.files.internal("audio/music/footstepsmusic.ogg"));
		rainAndThunder        = Gdx.audio.newMusic(Gdx.files.internal("audio/music/rain.ogg"));
		fire                  = Gdx.audio.newMusic(Gdx.files.internal("audio/music/fire.ogg"));
		nightTimeAmbientNoise = Gdx.audio.newMusic(Gdx.files.internal("audio/music/nighttimeambience.ogg"));
		ambientMusic          = Gdx.audio.newMusic(Gdx.files.internal("audio/music/NonCombat.ogg"));
		invincibleMusic       = Gdx.audio.newMusic(Gdx.files.internal("audio/music/InvincibleMusic.ogg"));
		bossDeafeatedMusic    = Gdx.audio.newMusic(Gdx.files.internal("audio/music/avalanche.ogg"));
		bossBattleMusic       = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Combat.ogg"));
		buff                  = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Buff.ogg"));
		buffOut               = Gdx.audio.newMusic(Gdx.files.internal("audio/music/BuffOut.ogg"));
		missionLoop           = Gdx.audio.newMusic(Gdx.files.internal("audio/music/MissionLoop.ogg"));
		missionIntro          = Gdx.audio.newMusic(Gdx.files.internal("audio/music/MissionIntro.ogg"));
		missionWin            = Gdx.audio.newMusic(Gdx.files.internal("audio/music/MissionWin.ogg"));
		typewriter            = Gdx.audio.newMusic(Gdx.files.internal("audio/music/Typewriter.ogg"));
	}

	public void dispose() {
		winMusic.dispose();
		lowHealthBeep.dispose();
		bossStinger.dispose();
		spinAudio.dispose();
		poison.dispose();
		theme.dispose();
		dayTimeAmbientNoise.dispose();
		nightTimeAmbientNoise.dispose();
		rainAndThunder.dispose();
		footsteps.dispose();
		fire.dispose();
		ocean.dispose();
		ambientMusic.dispose();
		invincibleMusic.dispose();
		bossDeafeatedMusic.dispose();
		bossBattleMusic.dispose();
		buff.dispose();
		buffOut.dispose();
		missionLoop.dispose();
		missionIntro.dispose();
		missionWin.dispose();
		typewriter.dispose();
	}
}
