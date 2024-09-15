package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.GUI.IPhotoAlbumViewGUI;

/**
 * Mock for PhotoAlbumViewGUI to test the PhotoAlbumControllerGUI in isolation.
 */
public class MockPhotoAlbumViewGUI implements IPhotoAlbumViewGUI {
  //A log that maps from methodName to all its call information.
  //Useful for tracking number of calls for a specific methodName,
  //and tracking the parameters passed for each of these calls.
  private Map<String, List<String>> log;
  private String uniqueSnapshotInfo;

  public MockPhotoAlbumViewGUI(String uniqueSnapshotInfo) {
    log = new HashMap<>();
    log.put("setSnapshotInfo", new ArrayList<>());
    log.put("displayErrorDialog", new ArrayList<>());
    log.put("setSnapshotList", new ArrayList<>());
    log.put("setSnapshotListSelectedValue", new ArrayList<>());
    log.put("drawRectangle", new ArrayList<>());
    log.put("drawOval", new ArrayList<>());
    log.put("showSnapshotBrowser", new ArrayList<>());
    log.put("closeSnapshotBrowser", new ArrayList<>());
    log.put("repaintDrawPanel", new ArrayList<>());
    log.put("clearDrawPanel", new ArrayList<>());
    log.put("setCallbacks", new ArrayList<>());
    this.uniqueSnapshotInfo = uniqueSnapshotInfo;
  }

  public Map<String, List<String>> getLog() {
    return log;
  }


  @Override
  public void setSnapshotInfo(String id, String description) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumViewGUI]: setSnapshotInfo(");
    callInfo.append("id:" + id + ",");
    callInfo.append("description:" + description + ")");
    log.get("setSnapshotInfo").add(callInfo.toString());
  }

  @Override
  public String getSnapshotInfo() {
    return uniqueSnapshotInfo;
  }

  @Override
  public void displayErrorDialog(String errorMessage) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumViewGUI]: displayErrorDialog(");
    callInfo.append("errorMessage:" + errorMessage + ")");
    log.get("displayErrorDialog").add(callInfo.toString());
  }

  @Override
  public void showSnapshotBrowser() {
    log.get("showSnapshotBrowser").add("");
  }

  @Override
  public void closeSnapshotBrowser() {
    log.get("closeSnapshotBrowser").add("");
  }

  @Override
  public void setSnapshotList(List<String> snapshotIDs) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumViewGUI]: setSnapshotList(");
    callInfo.append("snapshotIDs:" + snapshotIDs + ")");
    log.get("setSnapshotList").add(callInfo.toString());
  }

  @Override
  public void setSnapshotListSelectedValue(int index) {
    log.get("setSnapshotListSelectedValue").add("[MockPhotoAlbumViewGUI]: setSnapshotListSelectedValue(index:" + index + ")");
  }

  @Override
  public void repaintDrawPanel() {
    log.get("repaintDrawPanel").add("");
  }

  @Override
  public void clearDrawPanel() {
    log.get("clearDrawPanel").add("");
  }

  @Override
  public void setCallbacks(ICallbacks cbContainer) {
    log.get("setCallbacks").add("");
  }

  @Override
  public void drawRectangle(double x, double y, double xDim, double yDim, double r, double g,
      double b) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumViewGUI]: drawRectangle(");
    callInfo.append("x:" + x + ",");
    callInfo.append("y:" + y + ",");
    callInfo.append("xDim:" + xDim + ",");
    callInfo.append("yDim:" + yDim + ",");
    callInfo.append("r:" + r + ",");
    callInfo.append("g:" + g + ",");
    callInfo.append("b:" + b + ")");
    log.get("drawRectangle").add(callInfo.toString());
  }

  @Override
  public void drawOval(double x, double y, double xDim, double yDim, double r, double g, double b) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumViewGUI]: drawOval(");
    callInfo.append("x:" + x + ",");
    callInfo.append("y:" + y + ",");
    callInfo.append("xDim:" + xDim + ",");
    callInfo.append("yDim:" + yDim + ",");
    callInfo.append("r:" + r + ",");
    callInfo.append("g:" + g + ",");
    callInfo.append("b:" + b + ")");
    log.get("drawOval").add(callInfo.toString());
  }
}
