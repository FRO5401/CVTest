import org.opencv.core.*;//TODO eliminate wildcard imports
import org.opencv.videoio.*;
import edu.wpi.first.wpilibj.networktables.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;


public class Webcam {
  static NetworkTable myTable;
  static NetworkTable inCommand;
  static ArrayList<MatOfPoint> frameData;
  static Rect output;
  static String tableName = "BoilerPipeLineOut";
  static String commandName = "BoilerCommands";

  public static void main (String args[]){
	final String IP = "10.99.98.105";//XXX
	final int TEAM	= 9998;//XXX This is for the Tim radio
	int q = 0;
    int z = createNetworkTable(IP, TEAM);
	myTable = NetworkTable.getTable(tableName);
	inCommand = NetworkTable.getTable(commandName);
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
    boolean valid = false;
    boolean stopCmd = false;
    while(!stopCmd){
      camera.read(frame);
      System.out.println("Frame Obtained");
//      frame = Imgcodecs.imread("RetroflectiveTapeSample.jpg",-1);				//XXX Use for windows test
      mypipeline.process(frame);
      try {
    	  if(output.area() > 0) {
    		  valid = true;
    	  } else valid = false;
      }catch(Exception eNoOutput){
  	    	System.out.println("No target");
  	    	valid = false;
    	  }
      myTable.putBoolean("valid", valid);
      if (valid){
          myTable.putNumber("q", q);
          myTable.putNumber("X", output.x);
          myTable.putNumber("Y", output.y);
          myTable.putNumber("height", output.height);
          myTable.putNumber("width", output.width);
          System.out.println("q " + q);
          System.out.println("Webcam output: " + output);
      } else {
          myTable.putNumber("X", -99);
          myTable.putNumber("Y", -99);
          myTable.putNumber("height", -99);
          myTable.putNumber("width", -99);
          System.out.println("q " + q);
          System.out.println("Webcam output: " + output);
      } 
      stopCmd = inCommand.getBoolean("Cmd", false);
      q++;    	  
      if (q > 1000) {
    	  stopCmd = true;
      }
    }
//    Imgcodecs.imwrite("camera.jpg", frame);
    System.out.println("Loop complete");
  }

  public static int createNetworkTable(String IP, int TEAM){
	NetworkTable.setClientMode();
//	NetworkTable.setTeam(TEAM); //When RoboRIo is the server
	NetworkTable.setIPAddress(IP); //SERVER ADDRESS
	return 0;
  }
  
  
}
