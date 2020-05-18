package missions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import gameobjects.stationarygameobjects.Cauldron;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import loaders.ImageLoader;

public class MissionCauldron extends Mission {
	
	private Cauldron cauldron;
	
	public MissionCauldron() {
		cauldron = new Cauldron(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 62, 30);
		GameObjectLoader.gameObjectList.add(cauldron);
	}
	
	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderMission(SpriteBatch batch, ImageLoader imageLoader) {
		cauldron.renderObject(batch, imageLoader);
	}
	
	/**
	 * 
	 * @param MyGame myGame
	 */
	public void updateMission(MyGame myGame) {
		if (cauldron.rectangle.overlaps(myGame.getGameObject(Player.PLAYER_ONE).rectangle)) {
			System.exit(0);
		}
	}
}
