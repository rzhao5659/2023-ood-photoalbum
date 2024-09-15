package controller;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.IShape;
import model.ISnapshot;
import model.IPhoto;
import model.Photo;
import model.ShapeFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for PhotoAlbumControllerGUI.
 * It sets up a mock model and a mock view in order to test the controller in isolation.
 */
public class PhotoAlbumControllerGUITest {
  private PhotoAlbumControllerGUI controller;

  private MockPhotoAlbumApp mockModel;
  private List<ISnapshot> uniqueSnapshots;
  private ISnapshot uniqueSnapshot;
  private int uniqueSnapshotIndex;
  private List<String> uniqueSnapshotIDs;

  private MockPhotoAlbumViewGUI mockView;
  private String uniqueSnapshotInfo;

  @Before
  public void setUp() {
    //Setting uniqueSnapshots, which is the list of snapshots to be returned from getSnapshots() method of the mock model.
    IPhoto photo1 = new Photo();
    IShape r1 = ShapeFactory.createBasicShape("r1", "rectangle", 1, 1, 2, 2, 1, 1, 1);
    IShape r2 = ShapeFactory.createBasicShape("r2", "rectangle", 5, 5, 10, 10, 0, 0, 0);
    photo1.addShape(r1);
    photo1.addShape(r2);
    IShape o1 = ShapeFactory.createBasicShape("o1", "oval", 100, 100, 10, 10, 0.1, 0.1, 0.1);
    IPhoto photo2 = new Photo(photo1);
    photo2.addShape(o1);
    IPhoto photo3 = new Photo(photo2);
    photo3.getShape("r1").setPosition(99, 99);
    uniqueSnapshots = new LinkedList<>();
    uniqueSnapshots.add(new MockSnapshot("snapshot_0_id", "snapshot_0_description", photo1));
    uniqueSnapshots.add(new MockSnapshot("snapshot_1_id", "snapshot_1_description",  photo2));
    uniqueSnapshots.add(new MockSnapshot("snapshot_2_id", "snapshot_2_description",  photo3));

    //Setting uniqueSnapshot, which is the snapshot to be returned from getSnapshot() method of the mock model.
    IPhoto photo4 = new Photo();
    IShape shape = ShapeFactory.createBasicShape("getSnapshot_r1", "rectangle", 50, 50, 30, 30, 1, 1, 1);
    photo4.addShape(shape);
    uniqueSnapshot = new MockSnapshot("getSnapshot_snapshot_id","getSnapshot_snapshot_description", photo4);

    //Setting other constant values of the mock model.
    uniqueSnapshotIndex = 1;
    uniqueSnapshotIDs = new LinkedList<>();
    uniqueSnapshotIDs.add("AAA");
    uniqueSnapshotIDs.add("BBB");
    uniqueSnapshotIDs.add("CCC");
    uniqueSnapshotIDs.add("DDD");
    mockModel = new MockPhotoAlbumApp(uniqueSnapshots, uniqueSnapshot, uniqueSnapshotIndex, uniqueSnapshotIDs);

    //View mock setup.
    uniqueSnapshotInfo = "SNAPSHOT_ID_FROM_VIEW";
    mockView = new MockPhotoAlbumViewGUI(uniqueSnapshotInfo);

    controller = new PhotoAlbumControllerGUI(mockModel, mockView);
  }

  /**
   * Tests that the go method of the controller sets up correctly view.
   */
  @Test
  public void testGo() {
    controller.go();
    //Assert that the controller set up correctly view.
    Map<String, List<String>> viewLog = mockView.getLog();
    assertEquals(1, viewLog.get("setCallbacks").size());
    assertEquals("[MockPhotoAlbumViewGUI]: setSnapshotList(snapshotIDs:[AAA, BBB, CCC, DDD])", viewLog.get("setSnapshotList").get(0));
    //Draws the first snapshot.
    assertEquals("[MockPhotoAlbumViewGUI]: setSnapshotInfo(id:snapshot_0_id,description:snapshot_0_description)", viewLog.get("setSnapshotInfo").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: drawRectangle(x:2.0,y:2.0,xDim:1.0,yDim:1.0,r:1.0,g:1.0,b:1.0)", viewLog.get("drawRectangle").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: drawRectangle(x:10.0,y:10.0,xDim:5.0,yDim:5.0,r:0.0,g:0.0,b:0.0)", viewLog.get("drawRectangle").get(1));
    assertEquals(0, viewLog.get("drawOval").size());
    //The drawing process requires clearing the view screen and refresh it, so there should be exactly one call for clearDrawPanel and repaintDrawPanel.
    assertEquals(1, viewLog.get("clearDrawPanel").size());
    assertEquals(1, viewLog.get("repaintDrawPanel").size());
  }

