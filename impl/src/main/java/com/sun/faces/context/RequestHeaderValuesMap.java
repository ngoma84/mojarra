/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.context;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sun.faces.util.Util;

/**
 * @see javax.faces.context.ExternalContext#getRequestHeaderValuesMap()
 */
public class RequestHeaderValuesMap extends StringArrayValuesMap {

    private final HttpServletRequest request;


    // ------------------------------------------------------------ Constructors


    public RequestHeaderValuesMap(HttpServletRequest request) {
        this.request = request;
    }


    // -------------------------------------------------------- Methods from Map


    @Override
    public boolean containsKey(Object key) {
        return (request.getHeaders(key.toString()) != null);
    }


    @Override
    public String[] get(Object key) {
        Util.notNull("key", key);

        List<String> valuesList = new ArrayList<>();
        Enumeration valuesEnum = this.request.getHeaders(key.toString());
        while (valuesEnum.hasMoreElements()) {
            valuesList.add((String) valuesEnum.nextElement());
        }

        return valuesList.toArray(new String[valuesList.size()]);
    }


    @Override
    public Set<Map.Entry<String,String[]>> entrySet() {
        return Collections.unmodifiableSet(super.entrySet());
    }


    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(super.keySet());
    }


    @Override
    public Collection<String[]> values() {
        return Collections.unmodifiableCollection(super.values());
    }


    @Override
    public int hashCode() {
        return hashCode(request);
    }


    @Override
    public boolean equals(Object obj) {
       return !(obj == null || !(obj.getClass()
                   == ExternalContextImpl
                       .theUnmodifiableMapClass))
               && super.equals(obj);
    }


    // --------------------------------------------- Methods from BaseContextMap

    @Override
    protected Iterator<Map.Entry<String,String[]>> getEntryIterator() {
        return new EntryIterator(request.getHeaderNames());
    }


    @Override
    protected Iterator<String> getKeyIterator() {
        return new KeyIterator(request.getHeaderNames());
    }


    @Override
    protected Iterator<String[]> getValueIterator() {
        return new ValueIterator(request.getHeaderNames());
    }

} // END RequestHeaderValuesMap