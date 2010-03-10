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
