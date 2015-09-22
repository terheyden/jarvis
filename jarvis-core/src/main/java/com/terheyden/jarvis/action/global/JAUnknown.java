package com.terheyden.jarvis.action.global;

import com.terheyden.jarvis.JCore;
import com.terheyden.jarvis.action.JAction;
import com.terheyden.jarvis.action.JActionResult;
import com.terheyden.jarvis.request.JRequest;

public class JAUnknown extends JAction {

    public JAUnknown(JCore core) {
        super(core);
    }

    @Override
    public JActionResult run(JRequest request) {
        say("I'm sorry sir, I don't understand what \"%s\" means.", request.getUInput());
        return ok();
    }
}
