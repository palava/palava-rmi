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

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;

/**
 * Abstract base implementation of the {@link RmiRegistry} interface which 
 * allows configuration.
 * 
 * @author Tobias Sarnowski
 * @author Willi Schoenborn
 */
abstract class AbstractConfigurableRmiRegistry implements RmiRegistry, Initializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(AbstractConfigurableRmiRegistry.class);

    private String host = "localhost";
    private int port = Registry.REGISTRY_PORT;
    
    private Registry registry;

    @Inject(optional = true)
    void setHost(@Named(RmiConfig.REGISTRY_HOST) String host) {
        this.host = Preconditions.checkNotNull(host, "Host");
    }

    @Inject(optional = true)
    void setPort(@Named(RmiConfig.REGISTRY_PORT) int port) {
        this.port = port;
    }

    /**
     * Delegates registry initialization to sub classes.
     * 
     * @param host the configured host
     * @param port the configured port
     * @return an initialized {@link Registry}
     * @throws RemoteException if initializing failed
     */
    protected abstract Registry initializeRegistry(String host, int port) throws RemoteException;

    @Override
    public final void initialize() throws LifecycleException {
        try {
            LOG.info("Initializing rmi registry on {}:{}", host, port);
            registry = initializeRegistry(host, port);
        } catch (RemoteException e) {
            throw new LifecycleException(e);
        }
    }

    @Override
    public Remote lookup(String name) throws RemoteException, NotBoundException {
        Preconditions.checkNotNull(name, "Name");
        LOG.trace("Looking up {}", name);
        return registry.lookup(name);
    }

    @Override
    public void bind(String name, Remote remote) throws RemoteException, AlreadyBoundException {
        Preconditions.checkNotNull(name, "Name");
        Preconditions.checkNotNull(remote, "Remote");
        LOG.info("Binding {} with {}", name, remote);
        registry.bind(name, remote);
    }

    @Override
    public void unbind(String name) throws RemoteException, NotBoundException {
        Preconditions.checkNotNull(name, "Name");
        LOG.info("Unbinding {}", name);
        registry.unbind(name);
    }

    @Override
    public void rebind(String name, Remote remote) throws RemoteException {
        Preconditions.checkNotNull(name, "Name");
        Preconditions.checkNotNull(remote, "Remote");
        LOG.info("Rebinding {} with {}", name, remote);
        registry.rebind(name, remote);
    }

    @Override
    public String[] list() throws RemoteException {
        return registry.list();
    }

    @Override
    public <T extends Remote> T lookup(Class<T> type) throws RemoteException, NotBoundException {
        Preconditions.checkNotNull(type, "Type");
        return type.cast(lookup(type.getName()));
    }

    @Override
    public <T extends Remote> T lookup(Class<T> type, String name) throws RemoteException, NotBoundException {
        Preconditions.checkNotNull(type, "Type");
        Preconditions.checkNotNull(name, "Name");
        return type.cast(lookup(name));
    }

    @Override
    public void bind(Remote remote) throws RemoteException, AlreadyBoundException {
        Preconditions.checkNotNull(remote, "Remote");
        bind(remote.getClass().getName(), remote);
    }

    @Override
    public <T extends Remote> void bind(Class<? super T> type, T remote) throws RemoteException, AlreadyBoundException {
        Preconditions.checkNotNull(type, "Type");
        Preconditions.checkNotNull(remote, "Remote");
        bind(type.getName(), remote);
    }

    @Override
    public <T extends Remote> void unbind(Class<T> type) throws RemoteException, NotBoundException {
        Preconditions.checkNotNull(type, "Type");
        unbind(type.getName());
    }

    @Override
    public <T extends Remote> void rebind(Class<? super T> type, T remote) throws RemoteException {
        Preconditions.checkNotNull(type, "Type");
        Preconditions.checkNotNull(remote, "Remote");
        rebind(type.getName(), remote);
    }
    
}
