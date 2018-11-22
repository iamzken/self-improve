package com.gupaoedu.activity.services.processor;



public interface RewardProcessor<T> {

     /**
      * 领奖
      * @param activityDrawContext
      */
     T doReword(T activityDrawContext);
}
