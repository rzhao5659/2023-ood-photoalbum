package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Concrete class of snapshot used for the concrete PhotoAlbumApp model.
 * The ID of the snapshot is automatically generated from the current time.
 */
public class Snapshot implements ISnapshot {
  private final String id;
  private final String timeStamp;
  private final String description;
  private final IPhoto photo;

  /**
   * Constructor.
   * The ID of snapshot is generated from the current time, truncated to microseconds.
   *
   * @param description description for the snapshot
   * @param photo current photo of the photo album to take a snapshot
   * @throws IllegalArgumentException if photo is null,
   *                                  if description is null
   */
  public Snapshot(String description, IPhoto photo) {
    if (photo == null || description == null) {
      throw new IllegalArgumentException("Parameter can't be null.");
    }

    //Sleep for 1 millisecond to ensure that future snapshots won't have the same ID
    //if created under 1 millisecond.
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    }

    LocalDateTime currTime = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu kk:mm:ss");
    this.id = currTime.toString();
    this.timeStamp = currTime.format(formatter);
    this.photo = photo;
    this.description = description;
  }

  /**
   * Copy constructor.
   *
   * @param snapshot another snapshot
   * @throws IllegalArgumentException if snapshot is null.
   */
  public Snapshot(ISnapshot snapshot) {
    if (snapshot == null) {
      throw new IllegalArgumentException("Parameter can't be null");
    }
    this.id = snapshot.getID();
    this.description = snapshot.getDescription();
    this.timeStamp = snapshot.getTimeStamp();
    this.photo = snapshot.getPhoto().clone();
  }

  @Override
  public ISnapshot clone() {
    return new Snapshot(this);
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public String getTimeStamp() {
    return timeStamp;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public IPhoto getPhoto() {
    return photo;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("Snapshot ID: ").append(this.getID()).append("\n");
    result.append("Timestamp: ").append(this.getTimeStamp()).append("\n");
    result.append("Description: ").append(this.getDescription()).append("\n");
    result.append("Shape Information:").append("\n");
    result.append(this.getPhoto());
    return result.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ISnapshot)) {
      return false;
    }
    ISnapshot o = (ISnapshot) obj;
    return o.getID().equals(this.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

}
