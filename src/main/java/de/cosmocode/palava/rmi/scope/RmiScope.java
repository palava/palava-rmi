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

import com.google.common.base.Preconditions;
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
    
    public boolean inProgress() {
        return get() != null;
    }
    
    public void enter() {
        Preconditions.checkState(!inProgress(), "There is already a %s block in progress", this);
        contexts.set(new SimpleScopeContext());
    }
    
    public void exit() {
        Preconditions.checkState(inProgress(), "There is no %s block in progress", this);
        final ScopeContext context = get();
        context.clear();
        contexts.remove();
    }
    
}
