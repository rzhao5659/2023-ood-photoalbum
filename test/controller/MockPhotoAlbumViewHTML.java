package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.HTML.IPhotoAlbumViewText;

/**
 * Mock for PhotoAlbumViewHTML to test the PhotoAlbumControllerText in isolation.
 */
public class MockPhotoAlbumViewHTML implements IPhotoAlbumViewText {
  //A log that maps from methodName to all its call information.
  //Useful for tracking number of calls for a specific methodName,
  //and tracking the parameters passed for each of these calls.
  private Map<String, List<String>> log;

  public MockPhotoAlbumViewHTML() {
    log = new HashMap<>();
    log.put("startDrawingSnapshot", new ArrayList<>());
    log.put("endDrawingSnapshot", new ArrayList<>());
    log.put("printOutput", new ArrayList<>());
    log.put("drawRectangle", new ArrayList<>());
    log.put("drawOval", new ArrayList<>());
  }

  public Map<String, List<String>> getLog() {
    return log;
  }

  @Override
  public void startDrawingSnapshot(String id, String description) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumViewHTML]: startDrawingSnapshot(");
    callInfo.append("id:" + id + ",");
    callInfo.append("description:" + description + ")");
    log.get("startDrawingSnapshot").add(callInfo.toString());
  }

  @Override
  public void endDrawingSnapshot() {
    log.get("endDrawingSnapshot").add("");
  }

  @Override
  public void printOutput(PrintWriter out) throws IOException {
    log.get("printOutput").add("[MockPhotoAlbumViewHTML]: printOutput(out:" + out + ")");
  }

  @Override
  public void drawRectangle(double x, double y, double xDim, double yDim, double r, double g,
      double b) {
    StringBuilder callInfo = new StringBuilder();
    callInfo.append("[MockPhotoAlbumViewHTML]: drawRectangle(");
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
    callInfo.append("[MockPhotoAlbumViewHTML]: drawOval(");
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
