package mixer;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Mixer {
	public static final float MINIMUM_VOLUME        = 0f;
	public static final float THIRTY_SECOND_VOLUME  = 0.03f;
	public static final float SIXTEENTH_VOLUME      = 0.06f;
	public static final float TWELVETH_VOLUME       = 0.10f;
	public static final float EIGHTH_VOLUME         = 0.125f;
	public static final float QUARTER_VOLUME        = 0.25F;
	public static final float MEDIAN_VOLUME         = 0.50f;
	public static final float THREE_QUARTERS_VOLUME = 0.75f;
	public static final float MAX_VOLUME            = 1.0f;

	// Ui.
	public static final float CLICK_VOLUME         = MAX_VOLUME;
	public static final float PAUSE_VOLUME         = QUARTER_VOLUME;
	public static final float SWITCH_WEAPON_VOLUME = QUARTER_VOLUME;

	// When player buys something from the store.
	public static final float REGISTER_VOLUME = QUARTER_VOLUME;

	// When player is teleporting in tunnel.
	public static final float TUNNEL_VOLUME = QUARTER_VOLUME;

	// Music.
	public final static float AMBIENT_MUSIC_VOLUME     = MEDIAN_VOLUME;
	public final static float INVINCIBLE_MUSIC_VOLUME  = MEDIAN_VOLUME;
	public static final float BOSS_BATTLE_MUSIC_VOLUME = MEDIAN_VOLUME;
	public static final float MISSION_MUSIC_VOLUME     = MEDIAN_VOLUME;
	public static final float CUTSCENE_MUSIC_VOLUME    = MEDIAN_VOLUME;
	public static final float TYPEWRITER_VOLUME        = QUARTER_VOLUME;
	public static final float TITLE_SCREEN_VOLUME      = THREE_QUARTERS_VOLUME;

	// Game character walking and jumping.
	public final static float FOOTSTEPS_VOLUME     = TWELVETH_VOLUME;
	public final static float QUICK_SAND_VOLUME    = MEDIAN_VOLUME;
	public final static float JUMP_VOLUME          = TWELVETH_VOLUME;
	public final static float LAND_VOLUME          = TWELVETH_VOLUME;
	public final static float GIANT_LANDING_VOLUME = EIGHTH_VOLUME;

	// Ambient noise.
	public final static float DAY_TIME_AMBIENT_VOLUME   = QUARTER_VOLUME;
	public final static float NIGHT_TIME_AMBIENT_VOLUME = THREE_QUARTERS_VOLUME;
	public final static float STORM_VOLUME              = MAX_VOLUME;
	public final static float FIRE_VOLUME               = MAX_VOLUME;
	public final static float OCEAN_VOLUME              = MAX_VOLUME;

	// Pick up weapons.
	public final static float PICK_UP_SWORD_VOLUME  = EIGHTH_VOLUME;
	public final static float PICK_UP_GUN_VOLUME    = MAX_VOLUME;
	public static final float PICK_UP_BIRD_VOLUME   = QUARTER_VOLUME;
	public static final float PICK_UP_MONKEY_VOLUME = MEDIAN_VOLUME;
	public static final float PICK_UP_DAGGER_VOLUME = EIGHTH_VOLUME;

	// Damage to player and enemies.
	public final static float PLAYER_HIT_VOLUME            = EIGHTH_VOLUME;
	public final static float ENEMY_DEATH_VOLUME           = MEDIAN_VOLUME;
	public static final float ENEMY_DEATH_VOLUME_ALTERNATE = QUARTER_VOLUME;
	public final static float BOSS_GRUNT_VOLUME            = QUARTER_VOLUME;

	// Game character SFX volumes.
	public final static float BOSS_LAUGH_VOLUME  = QUARTER_VOLUME;
	public static final float DEATH_VOLUME       = THREE_QUARTERS_VOLUME;

	// Player attacks.
	public final static float SWORD_ATTACK_VOLUME  = SIXTEENTH_VOLUME;
	public final static float GUN_ATTACK_VOLUME    = SIXTEENTH_VOLUME;
	public final static float BUBBLE_ATTACK_VOLUME = SIXTEENTH_VOLUME;
	public static final float BIRD_ATTACK_VOLUME   = SIXTEENTH_VOLUME;
	public static final float PAW_ATTACK_VOLUME    = MEDIAN_VOLUME;
	public static final float DAGGER_ATTACK_VOLUME = EIGHTH_VOLUME;

	// Enemy attacks.
	public final static float CANNON_FIRE_VOLUME      = QUARTER_VOLUME;
	public final static float BOMB_VOLUME             = THIRTY_SECOND_VOLUME;
	public final static float ARROW_VOLUME            = MEDIAN_VOLUME;
	public static final float SPARK_VOLUME            = EIGHTH_VOLUME;
	public static final float POISON_VOLUME           = MEDIAN_VOLUME;
	public static final float BOSS_ATTACK_SPIN_VOLUME = MEDIAN_VOLUME;
	public static final float BOSS_ATTACK_BASH_VOLUME = SIXTEENTH_VOLUME;

	// Collectables.
	public final static float HEART_COLLECT_VOLUME   = EIGHTH_VOLUME;
	public final static float RUM_COLLECT_VOLUME     = QUARTER_VOLUME;
	public final static float CHEST_COLLECT_VOLUME   = MEDIAN_VOLUME;
	public final static float FEATHER_COLLECT_VOLUME = MEDIAN_VOLUME;
	public final static float BUBBLE_VOLUME          = SIXTEENTH_VOLUME;
	public final static float AMMO_COLLECT_VOLUME    = MAX_VOLUME;
	public static final float PAW_COLLECT_VOLUME     = MEDIAN_VOLUME;
	public static final float DRINK_VOLUME           = THREE_QUARTERS_VOLUME;

	// Location marker.
	public final static float LOCATION_MARKER_HIT_VOLUME  = MAX_VOLUME;
	public final static float LOCATION_MARKER_BEEP_VOLUME = QUARTER_VOLUME;

	// Missions.
	public static final float BIRD_STUMP_HOLE_MISSION_VOLUME = QUARTER_VOLUME;

	// Cutscene intro jingle.
	public static final float CUTSCENE_INTRO_JINGLE_VOLUME = QUARTER_VOLUME;

	// Objective change.
	public static final float OBJECTIVE_CHANGE_VOLUME = MEDIAN_VOLUME;
}
