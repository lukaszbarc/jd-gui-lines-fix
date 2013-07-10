package pl.lbarc.linesfix;

import pl.lbarc.linesfix.args.ComandlineArgResolver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.nio.file.Files.readAllLines;

/**
 * User: LBARC
 * Date: 10.07.13
 * Time: 08:54
 */
public class JdGuiFilesProcessor {

    public static final String JD_GUI_COMMENT_PATTERN = "^/\\*\\s*\\d+\\s*\\*/.*";
    private final ComandlineArgResolver comandlineArgResolver;
    private Pattern pattern = Pattern.compile("\\d+");

    public JdGuiFilesProcessor(ComandlineArgResolver comandlineArgResolver) {
        this.comandlineArgResolver = comandlineArgResolver;
    }

    public Map<File, List<String>> processFiles(List<File> files) throws IOException {
        Map<File, List<String>> result = newHashMap();
        for (File file : files) {
            String newFilePath = comandlineArgResolver.getOutputPath() + getRelativePath(file, new File(comandlineArgResolver.getInputPath()));
            List<String> sourceLines = readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
            List<String> resultLines = newArrayList();
            int currentLine = 1;
            for (String line : sourceLines) {
                String copy = line;
                if (line.matches(JD_GUI_COMMENT_PATTERN)) {
                    Matcher matcher = pattern.matcher(copy);
                    if (matcher.find()) {
                        int sourceLine = Integer.parseInt(matcher.group(0));
                        while (sourceLine > currentLine) {
                            resultLines.add("");
                            currentLine++;
                        }
                    }
                }
                resultLines.add(line);
                currentLine++;
            }
            result.put(new File(newFilePath), resultLines);
        }
        return result;
    }

    public String getRelativePath(File file, File folder) {
        String filePath = file.getAbsolutePath();
        String folderPath = folder.getAbsolutePath();
        if (filePath.startsWith(folderPath)) {
            return filePath.substring(folderPath.length() + 1);
        } else {
            return null;
        }
    }
}
