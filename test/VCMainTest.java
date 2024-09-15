import controller.PhotoAlbumControllerGUI;
import model.IPhotoAlbumApp;
import model.PhotoAlbumApp;
import view.GUI.PhotoAlbumViewGUI;

public class VCMainTest {
  public static void main(String[] args ) throws InterruptedException {
    IPhotoAlbumApp photoAlbum = new PhotoAlbumApp();
    photoAlbum.createBasicShape("r1", "rectangle", 500, 500, 800, 800, 0, 1, 0);
    photoAlbum.createBasicShape("c1", "oval", 100, 100, 1000, 400, 1, 0, 0);
    photoAlbum.createBasicShape("r2", "rectangle", 100, 100, 400, 400, 1, 1, 1);
    photoAlbum.setShapeColor("r2", 0, 0, 0);

    photoAlbum.takeSnapshot("SP1 131 321 312");
    photoAlbum.setShapePosition("c1", 800, 400);
    photoAlbum.setShapeColor("c1", 0, 0, 1);
    photoAlbum.createBasicShape("o1", "oval", 100, 100, 1000, 1000, 1, 1, 1);

    photoAlbum.takeSnapshot("sp2");
    photoAlbum.takeSnapshot("sp3");
    photoAlbum.removeShape("o1");
    photoAlbum.takeSnapshot("sp4");
    PhotoAlbumControllerGUI appController = new PhotoAlbumControllerGUI(photoAlbum, new PhotoAlbumViewGUI(10000, 2000));
    appController.go();
  }

}
