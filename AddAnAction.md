### How to Add an Action ###

##### Add the action to the official roster #####

```java
public class JGlobals {

    public enum ActionName {
        UNKNOWN,
        greeting,
        openApplication  // <------ this is our new action
    }
}
```

##### Add the regex to activate the command to application.conf #####

```
  regexActions {
    greeting = "^"${jarvis.greet}"\\s+"${jarvis.name}
    openApplication = open "(.+)"  # <-- notice too that we're regex capturing the app name
  }
```

##### Create your new JAction #####

```java
public class JAOpenApplication extends JAction {

    protected JAOpenApplication(JCore core) {
        super(core);
    }

    @Override
    public JActionResult run(JRequest request) {
        String appName = request.getMatcher().group(1);
        core.sayToUser("Opening \"%s\" ...", appName);
        return ok();
    }
}
```

##### Register it with the platform client #####

```java
    public static void main(String... args) {

        // Start Jarvis for command-line interfacing.
        JCore core = new JCore(new JDConfig());

        // ...

        // Add global actions.
        core.addAction(ActionName.UNKNOWN, new JAUnknown(core));
        core.addAction(ActionName.greeting, new JAGreeting(core));
        core.addAction(ActionName.openApplication, new JAOpenApplication(core));
    }
```
