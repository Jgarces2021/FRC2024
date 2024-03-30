// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RobotVision;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Subsystem;
import java.util.Arrays;
import java.util.List;

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
  private final Timer m_timer = new Timer();
  private final XboxController m_controller = new XboxController(0);

  private List<Subsystem> m_subsystems;
  private final Claw m_claw;
  private final Climber m_climber;
  private final Drivetrain m_drivetrain;
  private final RobotVision m_vision;
  private final Shooter m_shoorter;

  public Robot() {
    this.m_claw = new Claw();
    this.m_climber = new Climber();
    this.m_drivetrain = new Drivetrain();
    m_vision = new RobotVision();
    m_vision.startThreads();
    this.m_shoorter = new Shooter();
    this.m_subsystems = Arrays.asList(
        this.m_claw,
        this.m_climber,
        this.m_drivetrain,
        this.m_vision,
        this.m_shoorter);
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    this.m_subsystems.forEach(Subsystem::robotInit);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_drivetrain.drive(0.5, 0.0);
    } else {
      m_drivetrain.drive(0, 0); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // Update the state of the claw
    boolean aButtomPress = m_controller.getAButton();
    boolean bButtonPress = m_controller.getBButton();
    this.m_claw.setEnabled(aButtomPress, bButtonPress);

    // Update the state of the climber
    this.m_climber.setEnabled(m_controller.getRightBumper());

    // Update the state of the drivetrain
    this.m_drivetrain.drive(-m_controller.getLeftY(),
        -m_controller.getRightX());

    // Shooter
    if (m_controller.getRightBumperPressed()) {
      m_shoorter.shootAsync();
    }
    m_shoorter.setIntakeSpeed(m_controller.getRightTriggerAxis());
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void robotPeriodic() {
  }
}
