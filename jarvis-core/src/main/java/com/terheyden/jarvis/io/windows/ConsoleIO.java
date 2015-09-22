package com.terheyden.jarvis.io.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Helpers for interacting with the user from the console / shell / command line.
 * I stole this from another project I wrote so I'm keeping this file separate
 * from UIOWinConsole.java.
 */
public class ConsoleIO {

    private int width = 80;
    private InputStreamReader inStream = new InputStreamReader(System.in);
    private BufferedReader inBuf = new BufferedReader(inStream);

    public void print(String text, Object... args) {
        System.out.print(formatWordWrap(String.format(text, args), width));
    }

    public void println(String text, Object... args) {
        System.out.println(formatWordWrap(String.format(text, args), width));
    }

    public String getString(String prompt, Object... args) {
        try {

            print(prompt, args);
            String in = inBuf.readLine();

            if (in.isEmpty()) {
                return null;
            }

            return in;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns an int entered or -1 if blank / q entered.
     */
    public int getInt(String prompt) {
        while (true) {
            try {

                String in = getString(prompt).trim();
                if (in.isEmpty() || in.equalsIgnoreCase("q")) {
                    return -1;
                }

                return Integer.parseInt(in);

            } catch (NumberFormatException e) {
                println("Please enter a valid number.");
            }
        }
    }

    private static String formatWordWrap(String text, int maxLineLen) {

        if (text == null || text.length() <= maxLineLen) {
            return text;
        }

        String[] lines = text.split("\n");
        StringBuilder bui = new StringBuilder();

        for (String line : lines) {

            if (line.length() <= maxLineLen) {
                bui.append(line).append("\n");
                continue;
            }

            while (line.length() > maxLineLen) {

                for (int i = maxLineLen; i > 0; i--) {

                    if (line.charAt(i) == ' ') {
                        // Found a space, put in a break.
                        bui.append(line.substring(0, i)).append("\n");
                        line = line.substring(i + 1);
                        break;
                    }
                }
            }

            // Successfully shortened the line, now we can add it.
            bui.append(line).append("\n");
        }

        bui.deleteCharAt(bui.length() - 1);
        return bui.toString();
    }

    public String getSingleChoice(String description, String[] choices, int defaultChoice) {

        String defaultStr = defaultChoice < 0 ? null : "" + (defaultChoice + 1);

        println(description + "\n");

        for (int i = 0; i < choices.length; i++) {
            println(String.format("    %2d) %s", i + 1, choices[i]));
        }

        println("\n");

        String prompt = String.format("[1-%d", choices.length);

        if (defaultStr != null) {
            prompt += String.format(",quit] [%s] ", defaultStr);
        } else {
            prompt += "] ";
        }

        String reply = getString(prompt);

        if (reply == null) {
            if (defaultStr != null) {
                reply = defaultStr;
            } else {
                return null;
            }
        }

        try {
            int off = Integer.parseInt(reply);
            return choices[off-1];
        } catch (Exception e) {
            return null;
        }
    }

    public boolean showMenu(String desc, Map<String, Runnable> menuMap) {

        List<Runnable> runList = new LinkedList<Runnable>();
        int count = 1;

        println(desc);

        for (Map.Entry<String, Runnable> entry : menuMap.entrySet()) {
            println(String.format("    %2d. %s", count, entry.getKey()));
            runList.add(entry.getValue());
            count++;
        }

        String prompt = String.format("[1-%d,q] ", runList.size());
        int res = getInt(prompt);

        if (res < 1) {
            return false;
        }

        res--;

        try {
            Runnable run = runList.get(res);
            run.run();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
