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
