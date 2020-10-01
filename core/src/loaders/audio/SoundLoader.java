package loaders.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Loads sounds.
 * 
 * @author Fabulous Fellini
 *
 */
public class SoundLoader {

	public Sound gameOver;
	public Sound splash;
	public Sound trumpet;
	public Sound buzzer;
	public Sound bossAttackSpin;
	public Sound bossAttackBash;
	public Sound death;
	public Sound drink;
	public Sound objectiveChange;
	public Sound cutscene;
	public Sound monkey;
	public Sound spark;
	public Sound bow;
	public Sound switchWeapons;
	public Sound bird;
	public Sound birdTwo;
	public Sound pause;
	public Sound clickSound;
	public Sound heartSound;
	public Sound rumSound;
	public Sound swordSound;
	public Sound pistolSound;
	public Sound pickUpSwordSound;
	public Sound pickUpGunSound;
	public Sound chestSound;
	public Sound enemyDeathSound;
	public Sound jumpSound;
	public Sound landSound;
	public Sound bubbleSound;
	public Sound bombSound;
	public Sound cannonFire;
	public Sound giantLandingSound;
	public Sound playerHit;
	public Sound locationMarkerSound;
	public Sound locatorBeep;
	public Sound quickSand;
	public Sound bossGrunt;
	public Sound bossLaugh;
	public Sound register;
	public Sound arrow;
	public Sound tunnel;
	public Sound dagger;
	public Sound enemyHurt;

	public void init() {
		gameOver            = Gdx.audio.newSound(Gdx.files.internal("audio/sound/GameOver.wav"));
		splash              = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Splash.wav"));
		trumpet             = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Trumpet.wav"));
		buzzer              = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Buzzer.wav"));
		bossAttackSpin      = Gdx.audio.newSound(Gdx.files.internal("audio/sound/BossWhoosh.wav"));
		bossAttackBash      = Gdx.audio.newSound(Gdx.files.internal("audio/sound/swish.wav"));
		death               = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Death.wav"));
		drink               = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Drink.wav"));
		objectiveChange     = Gdx.audio.newSound(Gdx.files.internal("audio/sound/ObjectiveChange.wav"));
		dagger              = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Dagger.wav"));
		enemyHurt           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/HurtEnemy.wav"));
		cutscene            = Gdx.audio.newSound(Gdx.files.internal("audio/sound/CutScene.wav"));
		monkey              = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Monkey.wav"));
		spark               = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Spark.wav"));
		bow                 = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Bow.wav"));
		switchWeapons       = Gdx.audio.newSound(Gdx.files.internal("audio/sound/SwitchWeapons.wav"));
		birdTwo             = Gdx.audio.newSound(Gdx.files.internal("audio/sound/BirdTwo.wav"));
		bird                = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Bird.wav"));
		pause               = Gdx.audio.newSound(Gdx.files.internal("audio/sound/whistle.wav"));
		heartSound          = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Heart.wav"));
		rumSound            = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Glass.wav"));
		jumpSound           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/JumpSound.wav"));
		landSound           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/LandingSound.wav"));
		swordSound          = Gdx.audio.newSound(Gdx.files.internal("audio/sound/WeaponSword.wav"));
		pistolSound         = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Pistol.wav"));
		pickUpSwordSound    = Gdx.audio.newSound(Gdx.files.internal("audio/sound/PickUpSword.wav"));
		pickUpGunSound      = Gdx.audio.newSound(Gdx.files.internal("audio/sound/PickUpGun.wav"));
		chestSound          = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Chest.wav"));
		clickSound          = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Click.wav"));
		enemyDeathSound     = Gdx.audio.newSound(Gdx.files.internal("audio/sound/EnemyDeath.wav"));
		bubbleSound         = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Bubble.wav"));
		bombSound           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Bomb.wav"));
		cannonFire          = Gdx.audio.newSound(Gdx.files.internal("audio/sound/CannonFire.wav"));
		giantLandingSound   = Gdx.audio.newSound(Gdx.files.internal("audio/sound/rumble.wav"));
		playerHit           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/PlayerHit.wav"));
		locationMarkerSound = Gdx.audio.newSound(Gdx.files.internal("audio/sound/LocationMarkerSound.wav"));
		locatorBeep         = Gdx.audio.newSound(Gdx.files.internal("audio/sound/LocatorBeep.wav"));
		quickSand           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/QuickSand.wav"));
		bossGrunt           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/grunt.wav"));
		bossLaugh           = Gdx.audio.newSound(Gdx.files.internal("audio/sound/laugh.wav"));
		register            = Gdx.audio.newSound(Gdx.files.internal("audio/sound/register.wav"));
		arrow               = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Arrow.wav"));
		tunnel              = Gdx.audio.newSound(Gdx.files.internal("audio/sound/Tunnel.wav"));
	}

	public void dispose() {
		gameOver.dispose();
		splash.dispose();
		trumpet.dispose();
		buzzer.dispose();
		bossAttackSpin.dispose();
		bossAttackBash.dispose();
		death.dispose();
		drink.dispose();
		objectiveChange.dispose();
		enemyHurt.dispose();
		dagger.dispose();
		cutscene.dispose();
		monkey.dispose();
		spark.dispose();
		bow.dispose();
		switchWeapons.dispose();
		birdTwo.dispose();
		bird.dispose();
		pause.dispose();
		heartSound.dispose();
		rumSound.dispose();
		swordSound.dispose();
		pistolSound.dispose();
		pickUpSwordSound.dispose();
		pickUpGunSound.dispose();
		chestSound.dispose();
		clickSound.dispose();
		enemyDeathSound.dispose();
		jumpSound.dispose();
		landSound.dispose();
		bubbleSound.dispose();
		bombSound.dispose();
		cannonFire.dispose();
		giantLandingSound.dispose();
		playerHit.dispose();
		locationMarkerSound.dispose();
		locatorBeep.dispose();
		quickSand.dispose();
		bossGrunt.dispose();
		bossLaugh.dispose();
		register.dispose();
		arrow.dispose();
		tunnel.dispose();
	}
}
