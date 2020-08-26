package loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Class to hold and handle our images.
 * 
 * @author Fabulous Fellini
 *
 */
public class ImageLoader {

	public Texture[] oarRight = new Texture[6];
	public Texture[] oarLeft  = new Texture[6];
	public Texture[] oarUp    = new Texture[3];
	public Texture[] oarDown  = new Texture[3];

	// Title screen.
	public Texture titleScreen;

	// Collectibles.
	public Texture tenHearts;
	public Texture chestClosed;
	public Texture chestOpen;
	public Texture heart;
	public Texture whiteHeart;
	public Texture heartShadow;
	public Texture pawShadow;
	public Texture rum;
	public Texture rumShadow;
	public Texture ammo;
	public Texture ammoShadow;
	public Texture treasureMapRight;
	public Texture treasureMapShadow;

	// Boat objects.
	public Texture boatSide;
	public Texture boatUp;
	public Texture boatDown;

	// Sign objects.
	public Texture sign;
	public Texture[] townSigns = new Texture[7];

	// Nature Objects.
	public Texture[] shockPlants = new Texture[8];
	public Texture poisonPlantOne;
	public Texture poisonPlantTwo;
	public Texture poisonPlantThree;
	public Texture poisonPlantFour;
	public Texture poisonPlantFive;
	public Texture poisonPlantSix;
	public Texture poisonPlantSeven;
	public Texture poisonPlantEight;
	public Texture poisonPlantNine;
	public Texture poisonPlantTen;
	public Texture bubble;
	public Texture tunnel;
	public Texture hole;
	public Texture rock;
	public Texture drSuessTree;
	public Texture palmTree;
	public Texture palmTreeThree;
	public Texture plant;
	public Texture logs;
	public Texture light;
	public Texture rain;
	public Texture shadow;
	public Texture cloud;
	public Texture stump;
	public Texture sky;
	public Texture feather;
	public Texture quickSand;

	// Weapon Objects.
	public Texture daggerShadow;
	public Texture daggerUp;
	public Texture daggerDown;
	public Texture daggerLeft;
	public Texture daggerRight;
	public Texture paw;
	public Texture bullet;
	public Texture magicPearl;
	public Texture gunRight;
	public Texture gunLeft;
	public Texture gunUp;
	public Texture gunDown;
	public Texture legendSwordRed;
	public Texture legendSwordBlue;
	public Texture legendSwordGreen;
	public Texture legendSwordYellow;
	public Texture legendSwordOrange;
	public Texture legendSwordPurple;
	public Texture legendSwordPink;
	public Texture legendSwordRedHalf;
	public Texture legendSwordBlueHalf;
	public Texture legendSwordGreenHalf;
	public Texture legendSwordYellowHalf;
	public Texture legendSwordOrangeHalf;
	public Texture legendSwordPurpleHalf;
	public Texture legendSwordPinkHalf;
	public Texture legendSwordRainbow;
	public Texture oyster;
	public Texture oysterShadow;
	public Texture cannonBall;
	public Texture cannonLeft;
	public Texture cannonRight;
	public Texture arrowRight;
	public Texture arrowLeft;
	public Texture arrowUp;
	public Texture bowRight;
	public Texture bowLeft;
	public Texture bowUp;
	public Texture arrowShadowRight;
	public Texture arrowShadowLeft;
	public Texture arrowShadowUp;

	// Structure Objects.
	public Texture scallopCove;
	public Texture bar;
	public Texture barShadow;
	public Texture teePee;
	public Texture rawbar;
	public Texture lightHouse;
	public Texture pigglywiggly;
	public Texture slaveHut;
	public Texture gruntHut;
	public Texture dockLeft;
	public Texture dockRight;
	public Texture tradingPost;
	public Texture fortTop;
	public Texture fortSide;

	// Structure Shadows.
	public Texture lightHouseShadow;
	public Texture tradingPostShadow;
	public Texture rawBarShadow;
	public Texture pigglyWigglyShadow;
	public Texture fortShadow;

	// Effects Objects.
	public Texture dustParticleOne;
	public Texture dustParticleTwo;
	public Texture dustParticleThree;
	public Texture poisonCover;

	// Cutscene objects.
	//public Texture[] cutSceneDialogueOne = new Texture[9];
	public Texture cutsceneRum;
	public Texture cutsceneJollyRoger;
	public Texture cutsceneCutthroat;
	public Texture cutsceneFarzenplank;
	public Texture mapMessage;
	public Texture cauldronMessage;
	public Texture birdMessage;
	public Texture enemyMessage;

	// Cutscene background images.
	public Texture cutSceneBackGroundImageMap;
	public Texture cutSceneBackGroundImageCauldron;
	public Texture cutSceneBackGroundImageCutthroat;
	public Texture cutSceneBackGroundImageFarzenplank;
	public Texture cutSceneBackGroundImageBird;
	public Texture cutsceneBackGroundImageEnemy;

	// Map objects.
	public Texture worldMapReal;
	public Texture worldMapFake;
	public Texture worldMapMexicoBeach;
	public Texture worldMapApalachicola;
	public Texture worldMapStGeorge;
	public Texture worldMapPortStJoe;
	public Texture worldMapCapeSanBlas;
	public Texture worldMapThePoint;
	public Texture worldMapWewa;

	// Mission objects.
	public Texture cauldron;
	public Texture sun;
	public Texture legendOfTheSevenSwordsBeginMissionText;
	public Texture missionTransparentBlueSquare;
	public Texture rightHand;
	public Texture leftHand;
	public Texture rightHandClosed;
	public Texture leftHandClosed;
	public Texture fishLeft;
	public Texture fishRight;
	public Texture playerRight;
	public Texture playerLeft;
	public Texture transition;

	// Tile objects.
	public Texture grassTileOne;
	public Texture sandTile;
	public Texture sandTileHorizontalPath;
	public Texture sandTileLeftDownPath;
	public Texture sandTileVerticalPath;
	public Texture sandTileTopRightPath;
	public Texture sandTileCrossPath;
	public Texture waterTileOne;
	public Texture waterTileTwo;
	public Texture waterTileThree;
	public Texture nightTimeShader;

