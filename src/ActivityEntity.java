import processing.core.PImage;

import java.util.List;

public abstract class ActivityEntity extends AnimationEntity{
    private int actionPeriod;

    public ActivityEntity(String id,
                          Point position,
                          List<PImage> images,
                          int imageIndex,
                          int animationPeriod,
                          int actionPeriod)
    {
        super(id, position, images, imageIndex, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod()
    {
        return actionPeriod;
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                actionPeriod);
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0),
                super.getAnimationPeriod());
    }

    abstract protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
