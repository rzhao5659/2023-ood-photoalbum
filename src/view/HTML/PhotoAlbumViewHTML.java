package view.HTML;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * A text-based view that can print all snapshots as an HTML document.
 */
public class PhotoAlbumViewHTML implements IPhotoAlbumViewText {
  private final int SCREEN_WIDTH;
  private final int SCREEN_HEIGHT;
  private StringBuilder html;
  private StringBuilder htmlBody;

  /**
   * Constructor.
   *
   * @param screenWidth draw panel X dimension.
   * @param screenHeight draw panel Y dimension.
   */
  public PhotoAlbumViewHTML(int screenWidth, int screenHeight) {
    this.SCREEN_WIDTH = screenWidth;
    this.SCREEN_HEIGHT = screenHeight;
    this.html = new StringBuilder();
    this.htmlBody = new StringBuilder();
  }

  @Override
  public void drawRectangle(double x, double y, double xDim, double yDim, double r, double g,
      double b) {
    r = r * 255;
    g = g * 255;
    b = b * 255;
    String rectangleHTMLFormat = "<rect x=\"%f\" y=\"%f\" width=\"%f\" height=\"%f\" "
    + "fill=\"rgb(%f,%f,%f)\" stroke=\"black\" stroke-width=\"3\" />";
    htmlBody.append(String.format(rectangleHTMLFormat, x, y, xDim, yDim, r, g, b)).append("\n");
  }

  @Override
  public void drawOval(double x, double y, double xDim, double yDim, double r, double g, double b) {
    r = r * 255;
    g = g * 255;
    b = b * 255;
    String ovalHTMLFormat = "<ellipse cx=\"%f\" cy=\"%f\" rx=\"%f\" ry=\"%f\" "
        + "fill=\"rgb(%f,%f,%f)\" stroke=\"black\" stroke-width=\"3\" />";
    htmlBody.append(String.format(ovalHTMLFormat, x, y, xDim, yDim, r, g, b)).append("\n");
  }

  @Override
  public void startDrawingSnapshot(String id, String description) {
    htmlBody.append("<div class=\"snapshot\">").append("\n");
    htmlBody.append(String.format("<h2>%s</h2>", id)).append("\n");
    htmlBody.append(String.format("<h3>Description: %s</h3>", description)).append("\n");
    htmlBody.append(String.format("<svg width=\"%d\" height=\"%d\">",
                    this.SCREEN_WIDTH, this.SCREEN_HEIGHT)).append("\n");
  }

  @Override
  public void endDrawingSnapshot() {
    htmlBody.append("</svg></div><br>\n");
  }

  @Override
  public void printOutput(PrintWriter outputFile) throws IOException {
    //HTML Styles and Headings
    html.append("<!DOCTYPE html>\n"
              + "<html>\n"
              + "<head>\n"
              + "<style>\n"
              + "    body {\n"
              + "        font-family: Arial, Helvetica, sans-serif;\n"
              + "        background-color: #333333;\n"
              + "    }\n"
              + "    .snapshot {\n"
              + "        border: 10px ridge rgba(0, 0, 0);\n"
              + "        padding-left: 10px;\n"
              + "        padding-bottom: 5px;\n"
              + "        background-color: #f6546a;\n"
              + "        max-width: " + (this.SCREEN_WIDTH + 20) + "px;\n"
              + "    }\n"
              + "    svg {\n"
              + "        border: 5px outset rgba(0, 0, 0);\n"
              + "        background-color: #808080;\n"
              + "    }\n"
              + "    h1 {\n"
              + "        padding-left: 10px;\n"
              + "        color: white;\n"
              + "        font-weight: bold;\n"
              + "        font-size: 250%;\n"
              + "    }\n"
              + "    h2 {\n"
              + "        color: black;\n"
              + "        font-weight: bold;\n"
              + "        font-size: 200%;\n"
              + "    }\n"
              + "    h3 {\n"
              + "        color: black;\n"
              + "        font-size: 150%;\n"
              + "    }\n"
              + "</style>\n"
              + "</head>\n"
              + "<body>\n"
              + "<h1>Photo Album HTML View</h1>\n");
    //HTML body
    html.append(htmlBody.toString());

    //HTML Closing tags
    html.append("</body>\n");
    html.append("</html>\n");

    //Write to output stream
    outputFile.append(html.toString());
    outputFile.flush();
  }
}
