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

import java.io.Serializable;
import java.rmi.*;
import java.rmi.registry.Registry;

/**
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
     * @param cls
     * @param <T>
     * @return
     * @throws RemoteException
     * @throws NotBoundException
     */
    <T extends Remote> T lookup(Class<T> cls) throws RemoteException, NotBoundException;

    /**
     * Looks up the remote object with the given class type and a special name.
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
     * @param obj
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    void bind(Remote obj) throws RemoteException, AlreadyBoundException;

    /**
     * Binds the object with the full name of one of its super classes.
     * @param cls
     * @param obj
     * @param <T>
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    <T extends Remote> void bind(Class<? super T> cls, T obj) throws RemoteException, AlreadyBoundException;

    /**
     * Unbinds the object with the given full name of the class.
     * @param cls
     * @param <T>
     * @throws RemoteException
     * @throws NotBoundException
     */
    <T extends Remote> void unbind(Class<T> cls) throws RemoteException, NotBoundException;

    /**
     * Rebinds the object with the full name of one of its superclasses.
     * @param cls
     * @param obj
     * @param <T>
     * @throws RemoteException
     */
    <T extends Remote> void rebind(Class<? super T> cls, T obj) throws RemoteException;
}
