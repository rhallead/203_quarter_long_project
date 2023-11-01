import processing.core.PImage;

import java.util.List;

public abstract class PlantEntity extends ActivityEntity {
    private int health;

    public PlantEntity(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod, int health) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int increment) { this.health += increment; }
    protected void executeActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        if (!transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }
    protected boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {
            Stump stump = Factory.createStump(getId(), getPosition(), imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }
}