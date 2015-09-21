package com.terheyden.jarvis.util;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FileFindUtils {

    /**
     * Returns all files (recursively) that match the specified FileMatcher criteria.
     * @param startDir starting dir to search for files
     * @param fileMatcher declare this to specify file-matching criteria, or null to find all files. Files passed to the FileMatcher are guaranteed to exist and not be a directory
     * @return set of files or empty set, never null
     */
    public static Set<File> findFiles(File startDir, FileMatcher fileMatcher) {

        if (!startDir.exists() || !startDir.isDirectory()) {
            return Collections.emptySet();
        }

        Set<File> fileSet = new HashSet<File>();
        File[] dirFileList = startDir.listFiles();

        for (File file : dirFileList) {

            if (!file.exists()) {
                continue;
            }

            if (file.isDirectory()) {

                Set<File> fileSubSet = findFiles(file, fileMatcher);
                fileSet.addAll(fileSubSet);

            } else if (fileMatcher.matchFile(file)) {
                fileSet.add(file);
            }
        }

        return fileSet;
    }

    /**
     * Used to match found files by some criteria.
     */
    public abstract static class FileMatcher {
        public abstract boolean matchFile(File file);
    }
}
