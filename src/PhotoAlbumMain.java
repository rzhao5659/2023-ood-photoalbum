import controller.IPhotoAlbumController;
import controller.InputParser;
import controller.PhotoAlbumControllerGUI;
import controller.PhotoAlbumControllerText;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.io.File;
import model.PhotoAlbumApp;
import view.GUI.PhotoAlbumViewGUI;
import view.HTML.PhotoAlbumViewHTML;

/**
 * Main class of PhotoAlbum app, which contains the entry point.
 */
public class PhotoAlbumMain {

  /**
   * Data Structures for Parsing Console Arguments:
   * - namedOptionToValidationFunction: The keys represents all valid named options for the program.
   * It maps to their corresponding validation function for the option's value.
   * - namedOptionNameList: The valid named options names.
   * - unnamedOptionToValidationFunction: The keys represents all valid unnamed options for the program.
   * It maps to their corresponding validation function for the unnamed option pair of values.
   * - unnamedOptionNameList: Associate the unnamed options with a name.
   * - aliasToMain: Maps alias names of an option to the main name of the option.
   * If null, it means the parameter name is already the main name.
   * - optionToValues: Stores all option and its values.
   */
  private static Map<String, Function<String, Boolean>> namedOptionToValidationFunction = new HashMap<>();
  private static List<String> namedOptionNameList = new LinkedList<>();
  private static List<BiFunction<String, String, Boolean>> unnamedOptionValidationFunctionList = new LinkedList<>();
  private static List<String> unnamedOptionNameList = new LinkedList<>();
  private static Map<String, String> aliasToMain = new HashMap<>();
  private static Map<String, List<String>> optionToValues = new HashMap<>();

  private static boolean DEBUG_MODE = false;

  /**
   * The entry point of MVC (Main).
   *
   * @param args console arguments
   * @throws FileNotFoundException should never happen as arguments related to files are validated before using them.
   *
   */
  public static void main(String[] args) throws FileNotFoundException {
    consoleArgsParserSetUp();
    if (validConsoleArgs(args)) {
      if (DEBUG_MODE) {
        System.out.println("Great!");
        optionToValues.forEach((key, value) -> System.out.println(key + " " + value));
        System.out.println("\n");
      } else {

        //All options and its corresponding values is in optionToValues.
        File inputFile = new File(optionToValues.get("-in").get(0));
        String viewOption = optionToValues.get("-view").get(0);
        int viewScreenXDim = 1000; //px
        int viewScreenYDim = 1000; //px
        if (optionToValues.get("-dimension").size() != 0) {
          viewScreenXDim = Integer.parseInt(optionToValues.get("-dimension").get(0));
          viewScreenYDim = Integer.parseInt(optionToValues.get("-dimension").get(1));
        }
        File outputFile;
        if (optionToValues.get("-out").size() == 0) {
          outputFile = new File("photo_album.html");
        } else {
          outputFile = new File(optionToValues.get("-out").get(0));
        }

        //Based on view option, launch accordingly.
        if (viewOption.equals("graphical")) {
          IPhotoAlbumController photoAlbumController = new PhotoAlbumControllerGUI(new PhotoAlbumApp(),
              new PhotoAlbumViewGUI(viewScreenXDim, viewScreenYDim), new FileInputStream(inputFile), new InputParser());
          photoAlbumController.go();
        } else {
          IPhotoAlbumController photoAlbumController = new PhotoAlbumControllerText(new PhotoAlbumApp(),
              new PhotoAlbumViewHTML(viewScreenXDim, viewScreenYDim), new PrintWriter(outputFile), new FileInputStream(inputFile), new InputParser());
          photoAlbumController.go();
        }
      }
    }
  }

  /**
   * Initiates all data structures used for parsing console arguments.
   */
  private static void consoleArgsParserSetUp() {
    namedOptionToValidationFunction.put("-in", PhotoAlbumMain::validInputFile);
    namedOptionToValidationFunction.put("-out", PhotoAlbumMain::validOutputFile);
    namedOptionToValidationFunction.put("-view", PhotoAlbumMain::validView);
    namedOptionNameList.add("-in");
    namedOptionNameList.add("-out");
    namedOptionNameList.add("-view");
    unnamedOptionValidationFunctionList.add(PhotoAlbumMain::validScreenDimension);
    unnamedOptionNameList.add("-dimension");

    aliasToMain.put("-v", "-view");

    for (String option : namedOptionNameList) {
      optionToValues.put(option, new LinkedList<>());
    }
    for (String option : unnamedOptionNameList) {
      optionToValues.put(option, new LinkedList<>());
    }
  }

