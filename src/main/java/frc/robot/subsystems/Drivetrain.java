package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public class Drivetrain extends Subsystem {
  private final SparkMax m_frontLeft;
  private final SparkMax m_backLeft;

  private final SparkMax m_frontRight;
  private final SparkMax m_backRight;

  private final DifferentialDrive m_robotDrive;

  public Drivetrain() {
    m_frontLeft = new SparkMax(Constants.Drivetrain.DRIVETRAIN_FRONT_LEFT_MOTOR_ID, MotorType.kBrushed);
    m_backLeft = new SparkMax(Constants.Drivetrain.DRIVETRAIN_BACK_LEFT_MOTOR_ID, MotorType.kBrushed);
    m_frontRight = new SparkMax(Constants.Drivetrain.DRIVETRAIN_FRONT_RIGHT_MOTOR_ID, MotorType.kBrushed);
    m_backRight = new SparkMax(Constants.Drivetrain.DRIVETRAIN_BACK_RIGHT_MOTOR_ID, MotorType.kBrushed);

    // m_backLeft.follow(m_frontLeft);
    // m_backRight.follow(m_frontRight);
    m_robotDrive = new DifferentialDrive(m_frontLeft::set, m_frontRight::set);

    SendableRegistry.addChild(m_robotDrive, m_frontLeft);
    SendableRegistry.addChild(m_robotDrive, m_frontRight);
    SendableRegistry.addChild(m_robotDrive, m_backLeft);
    SendableRegistry.addChild(m_robotDrive, m_backRight);
  }

  public void drive(double xspeed, double zrotation) {
     m_robotDrive.arcadeDrive(
      xspeed, zrotation
    );
  }
}
