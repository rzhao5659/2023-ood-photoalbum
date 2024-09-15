package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.IPhoto;
import model.IPhotoAlbumApp;
import model.ISnapshot;
import java.util.Map;

/**
 * Model mock created to test controllers in isolation.
 */
public class MockPhotoAlbumApp implements IPhotoAlbumApp {

  private List<ISnapshot> uniqueSnapshots;
  private ISnapshot uniqueSnapshot;
  private int uniqueSnapshotIndex;
  private List<String> uniqueSnapshotIDs;

  //A log that maps from methodName to all its call information.
  //Useful for tracking number of calls for a specific methodName,
  //and tracking the parameters passed for each of these calls.
  private Map<String, List<String>> log;

  public MockPhotoAlbumApp(List<ISnapshot> uniqueSnapshots, ISnapshot uniqueSnapshot,
                           int uniqueSnapshotIndex, List<String> uniqueSnapshotIDs) {
    this.uniqueSnapshots = uniqueSnapshots;
    this.uniqueSnapshot = uniqueSnapshot;
    this.uniqueSnapshotIndex = uniqueSnapshotIndex;
    this.uniqueSnapshotIDs = uniqueSnapshotIDs;
    log = new HashMap<>();
    log.put("takeSnapshot", new ArrayList<>());
    log.put("getSnapshot", new ArrayList<>());
    log.put("getSnapshotIndex", new ArrayList<>());
    log.put("createBasicShape", new ArrayList<>());
    log.put("removeShape", new ArrayList<>());
    log.put("setShapeColor", new ArrayList<>());
    log.put("setShapePosition", new ArrayList<>());
    log.put("setShapeXDim", new ArrayList<>());
    log.put("setShapeYDim", new ArrayList<>());
  }

  public Map<String, List<String>> getLog() {
    return log;
  }


  @Override
  public void takeSnapshot(String description) {
    log.get("takeSnapshot").add("[MockPhotoAlbumApp]: takeSnapshot(description:" + description + ")");
  }

  @Override
  public ISnapshot getSnapshot(String id) {
    log.get("getSnapshot").add("[MockPhotoAlbumApp]: getSnapshot(id:" + id + ")");
    return uniqueSnapshot;
  }

  @Override
  public int getSnapshotIndex(String id) {
    log.get("getSnapshotIndex").add("[MockPhotoAlbumApp]: getSnapshotIndex(id:" + id + ")");
    return uniqueSnapshotIndex;
  }

  @Override
  public List<ISnapshot> getSnapshots() {
    return uniqueSnapshots;
  }

  @Override
  public List<String> getSnapshotIDs() {
    return uniqueSnapshotIDs;
  }

  /**
   * Does nothing or returns null because this model method isn't used
   * by the two concrete controllers and views.
   */
  @Override
  public void clearPhotos() {
  }

  /**
   * Does nothing or returns null because this model method isn't used
   * by the two concrete controllers and views.
   */
  @Override
  public List<IPhoto> getPhotoAlbum() {
    return null;
  }

  /**
   * Does nothing or returns null because this model method isn't used
   * by the two concrete controllers and views.
   */
  @Override
  public List<IPhoto> getPhotoAlbumRange(int lower, int upper) {
    return null;
  }

  /**
   * Does nothing or returns null because this model method isn't used
   * by the two concrete controllers and views.
   */
  @Override
  public IPhoto getCurrentPhoto() {
    return null;
  }

  @Override
  public void createBasicShape(String name, String type, double xDim, double yDim, double x,
      double y, double r, double g, double b) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumApp]: createBasicShape(");
    callInfo.append("name:" + name + ",");
    callInfo.append("type:" + type + ",");
    callInfo.append("xDim:" + xDim + ",");
    callInfo.append("yDim:" + yDim + ",");
    callInfo.append("x:" + x + ",");
    callInfo.append("y:" + y + ",");
    callInfo.append("r:" + r + ",");
    callInfo.append("g:" + g + ",");
    callInfo.append("b:" + b + ")");
    log.get("createBasicShape").add(callInfo.toString());

  }

  @Override
  public void removeShape(String name) {
    log.get("removeShape").add("[MockPhotoAlbumApp]: removeShape(name:" + name + ")");
  }

  @Override
  public void setShapeColor(String name, double r, double g, double b) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumApp]: setShapeColor(");
    callInfo.append("name:" + name + ",");
    callInfo.append("r:" + r + ",");
    callInfo.append("g:" + g + ",");
    callInfo.append("b:" + b + ")");
    log.get("setShapeColor").add(callInfo.toString());
  }

  @Override
  public void setShapePosition(String name, double x, double y) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumApp]: setShapePosition(");
    callInfo.append("name:" + name + ",");
    callInfo.append("x:" + x + ",");
    callInfo.append("y:" + y + ")");
    log.get("setShapePosition").add(callInfo.toString());
  }

  @Override
  public void setShapeXDim(String name, double xDim) {
    log.get("setShapeXDim").add("[MockPhotoAlbumApp]: setShapeXDim(name:" + name + "," + "xDim:" + xDim + ")");
  }

  @Override
  public void setShapeYDim(String name, double yDim) {
    log.get("setShapeYDim").add("[MockPhotoAlbumApp]: setShapeYDim(name:" + name + "," + "yDim:" + yDim + ")");
  }
}
