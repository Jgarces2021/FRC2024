// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the manifest
 * file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  private final CANSparkMax m_frontLeft;
  private final CANSparkMax m_backLeft;

  private final CANSparkMax m_frontRight;
  private final CANSparkMax m_backRight;
  private final DifferentialDrive m_robotDrive;
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();
  private final RobotVision vision;

  public Robot() {
    m_frontLeft = new CANSparkMax(2, MotorType.kBrushed);
    m_backLeft = new CANSparkMax(1, MotorType.kBrushed);
    m_frontRight = new CANSparkMax(3, MotorType.kBrushed);
    m_backRight = new CANSparkMax(4, MotorType.kBrushed);
    m_backLeft.follow(m_frontLeft);
    m_backRight.follow(m_frontRight);

    m_robotDrive = new DifferentialDrive(m_frontLeft::set, m_frontRight::set);
    SendableRegistry.addChild(m_robotDrive, m_frontLeft);
    SendableRegistry.addChild(m_robotDrive, m_frontRight);
    SendableRegistry.addChild(m_robotDrive, m_backLeft);
    SendableRegistry.addChild(m_robotDrive, m_backRight);

    vision = new RobotVision();
    vision.startThreads();
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_frontLeft.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  private boolean running = false;

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      if (!running) {
        running = true;
        System.out.println("Turning on motors");
      }
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      if (running) {
        running = false;
        System.out.println("Turning off motors");
      }
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(
      -m_controller.getLeftY(),
      -m_controller.getRightX()
    );
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void robotPeriodic() {}
}
