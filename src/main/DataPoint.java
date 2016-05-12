package main;

import java.util.Locale;
import java.util.Scanner;

/**
 * This class represents a DataPoint from the Orebody database, including information about the iron ore content.
 * @author Jesse
 */
public class DataPoint {

	/**
	 * Variable declaration
	 */
	private String id;
	private double x;
	private double y;
	private double depth;
	private double thickness1;
	private double thickness2;
	private double crude1;
	private double crude2;

	/**
	 * Constructor for a data point
	 * @param id
	 * 		The drill hole ID corresponding to a data point.
	 * @param x
	 * 		The x-coordinate of the drill hole.
	 * @param y
	 * 		The y-coordinate of the drill hole.
	 * @param depth
	 * 		The depth of the ore zones found in the drill hole.
	 * @param thickness1
	 * 		The thickness of ore zone 1.
	 * @param thickness2
	 * 		The thickness of ore zone 2.
	 * @param crude1
	 * 		The crude iron % of ore zone 1.
	 * @param crude2
	 * 		The crude iron % of ore zone 2.
	 */
	public DataPoint(String id, double x, double y, double depth, double thickness1, double crude1, double thickness2, double crude2) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.thickness1 = thickness1;
		this.crude1 = crude1;
		this.thickness2 = thickness2;
		this.crude2 = crude2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + "," + x + "," + y + "," + depth +"," + thickness1 + "," + crude1 + ","
				+ thickness2 + ","  + crude2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other instanceof DataPoint) {
			DataPoint that = (DataPoint) other;
			return (this.id.equals(that.id) && this.x == that.x && this.y == that.y && this.depth == that.depth && this.thickness1 == that.thickness1 && this.thickness2 == that.thickness2 && this.crude1 == that.crude1
					&& this.crude2==that.crude2);
		}	return false;
	}

	/**
	 * This method checks whether a datapoint is within a certain coordinate range.
	 * @param xmin
	 * 		The minimum x-coordinate.
	 * @param xmax
	 * 		The maximum x-coordinate.
	 * @param ymin
	 * 		The minimum y-coordinate.
	 * @param ymax
	 * 		The maximum y-coordinate.
	 * @return
	 * 		true if the DataPoint falls in this coordinate range.
	 * 		false if the DataPoint does not fall in this coordinate range.
	 */
	public boolean inRangeOf(double xmin, double xmax, double ymin, double ymax) {
		return (x < xmax && x >= xmin && y < ymax && y >= ymin);
	}

	/**
	 * Reads out a line in a text file of DataPoints where the parameters are delimited with a comma.
	 * The format of the file is as follows: <br>
	 * - The data point ID on the first token.<br>
	 * - The x-coordinate of the data point on the second token.<br>
	 * - The y-coordinate of the data point on the third token. <br>
	 * - The depth of the ore body in the data point on the fourth token.
	 * - The thickness of the first ore body on the fifth token. <br>
	 * - The thickness of the second ore body on the sixth token. <br>
	 * - The crude iron % of the first ore body on the seventh token.<br>
	 * - The crude iron % of the second ore body on the eight token. <br>
	 * @param sc
	 * 		The scanner to read in the DataPoint with.
	 * @return
	 * 		A DataPoint object with the aforementioned parameters.
	 */
	public static DataPoint read(String point) {
		Scanner sc = new Scanner(point);
		String delimiter = ";";
		try {
			//Scanner reading settings.
			sc.useDelimiter(delimiter);
			sc.useLocale(new Locale("en", "US"));

			String id = sc.next();
			double x = sc.nextDouble();
			double y = sc.nextDouble();
			double depth = sc.nextDouble();
			double thickness1 = sc.nextDouble();
			double crude1 = sc.nextDouble();
			double thickness2 = sc.nextDouble();
			double crude2 = sc.nextDouble();
			DataPoint readP = new DataPoint(id,x,y,depth,thickness1,crude1,thickness2,crude2);
			sc.close();
			return readP;
		}
		catch (Exception e) {
			e.printStackTrace();
		} return null;
	}

	/**
	 * @return
	 * 		the id field of the DataPoint.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id field of the DataPoint to the given name.
	 * @param id
	 * 		The name to set the id field to.	
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 * 		the x field of the DataPoint.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x field of the DataPoint to the given value.
	 * @param x
	 * 		The coordinate to set the x field to.	
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return
	 * 		the y field of the DataPoint
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y field of the DataPoint to the given name.
	 * @param y
	 * 		The coordinate to set the y field to.	
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return
	 * 		the thickness of the 1st ore zone of the DataPoint.
	 */
	public double getThickness1() {
		return thickness1;
	}

	/**
	 * Sets the thickness1 field of the DataPoint to the given value.
	 * @param thickness1
	 * 		The value to set the "thickness of the ore zone 1"- field to.	
	 */
	public void setThickness1(double thickness1) {
		this.thickness1 = thickness1;
	}

	/**
	 * @return
	 * 		the thickness of the 2nd ore zone of the DataPoint.
	 */
	public double getThickness2() {
		return thickness2;
	}

	/**
	 * Sets the thickness2 field of the DataPoint to the given value.
	 * @param thickness2
	 * 		The value to set the "thickness of the ore zone 2"- field to.	
	 */
	public void setThickness2(double thickness2) {
		this.thickness2 = thickness2;
	}

	/**
	 * @return
	 * 		The crude percentage of the 1st ore zone of the DataPoint.
	 */
	public double getCrude1() {
		return crude1;
	}

	/**
	 * Sets the crude1 field of the DataPoint to the given value.
	 * @param crude1
	 * 		The value to set the "crude% of the ore zone 1"- field to.	
	 */
	public void setCrude1(double crude1) {
		this.crude1 = crude1;
	}

	/**
	 * @return
	 * 		The crude percentage of the 2nd ore zone of the DataPoint.
	 */
	public double getCrude2() {
		return crude2;
	}

	/**
	 * Sets the crude2 field of the DataPoint to the given value.
	 * @param crude2
	 * 		The value to set the "crude% of the ore zone 2"- field to.	
	 */
	public void setCrude2(double crude2) {
		this.crude2 = crude2;
	}

	/**
	 * @return
	 * 		The depth of the ore body of the DataPoint.
	 */
	public double getDepth() {
		return depth;
	}

	/**
	 * Sets the depth field of the DataPoint to the given value.
	 * @param depth
	 * 		The value to set the depth of the ore body field to.
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}
}
