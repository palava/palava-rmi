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

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import de.cosmocode.palava.core.aop.AbstractPalavaAspect;

public final aspect RmiScopeAspect extends AbstractPalavaAspect issingleton() {

    private static final Logger LOG = LoggerFactory.getLogger(RmiScopeAspect.class);

    private RmiScope scope;
    
    @Inject
    void setScope(RmiScope scope) {
        this.scope = Preconditions.checkNotNull(scope, "Scope");
    }
    
    private pointcut noRmi(): !within(de.cosmocode.palava.rmi..*);
    
    pointcut invocation(): execution(public * Remote+.*(..) throws RemoteException) && noRmi();
    
    @SuppressAjWarnings("adviceDidNotMatch")
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
