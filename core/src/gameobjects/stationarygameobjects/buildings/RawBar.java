package gameobjects.stationarygameobjects.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import handlers.CollisionHandler;
import maps.MapHandler;
import towns.Apalachicola;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class RawBar extends Building {

	private Apalachicola apalachicola = new Apalachicola();

	/**
	 * Constructor.
	 * 
	 * @param int     x
	 * @param int     y
	 * @param int     width
	 * @param int     height
	 * @param Texture texture
	 */
	public RawBar(int x, int y, int width, int height, Texture texture) {
		super(x, y, width, height, texture);
		rectangle.width  = width;
		rectangle.height = height;
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		apalachicola.updateTown(myGame);
		if (apalachicola.isInTown()) {
			CollisionHandler.checkIfPlayerHasCollidedWithStructure(
					myGame.getGameObject(GameObject.PLAYER_ONE),
					this,
					"Raw Bar",
					myGame
					); 
		}
	}
}