	// UI objects.
	public Texture lootAddedToInventory;
	public Texture objectiveBackground;
	public Texture arcadeMap;
	public Texture arcadeInventory;
	public Texture oysterMeterUi;
	public Texture featherMeterUi;
	public Texture timeMeterUi;
	public Texture playGame;
	public Texture win;
	public Texture gameOver;
	public Texture daggerUi;
	public Texture pawUi;
	public Texture controlsUiBackground;
	public Texture credits;
	public Texture controls;
	public Texture lootAlternate;
	public Texture outOfAmmo;
	public Texture pressStart;
	public Texture transparentSquare;
	public Texture gunUi;
	public Texture magicPearlUi;
	public Texture woodyUi;
	public Texture healthUi;
	public Texture ammoUi;
	public Texture rumUi;
	public Texture legendSwordUi;
	public Texture addedToInventory;
	public Texture border;
	public Texture skullShadow;
	public Texture inventoryNavigationBar;
	public Texture mapNavigationBar;
	public Texture inventoryScreen;
	public Texture storeUi;
	public Texture inventoryLabel;
	public Texture locationSkull;
	public Texture attackBird;
	public Texture gamePaused;
	public Texture enemyHealthMeterBase;
	public Texture enemyHealthMeterBlack;
	public Texture oysterMeterBase;
	public Texture timeMeterBase;
	public Texture controlsUi;

	// Objective UI objects.
	public Texture objectiveCollectTheMapAtThePoint;
	public Texture objectiveFindTheTreasureAtBlacksIsland;
	public Texture objectiveCollectTheBird;
	public Texture objectiveAmmoUnlocked;
	public Texture objectiveMagicPearlUnlocked;
	public Texture objectiveFindTheCauldron;
	public Texture objectiveGoToWewa;
	public Texture objectiveTryAgain;
	public Texture objectiveRawBar;
	public Texture objectiveStumpHole;
	public Texture objectiveTradinPost;
	public Texture objectiveCollectLoot;
	public Texture objectiveCollectOysters;
	public Texture objectiveCollectFeathers;
	public Texture objectiveBuyTheGun;
	public Texture objectiveKillTheBoss;
	public Texture objectiveEnterTheTradingPost;
	public Texture objectiveEnterStore;
	public Texture objectiveExitStore;
	public Texture objectiveNotEnoughLoot;

	// Player name UI labels.
	public Texture jollyRogerUiNameLabel;
	public Texture blackBeardUiNameLabel;
	public Texture pegLegUiNameLabel;

	// Logo objects.
	public Texture splashScreenLogo;
	public Texture titleScreenLogo;
	public Texture icon;

	// Debugging objects.
	public Texture whiteSquare;
	public Texture blackSquare;
	public Texture redSquare;

	// Character objects.
	public Texture hanging;
	public Texture roast;
	public Texture hangingShadow;
	public Texture knightLeft;
	public Texture knightRight;
	public Texture bossLeft01;
	public Texture bossLeft02;
	public Texture bossRight01;
	public Texture bossRight02;

	// Number objects.
	public Texture[] numberBlack = new Texture[10];
	public Texture[] numberWhite = new Texture[10];

