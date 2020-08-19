package handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import cutscenes.CutScene;
import gameobjects.gamecharacters.players.Player;
import handlers.enemies.BossHandler;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
import missions.Mission;
import missions.MissionBlacksIsland;
import missions.MissionCauldron;
import missions.MissionChests;
import missions.MissionFinalFight;
import missions.MissionLegendOfTheSevenSwords;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import missions.MissionThePoint;
import missions.MissionTradinPost;
import missions.MissionWewa;
import ui.Win;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MissionHandler extends Mission {

	private MissionChests missionChests;
	private MissionRawBar missionRawBarPhaseOne;
	//private MissionRawBar missionRawBarPhaseTwo;
	//private MissionRawBar missionRawBarPhaseThree;
	private MissionStumpHole missionStumpHole;
	private MissionTradinPost missionTradinPost;
	private MissionWewa missionWewa;
	private MissionCauldron missionCauldron;
	private MissionThePoint missionThePoint;
	private MissionBlacksIsland missionBlacksIsland;
	//private MissionFinalFight missionFinalFight;

	/**
	 * This mission is always active.
	 * This used to be an actual mission, but now it only exists so the player can collect swords.
	 * If this mission is not here, swords will not be present in the game.
	 */
	private MissionLegendOfTheSevenSwords missionLegendOfTheSevenSwords;

	private boolean setUpRawBarMission = true;

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public MissionHandler(MyGame myGame) {
		missionChests                 = new MissionChests();
		missionLegendOfTheSevenSwords = new MissionLegendOfTheSevenSwords(myGame);
		initializeRawBarMission();
		missionStumpHole              = new MissionStumpHole();
		missionTradinPost             = new MissionTradinPost();
		missionWewa                   = new MissionWewa();
		missionCauldron               = new MissionCauldron();
		missionThePoint               = new MissionThePoint();
		missionBlacksIsland           = new MissionBlacksIsland();
		//missionFinalFight             = new MissionFinalFight();
	}

	private void initializeRawBarMission() {
		missionRawBarPhaseOne   = new MissionRawBar(MissionRawBar.NUMBER_OF_OYSTERS_NEEDED_PHASE_ONE, MissionRawBar.MAX_MISSION_TIME_PHASE_ONE);
		//missionRawBarPhaseTwo   = new MissionRawBar(MissionRawBar.NUMBER_OF_OYSTERS_NEEDED_PHASE_TWO, MissionRawBar.MAX_MISSION_TIME_PHASE_TWO);
		//missionRawBarPhaseThree = new MissionRawBar(MissionRawBar.NUMBER_OF_OYSTERS_NEEDED_PHASE_THREE, MissionRawBar.MAX_MISSION_TIME_PHASE_THREE);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHanlder mapHandler
	 */
	public void handleMissions(MyGame myGame, MapHandler mapHandler) {
		if (!CutScene.anyCutSceneIsInProgress) {
			// This mission is always active.
			missionLegendOfTheSevenSwords.updateMission(myGame, mapHandler);

			// Chest mission.
			if (!MissionChests.missionComplete) {
				missionChests.updateMission((Player) PlayerController.getCurrentPlayer(myGame), myGame, mapHandler);
			}

			// Go to Trading Post mission.
			if (MissionChests.chestMissionIsComplete) {
				missionTradinPost.updateMission(myGame, mapHandler);
			}

			// Player has collected the gun and is on his way to the Raw Bar.
			//if (Gun.hasBeenCollected && !MissionRawBar.locationMarkerHasBeenHit/*!MissionRawBar.phasesAreInProgress && !MissionRawBar.rawBarMissionComplete*/) {
			//if (Gun.hasBeenCollected && !MissionRawBar.phasesAreInProgress && !MissionRawBar.rawBarMissionComplete) {
			//MissionRawBar.updateLocationMarker(myGame);
			//}

			// Player has arrived at the Raw Bar, so set up Raw Bar mission.
			if (MissionRawBar.startMission && setUpRawBarMission ) {
				MissionRawBar.missionIsActive = true;
				setUpRawBarMission            = false;
			} 

			// Execute Raw Bar mission.
			if (MissionTradinPost.locationMarkerHasBeenHit && !MissionRawBar.rawBarMissionComplete) {
				handleRawBarMission(myGame);
			}

			// Raw Bar mission is complete.  Player is on his way to Stump Hole.
			if (MissionRawBar.rawBarMissionComplete && !MissionStumpHole.stumpHoleMissionComplete) {
				missionStumpHole.updateMission(myGame, mapHandler);
			} 

			if (MissionStumpHole.stumpHoleMissionComplete && BossLoader.boss[BossHandler.STUMP_HOLE].isDead() && !MissionWewa.wewaMissionComplete) {
				missionWewa.updateMission(myGame);
			}

			if (MissionWewa.wewaMissionComplete) {
				missionCauldron.updateMission(myGame);
			}

			if (MissionCauldron.missionCauldronComplete) {
				missionThePoint.updateMission(myGame);
			}

			if (MissionThePoint.missionThePointComplete) {
				missionBlacksIsland.updateMission(myGame);
			}

			// Keep this here, because without it, the missions keep breaking at this part and I can't figure out why.
			if (MissionRawBar.rawBarMissionComplete) {
				MissionRawBar.phasesAreInProgress = false;
			}

			/*
			if (MissionFinalFight.finalFightShouldBeSetup) {
				missionFinalFight.prepareForFinalFight(myGame.getGameObject(Player.PLAYER_ONE), myGame);
				MissionFinalFight.finalFightShouldBeSetup = false;
			}

			if (!Win.triggerWin) {
				missionFinalFight.updateFinalFight();
			} */
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void handleRawBarMission(MyGame myGame) {
		// Use this if we want to go through all phases.
		/*
		// Mission is the phases.
		if (missionRawBarPhaseTwo.isPhaseComplete()) {
			missionRawBarPhaseThree.updateMission(myGame);
		} else if (missionRawBarPhaseOne.isPhaseComplete()) {
			missionRawBarPhaseTwo.updateMission(myGame);
		} else {
			missionRawBarPhaseOne.updateMission(myGame);
		}

		// Entire mission is complete now.
		if (missionRawBarPhaseThree.isPhaseComplete()) {
			//System.exit(0);
			MissionRawBar.phasesAreInProgress   = false;
			MissionRawBar.rawBarMissionComplete = true;
			MissionRawBar.missionIsActive       = false;
		} */

		// Use this if we only want to go through one phase.
		if (!missionRawBarPhaseOne.isPhaseComplete()) {
			missionRawBarPhaseOne.updateMission(myGame);
		} 
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	public void renderMissions(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (!CutScene.anyCutSceneIsInProgress) {
			missionLegendOfTheSevenSwords.renderMission(batch, imageLoader, myGame);

			if (!MissionChests.missionComplete) {
				missionChests.renderMission(batch, imageLoader, myGame);
			}

			if (MissionChests.chestMissionIsComplete) {
				missionTradinPost.renderMission(batch, imageLoader, myGame);
			}

			if (MissionTradinPost.locationMarkerHasBeenHit && !MissionRawBar.rawBarMissionComplete) {
				renderRawBarMission(batch, imageLoader, myGame);
			}

			if (MissionRawBar.rawBarMissionComplete) {
				missionStumpHole.renderMission(batch, imageLoader, myGame);
			}

			if (MissionStumpHole.stumpHoleMissionComplete && BossLoader.boss[BossHandler.STUMP_HOLE].isDead() && !MissionWewa.wewaMissionComplete) {
				missionWewa.renderMission(batch, imageLoader);
			}

			// No need to render this because the cauldron is a game object, so it's rendered anyway.
			if (MissionWewa.wewaMissionComplete) {
				//missionCauldron.renderMission(batch, imageLoader);
			}

			if (MissionCauldron.missionCauldronComplete) {
				missionThePoint.renderMission(batch, imageLoader);
			}

			if (MissionThePoint.missionThePointComplete) {
				missionBlacksIsland.renderMission(batch, imageLoader);
			}
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	private void renderRawBarMission(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		// Use this if we want to render all three phases.
		/*
		if (missionRawBarPhaseTwo.isPhaseComplete()) {
			missionRawBarPhaseThree.renderMission(batch, imageLoader, myGame);
		} else if (missionRawBarPhaseOne.isPhaseComplete()) {
			missionRawBarPhaseTwo.renderMission(batch, imageLoader, myGame);
		} else {
			missionRawBarPhaseOne.renderMission(batch, imageLoader, myGame);
		}*/
		// Use this if we only want to render one phase.
		missionRawBarPhaseOne.renderMission(batch, imageLoader, myGame);
	}

	public static void resetMissions() {
		MissionRawBar.resetGame();
		MissionStumpHole.resetGame();
		MissionChests.resetGame();
		MissionTradinPost.resetGame();
		MissionWewa.resetGame();
		MissionCauldron.resetGame();
		MissionBlacksIsland.resetGame();
		MissionThePoint.resetGame();	
	}
}
