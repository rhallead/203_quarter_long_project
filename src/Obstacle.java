import processing.core.PImage;
import java.util.List;

public class Obstacle implements AnimationEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int animationPeriod;
    private int imageIndex;

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

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public PImage getCurrentImage() {
        return images.get(imageIndex);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
                scheduler.scheduleEvent(this,
                        Factory.createAnimationAction(this, 0),
                        this.getAnimationPeriod());
    }
}
