package gameobjects.weapons;

import gameobjects.GameObject;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Weapon extends GameObject {

	public static int WEAPON_TYPE_SWORD       = 0;
	public static int WEAPON_TYPE_GUN         = 1;
	public static int WEAPON_TYPE_MAGIC_PEARL = 2;
	public static int WEAPON_TYPE_BIRD        = 3;
	public static int WEAPON_TYPE_PAW         = 4;

	public static boolean shouldPlaySwitchWeaponAudio = false;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public Weapon(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
