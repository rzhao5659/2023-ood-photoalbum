package model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Concrete photo class for PhotoAlbumApp.
 */
public class Photo implements IPhoto {
  private Map<String, IShape> shapeMap;

  /**
   * Default constructor.
   */
  public Photo() {
    this.shapeMap = new LinkedHashMap<>();
  }

  /**
   * Copy constructor.
   *
   * @param photo another photo
   */
  public Photo(IPhoto photo) {
    this.shapeMap = new LinkedHashMap<>();
    List<IShape> shapeList = photo.getShapes();
    shapeList.forEach(shape -> this.shapeMap.put(shape.getName(), shape.clone()));
  }

  @Override
  public List<IShape> getShapes() {
    return new LinkedList<>(this.shapeMap.values());
  }

  @Override
  public IShape getShape(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Shape's name can't be null");
    }
    return shapeMap.get(name);
  }

  @Override
  public int size() {
    return shapeMap.size();
  }

  @Override
  public void addShape(IShape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("The parameter can't be null");
    }
    if (shapeMap.containsKey(shape.getName())) {
      throw new IllegalArgumentException("The name of the given shape is already used.");
    }
    shapeMap.put(shape.getName(), shape);
  }

  @Override
  public void removeShape(String name) {
    if (name == null) {
      throw new IllegalArgumentException("The parameter can't be null");
    }
    if (!shapeMap.containsKey(name)) {
      throw new IllegalArgumentException("The shape with this name doesn't exist in the photo");
    }
    shapeMap.remove(name);
  }

  @Override
  public IPhoto clone() {
    return new Photo(this);
  }

  /**
   * String representation of photo, which describes the state of every shape in the photo.
   *
   * @return string
   */
  @Override
  public String toString() {
    StringJoiner result = new StringJoiner("\n");
    this.getShapes().forEach(shape -> result.add(shape.toString()));
    return result.toString();
  }

  /**
   * Returns true if equal.
   *
   * @param obj another object
   * @return true if equal
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof IPhoto)) {
      return false;
    }
    IPhoto o = (IPhoto) obj;
    return o.getShapes().equals(this.getShapes());
  }

  /**
   * Returns the object hashcode.
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getShapes());
  }

}
