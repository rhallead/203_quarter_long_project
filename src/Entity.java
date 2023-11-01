import processing.core.PImage;
import java.util.*;

public abstract class Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;


    public Entity(String id, Point position, List<PImage> images, int imageIndex) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }
    public PImage getCurrentImage() {
        return this.images.get(this.imageIndex);
    }
    public String getId() {
        return id;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }

    public int getImageIndex() { return imageIndex; }
    public void setImageIndex(int imageIndex) { this.imageIndex = imageIndex; }
    public List<PImage> getImages() { return images;}

}