package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import cutscenes.CutScene;
import gameobjects.GameObject;
import handlers.enemies.BossHandler;
import handlers.holehandler.HoleHandler;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
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
	
	public static final int INVENTORY_SCREEN = 0;
	public static final int MAP_SCREEN       = 1;
	public static final int CONTROLS_SCREEN  = 2;
	
	public static final int userInterfaceMaxOptionValue = 3;

	public static int userInterfaceOption = 0;

	private HealthUi healthUi;
	private LootUi lootUi;
	private PlayerNameUi playerNameUi;
	private SelectedInventoryUi selectedInventoryUi;
	private RumUi rumUi;
	private ObjectiveUi objective;
	private AmmoUi ammoUi;
	private UnlockUi unlockUi;
	private AddedToInventory addedToInventory;
	private OutOfAmmo outOfAmmo;
	private ConfidenceUi confidenceUi;
	private LivesUi livesUi;

	/**
	 * Constructor.
	 */
	public UserInterface(MyGame myGame) {
		healthUi            = new HealthUi();
		lootUi              = new LootUi();
		playerNameUi        = new PlayerNameUi();
		selectedInventoryUi = new SelectedInventoryUi();
		rumUi               = new RumUi();
		objective           = new ObjectiveUi();
		ammoUi              = new AmmoUi();
		unlockUi            = new UnlockUi();
		addedToInventory    = new AddedToInventory();
		outOfAmmo           = new OutOfAmmo();
		confidenceUi        = new ConfidenceUi(myGame);
		livesUi             = new LivesUi();
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

			if (additionalStoreItemsAreUnlocked() && UnlockUi.shouldRenderUnlock) {
				unlockUi.renderObject(batch, imageLoader, player);
			}

			addedToInventory.render(myGame);
			outOfAmmo.render(myGame);
			
			confidenceUi.renderConfidenceUi(batch, imageLoader, myGame);
			
			livesUi.renderLivesUi(batch, imageLoader, myGame);
		}
	}

	/**
	 * This method determines if we should render and update the "ammo and magic pearl unlocked" UI.
	 * @return boolean
	 */
	private boolean additionalStoreItemsAreUnlocked() {
		return BossLoader.boss[BossHandler.APALACHICOLA].isDead();
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void updateUserInterface(GameObject player) {
		objective.updateObjective();
		if (additionalStoreItemsAreUnlocked() && UnlockUi.shouldRenderUnlock) {
			unlockUi.updateObject();
		}
		addedToInventory.update(player);
		outOfAmmo.update(player);
		confidenceUi.updateConfidenceUi();
	}
}
