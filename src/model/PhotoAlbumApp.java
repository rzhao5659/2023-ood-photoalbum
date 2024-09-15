package model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * PhotoAlbumApp is the main class in Model that will provide all functionalities to the controller.
 */
public class PhotoAlbumApp implements IPhotoAlbumApp {

  private IPhoto currPhoto;
  private List<PhotoCommand> photoCmdList;
  private Map<String, ISnapshot> snapshotMap;

  /**
   * Default constructor.
   */
  public PhotoAlbumApp() {
    currPhoto = new Photo();
    photoCmdList = new LinkedList<>();
    snapshotMap = new LinkedHashMap<>();
  }

  @Override
  public void takeSnapshot(String description) {
    ISnapshot snapshot = new Snapshot(description, currPhoto.clone());
    snapshotMap.put(snapshot.getID(), snapshot);
  }

  @Override
  public ISnapshot getSnapshot(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Parameter can't be null");
    }

    if (snapshotMap.containsKey(id)) {
      return snapshotMap.get(id).clone();
    }
    return null;
  }

  @Override
  public int getSnapshotIndex(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Parameter can't be null");
    }
    int idx = 0;
    for (String snapshotID : snapshotMap.keySet()) {
      if (snapshotID.equals(id)) {
        break;
      }
      idx ++;
    }
    return idx == snapshotMap.size() ? -1 : idx;
  }

  @Override
  public List<ISnapshot> getSnapshots() {
    List<ISnapshot> copySnapshots = new LinkedList<>();
    snapshotMap.forEach((id,snapshot) -> copySnapshots.add(snapshot.clone()));
    return copySnapshots;
  }

  @Override
  public List<String> getSnapshotIDs() {
    List<String> snapshotIDs = new LinkedList<>();
    this.snapshotMap.forEach((id, snapshot) -> snapshotIDs.add(id));
    return snapshotIDs;
  }

  @Override
  public void clearPhotos() {
    currPhoto = new Photo();
    photoCmdList = new LinkedList<>();
  }

  @Override
  public List<IPhoto> getPhotoAlbum() {
    if (photoCmdList.size() == 0) {
      return Collections.unmodifiableList(new LinkedList<IPhoto>());
    }
    return Collections.unmodifiableList(getPhotoAlbumRange(0, photoCmdList.size() - 1));
  }

  @Override
  public List<IPhoto> getPhotoAlbumRange(int lower, int upper) {
    if (upper < lower) {
      throw new IllegalArgumentException("Upper index can't be less than lower index");
    }
    if (lower < 0 || upper >= photoCmdList.size()) {
      throw new IllegalArgumentException("Lower and upper indices must be within valid bound");
    }

    //Create a new photo instance, and process on it the stored commands from start up to the lower index.
    IPhoto photo = new Photo();
    for (int i = 0; i < lower; i++) {
      photoCmdList.get(i).invoke(photo);
    }

    //Once we reach the desired range of index, start storing each photo inside a list.
    List<IPhoto> photoList = new LinkedList<>();
    for (int i = lower; i < upper + 1; i++) {
      photoCmdList.get(i).invoke(photo);
      photoList.add(photo.clone());
    }
    return Collections.unmodifiableList(photoList);
  }

  @Override
  public IPhoto getCurrentPhoto() {
    return this.currPhoto.clone();
  }

  @Override
  public void createBasicShape(String name, String type, double xDim, double yDim, double x,
                               double y, double r, double g, double b) {
    validateNoShapeWithThisName(name);
    //Update the current photo.
    createBasicShapeOnPhoto(this.currPhoto, name, type, xDim, yDim, x, y, r, g, b);
    //Condense the command into one string.
    String methodName = "CREATE_BASIC_SHAPE";
    String[] args = {methodName, name, type, Double.toString(xDim), Double.toString(yDim),
                     Double.toString(x), Double.toString(y), Double.toString(r),
                     Double.toString(g), Double.toString(b)};
    String cmdString = String.join(" ", args);
    //Add this command into the command list.
    PhotoCommand cmd = new PhotoCommand(cmdString);
    this.photoCmdList.add(cmd);
  }

  @Override
  public void removeShape(String name) {
    validateExistingShapeWithThisName(name);
    //Update the current photo.
    removeShapeOnPhoto(this.currPhoto, name);
    //Condense the command into one string.
    String methodName = "REMOVE_SHAPE";
    String[] args = {methodName, name};
    String cmdString = String.join(" ", args);
    //Add this command into the command list.
    PhotoCommand cmd = new PhotoCommand(cmdString);
    this.photoCmdList.add(cmd);

  }

  @Override
  public void setShapeColor(String name, double r, double g, double b) {
    validateExistingShapeWithThisName(name);
    //Update the current photo
    setShapeColorOnPhoto(this.currPhoto, name, r, g, b);
    //Condense the command into one string
    String methodName = "SET_COLOR";
    String[] args = {methodName, name, Double.toString(r), Double.toString(g), Double.toString(b)};
    String cmdString = String.join(" ", args);
    //Add this command into the command list.
    PhotoCommand cmd = new PhotoCommand(cmdString);
    this.photoCmdList.add(cmd);
  }

  @Override
  public void setShapePosition(String name, double x, double y) {
    validateExistingShapeWithThisName(name);
    //Update the current photo
    setShapePositionOnPhoto(this.currPhoto, name, x, y);
    //Condense the command into one string
    String methodName = "SET_POSITION";
    String[] args = {methodName, name, Double.toString(x), Double.toString(y)};
    String cmdString = String.join(" ", args);
    //Add this command into the command list.
    PhotoCommand cmd = new PhotoCommand(cmdString);
    this.photoCmdList.add(cmd);
  }

  @Override
  public void setShapeXDim(String name, double xDim) {
    validateExistingShapeWithThisName(name);
    //Update the current photo
    setShapeXDimOnPhoto(this.currPhoto, name, xDim);
    //Condense the command into one string
    String methodName = "SET_XDIM";
    String[] args = {methodName, name, Double.toString(xDim)};
    String cmdString = String.join(" ", args);
    //Add this command into the command list.
    PhotoCommand cmd = new PhotoCommand(cmdString);
    this.photoCmdList.add(cmd);
  }

  @Override
  public void setShapeYDim(String name, double yDim) {
    validateExistingShapeWithThisName(name);
    //Update the current photo
    setShapeYDimOnPhoto(this.currPhoto, name, yDim);
    //Condense the command into one string
    String methodName = "SET_YDIM";
    String[] args = {methodName, name, Double.toString(yDim)};
    String cmdString = String.join(" ", args);
    //Add this command into the command list.
    PhotoCommand cmd = new PhotoCommand(cmdString);
    this.photoCmdList.add(cmd);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    //Print every photo on the album:
    //Modifies the same instance of photo by running through the stored commands,
    //and prints it at each step.
    IPhoto photo = new Photo();
    for (int i = 0; i < this.photoCmdList.size(); i++) {
      photoCmdList.get(i).invoke(photo);
      result.append(photo).append("\n==========\n");
    }
    result.append("\n");

    //Print ID of snapshots:
    result.append("List of snapshots taken before reset: ").append("[");
    if (this.snapshotMap.size() != 0) {
      snapshotMap.forEach((id, snapshot) -> result.append(id).append(", "));
      result.deleteCharAt(result.length() - 1);
      result.deleteCharAt(result.length() - 1);
    }
    result.append("]").append("\n\n");

    //Print snapshots:
    result.append("Printing Snapshots").append("\n\n");
    if (this.snapshotMap.size() != 0) {
      snapshotMap.forEach((id, snapshot) -> result.append(snapshot).append("\n\n"));
      result.deleteCharAt(result.length() - 1);
      result.deleteCharAt(result.length() - 1);
    }

    return result.toString();
  }

  // --- Helper functions and class ---

  /**
   * A helper data structure that can store a transformation command related to photos and
   * invoke the command on a given photo and modify it in place.
   * The required format for the command string is a string of arguments delimited by space:
   * methodName param1 param2 ...
   *
   */
  private class PhotoCommand {

    private final String command;
    public PhotoCommand(String command) {
      this.command = command;
    }

    /**
     * Calls the method represented by the command stored on the given photo and
     * modify it in place.
     *
     * @param photo photo
     */
    public void invoke(IPhoto photo) {
      String[] args = this.command.split("\\s+");
      String methodName = args[0];
      //Invoke method according methodName.
      switch (methodName) {
        case "SET_COLOR" -> PhotoAlbumApp.setShapeColorOnPhoto(photo, args[1],
            Double.parseDouble(args[2]),
            Double.parseDouble(args[3]), Double.parseDouble(args[4]));
        case "CREATE_BASIC_SHAPE" ->
            PhotoAlbumApp.createBasicShapeOnPhoto(photo, args[1], args[2],
                Double.parseDouble(args[3]), Double.parseDouble(args[4]),
                Double.parseDouble(args[5]),
                Double.parseDouble(args[6]), Double.parseDouble(args[7]),
                Double.parseDouble(args[8]),
                Double.parseDouble(args[9]));
        case "SET_POSITION" -> PhotoAlbumApp.setShapePositionOnPhoto(photo, args[1],
            Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        case "SET_XDIM" -> PhotoAlbumApp.setShapeXDimOnPhoto(photo, args[1],
            Double.parseDouble(args[2]));
        case "SET_YDIM" -> PhotoAlbumApp.setShapeYDimOnPhoto(photo, args[1],
            Double.parseDouble(args[2]));
        case "REMOVE_SHAPE" -> PhotoAlbumApp.removeShapeOnPhoto(photo, args[1]);
      }
    }
  }

  /**
   * Checks if a shape with this name exists in the photo. Throws exception if not.
   *
   * @param name name of the shape
   * @throws IllegalArgumentException if shape with this name doesn't exist
   */
  private void validateExistingShapeWithThisName(String name) {
    if (this.currPhoto.getShape(name) == null) {
      throw new IllegalArgumentException("There isn't any shape with this name");
    }
  }

  /**
   * Checks that no shapes in the photo has this name. If there is a shape already with this name,
   * throws an exception.
   *
   * @param name name of the shape
   * @throws IllegalArgumentException if shape with this name exist
   */
  private void validateNoShapeWithThisName(String name) {
    if (this.currPhoto.getShape(name) != null) {
      throw new IllegalArgumentException("There is already a shape with this name");
    }
  }

  /**
   * Sets a shape's color on the given photo. Performs no validation.
   *
   * @param photo photo
   * @param name name of the shape
   * @param r red value of the color
   * @param g green value of the color
   * @param b blue value of the color
   */
  private static void setShapeColorOnPhoto(IPhoto photo, String name, double r,
                                             double g, double b) {
    photo.getShape(name).setColor(r, g, b);
  }

  /**
   * Creates a shape on the given photo.
   * Performs no validation.
   *
   * @param photo photo
   * @param name name of the shape
   * @param type type of the shape
   * @param xDim dimension of the shape in horizontal direction.
   * @param yDim dimension of the shape in vertical direction.
   * @param x x-coordinate of the shape
   * @param y y-coordinate of the shape
   * @param r red value of the color
   * @param g blue value of the color
   * @param b green value of the color
   */
  private static void createBasicShapeOnPhoto(IPhoto photo, String name, String type, double xDim,
                                                double yDim, double x, double y,
                                                double r, double g, double b) {
    IShape newShape = ShapeFactory.createBasicShape(name, type, xDim, yDim, x, y, r, g, b);
    photo.addShape(newShape);
  }

  /**
   * Removes a shape on the given photo.
   * Performs no validation.
   *
   * @param photo photo
   * @param name name of the shape
   */
  private static void removeShapeOnPhoto(IPhoto photo, String name) {
    photo.removeShape(name);
  }

  /**
   * Sets a shape's position on the given photo.
   * Performs no validation.
   *
   * @param photo photo
   * @param name name of the shape
   * @param x x-coordinate of the shape
   * @param y y-coordinate of the shape
   */
  private static void setShapePositionOnPhoto(IPhoto photo, String name, double x, double y) {
    photo.getShape(name).setPosition(x, y);
  }

  /**
   * Sets a shape's horizontal dimension on the given photo.
   * Performs no validation.
   *
   * @param photo photo
   * @param name name of the shape
   * @param xDim dimension of the shape in horizontal direction.
   */
  private static void setShapeXDimOnPhoto(IPhoto photo, String name, double xDim) {
    photo.getShape(name).setXDim(xDim);
  }

  /**
   * Sets a shape's vertical dimension on the given photo.
   * Performs no validation.
   *
   * @param photo photo
   * @param name name of the shape
   * @param yDim dimension of the shape in vertical direction.
   */
  private static void setShapeYDimOnPhoto(IPhoto photo, String name, double yDim) {
    photo.getShape(name).setYDim(yDim);
  }

}
