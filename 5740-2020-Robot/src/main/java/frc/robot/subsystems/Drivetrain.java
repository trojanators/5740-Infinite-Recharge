/**
 * This Class is For Controlling our Robot Drive Train
 * it has All our Functions Organized and used in all most all of our  commands
 * @author Luke Crum, Nicholas Blackburn
 */
package frc.robot.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.util.CvsLoggerStrings;

import frc.robot.util.PID;

public class Drivetrain extends SubsystemBase {

	private WPI_TalonSRX frontLDrive = new WPI_TalonSRX(Constants.leftDriveACAN);
	private WPI_TalonSRX backLDrive = new WPI_TalonSRX(Constants.leftDriveBCAN);
	private WPI_TalonSRX frontRDrive = new WPI_TalonSRX(Constants.rightDriveACAN);
	private WPI_TalonSRX backRDrive = new WPI_TalonSRX(Constants.rightDriveBCAN);

	final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	private final Encoder rightEncoder = new Encoder(1, 2);
	private final Encoder leftEncoder = new Encoder(3, 4);

	private final PID turnPID = new PID(Constants.PTurn, Constants.ITurn, Constants.DTurn, Constants.turnEpsilon);
	private final PID drivePID = new PID(Constants.PDrive, Constants.IDrive, Constants.DDrive, 1.0);

	private final SpeedControllerGroup leftDriveGroup = new SpeedControllerGroup(frontLDrive, backLDrive);
	private final SpeedControllerGroup rightDriveGroup = new SpeedControllerGroup(frontRDrive, backRDrive);

	private final DifferentialDrive drive = new DifferentialDrive(leftDriveGroup, rightDriveGroup);

	private double gyroWorkingZero = 0;

	public Drivetrain() {

		driveTrainConfig();
		currentLimit();
	}

	/**
	 * Gets creates and Configures the Drivetrain used in other subsystems
	 * 
	 * @author Nicholas Blackburn
	 */
	private void driveTrainConfig() {

		turnPID.setMaxOutput(1.0);
		drivePID.setMaxOutput(1.0);

		leftEncoder.setDistancePerPulse(1);
		rightEncoder.setDistancePerPulse(1);

		leftEncoder.setReverseDirection(true);
		rightEncoder.setReverseDirection(true);

		frontRDrive.setInverted(true);
		backRDrive.setInverted(true);

		frontLDrive.configOpenloopRamp(Constants.kRampRate);
		backLDrive.configOpenloopRamp(Constants.kRampRate);
		frontRDrive.configOpenloopRamp(Constants.kRampRate);
		backRDrive.configOpenloopRamp(Constants.kRampRate);

	}

	/**
	 * Limits the current of the DriveTrain's Sim Motors Using
	 * 
	 * @param TalonSrx Current Limiting
	 * 
	 * @author Luke Crumb , Nicholas Blackburn
	 */
	private void currentLimit() {

		frontLDrive.enableCurrentLimit(true);
		frontLDrive.configContinuousCurrentLimit(Constants.kContinuousCurrentLimit);
		frontLDrive.configPeakCurrentDuration(Constants.kPeakCurrentDuration);
		frontLDrive.configPeakCurrentLimit(Constants.kPeakCurrentLimit);

		backLDrive.enableCurrentLimit(true);
		backLDrive.configContinuousCurrentLimit(Constants.kContinuousCurrentLimit);
		backLDrive.configPeakCurrentDuration(Constants.kPeakCurrentDuration);
		backLDrive.configPeakCurrentLimit(Constants.kPeakCurrentLimit);

		frontRDrive.enableCurrentLimit(true);
		frontRDrive.configContinuousCurrentLimit(Constants.kContinuousCurrentLimit);
		frontRDrive.configPeakCurrentDuration(Constants.kPeakCurrentDuration);
		frontRDrive.configPeakCurrentLimit(Constants.kPeakCurrentLimit);

		backRDrive.enableCurrentLimit(true);
		backRDrive.configContinuousCurrentLimit(Constants.kContinuousCurrentLimit);
		backRDrive.configPeakCurrentDuration(Constants.kPeakCurrentDuration);
		backRDrive.configPeakCurrentLimit(Constants.kPeakCurrentLimit);
	}

	/**
	 * Zeros Encoder sensors back to 0 state
	 * 
	 * @param Encoders
	 * @author Nicholas Blackburn
	 */
	public void zeroSensors() {
		gyro.reset();
		rightEncoder.reset();
		leftEncoder.reset();

	}

