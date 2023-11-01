import processing.core.PImage;

import java.util.*;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Dude_Not_Full extends DudeEntity {
    private int resourceCount;

    public Dude_Not_Full(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, resourceLimit);
        this.resourceCount = resourceCount;
    }
    protected void executeActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        Optional<Entity> target =
                world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !moveTo( world,
                (PlantEntity)(target.get()),
                scheduler)
                || !transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }


    private boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount >= getResourceLimit()) {
            Dude_Full miner = Factory.createDudeFull(getId(), getPosition(), getActionPeriod(),
                    getAnimationPeriod(), getResourceLimit(), getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean moveTo(WorldModel world, PlantEntity target, EventScheduler scheduler) {
        if (adjacent(getPosition(), target.getPosition())) {
            resourceCount += 1;
            target.setHealth(-1);
            return true;
        }
        else {
            return super.moveTo(world, target, scheduler);
        }
    }
}
