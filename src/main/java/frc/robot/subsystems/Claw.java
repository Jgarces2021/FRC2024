package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;

public class Claw extends Subsystem {

  private CANSparkMax m_fodeIntake;

  public Claw() {
    this.m_fodeIntake = new CANSparkMax(Constants.Claw.CLAW_MOTOR_ID, MotorType.kBrushed);
  }

  public void setEnabled(boolean inButton, boolean outButton) {
    double Fodespeed = 0;
    if (inButton && !outButton) {
      Fodespeed = Constants.Claw.CLAW_IN_SPEED;
    } else if (!inButton && outButton) {
      Fodespeed = Constants.Claw.CLAW_OUT_SPEED;
    }
    m_fodeIntake.set(Fodespeed);
  }
}
