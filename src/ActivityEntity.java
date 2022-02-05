public interface ActivityEntity extends Entity{
    void executeActivity(WorldModel world,
                         ImageStore imageStore,
                         EventScheduler scheduler);
}
