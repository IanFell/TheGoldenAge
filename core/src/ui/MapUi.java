package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import handlers.InputHandler;
import loaders.ImageLoader;
import screens.Screens;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MapUi extends Screens {

	public static boolean mapShouldBeRendered;

	// Use this to make player location icon flash.
	private int timer                 = 0;
	private final int MAX_TIMER_VALUE = 16;

	private int locationMarkerSize = 1;

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public MapUi(final MyGame myGame) {
		super(myGame);
		mapShouldBeRendered = false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isMapShouldBeRendered() {
		return mapShouldBeRendered;
	}

	public void updateWorldMapUi() {
		timer++;
		if (timer > MAX_TIMER_VALUE) {
			timer = 0;
		}
		//playerLocation  = MapLocationFinder.getPlayerLocationOnMap((Player) myGame.getGameObject(Player.PLAYER_ONE));
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 * @param MyGame        myGame
	 */
	public void renderWorldMapUi(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (mapShouldBeRendered) {
			batch.draw(
					imageLoader.blackSquare,
					camera.position.x - getViewportWidth() / denominatorOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
					camera.viewportWidth, 
					-camera.viewportHeight
					);
			renderScrolls(batch, imageLoader, myGame);
			if (InputHandler.inputType == InputHandler.INPUT_ARCADE) {
				batch.draw(
						imageLoader.arcadeMap,
						camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
						(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
						camera.viewportWidth - borderShrinkOffset * 2, 
						-camera.viewportHeight
						);
			} else {
				batch.draw(
						imageLoader.worldMapFake,
						camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
						(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
						camera.viewportWidth - borderShrinkOffset * 2, 
						-camera.viewportHeight
						);
			}
			//renderUiNavigationBar(imageLoader.mapNavigationBar, batch);
			flashPlayerChunkLocation(batch, imageLoader);
		}
	}

	/**
	 * Flashes the player locator in whichever chunk the player currently resides.
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void flashPlayerChunkLocation(SpriteBatch batch, ImageLoader imageLoader) {
		float xPosStart = camera.position.x - getViewportWidth() / 2;
		float yPosStart = myGame.getGameObject(Player.PLAYER_ONE).getY();
		float xOffset   = -100; // Make sure this isn't rendered on screen if not needed.  Move it way to the left.
		float yOffset   = 0;
		if (myGame.getGameScreen().getTownHandler().getMexicoBeach().isInTown()) {
			xOffset = 8.2f;
			yOffset = -4.5f;
		} 
		else if (myGame.getGameScreen().getTownHandler().getWewa().isInTown()) {
			xOffset = 24.95f;
			yOffset = -4.3f;
		} 
		else if (myGame.getGameScreen().getTownHandler().getApalachicola().isInTown()) {
			xOffset = 25.1f;
			yOffset = 4.2f;
		} 
		else if (myGame.getGameScreen().getTownHandler().getStGeorge().isInTown()) {
			xOffset = 21.3f;
			yOffset = 6.0f;
		} 
		else if (myGame.getGameScreen().getTownHandler().getPortStJoe().isInTown()) {
			xOffset = 12.2f;
			yOffset = -2.4f;
		} 
		else if (myGame.getGameScreen().getTownHandler().getThePoint().isInTown()) {
			xOffset = 6.1f;
			yOffset = -2.6f;
		} 
		else if (myGame.getGameScreen().getTownHandler().getCapeSanBlas().isInTown()) {
			xOffset = 6.1f;
			yOffset = 0.2f; 
		}
		if (timer < MAX_TIMER_VALUE / 2) {
			batch.draw(
					imageLoader.locationSkull, 
					xPosStart + xOffset, 
					yPosStart + yOffset, 
					locationMarkerSize, 
					-locationMarkerSize
					);
		}
	}
}
