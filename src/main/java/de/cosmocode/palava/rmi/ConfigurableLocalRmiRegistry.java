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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link RmiRegistry} interface which uses a
 * local registry by calling {@link LocateRegistry#createRegistry(int)}.
 * 
 * @author Tobias Sarnowski
 */
final class ConfigurableLocalRmiRegistry extends AbstractConfigurableRmiRegistry {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurableRmiRemoteRegistry.class);

    @Override
    protected Registry initializeRegistry(String host, int port) throws RemoteException {
        LOG.info("starting local registry on port {}", port);
        return LocateRegistry.createRegistry(port);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
    
}
