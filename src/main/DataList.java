package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents an ArrayList of DataPoints.
 * @author Jesse
 *
 */
public class DataList {

	private ArrayList<DataPoint> dataList;

	/**
	 * Constructor for the DataList.
	 */
	public DataList() {
		dataList = new ArrayList<DataPoint>();
	}

	/**
	 * This method adds a DataPoint to the DataList, if it is not on the list already.
	 * @param dataPoint
	 * 		The DataPoint which you want to add to the DataList.
	 */
	public void addPoint(DataPoint dataPoint) {
		if (!this.contains(dataPoint)) {
			getDataList().add(dataPoint);
		} else System.out.println("This DataPoint already exists and will not be added");
	}

	/**
	 * This method checks if a DataPoint is present in the DataList.
	 * @param dataPoint
	 * 		The DataPoint to check presence.
	 * @return
	 * 		true if the DataPoint is found in the DataList.
	 * 		false if the DataPoint is not found.
	 */
	public boolean contains(DataPoint dataPoint) {
		for (int i = 0; i < getDataList().size(); i++) {
			if (getDataList().get(i).equals(dataPoint)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Peek method for the DataList.
	 * @return
	 * 		The DataList.
	 */
	public List<DataPoint> getDataList() {
		return dataList;
	}

	/**
	 * The method generates an ArrayList of DataPoints within a certain coordinate boundaries.
	 * @param xmin
	 * 		minimal x-coordinate of the boundary.
	 * @param xmax
	 * 		maximal x-coordinate of the boundary.
	 * @param ymin
	 * 		minimal y-coordinate of the boundary.
	 * @param ymax
	 * 		maximal y-coordinate of the boundary.
	 * @return
	 * 		An ArrayList of all DataPoints that lie between the specified coordinate boundaries.
	 */
	public ArrayList<DataPoint> PointsBetween(double xmin, double xmax, double ymin, double ymax) {
		ArrayList<DataPoint> PointsBetween = new ArrayList<DataPoint>();
		for (int i = 0; i < getDataList().size(); i++) {
			if (getDataList().get(i).inRangeOf(xmin, xmax, ymin, ymax)) {
				PointsBetween.add(getDataList().get(i));
			}
		}
		return PointsBetween;
	}

	/**
	 * This method reads in a text file which represents a list of DataPoints.
	 * 
	 * @param infile
	 *            The file location of the text file.
	 * @return A DataList with an ArrayList of DataPoints that are placed in the
	 *         text file.
	 */
	public static DataList read(String infile,DataList dataL) {
		try {
			Scanner scan = new Scanner(new File(infile));

			//Skip the header line
			scan.nextLine();

			//For each line is a datapoint, we scan each line as a new datapoint object.
			while(scan.hasNextLine()){
				String point = scan.nextLine();
				DataPoint dataP = DataPoint.read(point);
				dataL.addPoint(dataP);
			}
			scan.close();
			return dataL;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}