package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Shape factory.
 * To extend more types, modify createBasicShape method.
 */
public abstract class ShapeFactory {
  /**
   * Creates a basic shape.
   *
   * @param name name of the shape
   * @param type type of shape to create (rectangle, oval, etc)
   * @param xDim dimension of the shape in horizontal direction.
   * @param yDim dimension of the shape in vertical direction.
   * @param x x-coordinate of the shape
   * @param y y-coordinate of the shape
   * @param r red value of the color
   * @param g blue value of the color
   * @param b green value of the color
   * @throws IllegalArgumentException when type of shape is unknown or null
   */
  public static IShape createBasicShape(String name, String type, double xDim, double yDim,
                                        double x, double y,
                                        double r, double g, double b) {
    if (type == null) {
      throw new IllegalArgumentException("Parameter can't be null.");
    }
    IShape shape = switch (type) {
      case "rectangle" -> new Rectangle(name, xDim, yDim, x, y, r, g, b);
      case "oval" -> new Oval(name, xDim, yDim, x, y, r, g, b);
      default -> null;
    };
    if (shape == null) {
      throw new IllegalArgumentException("This type of shape is currently not supported");
    }
    return shape;
  }

}
