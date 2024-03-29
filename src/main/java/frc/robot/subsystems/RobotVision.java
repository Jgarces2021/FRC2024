package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class RobotVision extends Subsystem {

  private Thread m_visionThread;
  private Thread m_dataThread;

  public RobotVision() {
    for (int port = 5800; port <= 5807; port++) {
      PortForwarder.add(port, "limelight.local", port);
    }
    this.m_dataThread = this.initDataThread();
    this.m_visionThread = this.initVisionThread();
  }

  private Thread initDataThread() {
    Thread m_dataThread = new Thread(() -> {
      NetworkTable table = NetworkTableInstance
        .getDefault()
        .getTable("limelight");
      DoubleEntry targetArea = table.getDoubleTopic("ta").getEntry(0);
      Timer m_Timer = new Timer();
      while (!Thread.interrupted()) {
        if (m_Timer.get() > 2) {
          m_Timer.restart();
          System.out.println(targetArea.get());
        }
      }
    });
    m_dataThread.setDaemon(true);
    return m_dataThread;
  }

  private Thread initVisionThread() {
    Thread m_visionThread = new Thread(() -> {
      // Get the UsbCamera from CameraServer
      MjpegServer camera = CameraServer.addServer("limelight.local", 5800);

      // Set the resolution
      camera.setResolution(640, 480);

      // Get a CvSink. This will capture Mats from the camera
      CvSink cvSink = CameraServer.getVideo();
      // Setup a CvSource. This will send images back to the Dashboard
      CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);

      // Mats are very memory expensive. Lets reuse this Mat.
      Mat mat = new Mat();

      // This cannot be 'true'. The program will never exit if it is. This
      // lets the robot stop this thread when restarting robot code or
      // deploying.
      while (!Thread.interrupted()) {
        // Tell the CvSink to grab a frame from the camera and put it
        // in the source mat. If there is an error notify the output.
        if (cvSink.grabFrame(mat) == 0) {
          // Send the output the error.
          outputStream.notifyError(cvSink.getError());
          // skip the rest of the current iteration
          continue;
        }
        // Put a rectangle on the image
        Imgproc.rectangle(
          mat,
          new Point(100, 100),
          new Point(400, 400),
          new Scalar(255, 255, 255),
          5
        );
        // Give the output stream a new image to display
        outputStream.putFrame(mat);
      }
    });
    m_visionThread.setDaemon(true);
    return m_visionThread;
  }

  public void startThreads() {
    m_dataThread.start();
    m_visionThread.start();
  }
}
