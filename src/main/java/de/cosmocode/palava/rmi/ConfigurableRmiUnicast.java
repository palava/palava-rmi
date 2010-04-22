/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.rmi;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

/**
 * A configurable implementation of the {@link RmiUnicast} interface.
 * 
 * @author Tobias Sarnowski
 * @author Willi Schoenborn
 */
class ConfigurableRmiUnicast implements RmiUnicast {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurableRmiUnicast.class);
    
    private RmiRegistry rmiRegistry;
    
    private Integer port;

    @Inject
    public ConfigurableRmiUnicast(RmiRegistry rmiRegistry) {
        this.rmiRegistry = rmiRegistry;
    }

    /**
     * Configures the port of this registry.
     * 
     * @param port the new port value
     */
    @Inject(optional = true)
    protected void setPort(@Named(RmiConfig.PORT) int port) {
        LOG.debug("RMI unicast port set to {}", port);
        this.port = port;
    }

    @Override
    public <T extends Remote> T exportObject(Class<T> cls, T remote) throws RemoteException {
        LOG.info("exporting {}", remote.getClass().getName());
        if (port == null) {
            return cls.cast(UnicastRemoteObject.exportObject(remote, 0));
        } else {
            return cls.cast(UnicastRemoteObject.exportObject(remote, port));
        }
    }

    @Override
    public <T extends Remote> T exportObjectAndBind(Class<T> cls, T remote) throws RemoteException, 
        AlreadyBoundException {
        final T stub = exportObject(cls, remote);
        rmiRegistry.bind(cls, stub);
        return stub;
    }

    @Override
    public boolean unexportObject(Remote remote, boolean force) throws NoSuchObjectException {
        LOG.info("unexporting {}", remote.getClass().getName());
        return UnicastRemoteObject.unexportObject(remote, force);
    }

    @Override
    public String getClientHost() throws ServerNotActiveException {
        return UnicastRemoteObject.getClientHost();
    }

    @Override
    public PrintStream getLog() {
        return UnicastRemoteObject.getLog();
    }

    @Override
    public void setLog(OutputStream outputStream) {
        UnicastRemoteObject.setLog(outputStream);
    }

    @Override
    public Remote toStub(Remote remote) throws NoSuchObjectException {
        return UnicastRemoteObject.toStub(remote);
    }
    
}
