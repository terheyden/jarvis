package com.terheyden.jarvis.data;

import com.terheyden.jarvis.request.JRequest;

public abstract class JData {

    public abstract JRequest findRequest(String uInput);

    public abstract String getString(String key);
}
