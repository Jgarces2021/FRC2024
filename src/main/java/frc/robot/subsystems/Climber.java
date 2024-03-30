package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;

public class Climber extends Subsystem {

  private CANSparkMax m_rightclimber;
  private CANSparkMax m_leftclimber;

  public Climber() {
    this.m_rightclimber = new CANSparkMax(Constants.Climber.CLIMBER_RIGHT_MOTOR_ID, MotorType.kBrushed);
    this.m_leftclimber = new CANSparkMax(Constants.Climber.CLIMBER_LEFT_MOTOR_ID, MotorType.kBrushed);
  }

  public void setEnabled(boolean enabled) {
    double climberSpeed = 0;

    if (enabled) {
      climberSpeed = Constants.Climber.CLIMBER_SPEED;
    }

    m_rightclimber.set(climberSpeed);
    m_leftclimber.set(climberSpeed);
  }
}
