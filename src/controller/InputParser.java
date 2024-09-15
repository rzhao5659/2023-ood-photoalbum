package controller;

import java.io.InputStream;
import java.util.Scanner;
import java.util.StringJoiner;
import model.IPhotoAlbumApp;

/**
 * An input parser that parses inputs that specify commands with the following pattern delimited by space:
 * operationName parameter1 parameter2 parameter3 ...
 * The standard names for the operations are: color, shape, move, resize, remove, snapshot.
 */
public class InputParser implements IInputParser {

  @Override
  public void parse(InputStream in, IPhotoAlbumApp model) {
    Scanner scanner = new Scanner(in);
    while (scanner.hasNextLine()) {
      String command = scanner.nextLine();
      command = command.strip();
      invokeCommand(command, model);
    }
  }

  /**
   * Helper method that parses a string that represents a single model operation.
   *
   * @param command string that represents a single model operation
   * @param model model to modify in place
   */
  private void invokeCommand(String command, IPhotoAlbumApp model) {
    String[] args = command.split("\\s+");
    String methodName = args[0];
    //Invoke method according methodName.
    switch (methodName.toLowerCase()) {
      case "color" -> model.setShapeColor(args[1], Double.parseDouble(args[2]) / 255,
          Double.parseDouble(args[3]) / 255, Double.parseDouble(args[4]) / 255);
      case "shape" ->
          model.createBasicShape(args[1], args[2], Double.parseDouble(args[5]),
              Double.parseDouble(args[6]), Double.parseDouble(args[3]),
              Double.parseDouble(args[4]), Double.parseDouble(args[7]) / 255,
              Double.parseDouble(args[8]) / 255, Double.parseDouble(args[9]) / 255);
      case "move" -> model.setShapePosition(args[1],
          Double.parseDouble(args[2]), Double.parseDouble(args[3]));
      case "resize" -> {
          model.setShapeXDim(args[1], Double.parseDouble(args[2]));
          model.setShapeYDim(args[1], Double.parseDouble(args[3]));
      }
      case "remove" -> model.removeShape(args[1]);
      case "snapshot" -> {
          if (args.length == 1) {
            //Empty description snapshot.
            model.takeSnapshot("");
          } else {
            //Concatenate the other arguments into a single string.
            StringJoiner description = new StringJoiner(" ");
            for (int i = 1; i < args.length; i++) {
              description.add(args[i]);
            }
            model.takeSnapshot(description.toString());
          }
      }
    }
  }
}
