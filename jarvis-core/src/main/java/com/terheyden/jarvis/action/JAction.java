package com.terheyden.jarvis.action;

import com.terheyden.jarvis.JCore;
import com.terheyden.jarvis.io.UserIO;
import com.terheyden.jarvis.request.JRequest;

import java.util.Map;

/**
 * All JActions should be stateless. Keep state in the core. Pass state to the JAction.
 */
public abstract class JAction {

    protected JCore core;
    protected UserIO userIO;

    protected JAction(JCore core) {
        this.core = core;
        this.userIO = core.getUserIO();
    }

    public abstract JActionResult run(JRequest request);

    protected JActionResult ok() {
        return new JActionResult(true);
    }

    protected void say(String jMessage, Object... args) {
        userIO.say(String.format(jMessage, args));
    }

    protected boolean ask(String jQuestion, Map<String, Runnable> menuMap) {
        return userIO.ask(jQuestion, menuMap);
    }
}
