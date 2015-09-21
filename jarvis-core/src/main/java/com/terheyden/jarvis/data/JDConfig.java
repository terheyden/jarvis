package com.terheyden.jarvis.data;

import com.terheyden.jarvis.JGlobals.RequestName;
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

        for (String regex : regexActionNameMap.keySet()) {

            Matcher matcher = Pattern.compile(regex).matcher(uInput);
            if (!matcher.find()) {
                continue;
            }

            // Found a matching Request!
            RequestName requestName = RequestName.valueOf(regexActionNameMap.get(regex).toString());
            return new JRequest(uInput, matcher, requestName);
        }

        // Couldn't match this.
        return null;
    }

    @Override
    public String getString(String key) {
        return config.getString(key);
    }
}
