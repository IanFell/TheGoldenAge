package spawners;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import gameobjects.gamecharacters.enemies.Knight;
import helpers.GamePlayHelper;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowSpawner extends GameObject {

	private Knight[] knight = new Knight[4];

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float Y
	 * @param int   directionFacing
	 * @param float dx
	 * @param float dy
	 */
	public ArrowSpawner(float x, float y, int town, int directionFacing, float dx, float dy) {
		this.x               = x;
		this.y               = y;
		int arrowSpawnerSize = 7;
		this.width           = arrowSpawnerSize;
		this.height          = arrowSpawnerSize;
		float xPosKnight     = x + 1.3f;
		int knightSize       = 1;
		for (int i = 0; i < knight.length; i++) {
			knight[i] = new Knight(xPosKnight, y - 4, knightSize, knightSize, directionFacing);
			xPosKnight += 0.5f;
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(this)) {
			//batch.draw(imageLoader.fortShadow, x - 0.5f, y - 0.5f, width, -height);
			batch.draw(imageLoader.fortSide, x, y, width, -height);
			for (int i = 0; i < knight.length; i++) {
				Texture bowTexture = imageLoader.bowRight;
				if (knight[i].direction == DIRECTION_LEFT) {
					bowTexture = imageLoader.bowLeft;
				} else if (knight[i].direction == DIRECTION_UP) {
					bowTexture = imageLoader.bowUp;
				}
				int bowSize = 1;
				batch.draw(bowTexture, knight[i].getX(), knight[i].getY(), bowSize, -bowSize);
				knight[i].renderObject(batch, imageLoader);
			}
		}
	}
}
