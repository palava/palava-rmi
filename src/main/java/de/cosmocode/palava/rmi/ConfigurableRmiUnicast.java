/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
