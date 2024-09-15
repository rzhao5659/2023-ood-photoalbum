package controller;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.IPhoto;
import model.IShape;
import model.ISnapshot;
import model.Photo;
import model.ShapeFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for PhotoAlbumControllerText.
 * It sets up a mock model and a mock view in order to test the controller in isolation.
 */
public class PhotoAlbumControllerTextTest {
  private PhotoAlbumControllerText controller;

  private MockPhotoAlbumApp mockModel;
  private List<ISnapshot> uniqueSnapshots;
  private ISnapshot uniqueSnapshot;
  private int uniqueSnapshotIndex;
  private List<String> uniqueSnapshotIDs;

  private MockPhotoAlbumViewHTML mockView;
  private PrintWriter out;

  @Before
  public void setUp() {
    //Setting uniqueSnapshots, which is the list of snapshots to be returned from getSnapshots() method of the mock model.
    IPhoto photo1 = new Photo();
    IShape r1 = ShapeFactory.createBasicShape("r1", "rectangle", 1, 1, 2, 2, 1, 1, 1);
    IShape r2 = ShapeFactory.createBasicShape("r2", "rectangle", 5, 5, 10, 10, 0, 0, 0);
    photo1.addShape(r1);
    photo1.addShape(r2);
    IPhoto photo2 = new Photo(photo1);
    IShape o1 = ShapeFactory.createBasicShape("o1", "oval", 100, 100, 10, 10, 0.1, 0.1, 0.1);
    photo2.addShape(o1);
    IPhoto photo3 = new Photo(photo2);
    photo3.getShape("r1").setPosition(99, 99);
    uniqueSnapshots = new LinkedList<>();
    uniqueSnapshots.add(new MockSnapshot("snapshot_0_id", "snapshot_0_description", photo1));
    uniqueSnapshots.add(new MockSnapshot("snapshot_1_id", "snapshot_1_description",  photo2));
    uniqueSnapshots.add(new MockSnapshot("snapshot_2_id", "snapshot_2_description",  photo3));
    //Setting uniqueSnapshot, which is the snapshot to be returned from getSnapshot() method of the mock model.
    uniqueSnapshot = new MockSnapshot("getSnapshot_snapshot_id","getSnapshot_snapshot_description", new Photo());
    //Setting other constant values of the mock model.
    uniqueSnapshotIndex = 100;
    uniqueSnapshotIDs = new LinkedList<>();
    mockModel = new MockPhotoAlbumApp(uniqueSnapshots, uniqueSnapshot, uniqueSnapshotIndex, uniqueSnapshotIDs);

    //View mock setup.
    out = new PrintWriter(new StringWriter());
    mockView = new MockPhotoAlbumViewHTML();

    controller = new PhotoAlbumControllerText(mockModel, mockView, out);
  }

  /**
   * Tests the controller passes all snapshot (its metadata and the state of shapes) from the model correctly to view without any changes.
   * Tests the controller passes the output stream to view without any changes.
   */
  @Test
  public void testGo() {
    controller.go();
    Map<String, List<String>> modelLog = mockModel.getLog();
    Map<String, List<String>> viewLog = mockView.getLog();
    //Assert all snapshots from the model are passed to the view without changes.
    assertEquals("[MockPhotoAlbumViewHTML]: startDrawingSnapshot(id:snapshot_0_id,description:snapshot_0_description)", viewLog.get("startDrawingSnapshot").get(0));
    assertEquals("[MockPhotoAlbumViewHTML]: drawRectangle(x:2.0,y:2.0,xDim:1.0,yDim:1.0,r:1.0,g:1.0,b:1.0)", viewLog.get("drawRectangle").get(0));
    assertEquals("[MockPhotoAlbumViewHTML]: drawRectangle(x:10.0,y:10.0,xDim:5.0,yDim:5.0,r:0.0,g:0.0,b:0.0)", viewLog.get("drawRectangle").get(1));
    assertEquals("[MockPhotoAlbumViewHTML]: startDrawingSnapshot(id:snapshot_1_id,description:snapshot_1_description)", viewLog.get("startDrawingSnapshot").get(1));
    assertEquals("[MockPhotoAlbumViewHTML]: drawRectangle(x:2.0,y:2.0,xDim:1.0,yDim:1.0,r:1.0,g:1.0,b:1.0)", viewLog.get("drawRectangle").get(2));
    assertEquals("[MockPhotoAlbumViewHTML]: drawRectangle(x:10.0,y:10.0,xDim:5.0,yDim:5.0,r:0.0,g:0.0,b:0.0)", viewLog.get("drawRectangle").get(3));
    assertEquals("[MockPhotoAlbumViewHTML]: drawOval(x:10.0,y:10.0,xDim:100.0,yDim:100.0,r:0.1,g:0.1,b:0.1)", viewLog.get("drawOval").get(0));
    assertEquals("[MockPhotoAlbumViewHTML]: startDrawingSnapshot(id:snapshot_2_id,description:snapshot_2_description)", viewLog.get("startDrawingSnapshot").get(2));
    assertEquals("[MockPhotoAlbumViewHTML]: drawRectangle(x:99.0,y:99.0,xDim:1.0,yDim:1.0,r:1.0,g:1.0,b:1.0)", viewLog.get("drawRectangle").get(4));
    assertEquals("[MockPhotoAlbumViewHTML]: drawRectangle(x:10.0,y:10.0,xDim:5.0,yDim:5.0,r:0.0,g:0.0,b:0.0)", viewLog.get("drawRectangle").get(5));
    assertEquals("[MockPhotoAlbumViewHTML]: drawOval(x:10.0,y:10.0,xDim:100.0,yDim:100.0,r:0.1,g:0.1,b:0.1)", viewLog.get("drawOval").get(1));
    assertEquals(3, viewLog.get("endDrawingSnapshot").size());
    //Assert that printOutput is done over the output stream passed to the controller.
    assertEquals("[MockPhotoAlbumViewHTML]: printOutput(out:" + this.out +")", viewLog.get("printOutput").get(0));
  }
}