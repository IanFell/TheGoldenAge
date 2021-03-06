package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import loaders.ImageLoader;

/**
 * Handles game shadows.
 * 
 * @author Fabulous Felini
 *
 */
public class PlayerShadowHandler extends AbstractLightingHandler {

	/**
	 * Amount to offset shadows under player so they do not appear behind player.
	 */
	private float offset = -0.2f;

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param GameObject  player
	 */
	@Override
	public void renderLighting(SpriteBatch batch, ImageLoader imageLoader, GameObject player) {
		if (!Player.isInWater) {
			batch.draw(imageLoader.shadow, player.getX(), player.getY() + offset, width * 2f, height);
			if (Player.isInvincible) {
				batch.draw(imageLoader.shadow, player.getX(), player.getY() + offset, width * 4f, height * 2);
			}
			handleShadowLocationDuringPlayerJump((Player) player);
		}
	}

	/**
	 * 
	 * @param Player player
	 */
	private void handleShadowLocationDuringPlayerJump(Player player) {
		if (Player.jumpingAction == Player.ASCENDING_JUMP) {
			offset += player.getJumpingSpeedValue();
		} else if (Player.jumpingAction == Player.DESCENDING_JUMP) {
			offset -= player.getJumpingSpeedValue();
		} else {
			// If player has completed jump and is on ground.
			offset = -0.2f;
		}
	}
}