  /**
   * Tests next snapshot callback function when the current snapshot is not the final snapshot in the list.
   */
  @Test
  public void testNextSnapshotCallback1() {
    controller.nextSnapshotCallback();
    Map<String, List<String>> modelLog = mockModel.getLog();
    Map<String, List<String>> viewLog = mockView.getLog();
    //Assert that the model gets the snapshot id that is displayed in view.
    assertEquals("[MockPhotoAlbumApp]: getSnapshotIndex(id:SNAPSHOT_ID_FROM_VIEW)", modelLog.get("getSnapshotIndex").get(0));
    //Assert that view draws the next snapshot in the list of snapshots provided by the model.
    //Since the mock model always returns the index 1, the next snapshot is at index 2.
    assertEquals("[MockPhotoAlbumViewGUI]: setSnapshotInfo(id:snapshot_2_id,description:snapshot_2_description)", viewLog.get("setSnapshotInfo").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: setSnapshotListSelectedValue(index:2)", viewLog.get("setSnapshotListSelectedValue").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: drawRectangle(x:99.0,y:99.0,xDim:1.0,yDim:1.0,r:1.0,g:1.0,b:1.0)", viewLog.get("drawRectangle").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: drawRectangle(x:10.0,y:10.0,xDim:5.0,yDim:5.0,r:0.0,g:0.0,b:0.0)", viewLog.get("drawRectangle").get(1));
    assertEquals("[MockPhotoAlbumViewGUI]: drawOval(x:10.0,y:10.0,xDim:100.0,yDim:100.0,r:0.1,g:0.1,b:0.1)", viewLog.get("drawOval").get(0));
    //The drawing process requires clearing the view screen and refresh it, so there should be exactly one call for clearDrawPanel and repaintDrawPanel.
    assertEquals(1, viewLog.get("clearDrawPanel").size());
    assertEquals(1, viewLog.get("repaintDrawPanel").size());
    //Assert that error dialog isn't called.
    assertEquals(0, viewLog.get("displayErrorDialog").size());
  }

  /**
   * Tests next snapshot callback function when the current snapshot is the final snapshot in the list.
   */
  @Test
  public void testNextSnapshotCallback2() {
    uniqueSnapshotIndex = 2;
    mockModel = new MockPhotoAlbumApp(uniqueSnapshots, uniqueSnapshot, uniqueSnapshotIndex, uniqueSnapshotIDs);
    controller = new PhotoAlbumControllerGUI(mockModel, mockView);

    controller.nextSnapshotCallback();
    Map<String, List<String>> modelLog = mockModel.getLog();
    Map<String, List<String>> viewLog = mockView.getLog();
    //Assert that the model gets the snapshot id that is displayed in view.
    assertEquals("[MockPhotoAlbumApp]: getSnapshotIndex(id:SNAPSHOT_ID_FROM_VIEW)", modelLog.get("getSnapshotIndex").get(0));
    //Assert that view opens an error dialog and doesn't draw anything.
    assertEquals(1, viewLog.get("displayErrorDialog").size());
    assertEquals(0, viewLog.get("setSnapshotInfo").size());
    assertEquals(0, viewLog.get("setSnapshotListSelectedValue").size());
    assertEquals(0, viewLog.get("drawRectangle").size());
    assertEquals(0, viewLog.get("drawOval").size());
    assertEquals(0, viewLog.get("clearDrawPanel").size());
    assertEquals(0, viewLog.get("repaintDrawPanel").size());
  }

  /**
   * Tests prev snapshot callback function when the current snapshot is not the first snapshot in the list.
   */
  @Test
  public void testPrevSnapshotCallback1() {
    controller.prevSnapshotCallback();
    Map<String, List<String>> modelLog = mockModel.getLog();
    Map<String, List<String>> viewLog = mockView.getLog();
    //Assert that the model gets the snapshot id that is displayed in view.
    assertEquals("[MockPhotoAlbumApp]: getSnapshotIndex(id:SNAPSHOT_ID_FROM_VIEW)", modelLog.get("getSnapshotIndex").get(0));
    //Assert that view draws the previous snapshot in the list of snapshots provided by the model.
    //Since the mock model always returns the index 1, the previous snapshot is at index 0.
    assertEquals("[MockPhotoAlbumViewGUI]: setSnapshotInfo(id:snapshot_0_id,description:snapshot_0_description)", viewLog.get("setSnapshotInfo").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: setSnapshotListSelectedValue(index:0)", viewLog.get("setSnapshotListSelectedValue").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: drawRectangle(x:2.0,y:2.0,xDim:1.0,yDim:1.0,r:1.0,g:1.0,b:1.0)", viewLog.get("drawRectangle").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: drawRectangle(x:10.0,y:10.0,xDim:5.0,yDim:5.0,r:0.0,g:0.0,b:0.0)", viewLog.get("drawRectangle").get(1));
    assertEquals(0, viewLog.get("drawOval").size());
    //The drawing process requires clearing the view screen and refresh it, so there should be exactly one call for clearDrawPanel and repaintDrawPanel.
    assertEquals(1, viewLog.get("clearDrawPanel").size());
    assertEquals(1, viewLog.get("repaintDrawPanel").size());
    //Assert that error dialog isn't called.
    assertEquals(0, viewLog.get("displayErrorDialog").size());
  }

  /**
   * Tests prev snapshot callback function when the current snapshot is the first snapshot in the list.
   */
  @Test
  public void testPrevSnapshotCallback2() {
    uniqueSnapshotIndex = 0;
    mockModel = new MockPhotoAlbumApp(uniqueSnapshots, uniqueSnapshot, uniqueSnapshotIndex, uniqueSnapshotIDs);
    controller = new PhotoAlbumControllerGUI(mockModel, mockView);

    controller.prevSnapshotCallback();
    Map<String, List<String>> modelLog = mockModel.getLog();
    Map<String, List<String>> viewLog = mockView.getLog();
    //Assert that the model gets the snapshot id that is displayed in view.
    assertEquals("[MockPhotoAlbumApp]: getSnapshotIndex(id:SNAPSHOT_ID_FROM_VIEW)", modelLog.get("getSnapshotIndex").get(0));
    //Assert that view opens an error dialog and doesn't draw anything.
    assertEquals(1, viewLog.get("displayErrorDialog").size());
    assertEquals(0, viewLog.get("setSnapshotInfo").size());
    assertEquals(0, viewLog.get("setSnapshotListSelectedValue").size());
    assertEquals(0, viewLog.get("drawRectangle").size());
    assertEquals(0, viewLog.get("drawOval").size());
    assertEquals(0, viewLog.get("clearDrawPanel").size());
    assertEquals(0, viewLog.get("repaintDrawPanel").size());
  }

  /**
   * Tests browse snapshot callback function.
   */
  @Test
  public void testBrowseSnapshotCallback() {
    controller.browseSnapshotCallback();
    controller.browseSnapshotCallback();
    controller.browseSnapshotCallback();
    Map<String, List<String>> viewLog = mockView.getLog();

    //By default, the snapshot browser panel is hidden.
    //The first call of browseSnapshotCallback should make it appear.
    //The second call of browseSnapshotCallback should remove it.
    //The third call of browseSnapshotCallback should make it appear.
    assertEquals(2, viewLog.get("showSnapshotBrowser").size());
    assertEquals(1, viewLog.get("closeSnapshotBrowser").size());
  }

  /**
   * Tests select snapshot callback function.
   */
  @Test
  public void testSelectSnapshotCallback() {

    //View passes the selected snapshot ID to the controller when the user selects the snapshot from the list of snapshots.
    //Manually passing an ID.
    controller.selectSnapshotCallback("UNIQUE_ID_FROM_VIEW");
    Map<String, List<String>> modelLog = mockModel.getLog();
    Map<String, List<String>> viewLog = mockView.getLog();

    //Assert that the controller passes this ID from view to the model in order to get the correct snapshot.
    assertEquals("[MockPhotoAlbumApp]: getSnapshot(id:UNIQUE_ID_FROM_VIEW)", modelLog.get("getSnapshot").get(0));
    //Assert that the view receives the exact snapshot returned by the model and draws it.
    assertEquals("[MockPhotoAlbumViewGUI]: setSnapshotInfo(id:getSnapshot_snapshot_id,description:getSnapshot_snapshot_description)", viewLog.get("setSnapshotInfo").get(0));
    assertEquals("[MockPhotoAlbumViewGUI]: drawRectangle(x:30.0,y:30.0,xDim:50.0,yDim:50.0,r:1.0,g:1.0,b:1.0)", viewLog.get("drawRectangle").get(0));
    assertEquals(0, viewLog.get("drawOval").size());
    //The drawing process requires clearing the view screen and refresh it, so there should be exactly one call for clearDrawPanel and repaintDrawPanel.
    assertEquals(1, viewLog.get("clearDrawPanel").size());
    assertEquals(1, viewLog.get("repaintDrawPanel").size());
  }

}
