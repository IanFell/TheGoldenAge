package controllers;

import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import screens.Screens;

/**
 * Handles switching between game states.
 * 
 * @author Fabulous Fellini
 *
 */
public class GameStateController {

	/**
	 * 
	 * @param MyGame myGame
	 * @param int    newGameState
	 */
	public static void switchGameStates(MyGame myGame, int newGameState) {
		switch (newGameState) {
		case Screens.GAME_SCREEN:
			// Use our original game screen so current state is restored.
			myGame.setScreen(myGame.getGameScreen());
			GameAttributeHelper.gameState = Screens.GAME_SCREEN; 
			break;
		case Screens.TITLE_SCREEN:
			// Use our original title screen so current state is restored.
			myGame.setScreen(myGame.getTitleScreen());
			GameAttributeHelper.gameState = Screens.TITLE_SCREEN;
			break;
		case Screens.CONTROLS_SCREEN:
			myGame.setScreen(myGame.getControlsScreen());
			GameAttributeHelper.gameState = Screens.CONTROLS_SCREEN;
		}
	}
}
