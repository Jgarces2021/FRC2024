package frc.robot;

public class Constants {
    public static class Claw {
        public static int CLAW_MOTOR_ID = 9;

        public static double CLAW_IN_SPEED = 0.5;
        public static double CLAW_OUT_SPEED = -0.5;
    }

    public static class Climber {
        public static int CLIMBER_LEFT_MOTOR_ID = 8;
        public static int CLIMBER_RIGHT_MOTOR_ID = 7;

        public static double CLIMBER_SPEED = 0.5;
    }

    public static class Drivetrain {
        public static int DRIVETRAIN_FRONT_LEFT_MOTOR_ID = 2;
        public static int DRIVETRAIN_FRONT_RIGHT_MOTOR_ID = 3;
        public static int DRIVETRAIN_BACK_LEFT_MOTOR_ID = 1;
        public static int DRIVETRAIN_BACK_RIGHT_MOTOR_ID = 4;

        public static double CLIMBER_SPEED = 0.5;
    }

    public static class Shooter {
        public static int SHOOTER_TOP_MOTOR_ID = 5;
        public static int SHOOTER_BOTTOM_MOTOR_ID = 6;

        public static long SHOOTER_TOP_MOTOR_SPINUP_TIME = 500;
        public static long SHOOTER_RELEASE_TIME = 500;
    }
}
