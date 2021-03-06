package gameobjects.stationarygameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Fire;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Cauldron extends GamePlayObject {

	private Fire fire;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public Cauldron(int x, int y) {
		super(x, y);
		this.width       = 2;
		this.height      = 2;
		rectangle.y      = y - height;
		rectangle.width  = width;
		rectangle.height = height;
		fire             = new Fire(x + 0.5f, y - height + 0.5f, 1, 2, "Cauldron", false);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		// TODO RENDER SMOKE LIKE THE FIRE, OR FIRE COMING OUT OF THE CAULDRON
		batch.draw(imageLoader.cauldron, x, y, width, -height);
		fire.renderObject(batch, imageLoader);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		fire.updateObject(myGame, mapHandler);
		// TODO I have no idea why this is here, 
		// TODO it obviously does nothing because the game never crashes when this happens.
		/*
		if (myGame.getGameObject(Player.PLAYER_ONE).rectangle.overlaps(rectangle)) {
			System.exit(0);
		} */
	}
}