	public void init() {

		// Oar.
		oarRight[0] = new Texture(Gdx.files.internal("artwork/oars/right/OarRight01.png"));
		oarRight[1] = new Texture(Gdx.files.internal("artwork/oars/right/OarRight02.png"));
		oarRight[2] = new Texture(Gdx.files.internal("artwork/oars/right/OarRight03.png"));
		oarRight[3] = new Texture(Gdx.files.internal("artwork/oars/right/OarRight04.png"));
		oarRight[4] = new Texture(Gdx.files.internal("artwork/oars/right/OarRight05.png"));
		oarRight[5] = new Texture(Gdx.files.internal("artwork/oars/right/OarRight06.png"));
		oarLeft[0]  = new Texture(Gdx.files.internal("artwork/oars/left/OarLeft01.png"));
		oarLeft[1]  = new Texture(Gdx.files.internal("artwork/oars/left/OarLeft02.png"));
		oarLeft[2]  = new Texture(Gdx.files.internal("artwork/oars/left/OarLeft03.png"));
		oarLeft[3]  = new Texture(Gdx.files.internal("artwork/oars/left/OarLeft04.png"));
		oarLeft[4]  = new Texture(Gdx.files.internal("artwork/oars/left/OarLeft05.png"));
		oarLeft[5]  = new Texture(Gdx.files.internal("artwork/oars/left/OarLeft06.png"));
		oarUp[0]    = new Texture(Gdx.files.internal("artwork/oars/up/OarUp01.png"));
		oarUp[1]    = new Texture(Gdx.files.internal("artwork/oars/up/OarUp02.png"));
		oarUp[2]    = new Texture(Gdx.files.internal("artwork/oars/up/OarUp03.png"));
		oarDown[0]  = new Texture(Gdx.files.internal("artwork/oars/down/OarDown01.png"));
		oarDown[1]  = new Texture(Gdx.files.internal("artwork/oars/down/OarDown02.png"));
		oarDown[2]  = new Texture(Gdx.files.internal("artwork/oars/down/OarDown03.png"));

		// Title Screen.
		titleScreen = new Texture(Gdx.files.internal("artwork/titlescreen/goldenage.png"));

		// Collectibles.
		tenHearts         = new Texture(Gdx.files.internal("artwork/collectibles/TenHearts.png"));
		treasureMapShadow = new Texture(Gdx.files.internal("artwork/collectibles/MapShadow.png"));
		treasureMapRight  = new Texture(Gdx.files.internal("artwork/collectibles/TreasureMapTextWhole.png"));
		chestClosed       = new Texture(Gdx.files.internal("artwork/collectibles/Chest.png"));
		chestOpen         = new Texture(Gdx.files.internal("artwork/collectibles/ChestOpen.png"));
		heart             = new Texture(Gdx.files.internal("artwork/collectibles/Heart.png"));
		whiteHeart        = new Texture(Gdx.files.internal("artwork/collectibles/WhiteHeart.png"));
		heartShadow       = new Texture(Gdx.files.internal("artwork/collectibles/HeartShadow.png"));
		pawShadow         = new Texture(Gdx.files.internal("artwork/weapons/paw/PawShadow.png"));
		rum               = new Texture(Gdx.files.internal("artwork/collectibles/Rum_Alternate.png"));
		rumShadow         = new Texture(Gdx.files.internal("artwork/collectibles/RumShadow.png"));
		ammo              = new Texture(Gdx.files.internal("artwork/collectibles/Ammo.png"));
		ammoShadow        = new Texture(Gdx.files.internal("artwork/collectibles/AmmoShadow.png"));

		// Boat objects.
		boatSide = new Texture(Gdx.files.internal("artwork/boat/BOAT_NEW.png"));
		boatUp   = new Texture(Gdx.files.internal("artwork/boat/BoatUp.png"));
		boatDown = new Texture(Gdx.files.internal("artwork/boat/BoatDown.png"));

		// Nature Objects.
		poisonPlantOne   = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP1.png"));
		poisonPlantTwo   = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP2.png"));
		poisonPlantThree = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP3.png"));
		poisonPlantFour  = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP4.png"));
		poisonPlantFive  = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP5.png"));
		poisonPlantSix   = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP6.png"));
		poisonPlantSeven = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP7.png"));
		poisonPlantEight = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP8.png"));
		poisonPlantNine  = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP9.png"));
		poisonPlantTen   = new Texture(Gdx.files.internal("artwork/nature/poisonplants/PP10.png"));

		shockPlants[0] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant1.png"));
		shockPlants[1] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant2.png"));
		shockPlants[2] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant3.png"));
		shockPlants[3] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant4.png"));
		shockPlants[4] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant5.png"));
		shockPlants[5] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant6.png"));
		shockPlants[6] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant7.png"));
		shockPlants[7] = new Texture(Gdx.files.internal("artwork/nature/shockplants/ShockPlant8.png"));

		bubble         = new Texture(Gdx.files.internal("artwork/nature/Bubble.png"));
		tunnel         = new Texture(Gdx.files.internal("artwork/nature/TunnelMovement.png"));
		hole           = new Texture(Gdx.files.internal("artwork/nature/Hole.png"));
		rock 		   = new Texture(Gdx.files.internal("artwork/nature/Rock.png"));
		logs           = new Texture(Gdx.files.internal("artwork/nature/Logs.png"));
		palmTreeThree  = new Texture(Gdx.files.internal("artwork/nature/PalmTreeOneAlternate_April2020.png"));
		palmTree       = new Texture(Gdx.files.internal("artwork/nature/PalmTreeOne_April2020.png"));
		plant          = new Texture(Gdx.files.internal("artwork/nature/PlantApril2020.png"));
		drSuessTree    = new Texture(Gdx.files.internal("artwork/nature/DrSuessTree2020.png"));
		light          = new Texture(Gdx.files.internal("artwork/nature/Lighting.png"));
		rain 		   = new Texture(Gdx.files.internal("artwork/nature/Rain.png"));
		shadow         = new Texture(Gdx.files.internal("artwork/nature/Shadow.png"));
		cloud          = new Texture(Gdx.files.internal("artwork/nature/Cloud.png"));
		stump          = new Texture(Gdx.files.internal("artwork/nature/Stump.png"));
		sky            = new Texture(Gdx.files.internal("artwork/nature/Sky.png"));
		feather        = new Texture(Gdx.files.internal("artwork/nature/Feather.png"));
		quickSand      = new Texture(Gdx.files.internal("artwork/nature/QuickSand.png")); 

		// Weapon Objects.
		daggerShadow          = new Texture(Gdx.files.internal("artwork/weapons/dagger/DaggerShadow.png"));
		daggerUp              = new Texture(Gdx.files.internal("artwork/weapons/dagger/DaggerUp.png"));
		daggerDown            = new Texture(Gdx.files.internal("artwork/weapons/dagger/DaggerDown.png"));
		daggerLeft            = new Texture(Gdx.files.internal("artwork/weapons/dagger/DaggerLeft.png"));
		daggerRight           = new Texture(Gdx.files.internal("artwork/weapons/dagger/DaggerRight.png"));
		bullet                = new Texture(Gdx.files.internal("artwork/weapons/bullet/Bullet.png"));
		paw                   = new Texture(Gdx.files.internal("artwork/weapons/paw/Paw.png"));
		magicPearl            = new Texture(Gdx.files.internal("artwork/weapons/oyster/MagicPearl.png"));
		gunRight   			  = new Texture(Gdx.files.internal("artwork/weapons/gun/Right.png"));
		gunLeft               = new Texture(Gdx.files.internal("artwork/weapons/gun/Left.png"));
		gunUp                 = new Texture(Gdx.files.internal("artwork/weapons/gun/Up.png"));
		gunDown               = new Texture(Gdx.files.internal("artwork/weapons/gun/Down.png"));
		legendSwordRedHalf    = new Texture(Gdx.files.internal("artwork/weapons/sword/LegendSwordRedHalf.png"));
		legendSwordBlueHalf   = new Texture(Gdx.files.internal("artwork/weapons/sword/LegendSwordBlueHalf.png"));
		legendSwordGreenHalf  = new Texture(Gdx.files.internal("artwork/weapons/sword/LegendSwordGreenHalf.png"));
		legendSwordYellowHalf = new Texture(Gdx.files.internal("artwork/weapons/sword/LegendSwordYellowHalf.png"));
		legendSwordOrangeHalf = new Texture(Gdx.files.internal("artwork/weapons/sword/LegendSwordOrangeHalf.png"));
		legendSwordPurpleHalf = new Texture(Gdx.files.internal("artwork/weapons/sword/LegendSwordPurpleHalf.png"));
		legendSwordPinkHalf   = new Texture(Gdx.files.internal("artwork/weapons/sword/LegendSwordPinkHalf.png"));
		legendSwordRainbow    = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordRainbow.png"));
		legendSwordRed        = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordRed.png"));
		legendSwordBlue       = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordBlue.png"));
		legendSwordGreen      = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordGreen.png"));
		legendSwordYellow     = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordYellow.png"));
		legendSwordOrange     = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordOrange.png"));
		legendSwordPurple     = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordPurple.png"));
		legendSwordPink       = new Texture(Gdx.files.internal("artwork/weapons/sword/SwordPink.png"));
		oyster                = new Texture(Gdx.files.internal("artwork/weapons/oyster/Oyster.png"));
		oysterShadow          = new Texture(Gdx.files.internal("artwork/weapons/oyster/OysterShadow.png"));
		cannonBall            = new Texture(Gdx.files.internal("artwork/weapons/cannon/CannonBall.png"));
		cannonLeft            = new Texture(Gdx.files.internal("artwork/weapons/cannon/CannonLeft.png"));
		cannonRight           = new Texture(Gdx.files.internal("artwork/weapons/cannon/CannonRight.png"));
		arrowRight            = new Texture(Gdx.files.internal("artwork/weapons/arrows/Arrow.png"));
		arrowLeft             = new Texture(Gdx.files.internal("artwork/weapons/arrows/ArrowLeft.png"));
		arrowUp               = new Texture(Gdx.files.internal("artwork/weapons/arrows/ArrowUp.png"));
		bowRight              = new Texture(Gdx.files.internal("artwork/weapons/bows/BowRight.png"));
		bowLeft               = new Texture(Gdx.files.internal("artwork/weapons/bows/BowLeft.png"));
		bowUp                 = new Texture(Gdx.files.internal("artwork/weapons/bows/BowUp.png"));
		arrowShadowRight      = new Texture(Gdx.files.internal("artwork/weapons/arrows/ArrowShadowRight.png"));
		arrowShadowLeft       = new Texture(Gdx.files.internal("artwork/weapons/arrows/ArrowShadowLeft.png"));
		arrowShadowUp         = new Texture(Gdx.files.internal("artwork/weapons/arrows/ArrowShadowUp.png"));

		// Structure Objects.
		scallopCove  = new Texture(Gdx.files.internal("artwork/structures/ScallopCove.png"));
		bar          = new Texture(Gdx.files.internal("artwork/structures/Bar.png"));
		barShadow    = new Texture(Gdx.files.internal("artwork/structures/BarShadow.png"));
		slaveHut     = new Texture(Gdx.files.internal("artwork/structures/SlaveHut.png"));
		rawbar		 = new Texture(Gdx.files.internal("artwork/structures/RawBar3d.png"));
		lightHouse   = new Texture(Gdx.files.internal("artwork/structures/LightHouse.png"));
		pigglywiggly = new Texture(Gdx.files.internal("artwork/structures/PigglyWiggly3D.png"));
		teePee   	 = new Texture(Gdx.files.internal("artwork/structures/TeePee3D.png"));
		gruntHut     = new Texture(Gdx.files.internal("artwork/structures/GruntHut.png"));
		dockLeft   	 = new Texture(Gdx.files.internal("artwork/structures/DockLeft.png"));
		dockRight    = new Texture(Gdx.files.internal("artwork/structures/DockRight.png"));
		tradingPost  = new Texture(Gdx.files.internal("artwork/structures/TradingPost3d.png"));
		fortTop      = new Texture(Gdx.files.internal("artwork/structures/fort/FortTop.png"));
		fortSide     = new Texture(Gdx.files.internal("artwork/structures/fort/FortSide.png"));

		// Structure Shadows.
		fortShadow         = new Texture(Gdx.files.internal("artwork/structures/shadows/FortShadow.png"));
		lightHouseShadow   = new Texture(Gdx.files.internal("artwork/structures/shadows/LightHouseShadowVertical.png"));
		tradingPostShadow  = new Texture(Gdx.files.internal("artwork/structures/shadows/TradingPost3dShadow.png"));
		rawBarShadow       = new Texture(Gdx.files.internal("artwork/structures/shadows/RawBar3dShadow.png"));
		pigglyWigglyShadow = new Texture(Gdx.files.internal("artwork/structures/shadows/PigglyWiggly3DShadow.png"));

		// Effects Objects.
		poisonCover       = new Texture(Gdx.files.internal("artwork/effects/poison/PoisonCover.png"));
		dustParticleOne   = new Texture(Gdx.files.internal("artwork/effects/dust/DustParticleOne.png"));
		dustParticleTwo   = new Texture(Gdx.files.internal("artwork/effects/dust/DustParticleTwo.png"));
		dustParticleThree = new Texture(Gdx.files.internal("artwork/effects/dust/DustParticleThree.png"));

		// Cutscene objects.
		cutsceneRum         = new Texture(Gdx.files.internal("artwork/cutscenes/Confidence.png"));
		enemyMessage        = new Texture(Gdx.files.internal("artwork/cutscenes/EnemyMessage.png"));
		mapMessage          = new Texture(Gdx.files.internal("artwork/cutscenes/MapMessage.png"));
		birdMessage         = new Texture(Gdx.files.internal("artwork/cutscenes/BirdMessage.png"));
		cauldronMessage     = new Texture(Gdx.files.internal("artwork/cutscenes/CauldronMessage.png"));
		cutsceneJollyRoger  = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_JollyRoger.png"));
		cutsceneCutthroat   = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_Cutthroat.png"));
		cutsceneFarzenplank = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_Farzenplank.png"));

		//Cutscene background images.
		cutsceneBackGroundImageEnemy       = new Texture(Gdx.files.internal("artwork/cutscenes/EnemyCutSceneImage.png"));
		cutSceneBackGroundImageMap         = new Texture(Gdx.files.internal("artwork/cutscenes/MapCutSceneImage.png"));
		cutSceneBackGroundImageCauldron    = new Texture(Gdx.files.internal("artwork/cutscenes/CauldronCutSceneImage.png"));
		cutSceneBackGroundImageBird        = new Texture(Gdx.files.internal("artwork/cutscenes/BirdCutSceneImage.png"));
		cutSceneBackGroundImageCutthroat   = new Texture(Gdx.files.internal("artwork/cutscenes/CutthroatCutSceneImage.png"));
		cutSceneBackGroundImageFarzenplank = new Texture(Gdx.files.internal("artwork/cutscenes/FarzenplankCutSceneImage.png"));
		/*
		cutSceneDialogueOne[0] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_One.png"));
		cutSceneDialogueOne[1] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Two.png"));
		cutSceneDialogueOne[2] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Three.png"));
		cutSceneDialogueOne[3] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Four.png"));
		cutSceneDialogueOne[4] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Five.png"));
		cutSceneDialogueOne[5] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Six.png"));
		cutSceneDialogueOne[6] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Seven.png"));
		cutSceneDialogueOne[7] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Eight.png"));
		cutSceneDialogueOne[8] = new Texture(Gdx.files.internal("artwork/cutscenes/Cutscene_One_Dialogue_Nine.png")); */

		// Map objects.
		worldMapMexicoBeach  = new Texture(Gdx.files.internal("artwork/maps/WorldMapMexicoBeach.png"));
		worldMapThePoint	 = new Texture(Gdx.files.internal("artwork/maps/WorldMapThePoint.png"));
		worldMapPortStJoe	 = new Texture(Gdx.files.internal("artwork/maps/WorldMapPortStJoe.png"));
		worldMapCapeSanBlas	 = new Texture(Gdx.files.internal("artwork/maps/WorldMapCapeSanBlas.png"));
		worldMapApalachicola = new Texture(Gdx.files.internal("artwork/maps/WorldMapApalachicola.png"));
		worldMapWewa      	 = new Texture(Gdx.files.internal("artwork/maps/WorldMapWewa.png"));
		worldMapStGeorge     = new Texture(Gdx.files.internal("artwork/maps/WorldMapStGeorge.png"));
		worldMapFake		 = new Texture(Gdx.files.internal("artwork/maps/WorldMapFake_02.png"));
		worldMapReal		 = new Texture(Gdx.files.internal("artwork/maps/WorldMapReal.png"));

		// Mission objects.
		cauldron                               = new Texture(Gdx.files.internal("artwork/missions/Cauldron.png"));
		sun                                    = new Texture(Gdx.files.internal("artwork/missions/Sun.png"));
		legendOfTheSevenSwordsBeginMissionText = new Texture(Gdx.files.internal("artwork/missions/LegendOfTheSevenSwords.png"));
		missionTransparentBlueSquare           = new Texture(Gdx.files.internal("artwork/missions/TransparentBlueSquare.png"));
		rightHand      						   = new Texture(Gdx.files.internal("artwork/missions/HandOpenRight.png"));
		leftHand      						   = new Texture(Gdx.files.internal("artwork/missions/HandOpenLeft.png"));
		rightHandClosed      				   = new Texture(Gdx.files.internal("artwork/missions/HandClosedRight.png"));
		leftHandClosed      				   = new Texture(Gdx.files.internal("artwork/missions/HandClosedLeft.png"));
		fishLeft                               = new Texture(Gdx.files.internal("artwork/missions/fishLeft.png"));
		fishRight                              = new Texture(Gdx.files.internal("artwork/missions/fishRight.png"));
		playerLeft                             = new Texture(Gdx.files.internal("artwork/gamecharacters/player/PlayerLeft.png"));
		playerRight                            = new Texture(Gdx.files.internal("artwork/gamecharacters/player/PlayerRight.png"));
		transition                             = new Texture(Gdx.files.internal("artwork/missions/Transition.png"));

		// Tile objects.
		grassTileOne     	   = new Texture(Gdx.files.internal("artwork/tiles/GrassTileOne.png"));
		sandTile               = new Texture(Gdx.files.internal("artwork/tiles/SandTile2.png"));
		sandTileHorizontalPath = new Texture(Gdx.files.internal("artwork/tiles/SandTileHorizontalPath.png"));
		sandTileLeftDownPath   = new Texture(Gdx.files.internal("artwork/tiles/SandTileLeftDownPath.png"));
		sandTileVerticalPath   = new Texture(Gdx.files.internal("artwork/tiles/SandTileVerticalPath.png"));
		sandTileTopRightPath   = new Texture(Gdx.files.internal("artwork/tiles/SandTileTopRightPath.png"));
		sandTileCrossPath      = new Texture(Gdx.files.internal("artwork/tiles/SandTileCrossPath.png"));
		waterTileOne           = new Texture(Gdx.files.internal("artwork/tiles/WaterOne.png"));
		waterTileTwo           = new Texture(Gdx.files.internal("artwork/tiles/WaterTwo.png"));
		waterTileThree		   = new Texture(Gdx.files.internal("artwork/tiles/WaterThree.png"));
		nightTimeShader        = new Texture(Gdx.files.internal("artwork/tiles/NightTimeShader.png"));

		// UI objects.
		lootAddedToInventory         = new Texture(Gdx.files.internal("artwork/ui/LootAdded.png"));
		objectiveBackground          = new Texture(Gdx.files.internal("artwork/ui/ControlPanelBase.png"));
		arcadeMap                    = new Texture(Gdx.files.internal("artwork/ui/Map.png"));
		arcadeInventory              = new Texture(Gdx.files.internal("artwork/ui/Inventory.png"));
		featherMeterUi               = new Texture(Gdx.files.internal("artwork/ui/FeathersMeterUi.png"));
		oysterMeterUi                = new Texture(Gdx.files.internal("artwork/ui/OysterMeterUi.png"));
		timeMeterUi                  = new Texture(Gdx.files.internal("artwork/ui/TimeMeterUi.png"));
		win                          = new Texture(Gdx.files.internal("artwork/ui/WinningScreen.png"));
		playGame                     = new Texture(Gdx.files.internal("artwork/ui/PlayGame.png"));
		gameOver                     = new Texture(Gdx.files.internal("artwork/ui/GameOver.png"));
		daggerUi                     = new Texture(Gdx.files.internal("artwork/ui/DaggerUi.png"));
		pawUi                        = new Texture(Gdx.files.internal("artwork/ui/CursedPawUi.png"));
		controlsUiBackground         = new Texture(Gdx.files.internal("artwork/ui/ControlsUiBackground.png"));
		controlsUi                   = new Texture(Gdx.files.internal("artwork/ui/Controller.png"));
		credits                      = new Texture(Gdx.files.internal("artwork/ui/Credits.png"));
		controls                     = new Texture(Gdx.files.internal("artwork/ui/Controls.png"));
		lootAlternate                = new Texture(Gdx.files.internal("artwork/ui/loot2.png"));
		outOfAmmo                    = new Texture(Gdx.files.internal("artwork/ui/OutOfAmmo.png"));
		pressStart                   = new Texture(Gdx.files.internal("artwork/ui/PressStart.png"));
		transparentSquare            = new Texture(Gdx.files.internal("artwork/ui/TransparentSquare.png"));
		gunUi                        = new Texture(Gdx.files.internal("artwork/ui/Gun.png"));
		healthUi                     = new Texture(Gdx.files.internal("artwork/ui/Health.png"));
		woodyUi                      = new Texture(Gdx.files.internal("artwork/ui/Woody.png"));
		magicPearlUi                 = new Texture(Gdx.files.internal("artwork/ui/MagicPearl.png"));
		rumUi                        = new Texture(Gdx.files.internal("artwork/ui/Rum.png"));
		ammoUi                       = new Texture(Gdx.files.internal("artwork/ui/Ammo.png"));
		legendSwordUi                = new Texture(Gdx.files.internal("artwork/ui/LegendSword.png"));
		addedToInventory             = new Texture(Gdx.files.internal("artwork/ui/AddedToInventory.png"));
		border                       = new Texture(Gdx.files.internal("artwork/ui/Border.png"));
		skullShadow                  = new Texture(Gdx.files.internal("artwork/ui/SkullShadow.png"));
		mapNavigationBar             = new Texture(Gdx.files.internal("artwork/ui/UiMap.png"));
		inventoryNavigationBar       = new Texture(Gdx.files.internal("artwork/ui/UiInventory.png"));
		inventoryScreen              = new Texture(Gdx.files.internal("artwork/ui/InventoryScreen.png"));
		storeUi                      = new Texture(Gdx.files.internal("artwork/ui/StoreUi.png"));
		inventoryLabel               = new Texture(Gdx.files.internal("artwork/ui/InventoryLabel.png"));
		locationSkull                = new Texture(Gdx.files.internal("artwork/ui/Skull.png"));
		attackBird                   = new Texture(Gdx.files.internal("artwork/ui/AttackBird.png"));
		gamePaused                   = new Texture(Gdx.files.internal("artwork/ui/GamePaused.png"));
		enemyHealthMeterBase         = new Texture(Gdx.files.internal("artwork/ui/EnemyHealthMeterBase.png"));
		timeMeterBase                = new Texture(Gdx.files.internal("artwork/ui/TimeMeterBase.png"));
		oysterMeterBase              = new Texture(Gdx.files.internal("artwork/ui/OysterMeterBase.png"));
		enemyHealthMeterBlack        = new Texture(Gdx.files.internal("artwork/ui/EnemyHealthMeterBlack.png"));

		// Objective UI objects.
		objectiveCollectTheMapAtThePoint       = new Texture(Gdx.files.internal("artwork/ui/objectives/CollectTheMapAtThePoint.png"));
		objectiveFindTheTreasureAtBlacksIsland = new Texture(Gdx.files.internal("artwork/ui/objectives/FindTheTreasureAtBlacksIsland.png"));
		objectiveCollectTheBird                = new Texture(Gdx.files.internal("artwork/ui/objectives/CollectTheBird.png"));
		objectiveAmmoUnlocked                  = new Texture(Gdx.files.internal("artwork/ui/objectives/AmmoUnlocked.png"));
		objectiveMagicPearlUnlocked            = new Texture(Gdx.files.internal("artwork/ui/objectives/MagicPearlUnlocked.png"));
		objectiveGoToWewa                      = new Texture(Gdx.files.internal("artwork/ui/objectives/GoToWewa.png"));
		objectiveFindTheCauldron               = new Texture(Gdx.files.internal("artwork/ui/objectives/FindTheCauldron.png"));
		objectiveTryAgain                      = new Texture(Gdx.files.internal("artwork/ui/objectives/TryAgain.png"));
		objectiveRawBar                        = new Texture(Gdx.files.internal("artwork/ui/objectives/RawBar.png"));
		objectiveStumpHole                     = new Texture(Gdx.files.internal("artwork/ui/objectives/StumpHole.png"));
		objectiveCollectLoot                   = new Texture(Gdx.files.internal("artwork/ui/objectives/CollectLoot.png"));
		objectiveTradinPost                    = new Texture(Gdx.files.internal("artwork/ui/objectives/TradinPost.png"));
		objectiveCollectOysters                = new Texture(Gdx.files.internal("artwork/ui/objectives/CollectOysters.png"));
		objectiveCollectFeathers               = new Texture(Gdx.files.internal("artwork/ui/objectives/CollectFeathers.png"));
		objectiveBuyTheGun                     = new Texture(Gdx.files.internal("artwork/ui/objectives/BuyTheGun.png"));
		objectiveKillTheBoss                   = new Texture(Gdx.files.internal("artwork/ui/objectives/KillTheBoss.png"));
		objectiveEnterTheTradingPost           = new Texture(Gdx.files.internal("artwork/ui/objectives/EnterTheTradinPost.png"));
		objectiveEnterStore                    = new Texture(Gdx.files.internal("artwork/ui/objectives/EnterStore.png"));
		objectiveExitStore                     = new Texture(Gdx.files.internal("artwork/ui/objectives/ExitStore.png"));
		objectiveNotEnoughLoot                 = new Texture(Gdx.files.internal("artwork/ui/objectives/NotEnoughLoot.png"));

		// Player name UI labels.
		jollyRogerUiNameLabel = new Texture(Gdx.files.internal("artwork/ui/playernames/Goldenage.png"));
		blackBeardUiNameLabel = new Texture(Gdx.files.internal("artwork/ui/playernames/Cutthroat.png"));
		pegLegUiNameLabel     = new Texture(Gdx.files.internal("artwork/ui/playernames/Farzenplank.png"));

		// Logo objects.
		splashScreenLogo = new Texture(Gdx.files.internal("artwork/logos/logo_ffg.png"));
		titleScreenLogo  = new Texture(Gdx.files.internal("artwork/logos/GoldenAgeLogo.png"));
		icon             = new Texture(Gdx.files.internal("artwork/logos/GoldenAgeIcon.png"));

		// Sign objects.  sign can be removed.
		sign         = new Texture(Gdx.files.internal("artwork/signs/Sign.png"));
		townSigns[0] = new Texture(Gdx.files.internal("artwork/signs/Apalachicola.png"));
		townSigns[1] = new Texture(Gdx.files.internal("artwork/signs/PortStJoe.png"));
		townSigns[2] = new Texture(Gdx.files.internal("artwork/signs/Wewa.png"));
		townSigns[3] = new Texture(Gdx.files.internal("artwork/signs/MexicoBeach.png"));
		townSigns[4] = new Texture(Gdx.files.internal("artwork/signs/StGeorge.png"));
		townSigns[5] = new Texture(Gdx.files.internal("artwork/signs/CapeSanBlas.png"));
		townSigns[6] = new Texture(Gdx.files.internal("artwork/signs/ThePoint.png"));

		// Debugging objects.
		whiteSquare = new Texture(Gdx.files.internal("artwork/debugging/WhiteSquare.png"));
		blackSquare = new Texture(Gdx.files.internal("artwork/debugging/BlackSquare.png"));
		redSquare   = new Texture(Gdx.files.internal("artwork/debugging/RedSquare.png"));

		// Character objects.
		roast         = new Texture(Gdx.files.internal("artwork/gamecharacters/enemy/Roast.png"));
		hanging       = new Texture(Gdx.files.internal("artwork/gamecharacters/enemy/Hanging.png"));
		hangingShadow = new Texture(Gdx.files.internal("artwork/gamecharacters/enemy/HangingShadow.png"));
		knightLeft    = new Texture(Gdx.files.internal("artwork/gamecharacters/knight/KnightLeft.png"));
		knightRight   = new Texture(Gdx.files.internal("artwork/gamecharacters/knight/KnightRight.png"));
		bossLeft01    = new Texture(Gdx.files.internal("artwork/gamecharacters/boss/left/01.png"));
		bossLeft02    = new Texture(Gdx.files.internal("artwork/gamecharacters/boss/left/02.png"));
		bossRight01   = new Texture(Gdx.files.internal("artwork/gamecharacters/boss/right/01.png"));
		bossRight02   = new Texture(Gdx.files.internal("artwork/gamecharacters/boss/right/02.png"));

		// Number objects.
		numberBlack[0] = new Texture(Gdx.files.internal("artwork/numbers/black/0.png"));
		numberBlack[1] = new Texture(Gdx.files.internal("artwork/numbers/black/1.png"));
		numberBlack[2] = new Texture(Gdx.files.internal("artwork/numbers/black/2.png"));
		numberBlack[3] = new Texture(Gdx.files.internal("artwork/numbers/black/3.png"));
		numberBlack[4] = new Texture(Gdx.files.internal("artwork/numbers/black/4.png"));
		numberBlack[5] = new Texture(Gdx.files.internal("artwork/numbers/black/5.png"));
		numberBlack[6] = new Texture(Gdx.files.internal("artwork/numbers/black/6.png"));
		numberBlack[7] = new Texture(Gdx.files.internal("artwork/numbers/black/7.png"));
		numberBlack[8] = new Texture(Gdx.files.internal("artwork/numbers/black/8.png"));
		numberBlack[9] = new Texture(Gdx.files.internal("artwork/numbers/black/9.png"));

		numberWhite[0] = new Texture(Gdx.files.internal("artwork/numbers/white/0.png"));
		numberWhite[1] = new Texture(Gdx.files.internal("artwork/numbers/white/1.png"));
		numberWhite[2] = new Texture(Gdx.files.internal("artwork/numbers/white/2.png"));
		numberWhite[3] = new Texture(Gdx.files.internal("artwork/numbers/white/3.png"));
		numberWhite[4] = new Texture(Gdx.files.internal("artwork/numbers/white/4.png"));
		numberWhite[5] = new Texture(Gdx.files.internal("artwork/numbers/white/5.png"));
		numberWhite[6] = new Texture(Gdx.files.internal("artwork/numbers/white/6.png"));
		numberWhite[7] = new Texture(Gdx.files.internal("artwork/numbers/white/7.png"));
		numberWhite[8] = new Texture(Gdx.files.internal("artwork/numbers/white/8.png"));
		numberWhite[9] = new Texture(Gdx.files.internal("artwork/numbers/white/9.png"));
	}

