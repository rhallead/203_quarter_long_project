public class Animation implements Action {
    private int repeatCount;
    private AnimationEntity entity;

    public Animation(AnimationEntity entity, int repeatCount)
    {
       this.entity = entity;
       this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler)
    {
        entity.nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(entity,
                    Factory.createAnimationAction(entity,
                            Math.max(repeatCount - 1,
                                    0)),
                    entity.getAnimationPeriod());
        }
    }
}