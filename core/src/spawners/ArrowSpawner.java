package spawners;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.weapons.Arrow;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;
import maps.MapHandler;
import maps.MapInformationHolder;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowSpawner extends GameObject {

	private ArrayList <Arrow> arrows = new ArrayList<Arrow>();

	private final int NUMBER_OF_ARROWS_SHOT = 4;

	private final int RIGHT_BOUNDARY = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + MapInformationHolder.CHUNK_WIDTH;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float Y
	 * @param int   direction
	 */
	public ArrowSpawner(float x, float y, int town, int direction) {
		this.x      = x;
		this.y      = y;
		this.width  = 5;
		this.height = 5;
		createArrows(direction);

	}

	/**
	 * 
	 * @param int directonOfArrow
	 */
	private void createArrows(int directonOfArrow) {
		float yPos  = y + 0.8f;
		for (int i = 0; i < NUMBER_OF_ARROWS_SHOT; i++) {
			arrows.add(new Arrow(x, yPos, directonOfArrow));
			yPos += 1;
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateArrowSpawner(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < arrows.size(); i++) {
			arrows.get(i).updateObject(myGame, mapHandler);
			if (arrows.get(i).getX() > RIGHT_BOUNDARY) {
				arrows.remove(i);
				createArrows(GameObject.DIRECTION_RIGHT);
			}
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderArrowSpawner(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.blackSquare, x, y, width, height);
		for (int i = 0; i < arrows.size(); i++) {
			arrows.get(i).renderObject(batch, imageLoader);
		}
	}
}
