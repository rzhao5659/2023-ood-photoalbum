package controller;

import java.io.InputStream;
import java.util.List;
import model.IPhoto;
import model.IPhotoAlbumApp;
import model.IShape;
import model.ISnapshot;
import view.GUI.IPhotoAlbumViewGUI;

/**
 * Asynchronous controller for a graphical-based view.
 * Implements callback functions that the GUI components will need.
 */
public class PhotoAlbumControllerGUI implements ICallbacks, IPhotoAlbumController{
  private final IPhotoAlbumApp model;
  private final IPhotoAlbumViewGUI view;
  private final InputStream in;
  private final IInputParser inputParser;
  private boolean isSnapshotBrowserDisplayed;

  /**
   * Constructor.
   *
   * @param model photo album model
   * @param view photo album graphical-based view
   */
  public PhotoAlbumControllerGUI(IPhotoAlbumApp model, IPhotoAlbumViewGUI view) {
    this.model = model;
    this.view = view;
    this.in = null;
    this.inputParser = null;
  }
  
  /**
   * Overloaded constructor that allows user pass in a sequence of commands to perform on the model.
   *
   * @param model photo album model
   * @param view photo album graphical-based view
   * @param in input stream with the commands sequence
   * @param inputParser a parser that can parse the commands sequence from the input stream.
   */
  public PhotoAlbumControllerGUI(IPhotoAlbumApp model, IPhotoAlbumViewGUI view, InputStream in, IInputParser inputParser) {
    this.model = model;
    this.view = view;
    this.in = in;
    this.inputParser = inputParser;
  }

  @Override
  public void go() {
    this.modelSetup();
    this.viewSetup();
  }

  @Override
  public void nextSnapshotCallback() {
    //Obtain the current snapshot that view is displaying
    String currSnapshotID = view.getSnapshotInfo();
    int currSnapshotIdx = model.getSnapshotIndex(currSnapshotID);

    List<ISnapshot> snapshotList = model.getSnapshots();
    int numSnapshots = snapshotList.size();
    //Paint the next snapshot if not end of list, otherwise display an error message.
    if (currSnapshotIdx < numSnapshots - 1) {
      ISnapshot nextSnapshot = snapshotList.get(currSnapshotIdx + 1);
      view.setSnapshotInfo(nextSnapshot.getID(), nextSnapshot.getDescription());
      this.drawSnapshot(nextSnapshot);
      view.setSnapshotListSelectedValue(currSnapshotIdx + 1);
    } else {
      view.displayErrorDialog("End of the photo album. There is no snapshot after this.");
    }
  }

  @Override
  public void prevSnapshotCallback() {
    //Obtain the current snapshot that view is displaying
    String currSnapshotID = view.getSnapshotInfo();
    int currSnapshotIdx = model.getSnapshotIndex(currSnapshotID);

    List<ISnapshot> snapshotList = model.getSnapshots();
    //Paint the previous snapshot if not beginning of the list, otherwise display an error message.
    if (currSnapshotIdx > 0) {
      ISnapshot prevSnapshot = snapshotList.get(currSnapshotIdx - 1);
      view.setSnapshotInfo(prevSnapshot.getID(), prevSnapshot.getDescription());
      this.drawSnapshot(prevSnapshot);
      view.setSnapshotListSelectedValue(currSnapshotIdx - 1);
    } else {
      view.displayErrorDialog("Start of photo album. There is no snapshot before this.");
    }
  }

  @Override
  public void browseSnapshotCallback() {
    if (isSnapshotBrowserDisplayed) {
      view.closeSnapshotBrowser();
      this.isSnapshotBrowserDisplayed = false;
    } else {
      view.showSnapshotBrowser();
      this.isSnapshotBrowserDisplayed = true;
    }
  }

  @Override
  public void selectSnapshotCallback(String snapshotID) {
    ISnapshot selectedSnapshot = model.getSnapshot(snapshotID);
    view.setSnapshotInfo(selectedSnapshot.getID(), selectedSnapshot.getDescription());
    this.drawSnapshot(selectedSnapshot);
  }

  /**
   * Setup function for View. Set callback functions and pass snapshots information to View,
   * and make View display the first snapshot by default.
   */
  private void viewSetup() {
    //Set callbacks, give snapshots information to view, and set other auxiliary variables.
    view.setCallbacks(this);
    this.isSnapshotBrowserDisplayed = false;
    view.setSnapshotList(model.getSnapshotIDs());
    //Display the first snapshot
    if (model.getSnapshots().size() != 0) {
      ISnapshot firstSnapshot = model.getSnapshots().get(0);
      this.drawSnapshot(firstSnapshot);
      view.setSnapshotInfo(firstSnapshot.getID(), firstSnapshot.getDescription());
    }
  }

  /**
   * Setup function for Model. Invokes the input parser on the input stream if they are available.
   */
  private void modelSetup() {
    if (in != null && inputParser != null) {
      inputParser.parse(in, model);
    }
  }

  /**
   * Helper function that clears the draw panel and draws the given snapshot in View.
   *
   * @param snapshot snapshot to be drawn
   */
  private void drawSnapshot(ISnapshot snapshot) {
    view.clearDrawPanel();
    IPhoto photo = snapshot.getPhoto();
    for (IShape shape : photo.getShapes()) {
      double x = shape.getPosition()[0];
      double y = shape.getPosition()[1];
      double xDim = shape.getXDim();
      double yDim = shape.getYDim();
      double r = shape.getColor()[0];
      double g = shape.getColor()[1];
      double b = shape.getColor()[2];
      if (shape.getType().equals("rectangle")) {
        view.drawRectangle(x, y, xDim, yDim, r, g, b);
      } else {
        view.drawOval(x, y, xDim, yDim, r, g, b);
      }
    }
    view.repaintDrawPanel();
  }


}
