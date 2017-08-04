package com.tfr.scalaInAction.ch12.example;

import akka.actor.UntypedActor;
import com.tfr.scalaInAction.ch12.example.remoteActor.FileToCount;
import com.tfr.scalaInAction.ch12.example.remoteActor.WordCount;

/**
 *
 * Created by Erik on 8/3/2017.
 */
public class WorkCountWorker extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof FileToCount) {
            FileToCount c = (FileToCount)message;
            Integer count = c.countWords();
            getSender().tell(new WordCount(c.url(), count));
        } else {
            throw new IllegalArgumentException("Unknown message:" + message);
        }
    }
}
