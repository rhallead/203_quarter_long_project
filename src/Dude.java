import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class Dude extends People{
    private int resourceLimit;

    public Dude(String id,
                Point position,
                List<PImage> images,
                int imageIndex,
                int animationPeriod,
                int actionPeriod,
                int resourceLimit)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit()
    {
        return resourceLimit;
    }

    protected Point nextPosition(
            WorldModel world, Point destPos)
    {
        PathingStrategy strategy = new SingleStepPathingStrategy();
        PathingStrategy strategy2 = new AStarPathingStrategy();

        Predicate<Point> canPassThrough = p -> !(world.isOccupied(p) && world.getOccupancyCell(p).getClass() != Stump.class);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> Functions.adjacent(p1, p2);
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;


        List<Point> path = strategy2.computePath(super.getPosition(), destPos, canPassThrough, withinReach, potentialNeighbors);

        if (path.size() == 0)
            return super.getPosition();

        return path.get(0);

        /*int horiz = Integer.signum(destPos.x - super.getPosition().x);
        Point newPos = new Point(super.getPosition().x + horiz, super.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) {
            int vert = Integer.signum(destPos.y - super.getPosition().y);
            newPos = new Point(super.getPosition().x, super.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos) &&  world.getOccupancyCell(newPos).getClass() != Stump.class) {
                newPos = super.getPosition();
            }
        }

        return newPos;*/
    }
}
