package com.sap.java.modules;

import javax.ejb.Stateless;

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
