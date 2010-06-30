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

import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * A configurable implementation of the {@link RmiUnicast} interface.
 * 
 * @author Tobias Sarnowski
 * @author Willi Schoenborn
 */
class ConfigurableRmiUnicast implements RmiUnicast {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurableRmiUnicast.class);
    
    private final RmiRegistry registry;
    
    private int port;

    @Inject
    public ConfigurableRmiUnicast(RmiRegistry registry) {
        this.registry = Preconditions.checkNotNull(registry, "Registry");
    }

    @Inject(optional = true)
    protected void setPort(@Named(RmiConfig.PORT) int port) {
        this.port = port;
    }

    @Override
    public <T extends Remote> T export(Class<T> type, T remote) throws RemoteException {
        Preconditions.checkNotNull(type, "Type");
        Preconditions.checkNotNull(remote, "Remote");
        LOG.info("exporting {}", remote.getClass().getName());
        return type.cast(UnicastRemoteObject.exportObject(remote, port));
    }

    @Override
    public <T extends Remote> T exportAndBind(Class<T> type, T remote) throws RemoteException, AlreadyBoundException {
        Preconditions.checkNotNull(type, "Type");
        Preconditions.checkNotNull(remote, "Remote");
        final T stub = export(type, remote);
        registry.bind(type, stub);
        return stub;
    }

    @Override
    public boolean unexport(Remote remote, boolean force) throws NoSuchObjectException {
        Preconditions.checkNotNull(remote, "Remote");
        LOG.info("Unexporting {}", remote.getClass().getName());
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
    public void setLog(OutputStream stream) {
        Preconditions.checkNotNull(stream, "Stream");
        UnicastRemoteObject.setLog(stream);
    }

    @Override
    public Remote toStub(Remote remote) throws NoSuchObjectException {
        Preconditions.checkNotNull(remote, "Remote");
        return UnicastRemoteObject.toStub(remote);
    }
    
}
