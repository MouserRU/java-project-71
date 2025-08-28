package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class Differ {
    private Differ() {
    }

    // Метод возвращающий разницу между данными
    static String generate(String filePath1, String filePath2) throws IOException {

        // Получаем набор данных из первого файла
        Map<String, Object> map1;
        map1 = parseJsonFileToMap(filePath1);

        // Получаем набор данных из второго файла
        Map<String, Object> map2;
        map2 = parseJsonFileToMap(filePath2);

        // Возвращаем строку с результатом сравнения карт
        return getDifferenceString(map1, map2);

    }

    // Метод парсинга данных из json файла в структуру типа Map
    static Map<String, Object> parseJsonFileToMap(String filePath) throws IOException {
        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<>() { });
    }

    // Метод получения сортированного списка ключей из двух наборов данных ключ-значение
    static List<String> getSortedListKeys(Map<String, Object> map1, Map<String, Object> map2) {
        // Создаём список уникальных ключей
        List<String> allKeys = new ArrayList<>(map1.keySet());
        for (String key : map2.keySet()) {
            if (!allKeys.contains(key)) {
                allKeys.add(key);
            }
        }

        // Сортируем список
        allKeys.sort(Comparator.naturalOrder());

        return allKeys;
    }

    // Метод возвращает строку с разницей
    static String getDifferenceString(Map<String, Object> map1, Map<String, Object> map2) {
        // Создаём список уникальных ключей из наборов данных
        List<String> allKeys;
        allKeys = getSortedListKeys(map1, map2);

        // Создаём строку с "diff"
        StringBuilder diff = new StringBuilder("{\n");
        for (String key : allKeys) {
            Object value1 = map1.get(key);
            Object value2 = map2.get(key);
            diff.append("\t");
            if (Objects.equals(value1, value2)) {
                diff.append("  ").append(key).append(": ").append(value1.toString()).append("\n");
            }
            if (value1 == null) {
                diff.append("+ ").append(key).append(": ").append(value2).append("\n");
            }
            if (value2 == null) {
                diff.append("- ").append(key).append(": ").append(value1).append("\n");
            }
            if ((value1 != null && value2 != null) && !value1.equals(value2)) {
                diff.append("- ").append(key).append(": ").append(value1).append("\n")
                        .append("\t+ ").append(key).append(": ").append(value2).append("\n");
            }
        }
        diff.append("}");
        return diff.toString();
    }

}
