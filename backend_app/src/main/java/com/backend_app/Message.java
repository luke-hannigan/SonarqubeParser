/*
 * Test class, used for taking data from 'https://quoters.apps.pcfone.io/api/random'
 *      as structure from sonarqube is currently unknown.
 */

package com.backend_app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private String type;
    private Value value;

    public Message() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
