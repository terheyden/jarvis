package com.terheyden.jarvis.io;

import java.util.Map;

/**
 * Abstracts communication I/O with the user.
 */
public interface UserIO {

    /**
     * Say something to the user.
     */
    void say(String jOutput);

    /**
     * Ask the user something, and give them a list of options.
     * The corresponding Runnable will run depending on what is selected.
     * @return true if an option was selected and run, false if the user aborted selection
     */
    boolean ask(String jQuestion, Map<String, Runnable> responseMap);

    /**
     * Ask the user for an unbounded response to a question.
     * @return the user's text response
     */
    String ask(String jQuestion);
}
