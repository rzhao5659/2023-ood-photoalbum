package modelTest;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.junit.Before;
import org.junit.Test;
import model.IPhoto;
import model.ISnapshot;
import model.Photo;
import model.ShapeFactory;
import model.Snapshot;

public class SnapshotTest {
  private ISnapshot snapshot1;
  private ISnapshot snapshot2;
  private ISnapshot snapshot3;
  private IPhoto photo1;
  private IPhoto photo2;
  private IPhoto photo3;
  private LocalDateTime snapshot1CreateTime;
  private LocalDateTime snapshot2CreateTime;
  private LocalDateTime snapshot3CreateTime;
  private LocalDateTime t1;
  private LocalDateTime t2;
  private LocalDateTime t3;
  private LocalDateTime t4;

  /**
   * Setup function.
   */
  @Before
  public void setUp() throws InterruptedException {
    t1 = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    Thread.sleep(1);
    photo1 = new Photo();
    photo1.addShape(ShapeFactory.createBasicShape("r1", "rectangle", 5, 5, 0, 0, 0, 0,1));
    snapshot1 = new Snapshot("rectangle1 is created", photo1);
    snapshot1CreateTime = LocalDateTime.parse(snapshot1.getID());
    Thread.sleep(1);
    t2 = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    Thread.sleep(1);
    photo2 = new Photo();
    snapshot2 = new Snapshot("", photo2);
    snapshot2CreateTime = LocalDateTime.parse(snapshot2.getID());
    Thread.sleep(1);
    t3 = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    Thread.sleep(1);
    photo3 = new Photo();
    photo3.addShape(ShapeFactory.createBasicShape("r1", "rectangle", 5, 5, 0, 0, 0, 0,1));
    photo3.getShape("r1").setColor(1, 1, 1);
    snapshot3 = new Snapshot("r1 changed color from blue to white", photo3);
    snapshot3CreateTime = LocalDateTime.parse(snapshot3.getID());
    Thread.sleep(1);
    t4 = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
  }

  /**
   * Tests snapshot throws an exception if either parameter is null.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstruction1() {
    Snapshot snapshot = new Snapshot("", null);
  }

  /**
   * Tests snapshot throws an exception if either parameter is null.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstruction2() {
    Snapshot snapshot = new Snapshot(null, new Photo());
  }

  /**
   * Tests ID is generated from time stamp correctly.
   */
  @Test
  public void testGetID() {
    System.out.println(snapshot1CreateTime.toString()+ " " + t1.toString());
    assertTrue(snapshot1CreateTime.isAfter(t1));
    assertTrue(snapshot1CreateTime.isBefore(t2));
    assertTrue(snapshot2CreateTime.isAfter(t2));
    assertTrue(snapshot2CreateTime.isBefore(t3));
    assertTrue(snapshot3CreateTime.isAfter(t3));
    assertTrue(snapshot3CreateTime.isBefore(t4));
  }

  /**
   * Tests correct format of time stamp.
   */
  @Test
  public void getTimeStamp() {
    DateTimeFormatter TimeStampFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu kk:mm:ss");
    assertEquals(snapshot1CreateTime.format(TimeStampFormat), snapshot1.getTimeStamp());
    assertEquals(snapshot2CreateTime.format(TimeStampFormat), snapshot2.getTimeStamp());
    assertEquals(snapshot3CreateTime.format(TimeStampFormat), snapshot3.getTimeStamp());
  }

  /**
   * Tests getDescription.
   */
  @Test
  public void getDescription() {
    assertEquals("rectangle1 is created", snapshot1.getDescription());
    assertEquals("", snapshot2.getDescription());
    assertEquals("r1 changed color from blue to white", snapshot3.getDescription());
  }

  /**
   * Tests getPhoto.
   */
  @Test
  public void getPhoto() {
    assertSame(photo1, snapshot1.getPhoto());
    assertSame(photo2, snapshot2.getPhoto());
    assertSame(photo3, snapshot3.getPhoto());
  }

  /**
   * Tests toString.
   */
  @Test
  public void testToString() {
    String expectedStr = "Snapshot ID: " + snapshot1.getID() + "\n"
        + "Timestamp: " + snapshot1.getTimeStamp() + "\n"
        + "Description: " + snapshot1.getDescription() + "\n"
        + "Shape Information:" + "\n"
        + this.photo1;
    assertEquals(expectedStr, snapshot1.toString());
    expectedStr = "Snapshot ID: " + snapshot2.getID() + "\n"
        + "Timestamp: " + snapshot2.getTimeStamp() + "\n"
        + "Description: " + snapshot2.getDescription() + "\n"
        + "Shape Information:" + "\n"
        + this.photo2;
    assertEquals(expectedStr, snapshot2.toString());
    expectedStr = "Snapshot ID: " + snapshot3.getID() + "\n"
        + "Timestamp: " + snapshot3.getTimeStamp() + "\n"
        + "Description: " + snapshot3.getDescription() + "\n"
        + "Shape Information:" + "\n"
        + this.photo3;
    assertEquals(expectedStr, snapshot3.toString());
  }

  /**
   * Tests clone.
   */
  @Test
  public void testClone() {
    ISnapshot clone = snapshot1.clone();
    assertNotSame(clone, snapshot1);
    assertEquals(snapshot1.getDescription(), clone.getDescription());
    assertEquals(snapshot1.getID(), clone.getID());
    assertEquals(snapshot1.getTimeStamp(), clone.getTimeStamp());
    assertNotSame(clone.getPhoto(), snapshot1.getPhoto());
    assertEquals(clone.getPhoto().getShapes(), snapshot1.getPhoto().getShapes());
  }

  /**
   * Tests copy constructor.
   */
  @Test
  public void testCopyConstructor() {
    ISnapshot clone = new Snapshot(snapshot1);
    assertNotSame(clone, snapshot1);
    assertEquals(snapshot1.getDescription(), clone.getDescription());
    assertEquals(snapshot1.getID(), clone.getID());
    assertEquals(snapshot1.getTimeStamp(), clone.getTimeStamp());
    assertNotSame(clone.getPhoto(), snapshot1.getPhoto());
    assertEquals(clone.getPhoto().getShapes(), snapshot1.getPhoto().getShapes());
  }

}