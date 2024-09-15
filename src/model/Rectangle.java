package model;

import java.text.DecimalFormat;

/**
 * Represents a rectangle shape.
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructor. It has the default specifier, so it can only be instantiated through ShapeFactory.
   *
   * @param name name of the shape
   * @param xDim dimension of the shape in horizontal direction.
   * @param yDim dimension of the shape in vertical direction.
   * @param x x-coordinate of the shape
   * @param y y-coordinate of the shape
   * @param r red value of the color
   * @param g blue value of the color
   * @param b green value of the color
   */
  Rectangle(String name, double xDim, double yDim, double x, double y,
            double r, double g, double b) {
    super(name, "rectangle", xDim, yDim, x, y, r, g, b);
  }

  /**
   * Copy constructor. Only used for cloning itself.
   *
   * @param shape another shape
   */
  private Rectangle(IShape shape) {
    super(shape.getName(), shape.getType(), shape.getXDim(), shape.getYDim(),
          shape.getPosition()[0], shape.getPosition()[1],
          shape.getColor()[0], shape.getColor()[1], shape.getColor()[2]);
  }

  @Override
  public String toString() {
    String position = String.format("(%.2f, %.2f)", this.getPosition()[0],
        this.getPosition()[1]);
    String color = String.format("(%.2f, %.2f, %.2f)", this.getColor()[0],
        this.getColor()[1], this.getColor()[2]);
    DecimalFormat dim = new DecimalFormat("0.00");
    StringBuilder result = new StringBuilder();
    result.append("Name: ").append(this.getName()).append("\n");
    result.append("Type: ").append(this.getType()).append("\n");
    result.append("Min corner: ").append(position).append(", ");
    result.append("Width: ").append(dim.format(this.getXDim())).append(", ");
    result.append("Height: ").append(dim.format(this.getYDim())).append(", ");
    result.append("Color: ").append(color);
    return result.toString();
  }

  @Override
  public IShape clone() {
    return new Rectangle(this);
  }
}
