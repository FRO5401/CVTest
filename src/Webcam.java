import org.opencv.core.*;//TODO eliminate wildcard imports
import org.opencv.videoio.*;
import edu.wpi.first.wpilibj.networktables.*;
import org.opencv.imgcodecs.Imgcodecs;
import java.util.ArrayList;


public class Webcam {
  static NetworkTable myTable;
  static ArrayList<MatOfPoint> frameData;
  static Object[] output;

//  public void main (String args[]){
  public static void main (String args[]){
	final String IP = "169.254.170.78";//XXX
	final int TEAM	= 5401;
	int q;
    q = createNetworkTable(IP, TEAM);
	myTable = NetworkTable.getTable("PipeLineOut");

	System.out.println("Hello, OpenCV");
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
    camera.read(frame);
    System.out.println("Frame Obtained");
//    frame = Imgcodecs.imread("/home/pi/vision/RetroflectiveTapeSample.jpg",-1);
    frame = Imgcodecs.imread("RetroflectiveTapeSample.jpg",-1);
//  mypipeline.setsource0(frame); //Changed in GRIP 1.5.1
    mypipeline.process(frame);
    while(q<100){
    	output = frameData.toArray(); //http://docs.opencv.org/java/2.4.8/org/opencv/core/MatOfPoint.html
        myTable.putString("X", (String) output.toString());
    	System.out.println(frameData);
    	q++;
    }
//    myTable.putNumber("X", 3);
//    myTable.putNumber("Y", 4);
    }

  public static int createNetworkTable(String IP, int TEAM){
	NetworkTable.setClientMode();
	NetworkTable.setTeam(TEAM); //When RoboRIo is the server
	NetworkTable.setIPAddress(IP); //SERVER ADDRESS
	return 0;
  }
  
  
}
