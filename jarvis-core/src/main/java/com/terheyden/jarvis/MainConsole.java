package com.terheyden.jarvis;

import com.terheyden.jarvis.JGlobals.RequestName;
import com.terheyden.jarvis.action.global.JAUnknown;
import com.terheyden.jarvis.data.JDConfig;
import com.terheyden.jarvis.action.global.JAGreeting;

/**
 * Hello Jarvis!
 * 9/21/2015 - 1:43pm.
 */
public class MainConsole {

    private MainConsole() {}

    public static void main(String... args) {

        // Start Jarvis for command-line interfacing.
        JCore core = new JCore(new JDConfig());

        // Add console-based I/O.
        core.addOutput(new UOConsole());

        // Add global actions.
        core.addAction(RequestName.UNKNOWN, new JAUnknown(core));
        core.addAction(RequestName.greeting, new JAGreeting(core));

        core.sayToJarvis("Hi Jarvis");
    }
}
