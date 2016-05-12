package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Application {

	private static DataList dataList = new DataList();
	private static DataList	gridList = new DataList();
	private static double sizePointX;
	private static double sizePointY;

	static Scanner sc = new Scanner(System.in);

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		startmenu();
	}

	/**
	 * This method controls the command-line interface of the application and lets the user navigate through the different possible functions.
	 */
	private static void startmenu(){
		System.out.println("Main menu, please choose one of the following numbers");
		System.out.println("1. Import data points from file");
		System.out.println("2. Manually add a data point");
		System.out.println("3. Show data points");
		System.out.println("4. Calculate grid");
		System.out.println("5. Show grid points");
		System.out.println("6. Save grid to file");
		System.out.println("7. Close application");

		if (sc.hasNextInt()){
			int choice = sc.nextInt();
			switch (choice)
			{
			case 7:
				stop();
				break;
			case 6:
				saveGrid();
				break;
			case 5:
				showGrid();
				break;
			case 4:
				calculateGrid();
				break;
			case 3:
				showData();
				break;
			case 2:
				addDataPoint();
				break;
			case 1:
				readDataPoints();
				break;
			default:
				startmenu();
			}
		}

	}

	/**
	 * This method reads out DataPoints from a file. This file must contain the DataPoints in the following syntax:
	 * - One data point per line.
	 * - The different values for a data point are delimited by a comma.
	 * - The following order is applied: ID,x,y,depth,thickness1,thickness2,crude1,crude2
	 */
	private static void readDataPoints() {
		System.out.println("Give the name of the text file in the folder: C:/Users/Jesse/Documents/Universiteit/4e jaar/Q4 Field Exploration Project/");
		String file = sc.next();
		DataList.read(file,dataList);

		System.out.println("Reading in data succeeded. The following data points are in the system.");
		showData();
	}

	/**
	 * This method allows the user to manually add a DataPoint to the application through the command line. 
	 * The command line guides the user through the different values (s)he needs to provide.
	 */
	private static void addDataPoint() {
		System.out.println("Give the ID of the Data Point");
		String id = sc.next();
		System.out.println("Give the x coordinate of the data point");
		checkNextDouble(sc);
		double x = sc.nextDouble();
		System.out.println("Give the y coordinate of the data point");
		checkNextDouble(sc);
		double y = sc.nextDouble();
		System.out.println("Given the depth of the ore body");
		checkNextDouble(sc);
		double depth = sc.nextDouble();
		System.out.println("Give the thickness of ore zone 1");
		checkNextDouble(sc);
		double thickness1 = sc.nextDouble();
		System.out.println("Give the crude iron % of ore zone 1");
		checkNextDouble(sc);
		double crude1 = sc.nextDouble();
		System.out.print("Give the thickness of ore zone 2");
		checkNextDouble(sc);
		double thickness2 = sc.nextDouble();
		System.out.println("Give the crude iron % of ore zone 2");
		checkNextDouble(sc);
		double crude2 = sc.nextDouble();
		DataPoint datapoint = new DataPoint(id,x,y,depth,thickness1,thickness2,crude1,crude2);
		dataList.addPoint(datapoint);
		startmenu();
	}

	/**
	 * This method checks if the next token in the scanner can be seen as a double.
	 * @param sc
	 * 		The scanner we're working with.
	 */
	private static void checkNextDouble(Scanner sc){
		while (!sc.hasNextDouble()){
			sc.next();
		}
	}

	/**
	 * This method checks if the next token in the scanner can be seen as an int.
	 * @param sc
	 * 		The scanner we're working with.
	 */
	private static void checkNextInt(Scanner sc){
		while (!sc.hasNextInt()){
			sc.next();
		}
	}

	/**
	 * Shows the user a list of all data points currently in the system.
	 */
	private static void showData() {
		System.out.println("ID,x-coordinate,y-coordinate,depth,thickness1,crude1,thickness2,crude2");
		for(int i=0;i< dataList.getDataList().size(); i++){
			DataPoint dataPoint = dataList.getDataList().get(i);
			System.out.println(dataPoint.toString());
		}
		startmenu();
	}

	/**
	 * This method calculates the grid data for a user-specified grid.
	 */
	private static void calculateGrid() {
		generateGrid();

		for (int i = 0; i < gridList.getDataList().size();i++){
			double xmin = gridList.getDataList().get(i).getX() - 0.5 * sizePointX;
			double xmax = xmin + sizePointX;
			double ymin = gridList.getDataList().get(i).getY() - 0.5 * sizePointY;
			double ymax = ymin + sizePointY;
			ArrayList<DataPoint> pointsBetween = dataList.PointsBetween(xmin, xmax, ymin, ymax);
			double depth = 0;
			double crude1 = 0;
			double crude2 = 0;
			double thickness1 = 0;
			double thickness2 = 0;
			if (pointsBetween.size()>0){
				for (int n = 0; n < pointsBetween.size();n++){
					depth += pointsBetween.get(n).getDepth();
					crude1 += pointsBetween.get(n).getCrude1();
					crude2 += pointsBetween.get(n).getCrude2();
					thickness1 += pointsBetween.get(n).getThickness1();
					thickness2 += pointsBetween.get(n).getThickness2();	
				}
				depth = depth/pointsBetween.size();
				crude1 = crude1/pointsBetween.size();
				crude2 = crude2/pointsBetween.size();
				thickness1 = thickness1/pointsBetween.size();
				thickness2 = thickness2/pointsBetween.size();

				gridList.getDataList().get(i).setDepth(depth);
				gridList.getDataList().get(i).setCrude1(crude1);
				gridList.getDataList().get(i).setCrude2(crude2);
				gridList.getDataList().get(i).setThickness1(thickness1);
				gridList.getDataList().get(i).setThickness2(thickness2);
			}
		}

		System.out.println("Grid point values calculated. You now have the following grid:");
		showGrid();
	}

	/**
	 * This method asks the user a few questions in order to create a grid.
	 */
	private static void generateGrid(){
		System.out.println("How many grid points do you want in the x-direction?");
		checkNextInt(sc);
		int sizeX = sc.nextInt();
		System.out.println("How many grid points do you want in the y-direction?");
		checkNextInt(sc);
		int sizeY = sc.nextInt();
		System.out.println("What is the minimum x-coordinate in the grid?");
		checkNextDouble(sc);
		double minX = sc.nextDouble();
		System.out.println("What is the maximum x-coordinate in the grid?");
		checkNextDouble(sc);
		double maxX = sc.nextDouble();
		System.out.println("What is the minimum y-coordinate in the grid?");
		checkNextDouble(sc);
		double minY = sc.nextDouble();
		System.out.println("What is the maximum y-coordinate in the grid?");
		checkNextDouble(sc);
		double maxY = sc.nextDouble();

		sizePointX = (maxX-minX)/sizeX;
		sizePointY = (maxY-minY)/sizeY;

		for (int i=0;i<sizeX;i++){
			for (int n=0;n<sizeY;n++){
				String id = new String(+i+""+n);
				DataPoint dataPoint = new DataPoint(id, minX + (i + 0.5) * sizePointX, minY + (n + 0.5) * sizePointY, 0, 0, 0, 0, 0);
				gridList.addPoint(dataPoint);
			}
		}
	}

	/**
	 * Shows the user a list of all grid data point currently in the system.
	 * Will be empty if the user has not calculated the grid yet.
	 */
	private static void showGrid() {
		System.out.println("grid point ID,x-coordinate,y-coordinate, depth,thickness1,crude1,thickness2,crude2");
		for (int i=0;i<gridList.getDataList().size();i++){
			DataPoint gridPoint = gridList.getDataList().get(i);
			System.out.println(gridPoint.toString());
		}
		startmenu();	
	}

	/**
	 * Ends the application.
	 */
	private static void stop() {

	}

	/**
	 * This method asks the user for a file name for a new file. Then it returns
	 * the File.
	 * 
	 * @return A new file with the filename from the input.
	 */
	private static File returnFile() {
		System.out.println("Give a file name for the data to be stored at:");
		Scanner filesc = new Scanner(System.in);
		String fileName = filesc.next();
		if (!fileName.endsWith(".csv")) {
			fileName += ".csv";
		}
		fileName = "C:/Users/Jesse/Documents/Universiteit/4e jaar/Q4 Field Exploration Project/Java code/Orebody Model/" + fileName;
		filesc.close();
		return new File(fileName);
	}

	/**
	 * This method updates an existing file with extra text. It asks for a
	 * string using the fileInput() method. It then splits this string by
	 * recognizing "/n". Every time "/n" is spotted, a new line is started.
	 */
	private static void saveGrid() {
		File file = returnFile();
		String res = fileInput();
		String[] string = res.split("/n");
		writeFile(file, string);

		startmenu();
	}

	/**
	 * This method generates a string with all the information about a House, as
	 * given in the format of the assignment.
	 * 
	 * @return A big string with all the information that has been given in
	 *         AddKoop or AddHuur.
	 */
	private static String fileInput() {
		String res = "";
		for (int i=0; i< gridList.getDataList().size();i++){
			res += gridList.getDataList().get(i).toString() +"/n";
		}
		return res;
	}

	/**
	 * This method actually writes the string generated in fileInput to the
	 * given file.
	 * 
	 * @param file
	 *            The file to add text to.
	 * @param string
	 *            The text to add to the file.
	 */
	private static void writeFile(File file, String[] string) {
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
			for (int i = 0; i < string.length; i++) {
				bufferedWriter.write(string[i]);
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}