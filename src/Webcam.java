import org.opencv.core.Mat;
import org.opencv.videoio.*;
//import java.io.IOException;
import edu.wpi.first.wpilibj.networktables.*;
//import edu.wpi.first.wpilibj.tables.*;

public class Webcam {
  static NetworkTable myTable;

//  public void main (String args[]){
  public static void main (String args[]){

	NetworkTable.setClientMode();
	NetworkTable.setTeam(5401);

	System.out.println("Hello, OpenCV");
    // Load the native library.
    System.loadLibrary("opencv_java310");
    String OS = System.getProperty("os.name");
    System.out.println("x" + OS + "x");

    VideoCapture camera = new VideoCapture(0);
/*    try{
        Thread.sleep(1500);
    }catch(InterruptedException e){
        System.out.println("got interrupted!");
    }*/
    camera.open(0); //Useless
    if(!camera.isOpened()){
        System.out.println("Camera Error");
    }
    else{
        System.out.println("Camera OK?");
    }

    Mat frame = new Mat();

    camera.read(frame);
    System.out.println("Frame Obtained");

    Pipeline mypipeline = new Pipeline();
    mypipeline.setsource0(frame);
    mypipeline.process();

//    NetworkTable publishingTable = NetworkTable.getTable("CameraPublisher");
    myTable = NetworkTable.getTable("PipeLineOut");
    myTable.putNumber("X", 3);
    myTable.putNumber("Y", 4);
    }
}
