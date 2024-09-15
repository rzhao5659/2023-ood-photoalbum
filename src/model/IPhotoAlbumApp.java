package model;

import java.util.List;

/**
 * Model of the photo album app.
 */
public interface IPhotoAlbumApp {

  /**
   * Takes a snapshot for the current photo.
   *
   * @param description description for the snapshot.
   * @throws IllegalArgumentException if description is null
   */
  void takeSnapshot(String description);

  /**
   * Returns a copy of a specific snapshot by ID. If it fails to find it, returns null.
   *
   * @param id snapshot id
   * @return snapshot or null if it's not found.
   * @throws IllegalArgumentException if id is null
   */
  ISnapshot getSnapshot(String id);

  /**
   * Returns the index / position for a given snapshot specified by its ID.
   * If the album has no snapshots, returns -1.
   *
   * @param id snapshot id
   * @return snapshot index or -1 if it's not found
   * @throws IllegalArgumentException if id is null
   */
  int getSnapshotIndex(String id);

  /**
   * Returns an unmodifiable list of snapshots.
   *
   * @return list of snapshots
   */
  List<ISnapshot> getSnapshots();

  /**
   * Returns the IDs of all snapshots.
   *
   * @return list of snapshots ids
   */
  List<String> getSnapshotIDs();


  /**
   * Empty the photo album. This doesn't remove the snapshots.
   */
  void clearPhotos();

  /**
   * Returns an unmodifiable list of all photos on the photo album.
   * If the album is empty, returns null.
   *
   * @return list of photos on the album, or null
   */
  List<IPhoto> getPhotoAlbum();

  /**
   * Returns an unmodifiable list of photos of the photo album, selected by the range of index.
   *
   * @param lower lower index (starting from 0) of the desired range of photos.
   * @param upper upper index of the desired range of photos.
   * @return list of photos in the range of index
   * @throws IllegalArgumentException if upper is less than lower,
   *                                  if lower and upper isn't within bound.
   */
  List<IPhoto> getPhotoAlbumRange(int lower, int upper);


  /**
   * Returns a copy of the current photo on the album.
   *
   * @return copy of the current photo on the album
   */
  IPhoto getCurrentPhoto();


  // --- Photo creation methods ---

  /**
   * Creates and adds a new photo after creating a basic shape, such as rectangle or oval,
   * whose dimension can be represented with just two parameters.
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
   * @throws IllegalArgumentException if name is empty or null,
   *                                  if xDim or yDim is not positive,
   *                                  if r, g, or b is not between 0.0 and 1.0,
   *                                  if type is an unknown type.
   */
  void createBasicShape(String name, String type, double xDim, double yDim, double x, double y,
      double r, double g, double b);

  /**
   * Creates and adds a new photo after removing a shape.
   *
   * @param name name of the shape
   * @throws IllegalArgumentException if name is empty or null, or no shape with this name.
   */
  void removeShape(String name);

  /**
   * Creates and adds a new photo after changing a shape's color.
   *
   * @param name name of the shape to change
   * @param r red value of the color
   * @param g blue value of the color
   * @param b green value of the color
   * @throws IllegalArgumentException if name is empty, null, or no shapes with this name,
   *                                  if r, g, or b is not between 0.0 and 1.0
   */
  void setShapeColor(String name, double r, double g, double b);

  /**
   * Creates and adds a new photo after changing a shape's position.
   *
   * @param name name of the shape to change
   * @param x new x-coordinate of the shape
   * @param y new y-coordinate of the shape
   * @throws IllegalArgumentException if name is empty, null, or no shapes with this name,
   */
  void setShapePosition(String name, double x, double y);

  /**
   * Creates and adds a new photo after changing a shape's horizontal dimension.
   * For a basic shape, it changes the horizontal dimension parameter.
   * For a polygon, it changes the bounding box horizontal dimension.
   *
   * @param name name of the shape to change
   * @param xDim new horizontal dimension of the shape
   * @throws IllegalArgumentException if name is empty, null, or doesn't exist,
   *                                  if xDim is not positive
   */
  void setShapeXDim(String name, double xDim);

  /**
   * Creates and adds a new photo after changing a shape's vertical dimension.
   * For a basic shape, it changes the vertical dimension parameter.
   * For a polygon, it changes the bounding box vertical dimension.
   *
   * @param name name of the shape to change
   * @param yDim new vertical dimension of the shape
   * @throws IllegalArgumentException if name is empty, null, or doesn't exist,
   *                                  if yDim is not positive
   */
  void setShapeYDim(String name, double yDim);

}
