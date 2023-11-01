import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class PeopleEntity extends ActivityEntity {

    public PeopleEntity(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
    }
    protected abstract Point nextPosition(WorldModel world, Point destPos) ;

    public boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y
                && Math.abs(p1.x - p2.x) == 1);
    }
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
}