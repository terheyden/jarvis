package com.terheyden.jarvis;

import com.terheyden.jarvis.io.UOutput;

public class UOConsole implements UOutput {
    @Override
    public void say(String jOutput) {
        System.out.println(jOutput);
    }
}
