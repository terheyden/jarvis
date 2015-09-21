package com.terheyden.jarvis.request;

import com.terheyden.jarvis.JGlobals;
import com.terheyden.jarvis.JGlobals.ActionName;

import java.util.regex.Matcher;

/**
 * A data bag with action request details.
 * Passed as context to the matching Action.
 */
public class JRequest implements Runnable {

    private final String uInput;
    private final Matcher match;
    private final ActionName actionName;

    public JRequest(String uInput, Matcher match, JGlobals.ActionName actionName) {
        this.uInput = uInput;
        this.match = match;
        this.actionName = actionName;
    }

    public String getUInput() {
        return uInput;
    }

    public Matcher getMatcher() {
        return match;
    }

    public ActionName getActionName() {
        return actionName;
    }

    @Override
    public void run() {

    }
}
