package ui;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import screens.GameScreen;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class AddedToInventory extends GameObject {

	public static boolean shouldDisplayLegendSword = false;
	public static boolean shouldDisplayMagicPearl  = false;
	public static boolean shouldDisplayGun         = false;
	public static boolean shouldDisplayWoody       = false;
	public static boolean shouldDisplayHealth      = false;
	public static boolean shouldDisplayRum         = false;
	public static boolean shouldDisplayAmmo        = false;
	public static boolean shouldDisplayPaw         = false;
	public static boolean shouldDisplayDagger      = false;
	public static boolean shouldDisplayLoot        = false;

	public static boolean shouldRender = false;

	public static int timer = 0;

	private final int DISPLAY_VALUE = 50;

	public static void resetGame() {
		shouldDisplayLegendSword = false;
		shouldDisplayMagicPearl  = false;
		shouldDisplayGun         = false;
		shouldDisplayWoody       = false;
		shouldDisplayHealth      = false;
		shouldDisplayRum         = false;
		shouldDisplayAmmo        = false;
		shouldDisplayPaw         = false;
		shouldDisplayDagger      = false;
		shouldDisplayLoot        = false;

		shouldRender = false;

		timer = 0;
	}

	/**
	 * Constructor.
	 */
	public AddedToInventory() {
		this.width  = 9;
		this.height = 3;
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void render(MyGame myGame) {
		if (shouldRender) {
			float weaponNameUiWidth = width + 1;
			int offset              = 5;
			if (shouldDisplayLegendSword) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.legendSwordUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			} else if (shouldDisplayMagicPearl) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.magicPearlUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			} else if (shouldDisplayGun) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.gunUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			} else if (shouldDisplayWoody) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.woodyUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			} else if (shouldDisplayHealth) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.healthUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			} else if (shouldDisplayRum) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.rumUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			} else if (shouldDisplayAmmo) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.ammoUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			} else if (shouldDisplayPaw) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.pawUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			}
			else if (shouldDisplayDagger) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.daggerUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			}
			else if (shouldDisplayLoot) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.lootAddedToInventory, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - offset, 
						weaponNameUiWidth, 
						-height
						);
			}
			myGame.renderer.batch.draw(
					myGame.imageLoader.addedToInventory, 
					GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
					(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 3, 
					weaponNameUiWidth, 
					-height
					);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void update(GameObject player) {
		if (shouldRender) {
			timer++;
			if (timer > DISPLAY_VALUE) {
				shouldRender             = false;
				timer                    = 0;
				shouldDisplayLegendSword = false;
				shouldDisplayMagicPearl  = false;
				shouldDisplayGun         = false;
				shouldDisplayWoody       = false;
				shouldDisplayHealth      = false;
				shouldDisplayRum         = false;
				shouldDisplayAmmo        = false;
				shouldDisplayLoot        = false;
			}
		}
	}
}
