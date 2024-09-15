package controller;

import model.IPhoto;
import model.ISnapshot;

/**
 * Mock snapshot.
 */
public class MockSnapshot implements ISnapshot {
  private String id;
  private String description;
  private IPhoto photo;

  public MockSnapshot(String id, String description, IPhoto photo) {
    this.id = id;
    this.description = description;
    this.photo = photo;
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public IPhoto getPhoto() {
    return photo;
  }

  /**
   * Not used.
   * @return .
   */
  @Override
  public String getTimeStamp() {
    return null;
  }

  /**
   * Not used.
   * @return .
   */
  @Override
  public ISnapshot clone() {
    return null;
  }
}
