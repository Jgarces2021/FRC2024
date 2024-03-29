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
import java.util.ArrayList;

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

  private final CANSparkMax m_topIntake;
  private final CANSparkMax m_bottomIntake;

  private final DifferentialDrive m_robotDrive;
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();
  private final RobotVision vision;
  private CANSparkMax m_rightclimber;
  private CANSparkMax m_leftclimber;
  private CANSparkMax m_FodeIntake;

  private ArrayList<Subsystem> subsystems;

  public Robot() {
    m_frontLeft = new CANSparkMax(2, MotorType.kBrushed);
    m_backLeft = new CANSparkMax(1, MotorType.kBrushed);
    m_frontRight = new CANSparkMax(3, MotorType.kBrushed);
    m_backRight = new CANSparkMax(4, MotorType.kBrushed);

    this.m_topIntake = new CANSparkMax(5, MotorType.kBrushed);
    this.m_bottomIntake = new CANSparkMax(6, MotorType.kBrushed);

    this.m_rightclimber = new CANSparkMax(7, MotorType.kBrushed);
    this.m_leftclimber = new CANSparkMax(8, MotorType.kBrushed);

    this.m_FodeIntake = new CANSparkMax(9, MotorType.kBrushed);

    m_backLeft.follow(m_frontLeft);
    m_backRight.follow(m_frontRight);
    m_robotDrive = new DifferentialDrive(m_frontLeft::set, m_frontRight::set);

    SendableRegistry.addChild(m_robotDrive, m_frontLeft);
    SendableRegistry.addChild(m_robotDrive, m_frontRight);
    SendableRegistry.addChild(m_robotDrive, m_backLeft);
    SendableRegistry.addChild(m_robotDrive, m_backRight);
    vision = new RobotVision();

    vision.startThreads();

    this.subsystems = new ArrayList<Subsystem>();
    this.subsystems.add(new Shooter());
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    this.subsystems.forEach(Subsystem::robotInit);
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_frontLeft.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    this.subsystems.forEach(Subsystem::autonomousInit);

    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    this.subsystems.forEach(Subsystem::autonomousPeriodic);

    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    this.subsystems.forEach(Subsystem::teleopInit);
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    this.subsystems.forEach(Subsystem::teleopPeriodic);

    m_robotDrive.arcadeDrive(
      -m_controller.getLeftY(),
      -m_controller.getRightX()
    );

    {
      double rightTrigger = m_controller.getRightTriggerAxis();
      // double leftTrigger = m_controller.getLeftTriggerAxis();
      // double speed = rightTrigger - leftTrigger;

      if (rightTrigger > 0 || m_topIntake.get() > 0) {
        m_topIntake.set(rightTrigger);
        m_bottomIntake.set(rightTrigger);
      }

      // m_bottomIntake.set(speed);
      if (m_controller.getRightBumperPressed()) {
        Thread t = new Thread(() -> {
          m_topIntake.set(-1.0);
          try {
            Thread.sleep(250);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          m_bottomIntake.set(-1.0);
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          m_bottomIntake.set(0);
          m_topIntake.set(0);
        });
        t.start();
      }
    }

    {
      boolean bumperPressed = m_controller.getLeftBumper();
      double climberSpeed = 0.5;
      if (bumperPressed) {
        climberSpeed = 0.5;
      } else {
        climberSpeed = 0;
      }
      m_rightclimber.set(climberSpeed);
    }

    {
      boolean leftbumperPressed = m_controller.getRightBumper();
      double leftclimberSpeed = 0.5;

      if (leftbumperPressed) {
        leftclimberSpeed = 0.5;
      } else {
        leftclimberSpeed = 0;
      }
      m_leftclimber.set(leftclimberSpeed);
    }

    boolean aButtomPress = m_controller.getAButton();
    boolean bButtonPress = m_controller.getBButton();
    double Fodespeed = 0;
    if (!aButtomPress && bButtonPress) {
      Fodespeed = 0.5;
    } else if (aButtomPress && !bButtonPress) {
      Fodespeed = -0.5;
    }
    m_FodeIntake.set(Fodespeed);
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    this.subsystems.forEach(Subsystem::testInit);
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    this.subsystems.forEach(Subsystem::testPeriodic);
  }

  @Override
  public void disabledPeriodic() {
    this.subsystems.forEach(Subsystem::disabledPeriodic);
  }

  @Override
  public void robotPeriodic() {
    this.subsystems.forEach(Subsystem::robotPeriodic);
  }
}
