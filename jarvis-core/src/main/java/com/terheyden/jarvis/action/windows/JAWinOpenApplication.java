package com.terheyden.jarvis.action.windows;

import com.terheyden.jarvis.JCore;
import com.terheyden.jarvis.action.JAction;
import com.terheyden.jarvis.action.JActionResult;
import com.terheyden.jarvis.request.JRequest;
import com.terheyden.jarvis.util.FileFindUtils;
import com.terheyden.jarvis.util.FileFindUtils.FileMatcher;
import org.slf4j.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class JAWinOpenApplication extends JAction {

    private static final Logger log = getLogger(JAWinOpenApplication.class);

    private static final String menuSubDir = "\\Microsoft\\Windows\\Start Menu";

    public JAWinOpenApplication(JCore core) {
        super(core);
    }

    @Override
    public JActionResult run(JRequest request) {

        Set<File> menuFiles = new HashSet<>();
        String appName = request.getMatcher().group(1);
        FileMatcher appMatcher = getMenuAppMatcher(appName);

        core.sayToUser("Opening \"%s\" ...", appName);

        File allUsersMenu = new File(System.getenv("ALLUSERSPROFILE"), menuSubDir);
        File userMenu = new File(System.getenv("APPDATA"), menuSubDir);

        if (allUsersMenu.isDirectory()) {
            menuFiles.addAll(FileFindUtils.findFiles(allUsersMenu, appMatcher));
        } else {
            core.sayToUser("Sir, I can't find the all users menu.");
            log.warn("Can't find the all users menu: {}", allUsersMenu.getAbsolutePath());
        }

        if (userMenu.isDirectory()) {
            menuFiles.addAll(FileFindUtils.findFiles(userMenu, appMatcher));
        } else {
            core.sayToUser("Sir, I can't find your user menu.");
            log.warn("Can't find the user menu: {}", userMenu.getAbsolutePath());
        }

        for (File menuFile : menuFiles) {
            core.sayToUser("Found matching app: %s", menuFile.getName());
        }
        
        return ok();
    }

    private static FileFindUtils.FileMatcher getMenuAppMatcher(String name) {
        return new FileMatcher() {
            @Override
            public boolean matchFile(File file) {
                String fn = file.getName().toLowerCase();
                return fn.contains(name.toLowerCase()) && fn.endsWith(".lnk");
            }
        };
    }
}
