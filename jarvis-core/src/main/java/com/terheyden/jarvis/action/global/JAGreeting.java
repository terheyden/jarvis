package com.terheyden.jarvis.action.global;

import com.terheyden.jarvis.JCore;
import com.terheyden.jarvis.action.JAction;
import com.terheyden.jarvis.action.JActionResult;
import com.terheyden.jarvis.request.JRequest;

public class JAGreeting extends JAction {

    public JAGreeting(JCore core) {
        super(core);
    }

    @Override
    public JActionResult run(JRequest request) {
        say("Hello, sir.");
        return ok();
    }
}
