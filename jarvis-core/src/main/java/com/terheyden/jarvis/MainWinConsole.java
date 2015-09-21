package com.terheyden.jarvis;

import com.terheyden.jarvis.JGlobals.ActionName;
import com.terheyden.jarvis.action.windows.JAWinOpenApplication;
import com.terheyden.jarvis.action.global.JAUnknown;
import com.terheyden.jarvis.data.JDConfig;
import com.terheyden.jarvis.action.global.JAGreeting;
import com.terheyden.jarvis.io.windows.UIOWinConsole;

/**
 * Hello Jarvis!
 * 9/21/2015 - 1:43pm.
 */
public class MainWinConsole {

    private MainWinConsole() {}

    public static void main(String... args) {

        // Start Jarvis for command-line interfacing.
        JCore core = new JCore(new JDConfig());

        // Add console-based I/O.
        core.addOutput(new UIOWinConsole());

        // Add global actions.
        core.addAction(ActionName.UNKNOWN, new JAUnknown(core));
        core.addAction(ActionName.greeting, new JAGreeting(core));

        // Add Windows actions.
        core.addAction(ActionName.openApplication, new JAWinOpenApplication(core));

        core.sayToJarvis("Hi Jarvis");
        core.sayToJarvis("open notepad");
    }
}
