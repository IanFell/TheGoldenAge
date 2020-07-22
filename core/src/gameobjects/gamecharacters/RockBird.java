package gameobjects.gamecharacters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.weapons.RockDrop;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class RockBird extends GameCharacter {

	private ArrayList <RockDrop> rockDrop = new ArrayList<RockDrop>();

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public RockBird(float x, float y) {
		this.x           = x;
		this.y           = y;
		float size       = 1;
		this.width       = size;
		this.height      = size;
		rectangle.width  = width;
		rectangle.height = height;
		this.dx          = 0.5f;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.attackBird, x, y, width, -height);

		for (int i = 0; i < rockDrop.size(); i++) {
			rockDrop.get(i).renderObject(batch, imageLoader);	
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 * @param float      leftBoundary
	 * @param float      rightBoundary
	 */
	public void updateObject(MyGame myGame, MapHandler mapHandler, float leftBoundary, float rightBoundary) {
		x += dx;
		if (x < leftBoundary) {
			dx = 0.5f;
		} else if (x > rightBoundary) {
			dx = -0.5f;
		}
		updateRockDrop(rightBoundary, myGame, mapHandler);
	}

	/**
	 * 
	 * @param float      rightBoundary
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	private void updateRockDrop(float rightBoundary, MyGame myGame, MapHandler mapHandler) {
		if (x < rightBoundary) {
			if (rockDrop.size() < 3) {
				rockDrop.add(new RockDrop(x, y));
			}
		}
		for (int i = 0; i < rockDrop.size(); i++) {
			rockDrop.get(i).updateObject(myGame, mapHandler);
		}

		for (int i = 0; i < rockDrop.size(); i++) {
			if (rockDrop.get(i).getY() > y + 10) {
				rockDrop.remove(i);
			}
		}
	}
}
