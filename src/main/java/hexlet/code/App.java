package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import javax.annotation.processing.Generated;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.",
        version = "gendiff 1.0")
public class App implements Callable<Integer> {
    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private String filePath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public final Integer call() throws Exception {

        // Получаем абсолютные пути к файлам и сразу проверяем их существование
        String absoluteFilePath1 = getVerifiedAbsolutePath(filePath1);
        String absoluteFilePath2 = getVerifiedAbsolutePath(filePath2);

        // Вызываем метод возвращающий различия между данными в файлах в виде строки
        String jsonDiffReport = Differ.generate(absoluteFilePath1, absoluteFilePath2);

        // Выводим результат в консоль
        consolePrint(jsonDiffReport);

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    // Метод для получения верифицированного абсолютного пути к файлу
    static String getVerifiedAbsolutePath(String pathString) throws NoSuchFileException {
        Path path = Paths.get(pathString).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new NoSuchFileException("File '" + path + "' does not exist");
        }
        return path.toString();
    }

    // Специальный метод для вывода в консоль, игнорирующий Sonar правила
    @SuppressWarnings("java:S106")
    @Generated("jacoco-exclude") // JaCoCo игнорирует аннотированные методы
    static void consolePrint(String message) {
        System.out.println(message);
    }
}
