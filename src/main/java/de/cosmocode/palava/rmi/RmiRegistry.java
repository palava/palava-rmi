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
 * 
 * 
 * @author Tobias Sarnowski
 */
public interface RmiRegistry extends Registry {

    /**
     * Returns if the registry runs on the same jvm or not.
     * @return true if runs in the same jvm, false otherwise
     */
    boolean isLocal();

    /**
     * Looks up the remote object via its full name.
     * 
     * @param cls
     * @param <T>
     * @return
     * @throws RemoteException
     * @throws NotBoundException
     */
    <T extends Remote> T lookup(Class<T> cls) throws RemoteException, NotBoundException;

    /**
     * Looks up the remote object with the given class type and a special name.
     * 
     * @param cls
     * @param name
     * @param <T>
     * @return
     * @throws RemoteException
     * @throws NotBoundException
     */
    <T extends Remote> T lookup(Class<T> cls, String name) throws RemoteException, NotBoundException;

    /**
     * Binds the object with its full name.
     * 
     * @param obj
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    void bind(Remote obj) throws RemoteException, AlreadyBoundException;

    /**
     * Binds the object with the full name of one of its super classes.
     * 
     * @param cls
     * @param obj
     * @param <T>
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    <T extends Remote> void bind(Class<? super T> cls, T obj) throws RemoteException, AlreadyBoundException;

    /**
     * Unbinds the object with the given full name of the class.
     * 
     * @param cls
     * @param <T>
     * @throws RemoteException
     * @throws NotBoundException
     */
    <T extends Remote> void unbind(Class<T> cls) throws RemoteException, NotBoundException;

    /**
     * Rebinds the object with the full name of one of its superclasses.
     * 
     * @param cls
     * @param obj
     * @param <T>
     * @throws RemoteException
     */
    <T extends Remote> void rebind(Class<? super T> cls, T obj) throws RemoteException;
}
