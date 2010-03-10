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

import com.google.inject.Scope;

import de.cosmocode.palava.core.scope.AbstractScope;
import de.cosmocode.palava.core.scope.ScopeContext;
import de.cosmocode.palava.core.scope.SimpleScopeContext;

/**
 * A custom scope implementation for RMI calls.
 *
 * @author Willi Schoenborn
 */
public final class RmiScope extends AbstractScope<ScopeContext> implements Scope {

    private final ThreadLocal<ScopeContext> contexts = new ThreadLocal<ScopeContext>();

    @Override
    public ScopeContext get() {
        return contexts.get();
    }
    
    @Override
    protected boolean inProgress() {
        return contexts.get() != null;
    }
    
    @Override
    protected void doEnter() {
        contexts.set(new SimpleScopeContext());
    }
    
    @Override
    protected void doExit() {
        contexts.remove();
    }
    
}
