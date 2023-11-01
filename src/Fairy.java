import processing.core.PImage;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public final class Fairy extends PeopleEntity {

    public Fairy(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
    }

    protected Point nextPosition(WorldModel world, Point destPos) {
        AStarPathingStrategy path = new AStarPathingStrategy();
        //SingleStepPathingStrategy path = new SingleStepPathingStrategy();
        Point start = getPosition();
        Point end = destPos;
        Predicate<Point> canPassThrough = (p -> world.withinBounds(p) && !(world.isOccupied(p)));
        BiPredicate<Point, Point> withinReach = (p1, p2) -> adjacent(p1, p2);
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

       List<Point> path_lst = path.computePath(start, end, canPassThrough, withinReach, potentialNeighbors);
        if(path_lst.isEmpty())
            return start;
       return path_lst.get(0);
    }

    protected void executeActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        Optional<Entity> fairyTarget = world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Stump.class)));
        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveTo(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = Factory.createSapling("sapling_" + getId(), tgtPos, imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore), getActionPeriod());
    }

    public boolean moveTo(WorldModel world, PlantEntity target, EventScheduler scheduler) {
        if (adjacent(getPosition(), target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }

            return super.moveTo(world, target, scheduler);
    }

}