	public void dispose() {

		// Oar.
		for (int i = 0; i < oarRight.length; i++) {
			oarRight[i].dispose();
		}
		for (int i = 0; i < oarLeft.length; i++) {
			oarLeft[i].dispose();
		}
		for (int i = 0; i < oarUp.length; i++) {
			oarUp[i].dispose();
		}
		for (int i = 0; i < oarDown.length; i++) {
			oarDown[i].dispose();
		}

		// Title Screen.
		titleScreen.dispose();

		// Collectibles.
		tenHearts.dispose();
		treasureMapShadow.dispose();
		treasureMapRight.dispose();
		chestClosed.dispose();
		chestOpen.dispose();
		heart.dispose();
		whiteHeart.dispose();
		heartShadow.dispose();
		pawShadow.dispose();
		rum.dispose();
		rumShadow.dispose();
		ammo.dispose();
		ammoShadow.dispose();

		// Boat objects.
		boatSide.dispose();
		boatUp.dispose();
		boatDown.dispose();

		// Sign objects.
		sign.dispose();
		for (int i = 0; i < townSigns.length; i++) {
			townSigns[i].dispose();
		}

		// Nature Objects.
		for (int i = 0; i < shockPlants.length; i++) {
			shockPlants[i].dispose();
		}
		bubble.dispose();
		tunnel.dispose();
		hole.dispose();
		rock.dispose();
		logs.dispose();
		palmTreeThree.dispose();
		palmTree.dispose();
		plant.dispose();
		drSuessTree.dispose();
		light.dispose();
		rain.dispose();
		shadow.dispose();
		cloud.dispose();
		stump.dispose();
		sky.dispose();
		feather.dispose();
		quickSand.dispose();
		poisonPlantOne.dispose();
		poisonPlantTwo.dispose();
		poisonPlantThree.dispose();
		poisonPlantFour.dispose();
		poisonPlantFive.dispose();
		poisonPlantSix.dispose();
		poisonPlantSeven.dispose();
		poisonPlantEight.dispose();
		poisonPlantNine.dispose();
		poisonPlantTen.dispose();

		// Weapon Objects.
		daggerShadow.dispose();
		daggerUp.dispose();
		daggerDown.dispose();
		daggerLeft.dispose();
		daggerRight.dispose();
		bullet.dispose();
		paw.dispose();
		magicPearl.dispose();
		gunRight.dispose();
		gunLeft.dispose();
		gunUp.dispose();
		gunDown.dispose();
		legendSwordRed.dispose();
		legendSwordBlue.dispose();
		legendSwordGreen.dispose();
		legendSwordYellow.dispose();
		legendSwordPurple.dispose();
		legendSwordOrange.dispose();
		legendSwordPink.dispose();
		legendSwordRainbow.dispose();
		legendSwordRedHalf.dispose();
		legendSwordYellowHalf.dispose();
		legendSwordBlueHalf.dispose();
		legendSwordGreenHalf.dispose();
		legendSwordPurpleHalf.dispose();
		legendSwordPinkHalf.dispose();
		legendSwordOrangeHalf.dispose();
		oyster.dispose();
		oysterShadow.dispose();
		cannonBall.dispose();
		cannonLeft.dispose();
		cannonRight.dispose();
		arrowRight.dispose();
		arrowLeft.dispose();
		arrowUp.dispose();
		bowRight.dispose();
		bowLeft.dispose();
		bowUp.dispose();
		arrowShadowRight.dispose();
		arrowShadowLeft.dispose();
		arrowShadowUp.dispose();

		// Structure objects.
		scallopCove.dispose();
		bar.dispose();
		barShadow.dispose();
		slaveHut.dispose();
		teePee.dispose();
		rawbar.dispose();
		lightHouse.dispose();
		pigglywiggly.dispose();
		gruntHut.dispose();
		dockRight.dispose();
		dockLeft.dispose();
		tradingPost.dispose();
		fortTop.dispose();
		fortSide.dispose();

		// Structure Shadows.
		lightHouseShadow.dispose();
		tradingPostShadow.dispose();
		rawBarShadow.dispose();
		pigglyWigglyShadow.dispose();
		fortShadow.dispose();

		// Effects Objects.
		poisonCover.dispose();
		dustParticleOne.dispose();
		dustParticleTwo.dispose();
		dustParticleThree.dispose();

		// Cutscene objects.
		cutsceneRum.dispose();
		enemyMessage.dispose();
		mapMessage.dispose();
		birdMessage.dispose();
		cauldronMessage.dispose();
		cutsceneJollyRoger.dispose();
		cutsceneCutthroat.dispose();
		cutsceneFarzenplank.dispose();

		// Cutscene background images.
		cutsceneBackGroundImageEnemy.dispose();
		cutSceneBackGroundImageMap.dispose();
		cutSceneBackGroundImageCauldron.dispose();
		cutSceneBackGroundImageCutthroat.dispose();
		cutSceneBackGroundImageFarzenplank.dispose();
		cutSceneBackGroundImageBird.dispose();
		/*
		for(int i = 0; i < cutSceneDialogueOne.length; i++) {
			cutSceneDialogueOne[i].dispose();
		} */

		// Map objects.
		worldMapMexicoBeach.dispose();
		worldMapStGeorge.dispose();
		worldMapApalachicola.dispose();
		worldMapThePoint.dispose();
		worldMapCapeSanBlas.dispose();
		worldMapWewa.dispose();
		worldMapPortStJoe.dispose();
		worldMapFake.dispose();
		worldMapReal.dispose();

		// Mission objects.
		cauldron.dispose();
		sun.dispose();
		legendOfTheSevenSwordsBeginMissionText.dispose();
		missionTransparentBlueSquare.dispose();
		rightHand.dispose();
		leftHand.dispose();
		rightHandClosed.dispose();
		leftHandClosed.dispose();
		fishLeft.dispose();
		fishRight.dispose();
		playerRight.dispose();
		playerLeft.dispose();
		transition.dispose();

		// Tile objects.
		grassTileOne.dispose();
		sandTile.dispose();
		sandTileHorizontalPath.dispose();
		sandTileLeftDownPath.dispose();
		sandTileVerticalPath.dispose();
		sandTileTopRightPath.dispose();
		sandTileCrossPath.dispose();
		waterTileOne.dispose();
		waterTileTwo.dispose();
		waterTileThree.dispose();
		nightTimeShader.dispose();

		// UI objects.
		lootAddedToInventory.dispose();
		objectiveBackground.dispose();
		arcadeMap.dispose();
		arcadeInventory.dispose();
		featherMeterUi.dispose();
		oysterMeterUi.dispose();
		timeMeterUi.dispose();
		pressStart.dispose();
		playGame.dispose();
		win.dispose();
		gameOver.dispose();
		daggerUi.dispose();
		pawUi.dispose();
		controlsUi.dispose();
		controlsUiBackground.dispose();
		credits.dispose();
		controls.dispose();
		lootAlternate.dispose();
		outOfAmmo.dispose();
		transparentSquare.dispose();
		legendSwordUi.dispose();
		ammoUi.dispose();
		healthUi.dispose();
		magicPearlUi.dispose();
		rumUi.dispose();
		woodyUi.dispose();
		gunUi.dispose();
		addedToInventory.dispose();
		legendSwordUi.dispose();
		border.dispose();
		skullShadow.dispose();
		gamePaused.dispose();
		mapNavigationBar.dispose();
		inventoryNavigationBar.dispose();
		inventoryScreen.dispose();
		storeUi.dispose();
		inventoryLabel.dispose();
		locationSkull.dispose();
		attackBird.dispose();
		enemyHealthMeterBase.dispose();
		timeMeterBase.dispose();
		oysterMeterBase.dispose();
		enemyHealthMeterBlack.dispose();

		// Objective UI objects.
		objectiveCollectTheMapAtThePoint.dispose();
		objectiveFindTheTreasureAtBlacksIsland.dispose();
		objectiveAmmoUnlocked.dispose();
		objectiveMagicPearlUnlocked.dispose();
		objectiveCollectTheBird.dispose();
		objectiveFindTheCauldron.dispose();
		objectiveGoToWewa.dispose();
		objectiveTryAgain.dispose();
		objectiveRawBar.dispose();
		objectiveStumpHole.dispose();
		objectiveCollectLoot.dispose();
		objectiveTradinPost.dispose();
		objectiveCollectOysters.dispose();
		objectiveCollectFeathers.dispose();
		objectiveBuyTheGun.dispose();
		objectiveKillTheBoss.dispose();
		objectiveEnterTheTradingPost.dispose();
		objectiveEnterStore.dispose();
		objectiveExitStore.dispose();
		objectiveNotEnoughLoot.dispose();

		// Player name UI labels.
		jollyRogerUiNameLabel.dispose();
		blackBeardUiNameLabel.dispose();
		pegLegUiNameLabel.dispose();

		// Logo objects.
		splashScreenLogo.dispose();
		titleScreenLogo.dispose();
		icon.dispose();

		// Debugging objects.
		whiteSquare.dispose();
		blackSquare.dispose();
		redSquare.dispose();

		// Character objects.
		roast.dispose();
		hanging.dispose();
		hangingShadow.dispose();
		knightLeft.dispose();
		knightRight.dispose();
		bossLeft01.dispose();
		bossLeft02.dispose();
		bossRight01.dispose();
		bossRight02.dispose();

		// Number objects.
		for (int i = 0; i < numberBlack.length; i++) {
			numberBlack[i].dispose();
			numberWhite[i].dispose();
		}
	}
}
