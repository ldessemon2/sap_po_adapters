package com.sap.java.modules;

import javax.ejb.Stateless;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.TopicName;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.core.ApiFutureCallback;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

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
        Publisher publisher = null;
        try {
            TopicName topic = TopicName.of("internal-naoxyatrainingservice", "lud-topic");
            publisher = Publisher.newBuilder(topic).build();
            ByteString data = ByteString.copyFromUtf8("my-message");
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            ApiFutures.addCallback(messageIdFuture, new ApiFutureCallback<String>() {
                public void onSuccess(String messageId) {
                System.out.println("published with message id: " + messageId);
                }

                public void onFailure(Throwable t) {
                System.out.println("failed to publish: " + t);
                }
            }, MoreExecutors.directExecutor());
            //...
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }

}
