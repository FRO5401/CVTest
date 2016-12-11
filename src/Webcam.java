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

    camera.read(frame);
    System.out.println("Frame Obtained");

    Pipeline mypipeline = new Pipeline();
    mypipeline.setsource0(frame);
    mypipeline.process();
    }
}
