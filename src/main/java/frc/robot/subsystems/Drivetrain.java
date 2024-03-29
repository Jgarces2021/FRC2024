package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends Subsystem {

  private final Timer m_timer = new Timer();

  private final CANSparkMax m_frontLeft;
  private final CANSparkMax m_backLeft;

  private final CANSparkMax m_frontRight;
  private final CANSparkMax m_backRight;

  private final DifferentialDrive m_robotDrive;

  public Drivetrain() {
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
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_frontLeft.setInverted(true);
  }

  @Override
  public void teleopPeriodic(XboxController m_controller) {
    m_robotDrive.arcadeDrive(
      -m_controller.getLeftY(),
      -m_controller.getRightX()
    );
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
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }
}
