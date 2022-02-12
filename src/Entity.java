import processing.core.PImage;
import java.util.List;

public abstract class Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(String id, Point position, List<PImage> images, int imageIndex)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex =imageIndex;
    }

    public String getId()
    {
        return id;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }

    public List<PImage> getImages()
    {
        return images;
    }

    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }
}
