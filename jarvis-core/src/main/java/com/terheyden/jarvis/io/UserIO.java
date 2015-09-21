package com.terheyden.jarvis.io;

public interface UserIO {

    /**
     * Say something to the user.
     */
    void say(String jOutput);

    /**
     * Ask the user something, and give them a list of options.
     * @return
     */
    String ask(String jQuestion, String... possibleReplies);

    /**
     * Ask the user for an unbounded response to a question.
     */
    String ask(String jQuestion);
}
