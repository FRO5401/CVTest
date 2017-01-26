import org.opencv.core.*;//TODO eliminate wildcard imports
import org.opencv.videoio.*;
import edu.wpi.first.wpilibj.networktables.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;


public class Webcam {
  static NetworkTable myTable;
  static ArrayList<MatOfPoint> frameData;
  static Rect output;
  static String tableName = "BoilerPipeLineOut";

  public static void main (String args[]){
	final String IP = "10.160.129.155";//XXX
	final int TEAM	= 5401;
	int q;
    q = createNetworkTable(IP, TEAM);
	myTable = NetworkTable.getTable(tableName);

//	System.out.println("Hello, OpenCV");
    // Load the native library.
    System.loadLibrary("opencv_java310");

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
    Pipeline mypipeline = new Pipeline();
    Mat frame = new Mat();
    while(q<2){
//      camera.read(frame);
//      System.out.println("Frame Obtained");

//    frame = Imgcodecs.imread("/home/pi/vision/RetroflectiveTapeSample.jpg",-1);//XXX Use full pathname on pi
      frame = Imgcodecs.imread("RetroflectiveTapeSample.jpg",-1);				//XXX Use for windows test
      mypipeline.process(frame);
      myTable.putNumber("X", output.x);
      myTable.putNumber("Y", output.y);
      myTable.putNumber("height", output.height);
      myTable.putNumber("width", output.width);
      System.out.println("Webcam output: " + output);
	  double center[] = {(double)(output.x+ output.width/2), (double)(output.y+ output.height/2)};
	  Point targetCenter = new Point(center);
      Imgproc.circle(frame, targetCenter, 100, new Scalar(255,0,255), 100);
      q++;
    }
    Imgcodecs.imwrite("camera.jpg", frame);
    System.out.println("Loop complete");
  }

  public static int createNetworkTable(String IP, int TEAM){
	NetworkTable.setClientMode();
	NetworkTable.setTeam(TEAM); //When RoboRIo is the server
	NetworkTable.setIPAddress(IP); //SERVER ADDRESS
	return 0;
  }
  
  
}