	/**
	 * Cal's Gyro to current pos of robot for MAXIMUM ACCURACY
	 * 
	 * @param Gyro
	 * 
	 * @author Nicholas Blackburn
	 */
	public void calibrateGyro() {
		gyro.calibrate();

	}

	/**
	 * Sets Drivetrain Power to 3% speed
	 * 
	 * @param Drivetrain
	 * 
	 * @author Nicholas Blackburn
	 */
	public void driveForwardSlowly() {
		drive.arcadeDrive(0.35, 0);
	}

	/**
	 * 
	 * Sets @param Drivetrain Motor Power in a simple and Organized way
	 * 
	 * @author Luke Crumb
	 */
	public void setLeftRightPower(final double left, final double right) {
		frontLDrive.set(left);
		backLDrive.set(left);
		frontRDrive.set(right);
		backRDrive.set(right);
	}

	/**
	 * Distance in a Simple return type The output Should Be used in A Distace
	 * Related Method Gets
	 * 
	 * @param LeftEncoder
	 * 
	 * @author Nicholas Blackburn
	 */
	public double leftEncoderDistance() {
		return frontLDrive.getSelectedSensorPosition();
	}

	/**
	 * 
	 * Gets Distance in a Simple return type The output Should Be used in A Distace
	 * Related Method
	 * 
	 * @param RightEncoder
	 * 
	 * @author Nicholas Blackburn
	 */
	public double rightEncoderDistance() {
		return frontRDrive.getSelectedSensorPosition();
	}

	/**
	 * Gets Rate in a Simple return type The output Should Be used in A Rate Related
	 * Method
	 * 
	 * @param LeftEncoder
	 * 
	 * @author Nicholas Blackburn
	 */
	public double leftEncoderRate() {
		return leftEncoder.getRate();
	}

	/**
	 * Gets Rate in a Simple return type The output Should Be used in A Rate Related
	 * Method
	 * 
	 * @param RightEncoder
	 * 
	 * @author Nicholas Blackburn
	 */
	public double rightEncoderRate() {
		return rightEncoder.getRate();
	}

