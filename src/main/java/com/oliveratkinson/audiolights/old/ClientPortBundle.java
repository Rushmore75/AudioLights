/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights.old;

import org.jaudiolibs.jnajack.JackClient;
import org.jaudiolibs.jnajack.JackPort;

/**
 *
 * @author oliver
 */
@Deprecated
public class ClientPortBundle {

    private final JackClient client;
    private final JackPort port;

    public ClientPortBundle(JackClient client, JackPort port) {
        this.client = client;
        this.port = port;
    }

    public JackClient getClient() {
        return client;
    }

    public JackPort getPort() {
        return port;
    }
    
    

}
