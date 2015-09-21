package com.terheyden.jarvis.io.windows;

import com.terheyden.jarvis.io.UserIO;

public class UIOWinConsole implements UserIO {

    @Override
    public void say(String jOutput) {
        System.out.println(jOutput);
    }

    @Override
    public String ask(String jQuestion, String... possibleReplies) {
        return null;
    }

    @Override
    public String ask(String jQuestion) {
        return null;
    }
}
