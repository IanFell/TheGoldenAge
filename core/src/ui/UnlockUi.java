package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import loaders.ImageLoader;

/**
 * This is the message that opens alterting the player when weapons are unlocked.
 * Heart, rum, and gun all unlock on their own.  Ammo and magic pearl unlock after 
 * Raw Bar boss is defeated.
 * 
 * @author Fabulous Fellini
 *
 */
public class UnlockUi {

	private int timerAmmo;
	private int timerMagicPearl;
	private final int MAX_RENDER_TIME = 100;

	private boolean shouldRenderAmmoObjective;
	private boolean shouldRenderMagicPearlObjective;

	public static boolean shouldRenderUnlock = true;

	private int width;
	private int height;

	/**
	 * Constructor.
	 */
	public UnlockUi() {
		timerAmmo                       = 0;
		timerMagicPearl                 = 0;
		width                           = 8;
		height                          = 3;
		shouldRenderMagicPearlObjective = false;
		shouldRenderAmmoObjective       = true;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param GameObject  player
	 */
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader, GameObject player) {
		if (shouldRenderAmmoObjective) {
			batch.draw(imageLoader.objectiveAmmoUnlocked, player.getX() - width / 2, player.getY() - 1, width, -height);
		}
		if (shouldRenderMagicPearlObjective) {
			batch.draw(imageLoader.objectiveMagicPearlUnlocked, player.getX() - width / 2, player.getY() + 3, width + 1, -height - 2);
		}
	}

	public void updateObject() {
		if (shouldRenderUnlock) {
			// Render the ammo unlock UI.
			timerAmmo++;
			if (timerAmmo > MAX_RENDER_TIME) {
				shouldRenderAmmoObjective = false;
			}
			// If ammo UI is rendered for half it's max time, render magic pearl UI.
			if (timerAmmo >= MAX_RENDER_TIME / 2) {
				shouldRenderMagicPearlObjective = true;
			}
			if (shouldRenderMagicPearlObjective) {
				timerMagicPearl++;
			}
			// This UI is done now (magic pearl timer has reached it's max).  Turn this UI off.
			if (timerMagicPearl > MAX_RENDER_TIME) {
				shouldRenderUnlock              = false;
				shouldRenderMagicPearlObjective = false;
			}
		}
	}
}
