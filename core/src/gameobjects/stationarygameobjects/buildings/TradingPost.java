package gameobjects.stationarygameobjects.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import handlers.CollisionHandler;
import maps.MapHandler;
import missions.MissionTradinPost;
import store.Store;
import towns.CapeSanBlas;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class TradingPost extends Building {

	public static boolean hasBeenEntered;

	private CapeSanBlas capeSanBlas = new CapeSanBlas();

	/**
	 * Constructor.
	 * 
	 * @param int     x
	 * @param int     y
	 * @param int     width
	 * @param int     height
	 * @param Texture texture
	 */
	public TradingPost(int x, int y, int width, int height, Texture texture) {
		super(x, y, width, height, texture);
		rectangle.width  = width;
		rectangle.height = height;
		hasBeenEntered   = false;
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		if (MissionTradinPost.locationMarkerHasBeenHit && !Store.gunHasBeenPurchasedAtStore) {
			capeSanBlas.updateTown(myGame);
			if (capeSanBlas.isInTown()) {
				CollisionHandler.checkIfPlayerHasCollidedWithStructure(
						myGame.getGameObject(Player.PLAYER_ONE), 
						this, 
						"Trading Post", 
						myGame
						);
			}
		}
	}
}
