package missions;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import ui.Win;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MissionFinalFight extends Mission {

	private boolean enemiesHaveBeenSetAroundPlayer = false;

	public static boolean finalFightShouldBeSetup = false;

	private final int FIGHT_TIME = 500;

	/**
	 * 
	 * @param GameObject player
	 * @param MyGame     myGame
	 */
	public void prepareForFinalFight(GameObject player, MyGame myGame) {
		if (!enemiesHaveBeenSetAroundPlayer) {
			myGame.getGameScreen().enemyHandler.setEnemiesToPlayer(player);
			myGame.getGameScreen().gruntHandler.setGruntsToPlayer(player);
			//myGame.getGameScreen().giantHandler.setGiantsToPlayer(player);	
			enemiesHaveBeenSetAroundPlayer = true;
		}
	}

	public void updateFinalFight() {
		if (enemiesHaveBeenSetAroundPlayer) {
			//Player.isInvincible = true;
			timer++;
			if (timer > FIGHT_TIME) {
				Win.triggerWin = true;
			}
		}
	}
}
