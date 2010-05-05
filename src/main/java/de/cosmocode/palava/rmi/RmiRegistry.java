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

/**
 * Extension of the {@link Registry} interface which
 * adds generic type safe methods.
 * 
 * @since 1.0
 * @author Willi Schoenborn 
 * @author Tobias Sarnowski
 */
public interface RmiRegistry extends Registry {

    /**
     * Checks whether this registry runs in the current virtual machine.
     * 
     * @since 1.0
     * @return true if this registry is local, false otherwise
     */
    boolean isLocal();

    /**
     * Looks up a remote object via the fqcn of the specified class.
     * 
     * @since 1.0
     * @param <T> the generic remote type 
     * @param type the remote type
     * @return the found remote object
     * @throws NullPointerException if type is null
     * @throws NotBoundException if no object is bound for the name of the specified class
     * @throws RemoteException if lookup failed
     */
    <T extends Remote> T lookup(Class<T> type) throws RemoteException, NotBoundException;

    /**
     * 
     * Looks up the remote object with the given class type and a special name.
     * 
     * @since 1.0
     * @param <T> the generic remote type
     * @param type the remote type
     * @param name the name under which the remote was bound
     * @return the found remote object
     * @throws NullPointerException if type or name is null
     * @throws NotBoundException if no object is bound for the specified name
     * @throws RemoteException if lookup failed
     */
    <T extends Remote> T lookup(Class<T> type, String name) throws RemoteException, NotBoundException;

    /**
     * Binds the specified object using its fqcn.
     * 
     * @since 1.0
     * @param remote the object being bound
     * @throws AlreadyBoundException if object is alread bound
     * @throws RemoteException if bind failed
     */
    void bind(Remote remote) throws RemoteException, AlreadyBoundException;

    /**
     * Binds the specified object using the fqcn of one of its super types.
     * 
     * @since 1.0 
     * @param <T> the generic remote type
     * @param type the remote super type
     * @param remote the remote object being bound
     * @throws AlreadyBoundException if object is already bound
     * @throws RemoteException if bind failed
     */
    <T extends Remote> void bind(Class<? super T> type, T remote) throws RemoteException, AlreadyBoundException;

    /**
     * Unbinds a remote object with the fqcn of the specified class.
     * 
     * @since 1.0
     * @param <T> the generic remote type
     * @param type the remote type
     * @throws NotBoundException if no object is bound for the specified class name
     * @throws RemoteException if unbind failed
     */
    <T extends Remote> void unbind(Class<T> type) throws RemoteException, NotBoundException;

    /**
     * Rebinds the specified object using the fqcn of one of its super types.
     * 
     * @since 1.0 
     * @param <T> the generic remote type
     * @param type the remote super type
     * @param remote the remote object being rebound
     * @throws RemoteException if rebind failed
     */
    <T extends Remote> void rebind(Class<? super T> type, T remote) throws RemoteException;
}
