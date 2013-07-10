package pl.lbarc.linesfix;

import pl.lbarc.linesfix.args.ComandlineArgResolver;
import pl.lbarc.linesfix.filesystem.FilesystemManager;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * User: LBARC
 * Date: 09.07.13
 * Time: 15:08
 */
public class Main {

    private static ComandlineArgResolver comandlineArgResolver;
    private static FilesystemManager fileSystemManager;
    private static JdGuiFilesProcessor jdGuiFilesProcessor;

    public static void main(String[] args) {
        try {
            comandlineArgResolver = new ComandlineArgResolver();
            comandlineArgResolver.initialize(args);
            fileSystemManager = new FilesystemManager(comandlineArgResolver);
            jdGuiFilesProcessor = new JdGuiFilesProcessor(comandlineArgResolver);
            List<File> javaSources = fileSystemManager.getJavaSources();
            Map<File, List<String>> processedSources = jdGuiFilesProcessor.processFiles(javaSources);
            fileSystemManager.saveFilesWithContent(processedSources);
        } catch (IllegalArgumentException ex) {
            comandlineArgResolver.printHelp();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
