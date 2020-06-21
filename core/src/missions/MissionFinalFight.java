package missions;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MissionFinalFight extends Mission {

	private boolean enemiesHaveBeenSetAroundPlayer = false;

	public static boolean finalFightShouldBeSetup = false;

	/**
	 * 
	 * @param GameObject player
	 * @param MyGame     myGame
	 */
	public void prepareForFinalFight(GameObject player, MyGame myGame) {
		if (!enemiesHaveBeenSetAroundPlayer) {
			myGame.getGameScreen().enemyHandler.setEnemiesToPlayer(player);
			myGame.getGameScreen().gruntHandler.setGruntsToPlayer(player);
			myGame.getGameScreen().giantHandler.setGiantsToPlayer(player);	
			enemiesHaveBeenSetAroundPlayer = true;
		}
	}
}