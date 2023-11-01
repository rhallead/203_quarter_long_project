import processing.core.PImage;

import java.util.List;

public abstract class ActivityEntity extends AnimationEntity{
    private int actionPeriod;

    public ActivityEntity(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod) {
        super(id, position, images, imageIndex, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore), actionPeriod);
       super.scheduleActions(scheduler, world, imageStore);
    }

    public int getActionPeriod() {
        return actionPeriod;
    }
    protected abstract void executeActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}