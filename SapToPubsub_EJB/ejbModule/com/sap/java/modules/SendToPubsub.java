package com.sap.java.modules;

import javax.ejb.Stateless;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.TopicName;

/**
 * Session Bean implementation class SendToPubsub
 */
@Stateless
public class SendToPubsub implements SendToPubsubRemote, SendToPubsubLocal {

    /**
     * Default constructor. 
     */
    public SendToPubsub() {
        // TODO Auto-generated constructor stub 
    }

}
