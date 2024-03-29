package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public class Shooter extends Subsystem {

  private final CANSparkMax m_topIntake;
  private final CANSparkMax m_bottomIntake;
  private boolean isShooting;

  public Shooter() {
    this.m_topIntake = new CANSparkMax(5, MotorType.kBrushed);
    this.m_bottomIntake = new CANSparkMax(6, MotorType.kBrushed);
  }

  @Override
  public void teleopPeriodic(XboxController m_controller) {
    double rightTrigger = m_controller.getRightTriggerAxis();

    if (m_controller.getRightBumperPressed()) {
      this.shootAsync();
    }

    if (!this.isShooting) {
      m_topIntake.set(rightTrigger);
      m_bottomIntake.set(rightTrigger);
    }
  }

  public void shootAsync() {
    if (isShooting) {
      return;
    }

    new Thread(() -> {
      try {
        shoot();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    })
      .start();
  }

  private void shoot() throws InterruptedException {
    this.isShooting = true;
    m_topIntake.set(-1.0);
    m_bottomIntake.set(0);

    Thread.sleep(500);

    m_bottomIntake.set(-1.0);

    Thread.sleep(500);

    m_bottomIntake.set(0);
    m_topIntake.set(0);
    isShooting = false;
  }
}
