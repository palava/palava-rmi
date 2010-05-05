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

/**
 * Generic object oriented version of {@link UnicastRemoteObject}.
 * 
 * @since 1.0
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
public interface RmiUnicast {

    /**
     * Generic and type safe version of {@link UnicastRemoteObject#exportObject(Remote)}.
     *
     * @since 1.0
     * @see UnicastRemoteObject#exportObject(Remote)
     * @param <T> the generic remote type
     * @param type the remote type
     * @param remote the remote object to be exported
     * @return remote object stub
     * @throws NullPointerException if type or remote is null
     * @throws RemoteException if export failed
     */
    <T extends Remote> T export(Class<T> type, T remote) throws RemoteException;

    /**
     * Exports a {@link Remote} object binds it to the registry.
     *
     * @since 1.0
     * @param <T> the generic remote type
     * @param type the remote type
     * @param remote the remote object to be exported and bound
     * @return remote object stub
     * @throws NullPointerException if type or remote is null
     * @throws AlreadyBoundException if remote is already bound
     * @throws RemoteException if export and/or bind failed
     */
    <T extends Remote> T exportAndBind(Class<T> type, T remote) throws RemoteException, AlreadyBoundException;

    /**
     * Generic and type safe version of {@link UnicastRemoteObject#unexportObject(Remote, boolean)}.
     * 
     * @since 1.0
     * @see UnicastRemoteObject#unexportObject(Remote, boolean)
     * @param remote the remote object to be unexported
     * @param force if true, unexports the object even if there are pending or in-progress calls;
     *        if false, only unexports the object if there are no pending or in-progress calls
     * @return true if operation is successful, false otherwise
     * @throws NullPointerException if remote is null
     * @throws NoSuchObjectException if the remote object is not currently exported
     */
    boolean unexport(Remote remote, boolean force) throws NoSuchObjectException;

    /**
     * Non-static version of {@link UnicastRemoteObject#getClientHost()}.
     *
     * @since 1.0
     * @see UnicastRemoteObject#getClientHost()
     * @return a string representation of the client host
     * @throws ServerNotActiveException if no remote method invocation is being processed in the current thread
     */
    String getClientHost() throws ServerNotActiveException;

    /**
     * Non-static version of {@link UnicastRemoteObject#getLog()}.
     * 
     * @since 1.0
     * @see UnicastRemoteObject#getLog() 
     * @return the call log
     */
    PrintStream getLog();

    /**
     * Non static version of {@link UnicastRemoteObject#setLog(OutputStream)}.
     * 
     * @since 1.0
     * @see UnicastRemoteObject#setLog(OutputStream)
     * @param stream the output stream to which RMI calls should be logged
     * @throws NullPointerException if stream is null
     */
    void setLog(OutputStream stream);

    /**
     * Non-static version of {@link UnicastRemoteObject#toStub(Remote)}.
     * 
     * @since 1.0
     * @see UnicastRemoteObject#toStub(Remote)
     * @param remote the remote object whose stub is needed
     * @return the stub for the remote object, obj.
     * @throws NoSuchObjectException if the stub for the remote object could not be found
     */
    Remote toStub(Remote remote) throws NoSuchObjectException;
    
}
