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

import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.ServerNotActiveException;

/**
 * @author Tobias Sarnowski
 */
public interface RmiUnicast {

    /**
     * Exports a {@link Remote} object (makes it available through RMI to other JVMs).
     *
     * @param remote the object to publish
     * @param <T>
     * @return
     * @throws RemoteException
     */
    <T> T exportObject(Remote remote) throws RemoteException;

    /**
     * Removes the object from the public RMI interface.
     *
     * @param remote
     * @param force
     * @return
     * @throws NoSuchObjectException
     */
    boolean unexportObject(Remote remote, boolean force) throws NoSuchObjectException;

    String getClientHost() throws ServerNotActiveException;

    PrintStream getLog();

    void setLog(OutputStream outputStream);

    Remote toStub(Remote remote) throws NoSuchObjectException;
}
