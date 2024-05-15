package ru.rnizamov.web.server.application.filemanager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {
    private static final File file = new File("static");
    private static List<File> files = new ArrayList<>();

    public static File getFile(String name) {
        File[] listFiles = file.listFiles();
        files = Arrays.stream(listFiles).filter(File::isFile).collect(Collectors.toList());
        if (files.size() > 0) {
            for (File f : files) {
                if (f.getName().equals(name)) {
                    return f;
                }
            }
            System.out.println();
        }
        return null;
    }

    public static boolean existFile(String name) {
        File[] listFiles = file.listFiles();
        files = Arrays.stream(listFiles).filter(File::isFile).collect(Collectors.toList());
        if (files.size() > 0) {
            for (File f : files) {
                if (f.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static byte[] getBytesOfFile(String name) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("static/" + name))) {
            return in.readAllBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }
}

