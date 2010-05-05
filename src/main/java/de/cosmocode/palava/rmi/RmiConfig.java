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

package de.cosmocode.palava.rmi;

/**
 * Static constant holder class for rmi config key names.
 * 
 * @author Tobias Sarnowski
 * @author Willi Schoenborn
 */
public final class RmiConfig {

    public static final String PREFIX = "rmi.";

    public static final String PORT = PREFIX + "port";

    public static final String REGISTRY_PREFIX = PREFIX + "registry.";
    
    public static final String REGISTRY_HOST = REGISTRY_PREFIX + "host";
    
    public static final String REGISTRY_PORT = REGISTRY_PREFIX + "port";

    private RmiConfig() {
        
    }
    
}
