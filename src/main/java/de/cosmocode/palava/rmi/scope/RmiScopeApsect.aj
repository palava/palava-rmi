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
