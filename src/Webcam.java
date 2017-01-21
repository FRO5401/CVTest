import org.opencv.core.*;//TODO limit import to the commands being used
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

	NetworkTable.setClientMode();
//	NetworkTable.setTeam(5401); //When RoboRIo is the server
	NetworkTable.setIPAddress("169.254.170.78"); //SERVER ADDRESS
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

    Mat frame = new Mat();

    camera.read(frame);
    System.out.println("Frame Obtained");
    frame = Imgcodecs.imread("RetroflectiveTapeSample.jpg",-1);

    Pipeline mypipeline = new Pipeline();
//  mypipeline.setsource0(frame); //Changed in GRIP 1.5.1
  mypipeline.process(frame);
    int q;
    q=0;
    myTable = NetworkTable.getTable("PipeLineOut");
    while(q<100){
    	output = frameData.toArray(); //http://docs.opencv.org/java/2.4.8/org/opencv/core/MatOfPoint.html
        myTable.putString("X", (String) output.toString());
    	System.out.print(frameData);
    	q++;
    }
//    myTable.putNumber("X", 3);
//    myTable.putNumber("Y", 4);
    }
}
