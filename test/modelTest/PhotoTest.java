package modelTest;

import static org.junit.Assert.*;

import java.util.StringJoiner;
import org.junit.Before;
import org.junit.Test;
import model.IPhoto;
import model.Photo;
import model.ShapeFactory;
import model.IShape;


/**
 * Test suite for Photo.
 */
public class PhotoTest {

  private IPhoto photo1;
  private IPhoto photo2;
  private IShape r1;
  private IShape r2;
  private IShape o1;
  private IShape o2;

  /**
   * Setup function.
   */
  @Before
  public void setUp() {
    photo1 = new Photo();
    photo2 = new Photo();
    r1 = ShapeFactory.createBasicShape("r1", "rectangle", 1, 1, 1, 1, 1, 1, 1);
    r2 = ShapeFactory.createBasicShape("r2", "rectangle", 5, 3, 0, 1, 0.5, 0.5, 1);
    o1 = ShapeFactory.createBasicShape("o1", "oval", 1, 1, 1, 1, 1, 1, 1);
    o2 = ShapeFactory.createBasicShape("o2", "oval", 0.5, 100, -1, -1, 0, 0, 0);
  }

  /**
   * Tests getShapes(), getShape(), addShape().
   */
  @Test
  public void testAddAndGetShapes() {
    assertEquals(0,photo1.getShapes().size());

    photo1.addShape(r1);
    assertEquals(1,photo1.getShapes().size());
    assertEquals(r1, photo1.getShape("r1"));

    photo1.addShape(r2);
    assertEquals(2,photo1.getShapes().size());
    assertEquals(r1, photo1.getShape("r1"));
    assertEquals(r2, photo1.getShape("r2"));

    photo1.addShape(o1);
    assertEquals(3,photo1.getShapes().size());
    assertEquals(r1, photo1.getShape("r1"));
    assertEquals(r2, photo1.getShape("r2"));
    assertEquals(o1, photo1.getShape("o1"));

    photo2.addShape(o1);
    photo2.addShape(o2);
    photo2.addShape(r1);
    assertEquals(3,photo2.getShapes().size());
    assertEquals(r1, photo2.getShape("r1"));
    assertEquals(o2, photo2.getShape("o2"));
    assertEquals(o1, photo2.getShape("o1"));
  }

  /**
   * Tests getShapes() returns null if there isn't a shape with the given name.
   */
  @Test
  public void testGetNonExistingShape() {
    photo1.addShape(o1);
    photo1.addShape(o2);
    assertNull(photo1.getShape("o3"));
    assertNull(photo1.getShape("ewafewa fafe131aw"));
    assertNull(photo1.getShape("r1"));
  }

  /**
   * Tests getShapes() throws exception when given a null parameter.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailGetShapeNullParameter() {
    photo1.getShape(null);
  }

  /**
   * Tests adding different shapes but with same name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailAddSameNameShape() {
    photo1.addShape(r1);
    photo1.addShape(ShapeFactory.createBasicShape("r1", "oval", 30193, 313, 1, 1,0, 0, 0));
  }

  /**
   * Tests adding null shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailAddNullShape() {
    photo1.addShape(null);
  }

  /**
   * Tests size().
   */
  @Test
  public void size() {
    assertEquals(0, photo1.size());
    photo1.addShape(r1);
    assertEquals(1, photo1.size());
    photo1.addShape(r2);
    assertEquals(2, photo1.size());
    photo1.addShape(o1);
    assertEquals(3, photo1.size());
    photo1.addShape(o2);
    assertEquals(4, photo1.size());
  }

  /**
   * Tests removing shape.
   */
  @Test
  public void testRemoveShape() {
    photo1.addShape(r1);
    photo1.addShape(r2);
    photo1.addShape(o1);
    photo1.addShape(o2);
    photo1.removeShape("o1");
    photo1.removeShape("r1");
    assertEquals(2, photo1.size());
    assertFalse(photo1.getShapes().contains(r1));
    assertFalse(photo1.getShapes().contains(o1));
  }

  /**
   * Tests removing non-existing shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailRemoveNonExistingShape() {
    photo1.addShape(r1);
    photo1.addShape(o1);
    photo1.removeShape("r2");
  }

  /**
   * Tests removing null shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailRemoveNullParameter() {
    photo1.removeShape(null);
  }

  /**
   * Tests copy constructor returns a deep copy.
   */
  @Test
  public void testCopyConstructor() {
    photo1.addShape(r1);
    photo1.addShape(o1);
    IPhoto photo1Copy = new Photo(photo1);
    assertNotSame(photo1Copy, photo1);
    assertEquals(2, photo1Copy.size());
    assertNotNull(photo1Copy.getShape("r1"));
    assertNotNull(photo1Copy.getShape("o1"));
    //The shapes are also deep copied.
    assertNotSame(photo1.getShape("r1"), photo1Copy.getShape("r1"));
    assertNotSame(photo1.getShape("o1"), photo1Copy.getShape("o1"));
    assertEquals(photo1.getShape("r1"), photo1Copy.getShape("r1"));
    assertEquals(photo1.getShape("o1"), photo1Copy.getShape("o1"));
  }

  /**
   * Tests clone returns a deep copy.
   */
  @Test
  public void testClone() {
    photo1.addShape(r1);
    photo1.addShape(o1);
    IPhoto photo1Copy = photo1.clone();
    assertNotSame(photo1Copy, photo1);
    assertEquals(2, photo1Copy.size());
    assertNotNull(photo1Copy.getShape("r1"));
    assertNotNull(photo1Copy.getShape("o1"));
    //The shapes are also deep copied.
    assertNotSame(photo1.getShape("r1"), photo1Copy.getShape("r1"));
    assertNotSame(photo1.getShape("o1"), photo1Copy.getShape("o1"));
    assertEquals(photo1.getShape("r1"), photo1Copy.getShape("r1"));
    assertEquals(photo1.getShape("o1"), photo1Copy.getShape("o1"));
  }


  /**
   * Tests toString.
   */
  @Test
  public void testToString() {
    photo1.addShape(r1);
    photo1.addShape(o1);
    StringJoiner expectedString1 = new StringJoiner("\n");
    photo1.getShapes().forEach(shape -> expectedString1.add(shape.toString()));
    assertEquals(expectedString1.toString(), photo1.toString());

    photo2.addShape(r2);
    photo2.addShape(o2);
    StringJoiner expectedString2 = new StringJoiner("\n");
    photo2.getShapes().forEach(shape -> expectedString2.add(shape.toString()));
    assertEquals(expectedString2.toString(), photo2.toString());
  }
}
