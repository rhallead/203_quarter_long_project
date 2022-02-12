import processing.core.PImage;
import java.util.List;

public class Obstacle extends AnimationEntity{

    public Obstacle(
            String id,
            Point position,
            List<PImage> images,
            int imageIndex,
            int animationPeriod)
    {
        super(id, position, images, imageIndex, animationPeriod);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
                scheduler.scheduleEvent(this,
                        Factory.createAnimationAction(this, 0),
                        super.getAnimationPeriod());
    }
}
