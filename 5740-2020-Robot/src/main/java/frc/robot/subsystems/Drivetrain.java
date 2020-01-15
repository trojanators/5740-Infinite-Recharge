/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team2363.commands.HelixConditionalCommand;
import com.team2363.logger.HelixLogger;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.datacollection.limelightData.LimelightData;
import frc.robot.util.CvsLoggerStrings;
import frc.robot.util.PID;

public class Drivetrain extends SubsystemBase {

	/**
	 * Creates a new Drivetrain
	 */

	//private final WPI_TalonSRX tfrontRDrive = new WPI_TalonSRX(Constants.FrontRightDriveCAN);
	//private final WPI_TalonSRX tbackRDrive = new WPI_TalonSRX(Constants.BackRightDriveCAN);
	private final Victor frontRDrive = new Victor(2);
	private final Victor backRDrive = new Victor(3);

	//private final WPI_TalonSRX tfrontLDrive = new WPI_TalonSRX(Constants.FrontLeftDriveCAN);
	//private final WPI_TalonSRX tbackLDrive = new WPI_TalonSRX(Constants.BackLeftDriveCAN);
	private final Victor frontLDrive = new Victor(0);
	private final Victor backLDrive = new Victor(1);
	private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	private final Encoder rightEncoder = new Encoder(0, 1);
	private final Encoder leftEncoder = new Encoder(2, 3);

	private final PID turnPID = new PID(Constants.PTurn, Constants.ITurn, Constants.DTurn, Constants.turnEpsilon);
	private final PID drivePID = new PID(Constants.PDrive, Constants.IDrive, Constants.DDrive, 1.0);

	private final SpeedControllerGroup leftDriveGroup = new SpeedControllerGroup(frontLDrive, backLDrive);
	private final SpeedControllerGroup rightDriveGroup = new SpeedControllerGroup(frontRDrive, backRDrive);

	private final DifferentialDrive drive = new DifferentialDrive(leftDriveGroup, rightDriveGroup);

	private double gyroWorkingZero = 0;

	/**
	 * TODO: pid loop hold mode
	 * 
	 * TODO: current limiting
	 * 
	 * TODO: tip detection pid loop
	 * 
	 * 
	 *
	 * 
	 * 
	 */

	public Drivetrain() {
		// frontRDrive.setInverted(true);
		// backRDrive.setInverted(true);
		turnPID.setMaxOutput(1.0);
		drivePID.setMaxOutput(1.0);

		// Gets Drive train Default Pos on Init
		HelixLogger.getInstance().addDoubleSource("DRIVETRAIN Front LEFT Starting POS", frontLDrive::getPosition);
		HelixLogger.getInstance().addDoubleSource("DRIVETRAIN Front Right Starting POS", frontRDrive::getPosition);
		HelixLogger.getInstance().addDoubleSource("DRIVETRAIN Back Right Starting POS", backRDrive::getPosition);
		HelixLogger.getInstance().addDoubleSource("DRIVETRAIN Back Left Starting POS", backLDrive::getPosition);

		// LimelightData.isTargetVisible();

	}

	@Override
	public void periodic() {

		// This method will be called once per scheduler run
	}

	public void zeroSensors() {
		rightEncoder.reset();
		leftEncoder.reset();
		gyro.reset();
		// Logs Reseting Encoders
		HelixLogger.getInstance().addStringSource("Calibrating Sensors", CvsLoggerStrings.calabrating::toString);
	}
	public void calibrateGyro() {
		gyro.calibrate();
	}
	public void driveForwardSlowly() {
		drive.arcadeDrive(0.35, 0);
	}

	public void setLeftRightPower(final double left, final double right) {
		frontLDrive.set(left);
		backLDrive.set(left);
		frontRDrive.set(right);
		backRDrive.set(right);
	}

	public double leftEncoderDistance() {
		return leftEncoder.getDistance();
	}

	public double rightEncoderDistance() {
		return rightEncoder.getDistance();
	}

	public double leftEncoderRate() {
		return leftEncoder.getRate();
	}

	public double rightEncoderRate() {
		return rightEncoder.getRate();
	}

	public void zeroEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public double getGyroYaw() {
		return gyro.getAngle() - gyroWorkingZero;
	}

	public void setGyroYaw(final double yaw) {
		gyroWorkingZero = gyro.getAngle() - yaw;
	}

	public void stop() {
		backLDrive.set(0);
		frontLDrive.set(0);
		backRDrive.set(0);
		frontRDrive.set(0);
	}

	// Used for test mode
	public void setFrontLDrive(final double power) {
		frontLDrive.set(power);
	}

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

	public void holdPosition() {
		final double left = leftEncoder.getDistance() * 217.3 * Constants.PHold;
		final double right = rightEncoder.getDistance() * 217.3 * Constants.PHold;
		setLeftRightPower(left, right);
	}

	public void arcadeDrive(final double throttle, final double turn) {
		drive.arcadeDrive(throttle, turn);
	}

	public void deadbandedArcadeDrive() {
		double throttle, turn;
		if (RobotContainer.driverController.getRawAxis(Constants.leftStickY) > 0.1
				|| RobotContainer.driverController.getRawAxis(Constants.leftStickY) < -0.1) {
			if (RobotContainer.driverController.getRawAxis(Constants.leftStickY) < 0) {
				throttle = -Math.sqrt(Math.abs(RobotContainer.driverController.getRawAxis(Constants.leftStickY)));
			} else {
				throttle = Math.sqrt(RobotContainer.driverController.getRawAxis(Constants.leftStickY));
			}
		} else {
			throttle = 0;
		}
		/* check deadband */

		if (RobotContainer.driverController.getRawAxis(Constants.rightStickX) > 0.2
				|| RobotContainer.driverController.getRawAxis(Constants.rightStickX) < -0.2) {
			if (RobotContainer.driverController.getRawAxis(Constants.rightStickX) < 0) {
				turn = -Math.sqrt(Math.abs(RobotContainer.driverController.getRawAxis(Constants.rightStickX)));
			} else {
				turn = Math.sqrt(RobotContainer.driverController.getRawAxis(Constants.rightStickX));
			}
		} else {
			turn = 0;
		}
		arcadeDrive(-throttle, turn);
	}
}
