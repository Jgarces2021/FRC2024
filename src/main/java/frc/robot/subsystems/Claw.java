package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public class Claw extends Subsystem {

  private CANSparkMax m_fodeIntake;

  public Claw() {
    this.m_fodeIntake = new CANSparkMax(9, MotorType.kBrushed);
  }

  @Override
  public void teleopPeriodic(XboxController m_controller) {
    boolean aButtomPress = m_controller.getAButton();
    boolean bButtonPress = m_controller.getBButton();
    double Fodespeed = 0;
    if (!aButtomPress && bButtonPress) {
      Fodespeed = 0.5;
    } else if (aButtomPress && !bButtonPress) {
      Fodespeed = -0.5;
    }
    m_fodeIntake.set(Fodespeed);
  }
}
