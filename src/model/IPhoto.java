package model;

import java.util.List;

/**
 * This interface represents a photo in the photo album,
 * which describes the state of all shapes in the photo.
 */
public interface IPhoto {

  /**
   * Returns all shapes in the photo in a list.
   *
   * @return list of shapes in the photo
   */
  List<IShape> getShapes();

  /**
   * Returns a specific shape in the photo.
   *
   * @param name name of shape
   * @return shape or null if it doesn't exist
   * @throws IllegalArgumentException if the name is null
   */
  IShape getShape(String name);

  /**
   * Returns the number of shapes in the photo.
   *
   * @return number of shapes
   */
  int size();

  /**
   * Adds a new shape to the photo.
   *
   * @param shape .
   * @throws IllegalArgumentException if the name of the given shape already exist,
   *                                  if shape is null
   */
  void addShape(IShape shape);

  /**
   * Removes a specific shape in the photo.
   *
   * @param name name of shape to remove
   * @throws IllegalArgumentException if the name is null or there is no shape with this name
   */
  void removeShape(String name);

  /**
   * Returns a deep copy of photo.
   *
   * @return photo
   */
  IPhoto clone();
}
