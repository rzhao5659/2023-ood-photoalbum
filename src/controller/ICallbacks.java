package controller;


/**
 * An interface that displays the callbacks functions that the GUI Controller will implement.
 */
public interface ICallbacks {

  /**
   * Callback function for the GUI component that
   * triggers the event when the user wants to see the next snapshot.
   */
  void nextSnapshotCallback();

  /**
   * Callback function for the GUI component that
   * triggers the event when the user wants to see the previous snapshot.
   */
  void prevSnapshotCallback();

  /**
   * Callback function for the GUI component that
   * triggers the event when the user wants to browse the list of all snapshots.
   */
  void browseSnapshotCallback();

  /**
   * Callback function for the GUI component that
   * triggers the event when the user selects a snapshot from the list of all snapshots.
   */
  void selectSnapshotCallback(String snapshotID);
}
