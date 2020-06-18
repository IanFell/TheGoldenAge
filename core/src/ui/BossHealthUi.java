package ui;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.enemies.Boss;
import screens.GameScreen;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class BossHealthUi extends GameObject {

	// Only display health meter during battle.
	public static boolean shouldDisplay = false;

	public static boolean shouldChangeAlpha = false;

	// Change transparancy of image this value + Boss.percentToChangeAlphaEachHit to make image get brighter.
	public static float alpha = 0;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public BossHealthUi(float x, float y) {
		this.x      = x;
		this.y      = y;
		this.width  = 8;
		this.height = 2;
	}

	/**
	 * 
	 * @param MyGame myGame
	 * @param Boss   boss
	 */
	float fadeTimeAlpha = 0.5f;
	public void renderBossHealthUi(MyGame myGame, Boss boss) {
		if (shouldDisplay) {

			myGame.renderer.batch.draw(
					myGame.imageLoader.enemyHealthMeterBlack, 
					GameScreen.camera.position.x - width / 2, 
					(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 13.5f, 
					width, 
					height
					);	


			myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, alpha);
			myGame.renderer.batch.draw(
					myGame.imageLoader.enemyHealthMeterBase, 
					GameScreen.camera.position.x - width / 2, 
					(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 13.5f, 
					width, 
					height
					);	
			myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
		//batch.draw(imageLoader.redSquare, x, y, boss.getBossHealth(), height);
	}
}
