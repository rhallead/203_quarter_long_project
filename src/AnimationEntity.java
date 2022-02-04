public interface AnimationEntity extends Entity{
    void scheduleActions(EventScheduler scheduler,
                         WorldModel world,
                         ImageStore imageStore);
    int getAnimationPeriod();
    void executeActivity(WorldModel world,
                         ImageStore imageStore,
                         EventScheduler scheduler);
    void nextImage();
}