  /**
   * Parses and validates the console arguments.
   * The options provided must be valid, the values for the options must be valid, and the options provided
   * contain all the required options.
   * If the user provides duplicated values for an option, the program will use the first pair and ignore the others.
   *
   * @return true if valid
   */
  private static boolean validConsoleArgs(String[] args) {

    /*
     * Pseudocode for my algorithm to parse args/options:
     * 1. Scans through args linearly:
     *    For each arg, map to the main parameter name (to avoid aliases),
     *    validate that it's a valid option and its value is valid.
     *    If it's, then store values.
     * 2. Validate that all the required options are provided.
     *
     */

     if (args.length % 2 == 1) {
       System.out.println("All arguments must be in pairs.");
       return false;
     }

     //1. Validate console arguments: valid options and correct values format.
      for (int i = 0; i < args.length; i += 2) {
        //Convert the option's name into its main name.
        String option = aliasToMain.containsKey(args[i]) ? aliasToMain.get(args[i]) : args[i];
        String optionValue = args[i + 1];

        //Check if it's a valid named-option.
        boolean validNamedOptionFlag = namedOptionToValidationFunction.containsKey(option)
            && namedOptionToValidationFunction.get(option).apply(optionValue);
        if (validNamedOptionFlag) {
          //Store the provided value.
          optionToValues.put(option, new LinkedList<>());
          optionToValues.get(option).add(optionValue);
        }

        //Check if it's a valid unnamed-option.
        boolean validUnnamedOptionFlag = false;
        for (int j = 0; j < unnamedOptionValidationFunctionList.size(); j++) {
          if (unnamedOptionValidationFunctionList.get(j).apply(option, optionValue)) {
            //Store the provided values.
            optionToValues.put(unnamedOptionNameList.get(j), new LinkedList<>());
            optionToValues.get(unnamedOptionNameList.get(j)).add(option);
            optionToValues.get(unnamedOptionNameList.get(j)).add(optionValue);
            validUnnamedOptionFlag = true;
            break;
          }
        }

        //Non-valid option.
        if (!validUnnamedOptionFlag && !validNamedOptionFlag) {
          System.out.println("Invalid pair of arguments: " + args[i] + " " + args[i + 1]);
          return false;
        }
      }


      //2. Verify that the required options are provided.
      if (optionToValues.get("-in").size() == 0 || optionToValues.get("-view").size() == 0) {
        System.out.println("Missing required arguments: input file and view mode.");
        return false;
      }

      return true;
  }

  /**
   * Validates the value provided to the option "-in", which should represent an existing file name.
   *
   * @param fileName an existing file.
   * @return true if the file exists.
   */
  private static boolean validInputFile(String fileName) {
    File file = new File(fileName);
    return file.exists();
  }

  /**
   * Validates the value provided to the option "-out", which should
   * be a html file with a valid directory.
   *
   * @param fileName output file
   * @return true if the output file is valid
   */
  private static boolean validOutputFile(String fileName) {
    if (!fileName.endsWith(".html")) {
      return false;
    }
    //Get the directory.
    //If the fileName is a directory, see if the parent directory exists.
    //If the fileName is just a file name, then directoryWithoutFile will be equal to fileName.
    String directoryWithoutFile = fileName.replaceAll("[/\\\\]([^\\\\/])+\\.html", "");
    return directoryWithoutFile.equals(fileName) || (new File(directoryWithoutFile)).exists();
  }

  /**
   * Validates the value provided to the option "-view" or "-v", which should
   * be either "graphical" or "web".
   *
   * @param viewMode view mode, either "graphical" or "web"
   * @return true if valid
   */
  private static boolean validView(String viewMode) {
    return viewMode.equalsIgnoreCase("graphical")
        || viewMode.equalsIgnoreCase("web");
  }

  /**
   * Validates the value provided to the canvas's dimension for drawing shapes.
   * Both values provided must be integers.
   *
   * @param screenXDim screen horizontal dimension in px
   * @param screenYDim screen vertical dimension in px
   * @return true if valid
   */
  private static boolean validScreenDimension(String screenXDim, String screenYDim) {
    return screenXDim.matches("\\d+") && screenYDim.matches("\\d+");
  }



}
