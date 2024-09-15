package viewTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import view.HTML.PhotoAlbumViewHTML;

/**
 * Tests PhotoAlbumViewHTML on its own, mainly that the method printOutput works.
 */
public class PhotoAlbumViewHTMLTest {
  private PhotoAlbumViewHTML view;
  private StringWriter printResult;
  private static final int HTML_START_LENGTH = 38;
  private static final int HTML_END_LENGTH = 2;
  private static final int HTML_STARTSNAPSHOT_LENGTH = 4;
  private static final int HTML_ENDSNAPSHOT_LENGTH = 1;

  @Before
  public void setUp() {
    view = new PhotoAlbumViewHTML(1000, 1000);
  }

  /**
   * Tests printOutput with the output stream being a string.
   */
  @Test
  public void testPrintOutput1() throws IOException {
    printResult = new StringWriter();
    view.startDrawingSnapshot("SNAPSHOT1", "Hello world.");
    view.drawRectangle(0, 0, 100.50, 500.10, 1, 1, 1);
    view.endDrawingSnapshot();
    view.startDrawingSnapshot("SNAPSHOT2", "End world.");
    view.drawRectangle(0, 0, 100.50, 500.10, 1, 1, 1);
    view.drawOval(150, 200, 200, 33.33, 0.333, 0.333, 0.333);
    view.endDrawingSnapshot();
    view.printOutput(new PrintWriter(printResult));

    //Split the output into an array of lines
    String[] lines = printResult.toString().split("\n");
    //Check length of produced html file.
    int numSnapshots = 2;
    int numShapesSnapshot1 = 1;
    int numShapesSnapshot2 = 2;
    int expectedHTMLLength = HTML_START_LENGTH + numSnapshots * (HTML_STARTSNAPSHOT_LENGTH + HTML_ENDSNAPSHOT_LENGTH)
        + numShapesSnapshot1 + numShapesSnapshot2 + HTML_END_LENGTH;
    assertEquals(expectedHTMLLength, lines.length);
    //Check that the html part for shapes are correct.
    String actualHTMLShapes = String.join("\n", Arrays.copyOfRange(lines, HTML_START_LENGTH, lines.length - HTML_END_LENGTH));
    String expectedHTMLShapes = "<div class=\"snapshot\">\n"
        + "<h2>SNAPSHOT1</h2>\n"
        + "<h3>Description: Hello world.</h3>\n"
        + "<svg width=\"1000\" height=\"1000\">\n"
        + "<rect x=\"0.000000\" y=\"0.000000\" width=\"100.500000\" height=\"500.100000\" fill=\"rgb(255.000000,255.000000,255.000000)\" stroke=\"black\" stroke-width=\"3\" />\n"
        + "</svg></div><br>\n"
        + "<div class=\"snapshot\">\n"
        + "<h2>SNAPSHOT2</h2>\n"
        + "<h3>Description: End world.</h3>\n"
        + "<svg width=\"1000\" height=\"1000\">\n"
        + "<rect x=\"0.000000\" y=\"0.000000\" width=\"100.500000\" height=\"500.100000\" fill=\"rgb(255.000000,255.000000,255.000000)\" stroke=\"black\" stroke-width=\"3\" />\n"
        + "<ellipse cx=\"150.000000\" cy=\"200.000000\" rx=\"200.000000\" ry=\"33.330000\" fill=\"rgb(84.915000,84.915000,84.915000)\" stroke=\"black\" stroke-width=\"3\" />\n"
        + "</svg></div><br>";
    assertEquals(expectedHTMLShapes, actualHTMLShapes);
  }

  /**
   * Tests printOutput with the output stream being a file.
   * Requires manually open the generated html document on a browser.
   */
  @Test
  public void testPrintOutput2() throws IOException {
    File outputFile = new File("test/viewTest/testPrintOutput2.html");
    PrintWriter output = new PrintWriter(outputFile);
    view.startDrawingSnapshot("SNAPSHOT1", "Hello world.");
    view.drawRectangle(0, 0, 100.50, 500.10, 1, 1, 1);
    view.endDrawingSnapshot();
    view.startDrawingSnapshot("SNAPSHOT2", "End world.");
    view.drawRectangle(0, 0, 100.50, 500.10, 1, 1, 1);
    view.drawOval(150, 200, 200, 33.33, 0.333, 0.333, 0.333);
    view.endDrawingSnapshot();
    view.printOutput(output);
  }


}