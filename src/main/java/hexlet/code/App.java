package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.",
        version = "gendiff 1.0")
public class App {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }