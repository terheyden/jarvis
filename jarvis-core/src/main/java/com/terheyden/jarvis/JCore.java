//
// JARVIS - global, platform-aware assistance system.
// By Luke Terheyden. Shared under the GNU General Public License v2.
//
package com.terheyden.jarvis;

import com.terheyden.jarvis.JGlobals.RequestName;
import com.terheyden.jarvis.action.JAction;
import com.terheyden.jarvis.action.JActionResult;
import com.terheyden.jarvis.data.JData;
import com.terheyden.jarvis.io.UOutput;
import com.terheyden.jarvis.request.JRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.terheyden.jarvis.JGlobals.RequestName.UNKNOWN;

/**
 * UInput is passed here, to the JCore.
 * The JCore figures out what actions to take based on the input.
 */
public class JCore {

    private final Map<RequestName, JAction> actionMap = new HashMap<>();
    private final JData data;

    public JCore(JData data) {
        this.data = data;
    }

    /**
     * Registry of ways to communicate back to the user.
     */
    private final List<UOutput> outputs = new LinkedList<>();

    public void addOutput(UOutput output) {
        outputs.add(output);
    }

    /**
     * Registry of requests, and their corresponding class that fulfills the request.
     * @param request name of the request, "greeting"
     * @param action platform-specific class to respond, "WindowsGreeter"
     */
    public void addAction(RequestName request, JAction action) {
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
        JAction jAction = actionMap.get(req.getRequestName());

        // Run the action, passing in the request details for context.
        JActionResult res = jAction.run(req);
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
    public void sayToUser(String jOutput) {
        for (UOutput out : outputs) {
            out.say(jOutput);
        }
    }
}
