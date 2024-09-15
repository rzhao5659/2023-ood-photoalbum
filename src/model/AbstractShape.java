package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Abstract shape that implements common methods for all concrete shapes.
 */
public abstract class AbstractShape implements IShape {
  private final String name;
  private final String type;
  private double xDim;
  private double yDim;
  private double[] position;
  private double[] color;

  /**
   * Constructor.
   *
   * @param name name of shape
   * @param type type of shape
   * @param xDim dimension of the shape in horizontal direction.
   * @param yDim dimension of the shape in vertical direction.
   * @param x x-coordinate of the shape
   * @param y y-coordinate of the shape
   * @param r red value of the color
   * @param g blue value of the color
   * @param b green value of the color
   * @throws IllegalArgumentException if name is empty or null, or contains space
   *                                  if xDim or yDim is not positive,
   *                                  if r, g, or b is not between 0.0 and 1.0,
   *                                  if type of shape is null or unknown
   */
  public AbstractShape(String name, String type, double xDim, double yDim,
                       double x, double y, double r, double g, double b) {
    validateName(name);
    //Type is not validated since that is given by the concrete classes.
    validateDimension(xDim);
    validateDimension(yDim);
    validateColor(r, g, b);
    this.name = name;
    this.type = type;
    this.xDim = xDim;
    this.yDim = yDim;
    this.color = new double[]{r, g, b};
    this.position = new double[]{x, y};
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public double getXDim() {
    return this.xDim;
  }

  @Override
  public double getYDim() {
    return this.yDim;
  }

  @Override
  public double[] getPosition() {
    return position.clone();
  }

  @Override
  public double[] getColor() {
    return color.clone();
  }

  @Override
  public void setXDim(double xDim) {
    validateDimension(xDim);
    this.xDim = xDim;
  }

  @Override
  public void setYDim(double yDim) {
    validateDimension(yDim);
    this.yDim = yDim;
  }

  @Override
  public void setPosition(double x, double y) {
    this.position[0] = x;
    this.position[1] = y;
  }

  @Override
  public void setColor(double r, double g, double b) {
    validateColor(r, g, b);
    this.color[0] = r;
    this.color[1] = g;
    this.color[2] = b;
  }

  /**
   * Concrete classes should override clone() method properly.
   * This method exists to avoid calling super.clone() inherited from Objects
   * when calling concrete classes clone method.
   *
   * @return null
   */
  @Override
  public IShape clone() {
    return null;
  }

  /**
   * Returns true if equal. Equality compares all attributes.
   *
   * @param obj another object
   * @return true if equal
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof IShape)) {
      return false;
    }
    IShape o = (IShape) obj;
    return o.getName().equals(this.name)
        && o.getType().equals(this.type)
        && Math.abs(o.getColor()[0] - this.color[0]) < 0.01
        && Math.abs(o.getColor()[1] - this.color[1]) < 0.01
        && Math.abs(o.getColor()[2] - this.color[2]) < 0.01
        && Math.abs(o.getPosition()[0] - this.position[0]) < 0.01
        && Math.abs(o.getPosition()[1] - this.position[1]) < 0.01
        && Math.abs(o.getXDim() - this.xDim) < 0.01
        && Math.abs(o.getYDim() - this.yDim) < 0.01;
  }

  /**
   * Returns the object hashcode.
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.type, this.xDim, this.yDim, Arrays.hashCode(this.color),
        Arrays.hashCode(this.position));
  }

  // --- HELPER VALIDATION METHODS ---

  /**
   * Checks if the color values are valid. Throws exception if not.
   *
   * @param r red color
   * @param g green color
   * @param b blue color
   */
  private void validateColor(double r, double g, double b) {
    if (!(r >= 0.0 && r <= 1.0 && g >= 0.0 && g <= 1.0 && b >= 0.0 && b <= 1.0)) {
      throw new IllegalArgumentException("Each of the RGB values must be between 0.0 and 1.0");
    }
  }

  /**
   * Checks if the name of the shape is valid: not empty, not null and does not contain space.
   * Throws exception if not valid.
   *
   * @param name name of the shape
   * @throws IllegalArgumentException if name is null, empty or contains space
   */
  private void validateName(String name) {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Name of the shape can't be null or empty");
    }
    if (name.contains(" ")) {
      throw new IllegalArgumentException("Name for a shape can't contain spaces.");
    }
  }

  /**
   * Verify that dimension of a shape is positive.
   *
   * @param dim dimension of a shape
   * @throws IllegalArgumentException if dim is not positive
   */
  private void validateDimension(double dim) {
    if (dim <= 0) {
      throw new IllegalArgumentException("Dimension of a shape must be positive");
    }
  }


}
