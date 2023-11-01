import processing.core.PImage;

import java.util.*;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Dude_Full extends DudeEntity {

    public Dude_Full(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod, int resourceLimit) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, resourceLimit);
    }

    protected void executeActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
      Optional<Entity> fullTarget = world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        }
        else {
           scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), getActionPeriod());
        }
    }

    public boolean moveTo(WorldModel world, PlantEntity target, EventScheduler scheduler) {
        if (adjacent(getPosition(), target.getPosition())) {
            return true;
        } else {
            return super.moveTo(world, target, scheduler);
        }
    }

    private void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Dude_Not_Full miner = Factory.createDudeNotFull(getId(), getPosition(), getActionPeriod(), getAnimationPeriod(),
                getResourceLimit(), getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }
}
