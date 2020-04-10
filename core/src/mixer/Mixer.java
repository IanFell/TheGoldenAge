package mixer;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Mixer {
	public static final float MINIMUM_VOLUME        = 0f;
	public static final float SIXTEENTH_VOLUME      = 0.06f;
	public static final float TWELVETH_VOLUME       = 0.10f;
	public static final float EIGHTH_VOLUME         = 0.125f;
	public static final float QUARTER_VOLUME        = 0.25F;
	public static final float MEDIAN_VOLUME         = 0.50f;
	public static final float THREE_QUARTERS_VOLUME = 0.75f;
	public static final float MAX_VOLUME            = 1.0f;

	public static final float CLICK_VOLUME = MAX_VOLUME;

	// Music.
	public final static float AMBIENT_MUSIC_VOLUME     = QUARTER_VOLUME;
	public final static float INVINCIBLE_MUSIC_VOLUME  = QUARTER_VOLUME;
	public static final float BOSS_BATTLE_MUSIC_VOLUME = THREE_QUARTERS_VOLUME;

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
	public final static float PICK_UP_SWORD_VOLUME = TWELVETH_VOLUME;
	public final static float PICK_UP_GUN_VOLUME   = MAX_VOLUME;

	// Damage to player and enemies.
	public final static float PLAYER_HIT_VOLUME  = QUARTER_VOLUME;
	public final static float ENEMY_DEATH_VOLUME = MAX_VOLUME;
	public final static float BOSS_GRUNT_VOLUME  = MEDIAN_VOLUME;

	// Player attacks.
	public final static float SWORD_ATTACK_VOLUME  = MEDIAN_VOLUME;
	public final static float GUN_ATTACK_VOLUME    = SIXTEENTH_VOLUME;
	public final static float BUBBLE_ATTACK_VOLUME = MAX_VOLUME;

	// Enemy attacks.
	public final static float CANNON_FIRE_VOLUME = MEDIAN_VOLUME;
	public final static float BOMB_VOLUME        = SIXTEENTH_VOLUME;

	// Collectables.
	public final static float HEART_COLLECT_VOLUME   = EIGHTH_VOLUME;
	public final static float RUM_COLLECT_VOLUME     = EIGHTH_VOLUME;
	public final static float CHEST_COLLECT_VOLUME   = QUARTER_VOLUME;
	public final static float FEATHER_COLLECT_VOLUME = MAX_VOLUME;
	public final static float BUBBLE_VOLUME          = MAX_VOLUME;

	// Location marker.
	public final static float LOCATION_MARKER_HIT_VOLUME  = MAX_VOLUME;
	public final static float LOCATION_MARKER_BEEP_VOLUME = QUARTER_VOLUME;
}
