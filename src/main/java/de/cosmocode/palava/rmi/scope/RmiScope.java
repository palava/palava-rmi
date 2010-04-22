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

package de.cosmocode.palava.rmi.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Scope;

import de.cosmocode.palava.scope.AbstractScope;
import de.cosmocode.palava.scope.ScopeContext;
import de.cosmocode.palava.scope.SimpleScopeContext;

/**
 * A custom scope implementation for RMI calls.
 *
 * @author Willi Schoenborn
 */
public final class RmiScope extends AbstractScope<ScopeContext> implements Scope {
    
    private static final Logger LOG = LoggerFactory.getLogger(RmiScope.class);

    private final ThreadLocal<ScopeContext> contexts = new ThreadLocal<ScopeContext>();

    @Override
    public ScopeContext get() {
        return contexts.get();
    }
    
    /**
     * Returns true if this scope is currently in a scoping block.
     * 
     * @return true if this scope is currently in progress, false otherwise
     */
    public boolean inProgress() {
        return get() != null;
    }
    
    /**
     * Enters a scoping block.
     * 
     * @throws IllegalStateException if this scope is already in a scoping block
     */
    public void enter() {
        Preconditions.checkState(!inProgress(), "There is already a %s block in progress", this);
        LOG.trace("Entering {}", this);
        contexts.set(new SimpleScopeContext());
    }
    
    /**
     * Exits a scoping block.
     * 
     * @throws IllegalStateException if this scope is not in a scoping blick right now
     */
    public void exit() {
        Preconditions.checkState(inProgress(), "There is no %s block in progress", this);
        final ScopeContext context = get();
        LOG.trace("Exiting {}", this);
        context.clear();
        contexts.remove();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
}
