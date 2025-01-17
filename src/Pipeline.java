import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import org.opencv.core.*;
import org.opencv.core.Core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;
import org.opencv.objdetect.*;

/**
* Pipeline class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class Pipeline {

	//Outputs
	private Mat cvExtractchannel0Output = new Mat();
	private Mat cvExtractchannel1Output = new Mat();
	private Mat cvSubtractOutput = new Mat();
	private Mat cvThresholdOutput = new Mat();
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	public void process(Mat source0, Mat source1) {
		// Step CV_extractChannel0:
		Mat cvExtractchannel0Src = source0;
		double cvExtractchannel0Channel = 1.0;
		cvExtractchannel(cvExtractchannel0Src, cvExtractchannel0Channel, cvExtractchannel0Output);

		// Step CV_extractChannel1:
		Mat cvExtractchannel1Src = source1;
		double cvExtractchannel1Channel = 2.0;
		cvExtractchannel(cvExtractchannel1Src, cvExtractchannel1Channel, cvExtractchannel1Output);

		// Step CV_subtract0:
		Mat cvSubtractSrc1 = cvExtractchannel0Output;
		Mat cvSubtractSrc2 = cvExtractchannel1Output;
		cvSubtract(cvSubtractSrc1, cvSubtractSrc2, cvSubtractOutput);

		// Step CV_Threshold0:
		Mat cvThresholdSrc = cvSubtractOutput;
		double cvThresholdThresh = 0.0;
		double cvThresholdMaxval = 255.0;
		int cvThresholdType = Imgproc.THRESH_OTSU;
		cvThreshold(cvThresholdSrc, cvThresholdThresh, cvThresholdMaxval, cvThresholdType, cvThresholdOutput);

		// Step Find_Contours0:
		Mat findContoursInput = cvThresholdOutput;
		boolean findContoursExternalOnly = false;
		findContours(findContoursInput, findContoursExternalOnly, findContoursOutput);

		// Step Filter_Contours0:
		ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
		double filterContoursMinArea = 600.0;
		double filterContoursMinPerimeter = 0;
		double filterContoursMinWidth = 0;
		double filterContoursMaxWidth = 1000;
		double filterContoursMinHeight = 0;
		double filterContoursMaxHeight = 1000;
		double[] filterContoursSolidity = {75, 100};
		double filterContoursMaxVertices = 1000000;
		double filterContoursMinVertices = 0;
		double filterContoursMinRatio = 0;
		double filterContoursMaxRatio = 1000;
		filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter, filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight, filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices, filterContoursMinRatio, filterContoursMaxRatio, filterContoursOutput);
		//XXX Preserve the following line to paste into GRIP pipeline
		try {
			Webcam.output = findRectangle(filterContoursOutput.get(0));
		} catch(Exception ee)
			{
			System.out.println(ee);
	    	System.out.println("No Contours");
	    	Webcam.output = new Rect(0,0,0,0);
			}
		//XXX Paste into GRIP pipeline

	}

	/**
	 * This method is a generated getter for the output of a CV_extractChannel.
	 * @return Mat output from CV_extractChannel.
	 */
	public Mat cvExtractchannel0Output() {
		return cvExtractchannel0Output;
	}

	/**
	 * This method is a generated getter for the output of a CV_extractChannel.
	 * @return Mat output from CV_extractChannel.
	 */
	public Mat cvExtractchannel1Output() {
		return cvExtractchannel1Output;
	}

	/**
	 * This method is a generated getter for the output of a CV_subtract.
	 * @return Mat output from CV_subtract.
	 */
	public Mat cvSubtractOutput() {
		return cvSubtractOutput;
	}

	/**
	 * This method is a generated getter for the output of a CV_Threshold.
	 * @return Mat output from CV_Threshold.
	 */
	public Mat cvThresholdOutput() {
		return cvThresholdOutput;
	}

	/**
	 * This method is a generated getter for the output of a Find_Contours.
	 * @return ArrayList<MatOfPoint> output from Find_Contours.
	 */
	public ArrayList<MatOfPoint> findContoursOutput() {
		return findContoursOutput;
	}

	/**
	 * This method is a generated getter for the output of a Filter_Contours.
	 * @return ArrayList<MatOfPoint> output from Filter_Contours.
	 */
	public ArrayList<MatOfPoint> filterContoursOutput() {
		return filterContoursOutput;
	}


	/**
	 * Extracts given channel from an image.
	 * @param src the image to extract.
	 * @param channel zero indexed channel number to extract.
	 * @param dst output image.
	 */
	private void cvExtractchannel(Mat src, double channel, Mat dst) {
		Core.extractChannel(src, dst, (int)channel);
	}

	/**
	 * Subtracts the second Mat from the first.
	 * @param src1 the first Mat
	 * @param src2 the second Mat
	 * @param out the Mat that is the subtraction of the two Mats
	 */
	private void cvSubtract(Mat src1, Mat src2, Mat out) {
		Core.subtract(src1, src2, out);

	}

	/**
	 * Apply a fixed-level threshold to each array element in an image.
	 * @param src Image to threshold.
	 * @param threshold threshold value.
	 * @param maxVal Maximum value for THRES_BINARY and THRES_BINARY_INV
	 * @param type Type of threshold to appy.
	 * @param dst output Image.
	 */
	private void cvThreshold(Mat src, double threshold, double maxVal, int type,
		Mat dst) {
		Imgproc.threshold(src, dst, threshold, maxVal, type);
//	    Imgcodecs.imwrite("threshold.jpg", dst); //debugging tool

	}

	/**
	 * Sets the values of pixels in a binary image to their distance to the nearest black pixel.
	 * @param input The image on which to perform the Distance Transform.
	 * @param type The Transform.
	 * @param maskSize the size of the mask.
	 * @param output The image in which to store the output.
	 */
	private void findContours(Mat input, boolean externalOnly,
		List<MatOfPoint> contours) {
		Mat hierarchy = new Mat();
		contours.clear();
		int mode;
		if (externalOnly) {
			mode = Imgproc.RETR_EXTERNAL;
		}
		else {
			mode = Imgproc.RETR_LIST;
		}
		int method = Imgproc.CHAIN_APPROX_SIMPLE;
		Imgproc.findContours(input, contours, hierarchy, mode, method);

	}


	/**
	 * Filters out contours that do not meet certain criteria.
	 * @param inputContours is the input list of contours
	 * @param output is the the output list of contours
	 * @param minArea is the minimum area of a contour that will be kept
	 * @param minPerimeter is the minimum perimeter of a contour that will be kept
	 * @param minWidth minimum width of a contour
	 * @param maxWidth maximum width
	 * @param minHeight minimum height
	 * @param maxHeight maximimum height
	 * @param Solidity the minimum and maximum solidity of a contour
	 * @param minVertexCount minimum vertex Count of the contours
	 * @param maxVertexCount maximum vertex Count
	 * @param minRatio minimum ratio of width to height
	 * @param maxRatio maximum ratio of width to height
	 */
	private void filterContours(List<MatOfPoint> inputContours, double minArea,
		double minPerimeter, double minWidth, double maxWidth, double minHeight, double
		maxHeight, double[] solidity, double maxVertexCount, double minVertexCount, double
		minRatio, double maxRatio, List<MatOfPoint> output) {
		final MatOfInt hull = new MatOfInt();
		output.clear();
		//operation
//        System.out.println("Unfiltered Contours "+inputContours.size()); //Debugging tool
		for (int i = 0; i < inputContours.size(); i++) {
			final MatOfPoint contour = inputContours.get(i);
			final Rect bb = Imgproc.boundingRect(contour);
//			if (bb.width < minWidth || bb.width > maxWidth) continue;
//			if (bb.height < minHeight || bb.height > maxHeight) continue;
			final double area = Imgproc.contourArea(contour);
			if (area < minArea) continue;
//			if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter) continue;
//			Imgproc.convexHull(contour, hull);
//			MatOfPoint mopHull = new MatOfPoint();
//			mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
//			for (int j = 0; j < hull.size().height; j++) {
//				int index = (int)hull.get(j, 0)[0];
//				double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1]};
//				mopHull.put(j, 0, point);
//			}
//			final double solid = 100 * area / Imgproc.contourArea(mopHull);
//			if (solid < solidity[0] || solid > solidity[1]) continue;
//			if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount)	continue;
//			final double ratio = bb.width / (double)bb.height;
//			if (ratio < minRatio || ratio > maxRatio) continue;
			output.add(contour);
		}
//        System.out.println("Filtered Contours "+ output.size()); //debugging tool

	}

	public void reportFilter(ArrayList<MatOfPoint> output){ //XXX May be deprecated, outputs not useful
	    System.out.println(output);
  }
  
  private Rect findRectangle(MatOfPoint map)
  {
	  Rect targetRect = Imgproc.boundingRect(map);
	  return targetRect;
  }

}
