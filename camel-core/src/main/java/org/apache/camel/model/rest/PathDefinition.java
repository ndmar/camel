/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.model.ToDefinition;

@XmlRootElement(name = "path")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PathDefinition {

    private String uri;
    private List<VerbDefinition> verbs = new ArrayList<VerbDefinition>();

    public String getUri() {
        return uri;
    }

    @XmlAttribute(required = true)
    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<VerbDefinition> getVerbs() {
        return verbs;
    }

    @XmlElementRef
    public void setVerbs(List<VerbDefinition> verbs) {
        this.verbs = verbs;
    }

    // Fluent API
    //-------------------------------------------------------------------------

    public PathDefinition routeId(String routeId) {
        // set on last verb
        if (verbs != null && !verbs.isEmpty()) {
            VerbDefinition last = verbs.get(verbs.size() - 1);
            last.setRouteId(routeId);
        }
        return this;
    }

    public PathDefinition get() {
        return addVerb("get", null);
    }

    public PathDefinition get(String url) {
        return addVerb("get", url);
    }

    public PathDefinition post() {
        return addVerb("post", null);
    }

    public PathDefinition post(String url) {
        return addVerb("post", url);
    }

    public PathDefinition put() {
        return addVerb("put", null);
    }

    public PathDefinition put(String url) {
        return addVerb("put", url);
    }

    public PathDefinition delete() {
        return addVerb("delete", null);
    }

    public PathDefinition delete(String url) {
        return addVerb("delete", url);
    }

    public PathDefinition head() {
        return addVerb("head", null);
    }

    public PathDefinition head(String url) {
        return addVerb("head", url);
    }

    public PathDefinition verb(String verb) {
        return addVerb(verb, null);
    }

    public PathDefinition verb(String verb, String url) {
        return addVerb(verb, url);
    }

    public PathDefinition accept(String accept) {
        // add to last verb
        if (getVerbs().isEmpty()) {
            throw new IllegalArgumentException("Must add verb first, such as get/post/delete");
        }

        VerbDefinition verb = getVerbs().get(getVerbs().size() - 1);
        verb.setAccept(accept);

        return this;
    }

    @Deprecated
    public VerbDefinition to(String url) {
        // add to last verb
        if (getVerbs().isEmpty()) {
            throw new IllegalArgumentException("Must add verb first, such as get/post/delete");
        }

        VerbDefinition verb = getVerbs().get(getVerbs().size() - 1);
        verb.addOutput(new ToDefinition(url));

        return verb;
    }

    private PathDefinition addVerb(String verb, String url) {
        VerbDefinition answer = new VerbDefinition();
        answer.setMethod(verb);
        answer.setPath(this);
        getVerbs().add(answer);
        if (url != null) {
            answer.setUri(url);
        }
        return this;
    }

}