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

    private Registry registry;
    private String host = "localhost";
    private int port = Registry.REGISTRY_PORT;

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
            registry = initializeRegistry(host, port);
        } catch (RemoteException e) {
            throw new LifecycleException(e);
        }
    }

    /**
     * Sets the host of this registry.
     * 
     * @param host the new host value
     */
    @Inject(optional = true)
    public void setHost(@Named(RmiConfig.REGISTRY_HOST) String host) {
        this.host = host;
        LOG.trace("RMI registry host set to {}", host);
    }

    /**
     * Sets the port of this registry.
     * 
     * @param port the new port value
     */
    @Inject(optional = true)
    public void setPort(@Named(RmiConfig.REGISTRY_PORT) int port) {
        this.port = port;
        LOG.trace("RMI registry port set to {}", port);
    }

    @Override
    public Remote lookup(String name) throws RemoteException, NotBoundException {
        LOG.trace("looking up {}", name);
        return registry.lookup(name);
    }

    @Override
    public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException {
        LOG.info("binding {} with {}", name, obj);
        registry.bind(name, obj);
    }

    @Override
    public void unbind(String name) throws RemoteException, NotBoundException {
        LOG.info("unbinding {}", name);
        registry.unbind(name);
    }

    @Override
    public void rebind(String name, Remote obj) throws RemoteException {
        LOG.info("rebinding {} with {}", name, obj);
        registry.rebind(name, obj);
    }

    @Override
    public String[] list() throws RemoteException {
        return registry.list();
    }

    @Override
    public <T extends Remote> T lookup(Class<T> cls) throws RemoteException, NotBoundException {
        return cls.cast(lookup(cls.getName()));
    }

    @Override
    public <T extends Remote> T lookup(Class<T> cls, String name) throws RemoteException, NotBoundException {
        return cls.cast(lookup(name));
    }

    @Override
    public void bind(Remote obj) throws RemoteException, AlreadyBoundException {
        bind(obj.getClass().getName(), obj);
    }

    @Override
    public <T extends Remote> void bind(Class<? super T> cls, T obj) throws RemoteException, AlreadyBoundException {
        bind(cls.getName(), obj);
    }

    @Override
    public <T extends Remote> void unbind(Class<T> cls) throws RemoteException, NotBoundException {
        unbind(cls.getName());
    }

    @Override
    public <T extends Remote> void rebind(Class<? super T> cls, T obj) throws RemoteException {
        rebind(cls.getName(), obj);
    }
    
}
