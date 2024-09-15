package modelTest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import model.IPhoto;
import model.IPhotoAlbumApp;
import model.IShape;
import model.ISnapshot;
import model.Photo;
import model.PhotoAlbumApp;
import model.ShapeFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Suite for PhotoAlbumApp.
 * All test cases will use and test getPhotoAlbum() and getCurrentPhoto().
 */
public class PhotoAlbumAppTest {
  private IPhotoAlbumApp app;

  @Before
  public void setUp() {
    app = new PhotoAlbumApp();
  }

  /**
   * Tests initial behavior of getPhotoAlbum, getCurrentPhoto.
   * Although the photo album will be empty, the current photo is empty and
   * not null to indicate no shapes.
   */
  @Test
  public void testGetPhotosInitial() {
    IPhoto emptyPhoto = new Photo();
    assertEquals(emptyPhoto, app.getCurrentPhoto());
    assertEquals(0, app.getPhotoAlbum().size());
  }

  /**
   * Tests creation of shapes and the methods getPhotoAlbum, getCurrentPhoto.
   */
  @Test
  public void testCreateBasicShapeAndPhotosGetters() {
    IShape r1 = ShapeFactory.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    IShape c1 = ShapeFactory.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    IShape r2 = ShapeFactory.createBasicShape("r2", "rectangle", 51.23, 5.421, -41, +131, 1, 0.5, 0.23);

    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    //First photo should have 1 shape: r1.
    IPhoto firstPhoto = app.getCurrentPhoto();
    assertEquals(1, firstPhoto.size());
    assertEquals(r1, firstPhoto.getShape("r1"));

    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    //Second photo should have 2 shapes: r1 and c1.
    IPhoto secondPhoto = app.getCurrentPhoto();
    assertEquals(2, secondPhoto.size());
    assertEquals(r1, secondPhoto.getShape("r1"));
    assertEquals(c1, secondPhoto.getShape("c1"));

    app.createBasicShape("r2", "rectangle", 51.23, 5.421, -41, +131, 1, 0.5, 0.23);
    //Third photo should have 3 shapes: r1, c1 and r2.
    IPhoto thirdPhoto = app.getCurrentPhoto();
    assertEquals(3, thirdPhoto.size());
    assertEquals(r1, thirdPhoto.getShape("r1"));
    assertEquals(c1, thirdPhoto.getShape("c1"));
    assertEquals(r2, thirdPhoto.getShape("r2"));

    //PhotoAlbum should contain 3 photos, which tells the history of changes in shapes.
    List<IPhoto> photos = app.getPhotoAlbum();
    assertEquals(firstPhoto, photos.get(0));
    assertEquals(secondPhoto, photos.get(1));
    assertEquals(thirdPhoto, photos.get(2));
  }

  /**
   * Tests creating a new shape with a name that already exists on the photo fails.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateShapeDuplicateName() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("r1", "oval", 51.23, 5.421, -41, +131, 1, 0.5, 0.23);
  }

  /**
   * Tests creating shape with invalid type.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape1() {
    app.createBasicShape("r1", "helo", 2.5, 5, 1, 5, 0, 0, 0);
  }

  /**
   * Tests creating shape with invalid dimensions.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape2() {
    app.createBasicShape("r1", "rectangle", -1, 0, 1, 5, 0, 0, 0);
  }

  /**
   * Tests creating shape with invalid color.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape3() {
    app.createBasicShape("r1", "rectangle", 1, 1, 1, 5, 0.41, 0.5, 1.23);
  }

  /**
   * Tests creating shape with invalid color.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape4() {
    app.createBasicShape("r1", "rectangle", 1, 1, 1, 5, -0.41, 0.5, 1);
  }

  /**
   * Tests creating shape with invalid name that contains space.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape5() {
    app.createBasicShape("dq f93", "rectangle", 1, 1, 1, 5, -0.41, 0.5, 1);
  }

  /**
   * Tests creating shape with empty name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape6() {
    app.createBasicShape("", "rectangle", 1, 1, 1, 5, -0.41, 0.5, 1);
  }

  /**
   * Tests creating shape with null name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape7() {
    app.createBasicShape(null, "rectangle", 1, 1, 1, 5, -0.41, 0.5, 1);
  }

  /**
   * Tests creating shape with null type.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailCreateInvalidShape8() {
    app.createBasicShape("r1", null, 1, 1, 1, 5, -0.41, 0.5, 1);
  }

  /**
   * Tests getPhotoAlbumRange.
   */
  @Test
  public void testGetPhotoAlbumRange() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    IPhoto photo1 = app.getCurrentPhoto();
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    IPhoto photo2 = app.getCurrentPhoto();
    app.createBasicShape("c2", "oval", 1, 1, 1, 5, 1, 1, 1);
    IPhoto photo3 = app.getCurrentPhoto();
    app.createBasicShape("o1", "oval", 1, 1, 1, 5, 1, 1, 1);
    IPhoto photo4 = app.getCurrentPhoto();
    app.createBasicShape("r2", "rectangle", 51.23, 5.421, -41, +131, 1, 0.5, 0.23);
    IPhoto photo5 = app.getCurrentPhoto();

