package de.cosmocode.palava.rmi.scope;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Binds the {@link RmiScoped} to {@link RmiScope}.
 *
 * @author Willi Schoenborn
 */
public final class RmiScopeModule implements Module {

    @Override
    public void configure(Binder binder) {
        final RmiScope rmiScope = new RmiScope();
        binder.requestInjection(rmiScope);
        binder.bindScope(RmiScoped.class, rmiScope);
        binder.bind(RmiScope.class).toInstance(rmiScope);
    }

}
