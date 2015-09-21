package com.terheyden.jarvis.data;

import com.terheyden.jarvis.JGlobals.ActionName;
import com.terheyden.jarvis.request.JRequest;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDConfig extends JData {

    private final Config config;

    public JDConfig(Config config) {
        this.config = config;
    }

    public JDConfig() {
        config = ConfigFactory.load();
    }

    @Override
    public JRequest findRequest(String uInput) {

        Map<String, Object> regexActionNameMap = config.getObject("jarvis.regexActions").unwrapped();

        for (Map.Entry<String, Object> entry : regexActionNameMap.entrySet()) {

            String key = entry.getKey();
            String val = entry.getValue().toString();

            // TODO: Save the compiled patterns once.
            Matcher matcher = Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(uInput);
            if (!matcher.find()) {
                continue;
            }

            // Found a matching Request!
            ActionName actionName = ActionName.valueOf(key);
            return new JRequest(uInput, matcher, actionName);
        }

        // Couldn't match this.
        return null;
    }

    @Override
    public String getString(String key) {
        return config.getString(key);
    }
}
