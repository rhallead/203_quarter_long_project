import processing.core.PImage;
import java.util.List;

public class Obstacle implements Entity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int animationPeriod;

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public List<PImage> getImages() {
        return images;
    }

    public Obstacle(
            String id,
            Point position,
            List<PImage> images,
            int animationPeriod)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.animationPeriod = animationPeriod;
    }

    public PImage getCurrentImage() {
        return images.get(0);
    }
}
