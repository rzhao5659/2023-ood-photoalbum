package view.GUI;

import controller.ICallbacks;
import java.util.List;
import view.IPhotoAlbumView;

/**
 * Interface for a GUI-based view.
 */
public interface IPhotoAlbumViewGUI extends IPhotoAlbumView {

  /**
   * Set the text labels about the currently displayed snapshot.
   *
   * @param id snapshot ID
   * @param description snapshot description
   */
  void setSnapshotInfo(String id, String description);

  /**
   * Get the currently displayed snapshot's ID.
   *
   * @return currently displayed snapshot's ID
   */
  String getSnapshotInfo();

  /**
   * Display a new error dialog with a given message.
   *
   * @param errorMessage error message.
   */
  void displayErrorDialog(String errorMessage);

  /**
   * Display the snapshot browser from which the user can select which snapshot to display.
   */
  void showSnapshotBrowser();

  /**
   * Close the snapshot browser.
   */
  void closeSnapshotBrowser();

  /**
   * Set the snapshot ID list displayed in the snapshot browser.
   *
   * @param snapshotIDs snapshot ID list
   */
  void setSnapshotList(List<String> snapshotIDs);

  /**
   * Set which value is highlighted in the snapshot ID list.
   *
   * @param index
   */
  void setSnapshotListSelectedValue(int index);

  /**
   * Repaints the draw panel, which processes all the draw operations in batch.
   */
  void repaintDrawPanel();

  /**
   * Clears the draw panel. To see the effect, call repaintDrawPanel();
   */
  void clearDrawPanel();

  /**
   * Set callback functions for the GUI components in view.
   */
  void setCallbacks(ICallbacks cbContainer);

}
