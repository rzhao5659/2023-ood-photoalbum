package view;

/**
 * A common interface for other photo album view interfaces.
 */
public interface IPhotoAlbumView {

  /**
   * Draws a rectangle on a canvas / draw panel.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   * @param xDim horizontal dimension
   * @param yDim vertical dimension
   * @param r red
   * @param g green
   * @param b blue
   */
  void drawRectangle(double x, double y, double xDim, double yDim, double r, double g, double b);

  /**
   * Draws an oval on a canvas / draw panel.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   * @param xDim horizontal dimension
   * @param yDim vertical dimension
   * @param r red
   * @param g green
   * @param b blue
   */
  void drawOval(double x, double y, double xDim, double yDim, double r, double g, double b);
}
