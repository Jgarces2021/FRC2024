// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
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
    this.m_subsystems =
      Arrays.asList(
        this.m_claw,
        this.m_climber,
        this.m_drivetrain,
        this.m_vision,
        this.m_shoorter
      );
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
    this.m_subsystems.forEach(Subsystem::autonomousInit);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    this.m_subsystems.forEach(Subsystem::autonomousPeriodic);
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    this.m_subsystems.forEach(subsystem -> subsystem.teleopInit(m_controller));
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    this.m_subsystems.forEach(subsystem ->
        subsystem.teleopPeriodic(m_controller)
      );
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    this.m_subsystems.forEach(Subsystem::testInit);
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    this.m_subsystems.forEach(Subsystem::testPeriodic);
  }

  @Override
  public void disabledPeriodic() {
    this.m_subsystems.forEach(Subsystem::disabledPeriodic);
  }

  @Override
  public void robotPeriodic() {
    this.m_subsystems.forEach(Subsystem::robotPeriodic);
  }
}
