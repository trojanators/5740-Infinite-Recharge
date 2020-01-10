package frc.robot.auto;

import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSelector {
/*	
	private String gameData;
	private boolean leftSwitch, leftScale, startOnLeft;
	
	private static final String Left = "Left";
	private static final String Right = "Right";
	
	private static final String DoNothingAuto = "Do Nothing";
	private static final String GoForwardAuto = "Go Forward";
	//private static final String CrossOverAuto = "Cross Over";
	
	private static final String OneCubeSwitchAuto = "One Cube Switch";
	private static final String SideSwitchAuto = "Side Switch";
	private static final String TwoCubeSwitchAuto = "Two Cube Switch";
	
	private static final String NearScaleOneCubeAuto = "One Cube Scale Near";
	private static final String NearScaleTwoCubeAuto = "Two Cube Scale Near";
	private static final String NearScaleThreeCubeAuto = "Three Cube Scale Near";
	private static final String NearScaleSwitchAuto = "One Scale One Switch Near";
	
	private static final String FarScaleOneCubeAuto = "One Cube Scale Far";
	private static final String FarScaleTwoCubeAuto = "Two Cube Scale Far";
	//private static final String FarScaleSwitchAuto = "One Scale One Switch Far";
	
	private static final String EitherScaleTwoCubeAuto = "Either Scale Two Cubes";

	private static final String TestAuto = "Test Auto";
	
	private SendableChooser<String> sideChooser = new SendableChooser<>();
	private SendableChooser<String> primaryAutoChooser = new SendableChooser<>();
	private SendableChooser<String> secondaryAutoChooser = new SendableChooser<>();
	private SendableChooser<String> tertiaryAutoChooser = new SendableChooser<>();

	AutoMode autoMode;
	
	public AutoSelector(){
		primaryAutoChooser.addDefault("Primary Option", DoNothingAuto);
		primaryAutoChooser.addObject("Go Forward", GoForwardAuto);
		//primaryAutoChooser.addObject("One Cube in Switch", OneCubeSwitchAuto);
		primaryAutoChooser.addObject("Side Switch", SideSwitchAuto);//TESTED
		primaryAutoChooser.addObject("Two Cubes in Switch", TwoCubeSwitchAuto);
		primaryAutoChooser.addObject("Near Scale One Cube", NearScaleOneCubeAuto);
		primaryAutoChooser.addObject("Near Scale Two Cubes", NearScaleTwoCubeAuto);
		primaryAutoChooser.addObject("Near Scale Three Cubes", NearScaleThreeCubeAuto);
		primaryAutoChooser.addObject("Near Scale Switch", NearScaleSwitchAuto);
		primaryAutoChooser.addObject("Far Scale One Cube", FarScaleOneCubeAuto);
		primaryAutoChooser.addObject("Far Scale Two Cubes", FarScaleTwoCubeAuto);
		primaryAutoChooser.addObject("Either Scale Two Cubes", EitherScaleTwoCubeAuto);
		primaryAutoChooser.addObject("Test Auto", TestAuto);
		SmartDashboard.putData("Primary Option", primaryAutoChooser);
		
		secondaryAutoChooser.addDefault("Secondary Option", DoNothingAuto);
		secondaryAutoChooser.addObject("Go Forward", GoForwardAuto);
		//secondaryAutoChooser.addObject("One Cube in Switch", OneCubeSwitchAuto);
		secondaryAutoChooser.addObject("Side Switch", SideSwitchAuto);//TESTED
		secondaryAutoChooser.addObject("Two Cubes in Switch", TwoCubeSwitchAuto);
		secondaryAutoChooser.addObject("Near Scale One Cube", NearScaleOneCubeAuto);
		secondaryAutoChooser.addObject("Near Scale Two Cubes", NearScaleTwoCubeAuto);
		secondaryAutoChooser.addObject("Near Scale Three Cubes", NearScaleThreeCubeAuto);
		secondaryAutoChooser.addObject("Near Scale Switch", NearScaleSwitchAuto);
		secondaryAutoChooser.addObject("Far Scale One Cube", FarScaleOneCubeAuto);
		secondaryAutoChooser.addObject("Far Scale Two Cubes", FarScaleTwoCubeAuto);
		secondaryAutoChooser.addObject("Either Scale Two Cubes", EitherScaleTwoCubeAuto);
		SmartDashboard.putData("Secondary Option", secondaryAutoChooser);
		
		tertiaryAutoChooser.addDefault("TertiaryOption", DoNothingAuto);
		tertiaryAutoChooser.addObject("Go Forward", GoForwardAuto);
		//tertiaryAutoChooser.addObject("One Cube in Switch", OneCubeSwitchAuto);
		tertiaryAutoChooser.addObject("Side Switch", SideSwitchAuto);//TESTED
		tertiaryAutoChooser.addObject("Two Cubes in Switch", TwoCubeSwitchAuto);
		tertiaryAutoChooser.addObject("Near Scale One Cube", NearScaleOneCubeAuto);
		tertiaryAutoChooser.addObject("Near Scale Two Cubes", NearScaleTwoCubeAuto);
		tertiaryAutoChooser.addObject("Near Scale Three Cubes", NearScaleThreeCubeAuto);
		tertiaryAutoChooser.addObject("Near Scale Switch", NearScaleSwitchAuto);
		tertiaryAutoChooser.addObject("Far Scale One Cube", FarScaleOneCubeAuto);
		tertiaryAutoChooser.addObject("Far Scale Two Cubes", FarScaleTwoCubeAuto);
		tertiaryAutoChooser.addObject("Either Scale Two Cubes", EitherScaleTwoCubeAuto);
		SmartDashboard.putData("Tertiary Option", tertiaryAutoChooser);
		
		
		sideChooser.addDefault("Start On Left", Left);
		sideChooser.addObject("Start On Right", Right);
		SmartDashboard.putData("Starting Side", sideChooser);
		
	}
	
	public void getGameData(){
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		leftSwitch = (gameData.charAt(0) == 'L');
		leftScale = (gameData.charAt(1) == 'L');
		System.out.println("Game Data: " + gameData);
	}
	
	public AutoMode selectAuto(){
		getGameData();
		
		startOnLeft = (sideChooser.getSelected() == Left);
		String mode = primaryAutoChooser.getSelected();
		autoMode = instantiateFromString(mode);
		if(!autoMode.isValid(startOnLeft, leftSwitch, leftScale)){
			mode = secondaryAutoChooser.getSelected();
			autoMode = instantiateFromString(mode);
			if(!autoMode.isValid(startOnLeft, leftSwitch, leftScale)){
				mode = tertiaryAutoChooser.getSelected();
				autoMode = instantiateFromString(mode);
				if(!autoMode.isValid(startOnLeft, leftSwitch, leftScale)){
					mode = GoForwardAuto;
					autoMode = instantiateFromString(mode);
				}
			}	
		}
		
		return autoMode;
	}
	
	private AutoMode instantiateFromString(String name){
		
		AutoMode autoMode;
		
		switch(name){
		case DoNothingAuto://always valid
			autoMode = new DoNothingAuto();
			break;
		case GoForwardAuto://always valid
			autoMode = new GoForwardAuto();
			break;
		case OneCubeSwitchAuto://always valid
			autoMode = new OneCubeSwitchAuto(leftSwitch);
			break;
		case TwoCubeSwitchAuto://always valid
			autoMode = new TwoCubeSwitchAuto(leftSwitch);
			break;
		case SideSwitchAuto:
			autoMode = new SideSwitchAuto(leftSwitch);
			break;
		case NearScaleOneCubeAuto:
			//autoMode = new NearSideOneCubeScaleAuto(startOnLeft);
			autoMode = new NearSideOneCubeSpecialScaleAuto(startOnLeft);
			break;
		case NearScaleTwoCubeAuto:
			autoMode = new NearSideTwoCubeScaleAuto(startOnLeft);
			break;
		case NearScaleThreeCubeAuto:
			autoMode = new FastNearSideThreeCubeScaleAuto(startOnLeft);// TODO
			break;
		case NearScaleSwitchAuto:
			autoMode = new NearScaleSwitchAuto(startOnLeft);
			break;
		case FarScaleOneCubeAuto:
			autoMode = new FarSideTwoCubeScaleAuto(startOnLeft);
			break;
		case FarScaleTwoCubeAuto:
			if(CIAObjects.driveBase.gyroIsConnected()) autoMode = new FarScaleTwoCubeSnapAuto(startOnLeft);
			else{
				System.out.println("The Gyro is not connected!!! \n Defaulting to single cube using PIDF");
				autoMode = new FarSideTwoCubeScaleAuto(startOnLeft);
			}
			break;
		case EitherScaleTwoCubeAuto://always valid
			if(startOnLeft == leftScale) autoMode = new NearSideTwoCubeScaleAuto(startOnLeft);
			else{
				if(CIAObjects.driveBase.gyroIsConnected())autoMode = new FarScaleTwoCubeSnapAuto(startOnLeft);
				else{
					System.out.println("The Gyro is not connected!!! \n Defaulting to single cube using PIDF");
					autoMode = new FarSideTwoCubeScaleAuto(startOnLeft);
				}
			}
			break;
		default:
			autoMode = new DoNothingAuto();
			break;
		}
		
		return autoMode;
		
	}
	
*/
}