	/**
	 * Gets Encoders Rate in a Simple return type The output Should Be used in A
	 * Rate Related Method
	 * 
	 * @param Encoders
	 * 
	 * @author Nicholas Blackburn
	 */
	public void zeroEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
		frontLDrive.setSelectedSensorPosition(0);
		frontRDrive.setSelectedSensorPosition(0);
	}

	/**
	 * Gets Gyro Yaw in a Simple return type The output Should Be used in A Double
	 * Related Method
	 * 
	 * @param Gyro
	 * 
	 * @author Nicholas Blackburn
	 */

	public double getGyroYaw() {
		return gyro.getAngle() - gyroWorkingZero;
	}

	/**
	 * Sets Gyro Yaw in a Simple Double type The output Should Be used in A Double
	 * Related Method
	 * 
	 * @param Gyro
	 * 
	 * @author Nicholas Blackburn
	 */
	public void setGyroYaw(final double yaw) {
		gyroWorkingZero = gyro.getAngle() - yaw;
	}

	/**
	 * Sets Motor Power to 0
	 * 
	 * @param Drivetrain
	 * @param TalonSRX
	 * 
	 * @author Nicholas Blackburn
	 */
	public void stop() {
		backLDrive.set(0);
		frontLDrive.set(0);
		backRDrive.set(0);
		frontRDrive.set(0);
	}

	/**
	 * 
	 * Sets Motor Power of Front Left motor for Drivetrain
	 * 
	 * 
	 * @param Drivetrain
	 * 
	 * @author Nicholas Blackburn
	 */
	public void setFrontLDrive(final double power) {
		frontLDrive.set(power);
	}

	/*
	 * Sets Motor Power For Backl motor for Drivetrain
	 * 
	 * @param Driver
	 * 
	 * @author Nicholas Blackburn
	 */
	public void setBackLDrive(final double power) {
		backLDrive.set(power);
	}

	public void setFrontRDrive(final double power) {
		frontRDrive.set(power);
	}

	public void setBackRDrive(final double power) {
		backRDrive.set(power);
	}

	public boolean angleIsStable = false;

	public void turnToAngle(final double setpointAngle) {
		final double currentAngle = getGyroYaw();
		turnPID.setMaxOutput(1.0);
		turnPID.setConstants(Constants.PTurn, Constants.ITurn, Constants.DTurn);
		if (!turnPID.isDone()) {
			turnPID.setDesiredValue(setpointAngle);
			final double turnPower = turnPID.calcPID(currentAngle);
			setLeftRightPower(turnPower, -turnPower);
			angleIsStable = false;
		} else {
			setLeftRightPower(0, 0);
			angleIsStable = true;
		}
	}

	public boolean distanceIsStable = false;

	public void driveToDistancePID(final double setpointDistance, final double maxPower, final double angle) {
		if (!drivePID.isDone()) {
			turnPID.setConstants(Constants.PdriveTurn, Constants.IdriveTurn, Constants.DdriveTurn);
			turnPID.setMaxOutput(.6);
			drivePID.setMaxOutput(maxPower);
			drivePID.setDesiredValue(setpointDistance);
			turnPID.setDesiredValue(angle);
			final double power = drivePID.calcPID(leftEncoder.getDistance() / 217.3);
			final double turn = turnPID.calcPID(getGyroYaw());
			setLeftRightPower(power + turn, power - turn);
			distanceIsStable = false;
		} else {
			setLeftRightPower(0, 0);
			distanceIsStable = true;
		}
	}

	boolean turned = false;

	public void fastTurnAndDriveDistancePID(final double setpointDistance, final double maxPower, final double angle) {
		final double currentAngle = getGyroYaw();
		if (!turned) {
			turnPID.setConstants(Constants.PTurn, Constants.ITurn, Constants.DTurn);
			turnPID.setDesiredValue(angle);
			final double turnPower = turnPID.calcPID(currentAngle);
			setLeftRightPower(turnPower, -turnPower);
			if (Math.abs(currentAngle - angle) < 5)
				turned = true;
			else
				turned = false;
		} else if (!drivePID.isDone()) {
			turnPID.setConstants(Constants.PdriveTurn, Constants.IdriveTurn, Constants.DdriveTurn);
			drivePID.setMaxOutput(maxPower);
			drivePID.setDesiredValue(setpointDistance);
			turnPID.setDesiredValue(angle);
			final double power = drivePID.calcPID(leftEncoder.getDistance());
			final double turn = turnPID.calcPID(getGyroYaw());
			setLeftRightPower(power + turn, power - turn);
			distanceIsStable = false;
		} else {
			setLeftRightPower(0, 0);
			distanceIsStable = true;
		}

	}

	public void resetPID() {
		turned = false;
		distanceIsStable = false;
		angleIsStable = false;
		zeroEncoders();
	}

	/*
	 * public void holdPosition() { final double left = leftEncoder.getDistance() *
	 * 217.3 * Constants.PHold; final double right = rightEncoder.getDistance() *
	 * 217.3 * Constants.PHold; setLeftRightPower(left, right); }
	 */

	public void arcadeDrive(final double throttle, final double turn) {
		drive.arcadeDrive(throttle, turn);
	}

	public void deadbandedArcadeDrive() {
		double throttle, turn;
		if (RobotContainer.m_driverController.getRawAxis(Constants.kRightStickX) > 0.1
				|| RobotContainer.m_driverController.getRawAxis(Constants.kRightStickX) < -0.1) {
			if (RobotContainer.m_driverController.getRawAxis(Constants.kRightStickX) < 0) {
				throttle = -Math.sqrt(Math.abs(RobotContainer.m_driverController.getRawAxis(Constants.kRightStickX)));
			} else {
				throttle = Math.sqrt(RobotContainer.m_driverController.getRawAxis(Constants.kRightStickX));
			}
		} else {
			throttle = 0;
		}
		/* check deadband */

		if (RobotContainer.m_driverController.getRawAxis(Constants.kLeftStickY) > 0.2
				|| RobotContainer.m_driverController.getRawAxis(Constants.kLeftStickY) < -0.2) {
			if (RobotContainer.m_driverController.getRawAxis(Constants.kLeftStickY) < 0) {
				turn = -Math.sqrt(Math.abs(RobotContainer.m_driverController.getRawAxis(Constants.kLeftStickY)));
			} else {
				turn = Math.sqrt(RobotContainer.m_driverController.getRawAxis(Constants.kLeftStickY));
			}
		} else {
			turn = 0;
		}
		arcadeDrive(throttle, -turn);
	}

	public void setHoldPosition() {
		zeroSensors();
		drivePID.setDesiredValue(0);
		turnPID.setDesiredValue(0);
	}
}
