package loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Loads music.
 * 
 * @author Fabulous Fellini.
 *
 */
public class MusicLoader {

	public Music dayTimeAmbientNoise;
	public Music nightTimeAmbientNoise;
	public Music rainAndThunder;
	public Music fire;
	public Music ocean;
	public Music ambientMusic;
	public Music invincibleMusic;
	public Music bossDeafeatedMusic;
	public Music bossBattleMusic;

	/**
	 * This is currently a music type because it is a long file of footsteps.
	 * (It's not just one footstep sound).
	 */
	public Music footsteps;

	public void init() {
		ocean                 = Gdx.audio.newMusic(Gdx.files.internal("audio/ocean.ogg"));
		dayTimeAmbientNoise   = Gdx.audio.newMusic(Gdx.files.internal("audio/daytimeambience.ogg"));
		footsteps             = Gdx.audio.newMusic(Gdx.files.internal("audio/footstepsmusic.ogg"));
		rainAndThunder        = Gdx.audio.newMusic(Gdx.files.internal("audio/rain.ogg"));
		fire                  = Gdx.audio.newMusic(Gdx.files.internal("audio/fire.ogg"));
		nightTimeAmbientNoise = Gdx.audio.newMusic(Gdx.files.internal("audio/nighttimeambience.ogg"));
		ambientMusic          = Gdx.audio.newMusic(Gdx.files.internal("audio/AmbientMusic.ogg"));
		invincibleMusic       = Gdx.audio.newMusic(Gdx.files.internal("audio/InvincibleMusic.ogg"));
		bossDeafeatedMusic    = Gdx.audio.newMusic(Gdx.files.internal("audio/avalanche.ogg"));
		bossBattleMusic       = Gdx.audio.newMusic(Gdx.files.internal("audio/BossBattle.ogg"));
	}

	public void dispose() {
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
	}
}
