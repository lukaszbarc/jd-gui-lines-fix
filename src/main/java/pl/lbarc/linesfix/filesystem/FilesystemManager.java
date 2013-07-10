package pl.lbarc.linesfix.filesystem;

import com.google.common.collect.Lists;
import pl.lbarc.linesfix.args.ComandlineArgResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.Entry;

/**
 * User: LBARC
 * Date: 10.07.13
 * Time: 08:46
 */
public class FilesystemManager {

    private ComandlineArgResolver comandlineArgResolver;

    public FilesystemManager(ComandlineArgResolver comandlineArgResolver) {
        this.comandlineArgResolver = comandlineArgResolver;
    }

    public List<File> getJavaSources() {
        return processDirectory(new File(comandlineArgResolver.getInputPath()));
    }

    private List<File> processDirectory(File folder) {
        List<File> result = Lists.newArrayList();
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                result.add(file);
            } else if (file.isDirectory()) {
                result.addAll(processDirectory(file));
            }
        }
        return result;
    }

    public void saveFilesWithContent(Map<File, List<String>> filesContentMap) throws IOException {
        createResultDir();
        for (Entry<File, List<String>> entry : filesContentMap.entrySet()) {
            File file = entry.getKey();
            file.getParentFile().mkdirs();
            PrintWriter out = new PrintWriter(new FileWriter(file));
            List<String> resultLines = entry.getValue();
            for (String s : resultLines) {
                out.println(s);
            }
            out.close();
        }
    }

    private boolean createResultDir() {
        File resultDir = new File(comandlineArgResolver.getOutputPath());
        return resultDir.mkdir();
    }
}
