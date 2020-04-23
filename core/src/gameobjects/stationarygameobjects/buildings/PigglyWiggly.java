package gameobjects.stationarygameobjects.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import handlers.CollisionHandler;
import maps.MapHandler;
import towns.PortStJoe;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class PigglyWiggly extends Building {

	private PortStJoe portStJoe = new PortStJoe();

	/**
	 * Constructor.
	 * 
	 * @param int     x
	 * @param int     y
	 * @param int     width
	 * @param int     height
	 * @param Texture texture
	 */
	public PigglyWiggly(int x, int y, int width, int height, Texture texture) {
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
		portStJoe.updateTown(myGame);
		if (portStJoe.isInTown()) {
			CollisionHandler.checkIfPlayerHasCollidedWithStructure(
					myGame.getGameObject(GameObject.PLAYER_ONE),
					this,
					"Piggly Wiggly",
					myGame
					); 
		}
	}
}
