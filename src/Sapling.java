import processing.core.PImage;

import java.util.*;

public final class Sapling extends PlantEntity{
    private int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod, int health, int healthLimit) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, health);
        this.healthLimit = healthLimit;
    }
    protected void executeActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        setHealth(1);
        super.executeActivity(scheduler, world, imageStore);
    }
    protected boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if(super.transformPlant(world, scheduler, imageStore)) return true;
        else if (getHealth() >= healthLimit) {
            Tree tree = Factory.createTree("tree_" + getId(), getPosition(),
                    getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                    getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }

    private int getNumFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max - min);
    }
}