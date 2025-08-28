package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static hexlet.code.Differ.getDifferenceString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnitTest {
    @Test
    void getVerifiedAbsolutePathTest() throws NoSuchFileException {
        String path = "./src/test/resources/fixtures/testFile1.json";
        String actualPath = App.getVerifiedAbsolutePath(path);
        String expectedPath = "/home/sergei/JAVA/Hexlet/java-project-71/app/src/test/resources/fixtures/testFile1.json";
        assertEquals(expectedPath, actualPath);
        // Проверяем расширенный функционал
        String missingFilePath = "./src/test/resources/fixtures/testFile3.json";
        assertThrows(Exception.class, () -> App.getVerifiedAbsolutePath(missingFilePath));
    }

    @Test
    void parseJsonFileToMapTest() throws IOException {
        Map<String, Object> jsonData = Differ.parseJsonFileToMap("/home/sergei/JAVA/Hexlet/java-project-71"
                + "/app/src/test/resources/fixtures/testFile1.json");
        assertEquals(50, jsonData.get("timeout"));
        assertEquals(true, jsonData.get("verbose"));
        assertEquals(false, jsonData.get("follow"));
    }

    @Test
    void getSortedListKeysTest() {
        Map<String, Object> map1 = Map.of(
                "key1", 30,
                "key5", true,
                "key4", "value1",
                "key3", false
        );
        Map<String, Object> map2 = Map.of(
                "key6", true,
                "key1", 40,
                "key2", 50,
                "key5", false,
                "key3", false
        );

        List<String> result;
        result = Differ.getSortedListKeys(map1, map2);
        List<String> expected = new ArrayList<>(List.of("key1", "key2", "key3", "key4", "key5", "key6"));

        assertEquals(expected, result);
    }

    @Test
    void getDifferenceStringTest() throws Exception {
        Map<String, Object> map1 = Map.of(
                "key1", 30,
                "key2", "hexlet.io",
                "key3", "123.234.53.22"
        );
        Map<String, Object> map2 = Map.of(
                "key1", 40,
                "key2", "hexlet.io",
                "key4", false
        );

        String expeccted = Files.readString(Paths.get("src/test/resources/fixtures/ExpectedDifference.txt"));

        String result = getDifferenceString(map1, map2);

        assertEquals(expeccted, result);
    }

}
