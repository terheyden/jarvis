package com.terheyden.jarvis.io.windows;

import com.terheyden.jarvis.io.UserIO;

import java.util.Map;

public class UIOWinConsole implements UserIO {

    private final ConsoleIO console = new ConsoleIO();

    @Override
    public void say(String jOutput) {
        console.println(jOutput);
    }

    @Override
    public boolean ask(String jQuestion, Map<String, Runnable> responseMap) {
        return console.showMenu(jQuestion, responseMap);
    }

    @Override
    public String ask(String jQuestion) {
        return console.getString(jQuestion);
    }
}
