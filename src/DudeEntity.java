import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class DudeEntity extends PeopleEntity{
    private int resourceLimit;

    public DudeEntity(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod, int resourceLimit) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }
    protected Point nextPosition(WorldModel world, Point destPos) {
        AStarPathingStrategy path = new AStarPathingStrategy();
        //SingleStepPathingStrategy path = new SingleStepPathingStrategy();
        Point start = getPosition();
        Point end = destPos;
        Predicate<Point> canPassThrough = (p -> world.withinBounds(p) && (!(world.isOccupied(p)) || (world.getOccupancyCell(p) instanceof Stump)));
        BiPredicate<Point, Point> withinReach = (p1, p2) -> adjacent(p1, p2);
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        List<Point> path_lst = path.computePath(start, end, canPassThrough, withinReach, potentialNeighbors);
        if(path_lst.isEmpty())
            return start;
        return path_lst.get(0);
    }
    public int getResourceLimit() {
        return resourceLimit;
    }
}