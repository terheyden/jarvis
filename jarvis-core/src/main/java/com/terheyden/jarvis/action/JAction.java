package com.terheyden.jarvis.action;

import com.terheyden.jarvis.JCore;
import com.terheyden.jarvis.request.JRequest;

/**
 * All JActions should be stateless. Keep state in the core. Pass state to the JAction.
 */
public abstract class JAction {

    protected JCore core;

    protected JAction(JCore core) {
        this.core = core;
    }

    public abstract JActionResult run(JRequest request);

    protected JActionResult ok() {
        return new JActionResult(true);
    }
}
