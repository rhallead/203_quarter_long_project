import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface Entity {
    String getId();
    Point getPosition();
    void setPosition(Point position);
    PImage getCurrentImage();
}



/*import java.util.*;

        import processing.core.PImage;


 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.

public final class Entity
{
    private EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private int health;
    private int healthLimit;

    public EntityKind getKind() {
        return kind;
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

    public int getImageIndex() {
        return imageIndex;
    }

    public List<PImage> getImages() {
        return images;
    }

    public int getHealth() {
        return health;
    }

    public Entity(
            EntityKind kind,
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod,
            int health,
            int healthLimit)
    {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public void executeSaplingActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        health++;
        if (!transformPlant(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Functions.createActivityAction(this, world, imageStore),
                    actionPeriod);
        }
    }

    public void executeTreeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {

        if (!transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    Functions.createActivityAction(this, world, imageStore),
                    actionPeriod);
        }
    }

    public void executeFairyActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fairyTarget =
                world.findNearest(position, new ArrayList<>(Arrays.asList(EntityKind.STUMP)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().position;

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
                Entity sapling = Functions.createSapling("sapling_" + id, tgtPos,
                        imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Functions.createActivityAction(this, world, imageStore),
                actionPeriod);
    }

    public void executeDudeNotFullActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

        if (!target.isPresent() || !this.moveToNotFull(world,
                target.get(),
                scheduler)
                || !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Functions.createActivityAction(this, world, imageStore),
                    actionPeriod);
        }
    }

    public void executeDudeFullActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(position, new ArrayList<>(Arrays.asList(EntityKind.HOUSE)));

        if (fullTarget.isPresent() && this.moveToFull(world,
                fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    Functions.createActivityAction(this, world, imageStore),
                    actionPeriod);
        }
    }

    private boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit) {
            Entity miner = Functions.createDudeFull(id,
                    position, actionPeriod,
                    animationPeriod,
                    resourceLimit,
                    images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    private void transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        Entity miner = Functions.createDudeNotFull(id,
                position, actionPeriod,
                animationPeriod,
                resourceLimit,
                images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    private Point nextPositionFairy(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = position;
            }
        }

        return newPos;
    }

    private Point nextPositionDude(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);

            if (vert == 0 || world.isOccupied(newPos) &&  world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
                newPos = position;
            }
        }

        return newPos;
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        switch (kind) {
            case DUDE_FULL:
                scheduler.scheduleEvent(this,
                        Functions.createActivityAction(this, world, imageStore),
                        this.actionPeriod);
                scheduler.scheduleEvent(this,
                        Functions.createAnimationAction(this, 0),
                        getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                scheduler.scheduleEvent(this,
                        Functions.createActivityAction(this, world, imageStore),
                        actionPeriod);
                scheduler.scheduleEvent(this,
                        Functions.createAnimationAction(this, 0),
                        getAnimationPeriod());
                break;

            case OBSTACLE:
                scheduler.scheduleEvent(this,
                        Functions.createAnimationAction(this, 0),
                        getAnimationPeriod());
                break;

            case FAIRY:
                scheduler.scheduleEvent(this,
                        Functions.createActivityAction(this, world, imageStore),
                        actionPeriod);
                scheduler.scheduleEvent(this,
                        Functions.createAnimationAction(this, 0),
                        getAnimationPeriod());
                break;

            case SAPLING:
                scheduler.scheduleEvent(this,
                        Functions.createActivityAction(this, world, imageStore),
                        actionPeriod);
                scheduler.scheduleEvent(this,
                        Functions.createAnimationAction(this, 0),
                        getAnimationPeriod());
                break;

            case TREE:
                scheduler.scheduleEvent(this,
                        Functions.createActivityAction(this, world, imageStore),
                        actionPeriod);
                scheduler.scheduleEvent(this,
                        Functions.createAnimationAction(this, 0),
                        getAnimationPeriod());
                break;

            default:
        }
    }

    public PImage getCurrentImage() {
        return images.get(imageIndex);
    }

    public int getAnimationPeriod() {
        switch (kind) {
            case DUDE_FULL:
            case DUDE_NOT_FULL:
            case OBSTACLE:
            case FAIRY:
            case SAPLING:
            case TREE:
                return animationPeriod;
            default:
                throw new UnsupportedOperationException(
                        String.format("getAnimationPeriod not supported for %s",
                                kind));
        }
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    private boolean transformPlant( WorldModel world,
                                    EventScheduler scheduler,
                                    ImageStore imageStore)
    {
        if (kind == EntityKind.TREE)
        {
            return transformTree(world, scheduler, imageStore);
        }
        else if (kind == EntityKind.SAPLING)
        {
            return transformSapling(world, scheduler, imageStore);
        }
        else
        {
            throw new UnsupportedOperationException(
                    String.format("transformPlant not supported for %s", this));
        }
    }

    private boolean transformTree(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (health <= 0) {
            Entity stump = Functions.createStump(id,
                    position,
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);
            stump.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    private boolean transformSapling(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (health <= 0) {
            Entity stump = Functions.createStump(id,
                    position,
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);
            stump.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        else if (health >= healthLimit)
        {
            Entity tree = Functions.createTree("tree_" + id,
                    position,
                    Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                    Functions.getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    private boolean moveToFairy(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (Functions.adjacent(this.position, target.position)) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = this.nextPositionFairy(world, target.position);

            if (!this.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private boolean moveToNotFull(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (Functions.adjacent(this.position, target.position)) {
            this.resourceCount += 1;
            target.health--;
            return true;
        }
        else {
            Point nextPos = this.nextPositionDude(world, target.position);

            if (!this.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private boolean moveToFull(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (Functions.adjacent(this.position, target.position)) {
            return true;
        }
        else {
            Point nextPos = this.nextPositionDude(world, target.position);

            if (!this.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
}
*/
