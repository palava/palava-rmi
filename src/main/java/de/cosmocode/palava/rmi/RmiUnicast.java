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

/**
 * 
 * 
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
    <T extends Remote> T exportObject(Class<T> cls, T remote) throws RemoteException;

    /**
     * Exports a {@link Remote} object (makes it available through RMI to other JVMs) and
     * binds it to the registry.
     *
     * @param cls
     * @param remote
     * @param <T>
     * @return
     * @throws RemoteException
     */
    <T extends Remote> T exportObjectAndBind(Class<T> cls, T remote) throws RemoteException, AlreadyBoundException;

    /**
     * Removes the object from the public RMI interface.
     *
     * @param remote
     * @param force
     * @return
     * @throws NoSuchObjectException
     */
    boolean unexportObject(Remote remote, boolean force) throws NoSuchObjectException;

    /**
     * 
     * @return
     * @throws ServerNotActiveException
     */
    String getClientHost() throws ServerNotActiveException;

    /**
     * 
     * @return
     */
    PrintStream getLog();

    /**
     * 
     * @param outputStream
     */
    void setLog(OutputStream outputStream);

    /**
     * 
     * @param remote
     * @return
     * @throws NoSuchObjectException
     */
    Remote toStub(Remote remote) throws NoSuchObjectException;
    
}
