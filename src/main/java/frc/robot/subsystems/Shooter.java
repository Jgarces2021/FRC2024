package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;

public class Shooter extends Subsystem {

  private final CANSparkMax m_topIntake;
  private final CANSparkMax m_bottomIntake;
  private boolean isShooting;

  public Shooter() {
    this.m_topIntake = new CANSparkMax(Constants.Shooter.SHOOTER_TOP_MOTOR_ID, MotorType.kBrushed);
    this.m_bottomIntake = new CANSparkMax(Constants.Shooter.SHOOTER_BOTTOM_MOTOR_ID, MotorType.kBrushed);
  }

  public void setIntakeSpeed(double speed) {
    if (!this.isShooting) {
      m_topIntake.set(speed);
      m_bottomIntake.set(speed);
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

    Thread.sleep(Constants.Shooter.SHOOTER_TOP_MOTOR_SPINUP_TIME);

    m_bottomIntake.set(-1.0);

    Thread.sleep(Constants.Shooter.SHOOTER_RELEASE_TIME);

    m_bottomIntake.set(0);
    m_topIntake.set(0);
    isShooting = false;
  }
}
