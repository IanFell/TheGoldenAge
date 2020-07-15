package input.controllers;

import com.mygdx.mygame.MyGame;

import controllers.GameStateController;
import debugging.Debugger;
import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.Stump;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.Gun;
import gameobjects.weapons.MagicPearl;
import gameobjects.weapons.Weapon;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.CollectibleHandler;
import handlers.collectibles.RumHandler;
import helpers.GameAttributeHelper;
import inventory.Inventory;
import loaders.GameObjectLoader;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import screens.Screens;
import screens.TitleScreen;
import store.Store;
import ui.AddedToInventory;
import ui.InventoryUi;

public class Arcade extends ControllerInput {
	
	/**
	 * Constructor.
	 */
	public Arcade() {
		super();
		this.BUTTON_ATTACK = 0;
		this.BUTTON_JUMP   = 1;
		this.BUTTON_UI     = 2;
		this.BUTTON_SELECT = 3;
		this.AXIS_LEFT_X   = 3; // -1 is left | +1 is right
		this.AXIS_LEFT_Y   = 2; // -1 is up | +1 is down
		this.AXIS_RIGHT_X  = 1; // -1 is left | +1 is right
		this.AXIS_RIGHT_Y  = 0; // -1 is up | +1 is down
		
		//System.exit(0);
	}
	
	/**
	 * 
	 * @param GameObject player
	 */
	@Override
	protected void pollMainFourButtons(GameObject player, MyGame myGame) {
		if(controller.getButton(BUTTON_ATTACK)) {
			switch (GameAttributeHelper.gameState) {
			case Screens.SPLASH_SCREEN:
				//GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
				break;
			case Screens.TITLE_SCREEN:
				if (TitleScreen.titleScreenHover == TitleScreen.PRESS_START) {
					GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
					Weapon.shouldPlaySwitchWeaponAudio = true;
				}
				else if (TitleScreen.titleScreenHover == TitleScreen.CONTROLS) {
					GameStateController.switchGameStates(myGame, Screens.CONTROLS_SCREEN);
					Weapon.shouldPlaySwitchWeaponAudio = true; 
				}
				break;
			case Screens.GAME_SCREEN:
				// Skip Intro.
				Debugger.skipIntroCutscene = true;
				
				if (!Inventory.allInventoryShouldBeRendered) {
					Player.isJumping = true;
				}

			}
		}
	}
}
