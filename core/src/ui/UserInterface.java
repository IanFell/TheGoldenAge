package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import cutscenes.CutScene;
import gameobjects.GameObject;
import handlers.holehandler.HoleHandler;
import loaders.ImageLoader;
import store.Store;
import ui.collectibles.AmmoUi;
import ui.collectibles.HealthUi;
import ui.collectibles.LootUi;
import ui.collectibles.RumUi;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class UserInterface {

	private HealthUi healthUi;
	private LootUi lootUi;
	private PlayerNameUi playerNameUi;
	private SelectedInventoryUi selectedInventoryUi;
	private RumUi rumUi;
	private ObjectiveUi objective;
	private AmmoUi ammoUi;

	/**
	 * Constructor.
	 */
	public UserInterface() {
		healthUi            = new HealthUi();
		lootUi              = new LootUi();
		playerNameUi        = new PlayerNameUi();
		selectedInventoryUi = new SelectedInventoryUi();
		rumUi               = new RumUi();
		objective           = new ObjectiveUi();
		ammoUi              = new AmmoUi();
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	public void renderUserInterface(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		// Dont render this if a cutscene is in progress.
		if (!CutScene.anyCutSceneIsInProgress && !HoleHandler.playerIsInHole) {
			healthUi.renderHealthUi(batch, imageLoader, myGame);
			GameObject player = PlayerController.getCurrentPlayer(myGame);
			lootUi.renderUi(batch, imageLoader, myGame, player);
			rumUi.renderUi(batch, imageLoader, myGame, player);
			ammoUi.renderUi(batch, imageLoader, myGame, player);
			objective.renderUi(batch, imageLoader, myGame, player);
			
			if (!Store.playerWantsToEnterStore) {
				playerNameUi.renderUi(batch, imageLoader, myGame, player, 10.5f, 6.0f);
				selectedInventoryUi.renderSelectedInventoryUi(batch, imageLoader, myGame, player);
			}
		}
	}

	public void updateUserInterface() {
		objective.updateObjective();
	}
}
