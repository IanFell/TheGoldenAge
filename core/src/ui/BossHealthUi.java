package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import gameobjects.gamecharacters.Boss;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class BossHealthUi extends GameObject {

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public BossHealthUi(float x, float y) {
		this.x      = x;
		this.y      = y;
		this.width  = 7;
		this.height = 1;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderBossHealthUi(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.blackSquare, x, y, width, height);
	}

	/**
	 * 
	 * @param Boss boss
	 */
	public void updateBossHealthUi(Boss boss) {
		x = boss.getX();
		y = boss.getY() - 2;
	}
}
