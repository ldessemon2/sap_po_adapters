package com.sap.java.modules;

import java.io.IOException;
import com.sap.aii.af.service.auditlog.Audit;
import javax.ejb.Stateless;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.TopicName;
import com.sap.aii.af.lib.mp.module.Module;
import com.sap.aii.af.lib.mp.module.ModuleContext;
import com.sap.aii.af.lib.mp.module.ModuleData;
import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.MessageKey;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;
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
public class SendToPubsub implements Module, SendToPubsubRemote, SendToPubsubLocal {
	MessageKey amk = null;
    /**
     * Default constructor. 
     */
    public SendToPubsub() {
        // TODO Auto-generated constructor stub 
        
    }

	@Override
	public ModuleData process(ModuleContext moduleContext, ModuleData inputModuleData) throws ModuleException {
		// TODO Auto-generated method stub
		Audit.addAuditLogEntry(amk, AuditLogStatus.SUCCESS,"step 1");
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
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (publisher != null) {
                publisher.shutdown();
                //publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
		return null;
	}

}