    List<IPhoto> expectedPhotos;
    expectedPhotos = new LinkedList<>(Arrays.asList(photo1));
    assertEquals(expectedPhotos, app.getPhotoAlbumRange(0, 0));

    expectedPhotos = new LinkedList<>(Arrays.asList(photo1, photo2, photo3));
    assertEquals(expectedPhotos, app.getPhotoAlbumRange(0, 2));

    expectedPhotos = new LinkedList<>(Arrays.asList(photo2, photo3, photo4, photo5));
    assertEquals(expectedPhotos, app.getPhotoAlbumRange(1, 4));

    expectedPhotos = new LinkedList<>(Arrays.asList(photo1, photo2, photo3, photo4, photo5));
    assertEquals(expectedPhotos, app.getPhotoAlbumRange(0, 4));
  }

  /**
   * Tests getPhotoAlbumRange fails when photo album is empty.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailGetPhotoAlbumRangeEmpty() {
    app.getPhotoAlbumRange(0, 0);
  }

  /**
   * Tests getPhotoAlbumRange fails when lower index is higher than upper index.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailGetPhotoAlbumRange2() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.getPhotoAlbumRange(1, 0);
  }

  /**
   * Tests getPhotoAlbumRange fails when the lower index is less than 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailGetPhotoAlbumRange3() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.getPhotoAlbumRange(-1, 1);
  }

  /**
   * Tests getPhotoAlbumRange fails when the upper index is equal to or
   * greater than the number of photos.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailGetPhotoAlbumRange4() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.getPhotoAlbumRange(0, 2);
  }

  /**
   * Tests removing shapes.
   */
  @Test
  public void testRemoveShape() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);

    //The current photo should only have c1.
    app.removeShape("r1");
    IPhoto thirdPhoto = app.getCurrentPhoto();
    assertEquals(1, thirdPhoto.size());
    IShape c1 = ShapeFactory.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    assertEquals(c1, thirdPhoto.getShape("c1"));

    //The current photo should now be empty (no shapes).
    app.removeShape("c1");
    IPhoto fourthPhoto = app.getCurrentPhoto();
    assertEquals(0, fourthPhoto.size());

    //The photo album should have 4 photos in total.
    List<IPhoto> photos = app.getPhotoAlbum();
    assertEquals(4, photos.size());
    assertEquals(thirdPhoto, photos.get(2));
    assertEquals(fourthPhoto, photos.get(3));
  }

  /**
   * Tests removing an unknown shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailRemoveUnknownShape() {
    app.removeShape("r1");
  }

  /**
   * Tests remove shape with a null name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailRemoveNullNameShape() {
    app.removeShape(null);
  }

  /**
   * Tests setting colors of shapes.
   */
  @Test
  public void testSetShapeColor() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.setShapeColor("c1", 0.512, 0.149, 0.234);
    app.setShapeColor("c1", 0, 0.31, 0.31);
    app.setShapeColor("r1", 0.1, 0.1, 0.1);
    List<IPhoto> photos = app.getPhotoAlbumRange(2, 4);
    //Tests that all photos have the right colors.
    assertArrayEquals(new double[]{0.512, 0.149, 0.234}, photos.get(0).getShape("c1").getColor(), 0.001);
    assertArrayEquals(new double[]{0, 0.31, 0.31}, photos.get(1).getShape("c1").getColor(), 0.001);
    assertArrayEquals(new double[]{0.1, 0.1, 0.1}, photos.get(2).getShape("r1").getColor(), 0.001);
    //Tests that old photos aren't changed.
    List<IPhoto> OldPhotos = app.getPhotoAlbumRange(1, 1);
    assertArrayEquals(new double[]{0, 0, 0}, OldPhotos.get(0).getShape("r1").getColor(), 0.001);
    assertArrayEquals(new double[]{1, 1, 1}, OldPhotos.get(0).getShape("c1").getColor(), 0.001);
  }

  /**
   * Test setting color an unknown shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapeColorUnknownShape() {
    app.setShapeColor("r2", 0.512, 0.149, 0.234);
  }

  /**
   * Test setShapeColor on a null name shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapeColorNullName() {
    app.setShapeColor(null, 0.512, 0.149, 0.234);
  }

  /**
   * Test setShapeColor with invalid colors.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapeInvalidColor() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.setShapeColor("r1", 1.3, 0.149, 0.234);
  }

  /**
   * Test setting shape positions.
   */
  @Test
  public void setShapePosition() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.setShapePosition("c1", 0.512, 0.149);
    app.setShapePosition("c1", -412, 41);
    app.setShapePosition("r1", 0, 0);
    List<IPhoto> photos = app.getPhotoAlbumRange(2, 4);
    //Tests that all photos have the right positions.
    assertArrayEquals(new double[]{0.512, 0.149}, photos.get(0).getShape("c1").getPosition(), 0.001);
    assertArrayEquals(new double[]{-412, 41}, photos.get(1).getShape("c1").getPosition(), 0.001);
    assertArrayEquals(new double[]{0, 0}, photos.get(2).getShape("r1").getPosition(), 0.001);
    //Tests that old photos aren't changed.
    List<IPhoto> OldPhotos = app.getPhotoAlbumRange(1, 1);
    assertArrayEquals(new double[]{1, 5}, OldPhotos.get(0).getShape("r1").getPosition(), 0.001);
    assertArrayEquals(new double[]{1, 5}, OldPhotos.get(0).getShape("c1").getPosition(), 0.001);
  }

  /**
   * Test setting position for an unknown shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapePositionUnknownShape() {
    app.setShapePosition("r2", 0, 0);
  }

  /**
   * Test setShapePosition with null parameter.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapePositionNullName() {
    app.setShapePosition(null, 0, 0);
  }

  /**
   * Test setShapeXDim.
   */
  @Test
  public void testSetShapeXDim() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.setShapeXDim("c1", 0.512);
    app.setShapeXDim("r1", 1412);
    List<IPhoto> photos = app.getPhotoAlbumRange(2, 3);
    assertEquals(0.512, photos.get(0).getShape("c1").getXDim(), 0.001);
    assertEquals(1412, photos.get(1).getShape("r1").getXDim(), 0.001);
    //Tests that old photos aren't changed.
    List<IPhoto> OldPhotos = app.getPhotoAlbumRange(1, 1);
    assertEquals(2.5, OldPhotos.get(0).getShape("r1").getXDim(), 0.001);
    assertEquals(1, OldPhotos.get(0).getShape("c1").getXDim(), 0.001);
  }

  /**
   * Test setShapeXDim on an unknown shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetUnknownShapeXDim() {
    app.setShapeXDim("c1", 0.512);
  }

  /**
   * Test setShapeXDim with null name as parameter.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetNullNameShapeXDim() {
    app.setShapeXDim(null, 0.512);
  }

  /**
   * Test setShapeXDim with a negative dimension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapeNegativeXDim() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.setShapeXDim("r1", -10);
  }

  /**
   * Test setShapeXDim with a zero dimension value.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapeZeroXDim() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.setShapeXDim("r1", 0);
  }

  /**
   * Test setShapeYDim.
   */
  @Test
  public void testSetShapeYDim() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.setShapeYDim("c1", 0.512);
    app.setShapeYDim("r1", 1412);
    List<IPhoto> photos = app.getPhotoAlbumRange(2, 3);
    assertEquals(0.512, photos.get(0).getShape("c1").getYDim(), 0.001);
    assertEquals(1412, photos.get(1).getShape("r1").getYDim(), 0.001);
    //Tests that old photos aren't changed.
    List<IPhoto> OldPhotos = app.getPhotoAlbumRange(1, 1);
    assertEquals(5, OldPhotos.get(0).getShape("r1").getYDim(), 0.001);
    assertEquals(1, OldPhotos.get(0).getShape("c1").getYDim(), 0.001);
  }

  /**
   * Test setShapeYDim on an unknown shape.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetUnknownShapeYDim() {
    app.setShapeYDim("c1", 0.512);
  }

  /**
   * Test setShapeYDim with null name as parameter.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetNullNameShapeYDim() {
    app.setShapeYDim(null, 0.512);
  }

  /**
   * Test setShapeYDim with a negative dimension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapeNegativeYDim() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.setShapeYDim("r1", -10);
  }

  /**
   * Test setShapeYDim with a zero dimension value.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetShapeZeroYDim() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.setShapeYDim("r1", 0);
  }

  /**
   * Test getting and taking snapshots.
   */
  @Test
  public void testSnapshot() {
    app.takeSnapshot("");
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.setShapeYDim("r1", 0.512);
    app.takeSnapshot("I don't date short rectangles.");
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.setShapePosition("c1", 50, -50);
    app.takeSnapshot("c1 in 50 and -50.");
    app.setShapeXDim("r1", 1412);
    app.takeSnapshot("I don't date fat and short rectangles.");
    app.takeSnapshot("lastSnapshot");

    List<ISnapshot> snapshots = app.getSnapshots();
    assertEquals(5, snapshots.size());

    assertEquals("", snapshots.get(0).getDescription());
    assertEquals("I don't date short rectangles.", snapshots.get(1).getDescription());
    assertEquals("c1 in 50 and -50.", snapshots.get(2).getDescription());
    assertEquals("I don't date fat and short rectangles.", snapshots.get(3).getDescription());
    assertEquals("lastSnapshot", snapshots.get(4).getDescription());

    assertEquals(snapshots.get(0), app.getSnapshot(snapshots.get(0).getID()));
    assertEquals(snapshots.get(1), app.getSnapshot(snapshots.get(1).getID()));
    assertEquals(snapshots.get(2), app.getSnapshot(snapshots.get(2).getID()));
    assertEquals(snapshots.get(3), app.getSnapshot(snapshots.get(3).getID()));
    assertEquals(snapshots.get(4), app.getSnapshot(snapshots.get(4).getID()));

    //Verify that first, second, and last snapshots contain the correct photos.
    assertNull(snapshots.get(0).getPhoto().getShape("r1"));
    assertEquals(
        ShapeFactory.createBasicShape("r1", "rectangle", 2.5, 0.512, 1, 5, 0, 0, 0),
        snapshots.get(1).getPhoto().getShape("r1")
    );
    assertNull(snapshots.get(1).getPhoto().getShape("c1"));
    assertEquals(
        ShapeFactory.createBasicShape("r1", "rectangle", 1412, 0.512, 1, 5, 0, 0, 0),
        snapshots.get(4).getPhoto().getShape("r1")
    );
    assertEquals(
        ShapeFactory.createBasicShape("c1", "oval", 1, 1, 50, -50, 1, 1, 1),
        snapshots.get(4).getPhoto().getShape("c1")
    );

  }

  /**
   * Test getting snapshot IDS
   */
  @Test
  public void testGetSnapshotIDs() {
    app.takeSnapshot("");
    app.takeSnapshot("I don't date short rectangles.");
    app.takeSnapshot("c1 in 50 and -50.");
    app.takeSnapshot("I don't date fat and short rectangles.");
    app.takeSnapshot("lastSnapshot");

    List<ISnapshot> snapshots = app.getSnapshots();
    List<String> snapshotIDs = app.getSnapshotIDs();

    assertEquals(5, snapshots.size());
    assertEquals(snapshotIDs.get(0), snapshots.get(0).getID());
    assertEquals(snapshotIDs.get(1), snapshots.get(1).getID());
    assertEquals(snapshotIDs.get(2), snapshots.get(2).getID());
    assertEquals(snapshotIDs.get(3), snapshots.get(3).getID());
    assertEquals(snapshotIDs.get(4), snapshots.get(4).getID());
  }


  /**
   * Test getSnapshot returns null if it's not found.
   */
  @Test
  public void testGetUnknownSnapshot() {
    app.takeSnapshot("");
    assertNull(app.getSnapshot("___"));
  }

  /**
   * Test getSnapshot fails if ID is null
   */
  @Test (expected = IllegalArgumentException.class)
  public void testGetSnapshotNullID() {
    app.takeSnapshot("");
    assertNull(app.getSnapshot(null));
  }

  /**
   * Tests that photos are cleared and snapshots are still in memory.
   */
  @Test
  public void testClearPhotos() {
    app.clearPhotos();
    app.takeSnapshot("");
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.setShapeYDim("r1", 0.512);
    app.takeSnapshot("I don't date short rectangles.");
    app.createBasicShape("c1", "oval", 1, 1, 1, 5, 1, 1, 1);
    app.setShapePosition("c1", 50, -50);
    app.takeSnapshot("c1 in 50 and -50.");

    app.clearPhotos();
    //Current photo now returns a photo with no shapes, and the photo album has no photos.
    assertEquals(0, app.getCurrentPhoto().size());
    assertEquals(0, app.getPhotoAlbum().size());
    //Snapshots still exist in memory.
    assertEquals(3, app.getSnapshots().size());
    assertEquals("", app.getSnapshots().get(0).getDescription());
    assertEquals("I don't date short rectangles.", app.getSnapshots().get(1).getDescription());
    assertEquals("c1 in 50 and -50.", app.getSnapshots().get(2).getDescription());
  }

  /**
   * Tests that one can't modify the photo from the returned snapshot.
   */
  @Test
  public void testGetSnapshotReturnsCopy() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    app.takeSnapshot("");
    //Modifying snapshot from getSnapshots.
    ISnapshot snapshot = app.getSnapshots().get(0);
    snapshot.getPhoto().getShape("r1").setXDim(100);
    //Modifying snapshot from getSnapshot
    ISnapshot anotherSnapshot = app.getSnapshot(snapshot.getID());
    anotherSnapshot.getPhoto().getShape("r1").setYDim(50);
    //Snapshot stored in system should be the same as original.
    assertEquals(2.5, app.getSnapshots().get(0).getPhoto().getShape("r1").getXDim(), 0.001);
    assertEquals(5, app.getSnapshots().get(0).getPhoto().getShape("r1").getYDim(), 0.001);
  }

  /**
   * Tests that one can't modify the photo from the returned photos.
   */
  @Test
  public void testGetPhotosReturnsCopy() {
    app.createBasicShape("r1", "rectangle", 2.5, 5, 1, 5, 0, 0, 0);
    //Modifying photo from getCurrentPhoto.
    IPhoto photo = app.getCurrentPhoto();
    photo.getShape("r1").setXDim(100);
    //Modifying photo from getPhotoAlbums.
    IPhoto photo2 = app.getPhotoAlbum().get(0);
    photo2.getShape("r1").setYDim(50);
    //Modifying photo from getPhotoAlbumsRange.
    IPhoto photo3 = app.getPhotoAlbumRange(0,0).get(0);
    photo3.getShape("r1").setPosition(50, 50);
    //Photo stored in system should be the same as original.
    assertEquals(2.5, app.getCurrentPhoto().getShape("r1").getXDim(), 0.001);
    assertEquals(5, app.getCurrentPhoto().getShape("r1").getYDim(), 0.001);
    assertArrayEquals(new double[]{1, 5}, app.getCurrentPhoto().getShape("r1").getPosition(), 0.001);
  }

  @Test
  public void testToString() {
    app.createBasicShape("r1", "rectangle", 1, 1, 1, 1, 1, 1, 1);
    app.takeSnapshot("r1 created");
    app.createBasicShape("o1", "oval", 2, 2, 2, 2, 0.5, 0.5, 0.5);
    app.setShapePosition("r1", 50, 50);
    app.takeSnapshot("changed r1 position");
    app.removeShape("r1");
    app.takeSnapshot("r1 removed");

    StringBuilder photosString = new StringBuilder();
    app.getPhotoAlbum().forEach(photo -> photosString.append(photo.toString()).append("\n==========\n"));
    photosString.append("\n");

    StringBuilder listSnapshotsString = new StringBuilder();
    listSnapshotsString.append("List of snapshots taken before reset: [");
    app.getSnapshots().forEach(snapshot -> listSnapshotsString.append(snapshot.getID()).append(", "));
    listSnapshotsString.deleteCharAt(listSnapshotsString.length()-1);
    listSnapshotsString.deleteCharAt(listSnapshotsString.length()-1);
    listSnapshotsString.append("]\n\n");

    StringBuilder snapshotsString = new StringBuilder();
    snapshotsString.append("Printing Snapshots\n\n");
    StringJoiner snapshotsInfo = new StringJoiner("\n\n");
    app.getSnapshots().forEach(snapshot -> snapshotsInfo.add(snapshot.toString()));
    snapshotsString.append(snapshotsInfo.toString());

    assertEquals(photosString.toString()
        + listSnapshotsString.toString() + snapshotsString.toString(), app.toString());
  }

}