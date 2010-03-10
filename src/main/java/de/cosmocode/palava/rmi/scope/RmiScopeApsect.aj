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

package de.cosmocode.palava.rmi.scope;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import de.cosmocode.palava.core.aop.AbstractPalavaAspect;

public final aspect RmiScopeApsect extends AbstractPalavaAspect issingleton() {

    private static final Logger LOG = LoggerFactory.getLogger(RmiScopeApsect.class);

    private RmiScope scope;
    
    @Inject
    void setScope(RmiScope scope) {
        this.scope = Preconditions.checkNotNull(scope, "Scope");
    }
    
    pointcut invocation(): execution(public * Remote+.*(..) throws RemoteException);
    
    Object around(): invocation() {
        if (scope.inProgress()) {
            LOG.trace("Rmi scope already in progress");
            return proceed();
        } else {
            scope.enter();
            try {
                return proceed();
            } finally {
                scope.exit();
            }
        }
    }
    
}
