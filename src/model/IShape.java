package model;

/**
 * Represents a geometric shape in 2D plane.
 */
public interface IShape {

  /**
   * Returns name of the shape.
   *
   * @return .
   */
  String getName();

  /**
   * Returns type of the shape.
   *
   * @return .
   */
  String getType();

  /**
   * Returns horizontal dimension of the shape.
   *
   * @return .
   */
  double getXDim();

  /**
   * Returns vertical dimension of the shape.
   *
   * @return .
   */
  double getYDim();

  /**
   * Returns the position (x,y) of the shape.
   *
   * @return array of size 2 that represents the 2D position of the shape
   */
  double[] getPosition();

  /**
   * Returns the color (red, green, blue) of the shape.
   *
   * @return array of size 3 that represents the color of the shape
   */
  double[] getColor();


  /**
   * Sets a new value for the horizontal dimension of the shape.
   *
   * @param xDim new horizontal dimension value
   */
  void setXDim(double xDim);

  /**
   * Sets a new value for the vertical dimension of the shape.
   *
   * @param yDim new vertical dimension value
   */
  void setYDim(double yDim);

  /**
   * Sets a new position for the shape.
   *
   * @param x new x-coordinate
   * @param y new y-coordinate
   */
  void setPosition(double x, double y);

  /**
   * Sets a new color for the shape.
   *
   * @param r red
   * @param g green
   * @param b blue
   */
  void setColor(double r, double g, double b);
  
  /**
   * Returns a deep copy of the shape.
   *
   * @return copy of shape
   */
  IShape clone();
}
