package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public class Climber extends Subsystem {

  private CANSparkMax m_rightclimber;
  private CANSparkMax m_leftclimber;

  public Climber() {
    this.m_rightclimber = new CANSparkMax(7, MotorType.kBrushed);
    this.m_leftclimber = new CANSparkMax(8, MotorType.kBrushed);
  }

  @Override
  public void teleopPeriodic(XboxController m_controller) {
    boolean leftbumperPressed = m_controller.getRightBumper();
    double leftclimberSpeed = 0.5;

    if (leftbumperPressed) {
      leftclimberSpeed = 0.5;
    } else {
      leftclimberSpeed = 0;
    }
    m_leftclimber.set(leftclimberSpeed);
  }
}
