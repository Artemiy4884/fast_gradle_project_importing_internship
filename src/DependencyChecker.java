import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DependencyChecker {

    public static void main(String[] args) {
        String mainClass = args[0];

        List<String> jarPaths = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            jarPaths.add(args[i]);
        }

        checkAndPrintDependency(mainClass, jarPaths);
    }

    private static void checkAndPrintDependency(String mainClass, List<String> jarPaths) {
        for (String jarPath : jarPaths) {
            try {
                boolean isDependencyAvailable = isClassLoadable(mainClass, jarPath);
                System.out.println(isDependencyAvailable + ": " + mainClass + " " + jarPath);
            } catch (Exception e) {
                System.out.println(false + ": " + mainClass + " " + jarPath);
            }
        }
    }

    private static boolean isClassLoadable(String mainClassName, String jarPath) {
        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            return false;
        }

        try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()})) {
            urlClassLoader.loadClass(mainClassName);
            return true;
        } catch (ClassNotFoundException | IOException e) {
            return false;
        }
    }
}