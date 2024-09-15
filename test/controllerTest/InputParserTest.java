package controllerTest;

import static org.junit.Assert.*;

import controller.IInputParser;
import controller.InputParser;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringJoiner;
import model.IPhotoAlbumApp;
import model.ISnapshot;
import model.PhotoAlbumApp;
import model.ShapeFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the input parser.
 * Assumes the given PhotoAlbumApp model is well tested.
 */
public class InputParserTest {
  private IInputParser inputParser;
  private IPhotoAlbumApp model;

  @Before
  public void setUp() {
    inputParser = new InputParser();
    model = new PhotoAlbumApp();
  }

  /**
   * Tests parsing a sequence of commands from a string.
   */
  @Test
  public void testParse1() {
    StringJoiner cmds = new StringJoiner("\n");
    cmds.add("shape   myrect   rectangle  200  200 50  100  255  0  0");
    cmds.add("shape   myoval   oval       500  100 60  30   0 255 1");
    cmds.add("snapShot created myrect and myoval");
    cmds.add("# blabla");
    cmds.add("move myrect     300     200");
    cmds.add("resize myrect   25      100");
    cmds.add("# blabla");
    cmds.add("color myrect    0  0  255");
    cmds.add("snapShot changed myrect");
    cmds.add("remove myrect");
    cmds.add("move myoval     500   400");
    cmds.add("# blabla");
    cmds.add("snapShot");
    inputParser.parse(new ByteArrayInputStream(cmds.toString().getBytes()), model);

    //Tests all snapshots.
    List<ISnapshot> snapshots = model.getSnapshots();
    assertEquals(3, snapshots.size());
    //First snapshot.
    assertEquals("created myrect and myoval", snapshots.get(0).getDescription());
    assertEquals(
        ShapeFactory.createBasicShape("myrect", "rectangle", 50, 100, 200, 200, 1, 0, 0),
        snapshots.get(0).getPhoto().getShape("myrect")
    );
    assertEquals(
        ShapeFactory.createBasicShape("myoval", "oval", 60, 30, 500, 100, 0, 1, 1/255),
        snapshots.get(0).getPhoto().getShape("myoval")
    );
    //Second snapshot
    assertEquals("changed myrect", snapshots.get(1).getDescription());
    assertEquals(
        ShapeFactory.createBasicShape("myrect", "rectangle", 25, 100, 300, 200, 0, 0, 1),
        snapshots.get(1).getPhoto().getShape("myrect")
    );
    assertEquals(
        ShapeFactory.createBasicShape("myoval", "oval", 60, 30, 500, 100, 0, 1, 1/255),
        snapshots.get(1).getPhoto().getShape("myoval")
    );
    //Third snapshot
    assertEquals("", snapshots.get(2).getDescription());
    assertNull(snapshots.get(2).getPhoto().getShape("myrect"));
    assertEquals(
        ShapeFactory.createBasicShape("myoval", "oval", 60, 30, 500, 400, 0, 1, 1/255),
        snapshots.get(2).getPhoto().getShape("myoval")
    );
  }

  /**
   * Tests parsing a sequence of commands from a file.
   */
  @Test
  public void testParse2() throws FileNotFoundException {
    java.io.File file = new java.io.File("test/controllerTest/testParse2.txt");
    inputParser.parse(new FileInputStream(file), model);

    //Tests all snapshots.
    List<ISnapshot> snapshots = model.getSnapshots();
    assertEquals(4, snapshots.size());
    //First snapshot.
    assertEquals("After first selfie", snapshots.get(0).getDescription());
    assertEquals(
        ShapeFactory.createBasicShape("myrect", "rectangle", 50, 100, 200, 200, 1, 0, 0),
        snapshots.get(0).getPhoto().getShape("myrect")
    );
    assertEquals(
        ShapeFactory.createBasicShape("myoval", "oval", 60, 30, 500, 100, 0, 1, 1/255),
        snapshots.get(0).getPhoto().getShape("myoval")
    );
    //Second snapshot
    assertEquals("2nd selfie", snapshots.get(1).getDescription());
    assertEquals(
        ShapeFactory.createBasicShape("myrect", "rectangle", 25, 100, 100, 300, 1, 0, 0),
        snapshots.get(1).getPhoto().getShape("myrect")
    );
    assertEquals(
        ShapeFactory.createBasicShape("myoval", "oval", 60, 30, 500, 100, 0, 1, 1/255),
        snapshots.get(1).getPhoto().getShape("myoval")
    );
    //Third snapshot
    assertEquals("", snapshots.get(2).getDescription());
    assertEquals(
        ShapeFactory.createBasicShape("myrect", "rectangle", 25, 100, 100, 300, 0, 0, 1),
        snapshots.get(2).getPhoto().getShape("myrect")
    );
    assertEquals(
        ShapeFactory.createBasicShape("myoval", "oval", 60, 30, 500, 400, 0, 1, 1/255),
        snapshots.get(2).getPhoto().getShape("myoval")
    );
    //Fourth snapshot
    assertEquals("Selfie after removing the rectangle from the picture", snapshots.get(3).getDescription());
    assertNull(snapshots.get(3).getPhoto().getShape("myrect"));
    assertEquals(
        ShapeFactory.createBasicShape("myoval", "oval", 60, 30, 500, 400, 0, 1, 1/255),
        snapshots.get(3).getPhoto().getShape("myoval")
    );
  }

}