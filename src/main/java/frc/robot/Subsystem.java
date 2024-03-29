package frc.robot;

public abstract class Subsystem {

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  public void robotInit() {}

  /** This function is run once each time the robot enters autonomous mode. */
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  public void autonomousPeriodic() {}

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  public void teleopPeriodic() {}

  /** This function is called once each time the robot enters test mode. */
  public void testInit() {}

  /** This function is called periodically during test mode. */
  public void testPeriodic() {}

  public void disabledPeriodic() {}

  public void robotPeriodic() {}
}
