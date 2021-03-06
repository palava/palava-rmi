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

package not.in.de.cosmocode.palava.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A dummy interface to check whether an advice matches.
 *
 * @author Willi Schoenborn
 */
public interface UselessService extends Remote {

    /**
     * Dummy method.
     * 
     * @return a name
     * @throws RemoteException sometimes
     */
    String getName() throws RemoteException;
    
}
