import org.opencv.core.Mat;
//import org.opencv.highgui.Highgui;
//import org.opencv.highgui.VideoCapture;
import org.opencv.videoio.*;
import org.opencv.imgcodecs.Imgcodecs;
//import java.io.IOException;

//Running on Raspberry Pi 3 with Pi Camera module requires adding the camera module to the kernel.
//This is done with the command $sudo modprobe bcm2835-v4l2
//Sending this to the command line was removed here because its better done through a shell script than invoking sudo within the code

public class Webcam {

    public static void main (String args[]){

    System.out.println("Hello, OpenCV");
    // Load the native library.
    System.loadLibrary("opencv_java310");
    String OS = System.getProperty("os.name");
    System.out.println("x" + OS + "x");
/*  //Following lines are an internal activation of the PiCamera module within linux.
    //Removing this and adding a note above because this is better accomplished through shell scripting
    if(OS.contains("inu")){
        System.out.println("Linux");
        Runtime rt = Runtime.getRuntime();
        try{
            Process pr = rt.exec("sudo modprobe bcm2835-v4l2");
            Process pr = rt.exec("echo Camera Enabled");
        }catch(IOException f){
            System.out.println("commandline exception!");
        }
    }
    else{
        System.out.println("Not Linux?");
    }
*/
    VideoCapture camera = new VideoCapture(0);
    try{
        Thread.sleep(1500);
    }catch(InterruptedException e){
        System.out.println("got interrupted!");
    }
    camera.open(0); //Useless
    if(!camera.isOpened()){
        System.out.println("Camera Error");
    }
    else{
        System.out.println("Camera OK?");
    }

    Mat frame = new Mat();

    //camera.grab();
    //System.out.println("Frame Grabbed");
    //camera.retrieve(frame);
    //System.out.println("Frame Decoded");

    camera.read(frame);
    System.out.println("Frame Obtained");

    /* No difference
    camera.release();
    */

    System.out.println("Captured Frame Width " + frame.width());

    Imgcodecs.imwrite("camera.jpg", frame);
    System.out.println("OK");
    }
}
