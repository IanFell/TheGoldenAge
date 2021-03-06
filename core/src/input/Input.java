package input;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import inventory.Inventory;
import physics.Lighting.Fire;
import store.Store;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Input extends ApplicationAdapter {
	
	protected final int PURCHASE_BUTTON_HEART = 0;
	protected final int PURCHASE_BUTTON_RUM   = 1;
	protected final int PURCHASE_BUTTON_GUN   = 2;
	protected final int PURCHASE_BUTTON_PEARL = 3;
	protected final int PURCHASE_BUTTON_AMMO  = 4;
	protected final int PURCHASE_BUTTON_NULL  = 5;

	/**
	 * Inventory buttons reside here because it is easier to translate mouse to world coordinates.
	 */
	protected static int maxNumberOfInventoryObjects  = 12;
	protected static Rectangle[] inventoryButtons     = new Rectangle[maxNumberOfInventoryObjects];
	public static boolean[] inventoryButtonIsPressed  = new boolean[maxNumberOfInventoryObjects];
	
	/**
	 * Purchasing buttons reside here because it is easier to translate mouse to world coordinates.
	 */
	protected static int maxNumberOfPurchasingObjects  = 6;
	protected static Rectangle[] purchasingButtons     = new Rectangle[maxNumberOfPurchasingObjects];
	public static boolean[] purchasingButtonIsPressed  = new boolean[maxNumberOfPurchasingObjects];

	protected static double yStartPositionTopRow;
	protected static double yStartPositionBottomRow;
	protected static double xStartPositionColumnOne;
	protected static double xStartPositionColumnTwo;
	protected static double xStartPositionColumnThree;
	protected static double xStartPositionColumnFour;
	protected static double xStartPositionColumnFive;
	protected static double xStartPositionColumnSix;

	public static void initializeInventoryAndPurchasingUiForInput() {
		for (int i = 0; i < inventoryButtons.length; i++) {
			inventoryButtons[i]         = new Rectangle(0, 0, 0, 0);
			inventoryButtonIsPressed[i] = false;
			inventoryButtons[i].width   = (float) (Gdx.graphics.getWidth() / 6.9);
			inventoryButtons[i].height  = (float) (Gdx.graphics.getHeight() / 3.2);
		}
		for (int i = 0; i < purchasingButtons.length; i++) {
			purchasingButtons[i]         = new Rectangle(0, 0, 0, 0);
			purchasingButtonIsPressed[i] = false;
			purchasingButtons[i].width   = (float) (Gdx.graphics.getWidth() / 6.9);
			purchasingButtons[i].height  = (float) (Gdx.graphics.getHeight() / 3.2);
		}
		yStartPositionTopRow      = Gdx.graphics.getHeight() / 3.07;
		yStartPositionBottomRow   = Gdx.graphics.getHeight() / 1.595;
		xStartPositionColumnOne   = Gdx.graphics.getWidth() / 15.756;
		xStartPositionColumnTwo   = Gdx.graphics.getWidth() / 4.5;
		xStartPositionColumnThree = Gdx.graphics.getWidth() / 2.77;
		xStartPositionColumnFour  = Gdx.graphics.getWidth() / 1.967;
		xStartPositionColumnFive  = Gdx.graphics.getWidth() / 1.525;
		xStartPositionColumnSix   = Gdx.graphics.getWidth() / 1.25;

		inventoryButtons[0].x      = (float) xStartPositionColumnOne;
		inventoryButtons[0].y      = (float) yStartPositionTopRow;

		inventoryButtons[1].x      = (float) xStartPositionColumnTwo;
		inventoryButtons[1].y      = (float) yStartPositionTopRow;

		inventoryButtons[2].x      = (float) xStartPositionColumnThree;
		inventoryButtons[2].y      = (float) yStartPositionTopRow;

		inventoryButtons[3].x      = (float) xStartPositionColumnFour;
		inventoryButtons[3].y      = (float) yStartPositionTopRow;

		inventoryButtons[4].x      = (float) xStartPositionColumnFive;
		inventoryButtons[4].y      = (float) yStartPositionTopRow;

		inventoryButtons[5].x      = (float) xStartPositionColumnSix;
		inventoryButtons[5].y      = (float) yStartPositionTopRow;

		inventoryButtons[6].x      = (float) xStartPositionColumnOne;
		inventoryButtons[6].y      = (float) yStartPositionBottomRow;

		inventoryButtons[7].x      = (float) xStartPositionColumnTwo;
		inventoryButtons[7].y      = (float) yStartPositionBottomRow;

		inventoryButtons[8].x      = (float) xStartPositionColumnThree;
		inventoryButtons[8].y      = (float) yStartPositionBottomRow;

		inventoryButtons[9].x      = (float) xStartPositionColumnFour;
		inventoryButtons[9].y      = (float) yStartPositionBottomRow;

		inventoryButtons[10].x      = (float) xStartPositionColumnFive;
		inventoryButtons[10].y      = (float) yStartPositionBottomRow;

		inventoryButtons[11].x      = (float) xStartPositionColumnSix;
		inventoryButtons[11].y      = (float) yStartPositionBottomRow;
		
		//////////////////////////////////////////////////////////////
		
		purchasingButtons[0].x      = (float) xStartPositionColumnOne;
		purchasingButtons[0].y      = (float) yStartPositionBottomRow;

		purchasingButtons[1].x      = (float) xStartPositionColumnTwo;
		purchasingButtons[1].y      = (float) yStartPositionBottomRow;

		purchasingButtons[2].x      = (float) xStartPositionColumnThree;
		purchasingButtons[2].y      = (float) yStartPositionBottomRow;

		purchasingButtons[3].x      = (float) xStartPositionColumnFour;
		purchasingButtons[3].y      = (float) yStartPositionBottomRow;

		purchasingButtons[4].x      = (float) xStartPositionColumnFive;
		purchasingButtons[4].y      = (float) yStartPositionBottomRow;

		purchasingButtons[5].x      = (float) xStartPositionColumnSix;
		purchasingButtons[5].y      = (float) yStartPositionBottomRow;
	}
	
	protected void closeStore() {
		Store.storeShouldBeRendered          = false;
		Store.playerWantsToEnterStore        = false;
		Store.shouldDisplayEnterStoreMessage = false;
	}

	/**
	 * 
	 * @param int        element
	 * @param GameObject player
	 */
	protected void selectAlternateInventoryObject(int element, GameObject player) {
		inventoryButtonIsPressed[element]          = true;
		Inventory.mouseIsClickingOnInventoryObject = true;
		Inventory.currentlySelectedInventoryObject = element;
		((Player) player).getInventory().setInventoryIsEquipped(true);
		Inventory.playClickSound                   = true;
		Fire.playSound                             = true;
	}
}
