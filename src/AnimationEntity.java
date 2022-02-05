public interface AnimationEntity extends Entity{
    void scheduleActions(EventScheduler scheduler,
                         WorldModel world,
                         ImageStore imageStore);
    int getAnimationPeriod();
    void nextImage();
}
