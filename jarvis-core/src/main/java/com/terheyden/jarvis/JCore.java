//
// JARVIS - global, platform-aware assistance system.
// By Luke Terheyden. Shared under the GNU General Public License v2.
//
package com.terheyden.jarvis;

import com.terheyden.jarvis.action.JAction;
import com.terheyden.jarvis.data.JData;
import com.terheyden.jarvis.io.UserIO;
import com.terheyden.jarvis.request.JRequest;
import com.terheyden.jarvis.util.EnglishSyntax;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.terheyden.jarvis.JGlobals.ActionName.UNKNOWN;

/**
 * UInput is passed here, to the JCore.
 * The JCore figures out what actions to take based on the input.
 */
public class JCore {

    /**
     * Map of actions, and the platform-specific class to perform it.
     */
    private final Map<JGlobals.ActionName, JAction> actionMap = new HashMap<>();

    /**
     * Data store / retrieve.
     */
    private final JData data;

    /**
     * Registry of ways to communicate back to the user.
     */
    private final List<UserIO> outputs = new LinkedList<>();

    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
        0,                              // Keep 0 idle threads hanging around.
        100,                            // Jarvis can do 100 things at once, why not?
        60L,                            // Idle threads die after 60 (units of time).
        TimeUnit.SECONDS,               // Units of time = seconds, so idle threads die in 60 secs.
        new LinkedBlockingQueue<>());   // Queue of worker items - unlimited, so we never reject.

    public JCore(JData data) {
        this.data = data;
    }

    public void addOutput(UserIO output) {
        outputs.add(output);
    }

    /**
     * Registry of requests, and their corresponding class that fulfills the request.
     * @param request name of the request, "greeting"
     * @param action platform-specific class to respond, "WindowsGreeter"
     */
    public void addAction(JGlobals.ActionName request, JAction action) {
        actionMap.put(request, action);
    }

    /**
     * Input to jarvis, which will become actions to perform, and
     * ultimately end up saying something back to the user.
     * @param uInput raw English input from the user
     */
    public void sayToJarvis(String uInput) {

        // Strip down the input.
        String input = EnglishSyntax.clean(uInput);

        // Convert their english request into a concrete action name.
        JRequest req = findMatchingRequest(input);

        // Convert the action name into the platform-specific version of the Action.
        JAction jAction = actionMap.get(req.getActionName());

        // Run the action on its own thread.
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                jAction.run(req);
            }
        });
    }

    /**
     * Based on what the user said, try to find a matching Action for Jarvis to take.
     * Doesn't return null - if the action can't be found, returns UNKNOWN action.
     * @param uInput spoken / written English command to Jarvis (stripped)
     * @return matching action name and contextual details
     */
    private JRequest findMatchingRequest(String uInput) {

        // Use regex or whatever to find the name of an action to perform.
        JRequest request = data.findRequest(uInput);

        if (request == null) {
            request = new JRequest(uInput, null, UNKNOWN);
        }

        return request;
    }

    /**
     * Jarvis Actions can use this to communicate with the user.
     * @param jOutput speech from Jarvis to the user who made a request
     */
    public void sayToUser(String jOutput, Object... args) {
        for (UserIO out : outputs) {
            out.say(String.format(jOutput, args));
        }
    }
}
