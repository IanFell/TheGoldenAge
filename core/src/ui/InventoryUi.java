package ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.weapons.BirdWeapon;
import gameobjects.weapons.Dagger;
import gameobjects.weapons.Gun;
import gameobjects.weapons.LegendSword;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Paw;
import handlers.InputHandler;
import input.computer.Mouse;
import input.controllers.LogitechF310;
import loaders.ImageLoader;
import physics.Lighting.Fire;
import screens.GameScreen;
import screens.Screens;

/**
 * Extend Screens so we can use camera easier.
 * 
 * @author Fabulous Fellini
 *
 */
public class InventoryUi extends Screens {

	public static int clickedObject = 0;
	
	public static void resetGame() {
		clickedObject = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public InventoryUi(MyGame myGame) {
		super(myGame);
	}

	/**
	 * 
	 * @param SpriteBatch            batch
	 * @param ImageLoader            imageLoader
	 * @param ArrayList <GameObject> inventory
	 * @param boolean                inventoryIsEquipped
	 * @param int                    currentlySelectedInventoryObject
	 * @param boolean                allInventoryShouldBeRendered
	 * @param Fire                   fire
	 * @param boolean                mouseIsClickingOnInventoryObject
	 * @param Rectangle              rectangle
	 */
	public void renderInventoryUi(
			SpriteBatch batch, 
			ImageLoader imageLoader, 
			ArrayList <GameObject> inventory,
			boolean inventoryIsEquipped,
			int currentlySelectedInventoryObject,
			boolean allInventoryShouldBeRendered,
			Fire fire,
			boolean mouseIsClickingOnInventoryObject,
			Rectangle rectangle
			) {
		if (inventory.size() > 0) {
			if (inventoryIsEquipped) {
				if (currentlySelectedInventoryObject < inventory.size()) {
					inventory.get(currentlySelectedInventoryObject).renderObject(batch, imageLoader);
				}
			}
		}
		if (allInventoryShouldBeRendered) {
			batch.draw(
					imageLoader.blackSquare,
					camera.position.x - getViewportWidth() / denominatorOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
					camera.viewportWidth, 
					-camera.viewportHeight
					);
			renderScrolls(batch, imageLoader, myGame);
			int borderShrinkOffset = 1;
			if (InputHandler.inputType == InputHandler.INPUT_ARCADE) {
				batch.draw(
						imageLoader.arcadeInventory,
						camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
						(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
						camera.viewportWidth - borderShrinkOffset * 2, 
						-camera.viewportHeight
						);
			} 
			else if (InputHandler.inputType == InputHandler.INPUT_CONTROLLER) {
				batch.draw(
						imageLoader.inventoryScreen,
						camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
						(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
						camera.viewportWidth - borderShrinkOffset * 2, 
						-camera.viewportHeight
						);
			}
			else {
				batch.draw(
						imageLoader.inventoryComputer,
						camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
						(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
						camera.viewportWidth - borderShrinkOffset * 2, 
						-camera.viewportHeight
						);
			}
			renderInventoryDisplay(batch, imageLoader, inventory);
			// White square that flashes when player clicks on an inventory square.
			drawClickHover(batch, imageLoader, clickedObject, rectangle);
			renderInventoryNames(batch, imageLoader, inventory);

			if (mouseIsClickingOnInventoryObject) {
				for (int i = 0; i < Mouse.inventoryButtonIsPressed.length; i++) {
					if (Mouse.inventoryButtonIsPressed[i] || LogitechF310.inventoryButtonIsPressed[i]) {
						clickedObject = i;
					}
				}
			}

			if (inventory.size() > 0) {
				fire.setX(rectangle.x + 1.5f);
				fire.setY(rectangle.y);
				fire.setWidth(rectangle.getWidth() / 6.5f);
				fire.setHeight(rectangle.getHeight() / 2);
				fire.renderObject(batch, imageLoader);
			}

			//renderUiNavigationBar(imageLoader.inventoryNavigationBar, batch);
		}
	}

	/**
	 * Draws the white square that flashes when player clicks on an inventory square.
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param int         hoverValue
	 * @param Rectangle   rectangle
	 */
	private void drawClickHover(SpriteBatch batch, ImageLoader imageLoader, int hoverValue, Rectangle rectangle) {
		int borderShrinkOffset = 1;
		float yOffsetTopRow    = 6f;
		float yOffsetBottomRow = 1.45f; 
		switch (hoverValue) {
		case 0:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetTopRow;
			break;
		case 1:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 4.2f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetTopRow;
			break;
		case 2:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 7.9f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetTopRow;
			break;
		case 3:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 11.8f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetTopRow;
			break;
		case 4:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 15.75f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetTopRow;
			break;
		case 5:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 19.6f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetTopRow;
			break;
		case 6:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetBottomRow;
			break;
		case 7:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 4.2f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetBottomRow;
			break;
		case 8:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 7.9f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetBottomRow;
			break;
		case 9:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 11.8f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetBottomRow;
			break;
		case 10:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 15.75f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetBottomRow;
			break;
		case 11:
			rectangle.x = camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset + 0.7f + 19.6f;
			rectangle.y = (camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight - yOffsetBottomRow;
			break;
		}

		float compensatedWidth = rectangle.width;
		if (hoverValue == 0 || hoverValue == 6) {
			compensatedWidth = rectangle.width - 0.1f;
		}
		if (hoverValue == 1 || hoverValue == 7) {
			compensatedWidth = rectangle.width - 0.6f;
		}
		else if (hoverValue == 2 || hoverValue == 8) {
			compensatedWidth = rectangle.width - 0.4f;
		}
		else if (hoverValue == 3 || hoverValue == 9) {
			compensatedWidth = rectangle.width - 0.4f;
		}
		else if (hoverValue == 4 || hoverValue == 10) {
			compensatedWidth = rectangle.width - 0.45f;
		}
		else if (hoverValue == 5 || hoverValue == 11) {
			compensatedWidth = rectangle.width - 0.35f;
		}
		batch.draw(
				imageLoader.transparentSquare,
				rectangle.x,
				rectangle.y - 0.35f,
				compensatedWidth,
				-rectangle.height + 0.2f
				);
	}

	/**
	 * 
	 * @param SpriteBatch            batch
	 * @param ImageLoader            imageLoader
	 * @param ArrayList <GameObject> inventory
	 */
	public void renderInventoryNames(SpriteBatch batch, ImageLoader imageLoader, ArrayList <GameObject> inventory) {
		if (inventory.size() > 0) {
			for (int i = 0; i < inventory.size(); i++) {
				float xPos   = inventory.get(i).getX() - 1.5f;
				float yPos   = inventory.get(i).getY() + 1.3f;
				float width  = 4.0f;
				float height = -1.0f;
				if (inventory.get(i) instanceof LegendSword) {
					batch.draw(imageLoader.legendSwordUi, xPos, yPos, width, height);
				}
				if (inventory.get(i) instanceof MagicPearl) {
					batch.draw(imageLoader.magicPearlUi, xPos, yPos, width, height);
				}
				if (inventory.get(i) instanceof Gun) {
					batch.draw(imageLoader.gunUi, xPos, yPos, width, height);
				}
				if (inventory.get(i) instanceof BirdWeapon) {
					batch.draw(imageLoader.woodyUi, xPos, yPos, width, height);
				}
				if (inventory.get(i) instanceof Paw) {
					batch.draw(imageLoader.pawUi, xPos, yPos, width, height);
				}
				if (inventory.get(i) instanceof Dagger) {
					batch.draw(imageLoader.daggerUi, xPos + 0.5f, yPos, width, height);
				}
			}
		}
	}

	/**
	 * Renders the inventory objects over their inventory sqaures.
	 * 
	 * @param SpriteBatch            batch
	 * @param ImageLoader            imageLoader
	 * @param ArrayList <GameObject> inventory
	 */
	public void renderInventoryDisplay(SpriteBatch batch, ImageLoader imageLoader, ArrayList <GameObject> inventory) {
		float xStartPosition = GameScreen.camera.position.x - GameScreen.cameraWidth / 2 - 5.5f;
		float offset         = 0.25f;
		float yStartPosition = GameScreen.camera.position.y + offset;
		float x              = xStartPosition;
		float y              = GameScreen.camera.position.y + offset;
		boolean resetX       = true;
		if (inventory.size() > 0) {
			for (int i = 0; i < inventory.size(); i++) {
				inventory.get(i).setX(x);
				inventory.get(i).setY(y);
				inventory.get(i).renderObject(batch, imageLoader);
				x += 4f;

				if (i > 4 && resetX) {
					x = xStartPosition;
					y = yStartPosition + 4.6f;
					resetX = false;
				}
			}
		}
	}
}
