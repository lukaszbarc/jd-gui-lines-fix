package pl.lbarc.linesfix.args;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

/**
 * User: LBARC
 * Date: 10.07.13
 * Time: 08:44
 */
public class ComandlineArgResolver {

    private String inputPath;
    private String outputPath;

    private Options options;

    public void initialize(String[] args) {
        options = new Options();
        boolean isArgsValid;
        try {

            options.addOption("out", true, "output root directory");
            options.addOption("src", true, "sources root directory");
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);
            inputPath = cmd.getOptionValue("src");
            outputPath = cmd.getOptionValue("out");
            isArgsValid = StringUtils.isNotBlank(inputPath) && StringUtils.isNotBlank(outputPath);
        } catch (ParseException e) {
            isArgsValid = false;
        }
        if (!isArgsValid) {
            throw new IllegalArgumentException();
        }
    }

    public String getInputPath() {
        return inputPath + "/";
    }

    public String getOutputPath() {
        return outputPath + "/";
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar jd-gui-lines-fix-0.0.1-SNAPSHOT-jar-with-dependencies.jar", options);
    }
}
