package model;

/**
 * Snapshot captures the current photo and can be retrieved irrespective of the photo album state.
 */
public interface ISnapshot {

  /**
   * Returns the snapshot's ID.
   *
   * @return snapshot's id
   */
  String getID();

  /**
   * Returns the snapshot's time stamp.
   *
   * @return snapshot's time stamp
   */
  String getTimeStamp();

  /**
   * Returns the snapshot's description.
   *
   * @return snapshot's description
   */
  String getDescription();

  /**
   * Returns the photo when the snapshot was taken.
   *
   * @return snapshot's photo
   */
  IPhoto getPhoto();

  /**
   * Returns a deep copy of the snapshot.
   *
   * @return snapshot
   */
  ISnapshot clone();

}
