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

import de.cosmocode.palava.ipc.IpcCallScoped;
import de.cosmocode.palava.ipc.IpcConnectionScoped;
import de.cosmocode.palava.ipc.IpcSessionScoped;

/**
 * Binds the {@link RmiScope} to the following scope annotations.
 * <ul>
 *   <li>{@link RmiScoped}</li>
 *   <li>{@link IpcCallScoped}</li>
 *   <li>{@link IpcConnectionScoped}</li>
 *   <li>{@link IpcSessionScoped}</li>
 * </ul>
 *
 * @author Willi Schoenborn
 */
public final class RmiRescopeModule implements Module {

    @Override
    public void configure(Binder binder) {
        final RmiScope scope = new RmiScope();
        binder.requestInjection(scope);
        binder.bind(RmiScope.class).toInstance(scope);
        
        binder.bindScope(RmiScoped.class, scope);
        binder.bindScope(IpcCallScoped.class, scope);
        binder.bindScope(IpcConnectionScoped.class, scope);
        binder.bindScope(IpcSessionScoped.class, scope);
    }

}
