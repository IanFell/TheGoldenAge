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

	public static boolean shouldDisplay = false;

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
	public void renderBossHealthUi(MyGame myGame, Boss boss) {
		if (shouldDisplay) {
			myGame.renderer.batch.draw(
					myGame.imageLoader.enemyHealthMeterBase, 
					GameScreen.camera.position.x - width / 2, 
					(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 13.5f, 
					width, 
					height
					);	
		}
		//batch.draw(imageLoader.redSquare, x, y, boss.getBossHealth(), height);
	}
}
