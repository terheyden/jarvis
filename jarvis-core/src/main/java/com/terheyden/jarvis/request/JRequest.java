package com.terheyden.jarvis.request;

import com.terheyden.jarvis.JGlobals;
import com.terheyden.jarvis.JGlobals.RequestName;

import java.util.regex.Matcher;

/**
 * A data bag with action request details.
 * Passed as context to the matching Action.
 */
public class JRequest {

    private final String uInput;
    private final Matcher match;
    private final JGlobals.RequestName requestName;

    public JRequest(String uInput, Matcher match, RequestName requestName) {
        this.uInput = uInput;
        this.match = match;
        this.requestName = requestName;
    }

    public String getUInput() {
        return uInput;
    }

    public Matcher getMatcher() {
        return match;
    }

    public RequestName getRequestName() {
        return requestName;
    }
}
