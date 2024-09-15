import model.PhotoAlbumApp;

public class ModelMainTest {
  public static void main(String[] args) {
    PhotoAlbumApp photoAlbum = new PhotoAlbumApp();
    photoAlbum.createBasicShape("r1", "rectangle", 1, 1, 0, 0, 0, 1, 0);
    photoAlbum.createBasicShape("c1", "oval", 5, 5, 1, 0, 1, 0, 0);
    photoAlbum.createBasicShape("r2", "rectangle", 1, 1, 0, 0, 1, 1, 0);
    photoAlbum.setShapeColor("r2", 0, 0, 0);
    photoAlbum.takeSnapshot("");
    photoAlbum.setShapePosition("c1", 10, 10);
    photoAlbum.setShapeColor("c1", 0.5, 0.5, 0.5);
    photoAlbum.takeSnapshot("sp2");
    System.out.println(photoAlbum);
  }
}
