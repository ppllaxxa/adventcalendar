package advent2024.day11;

import java.util.HashMap;
import java.util.Map;

public class Ideone {
    public static void main (String[] args) throws Exception {
        String input = "";

        String[] split = input.split(" ");

        Map<String, Long> result = new HashMap<>();

        for (int i = 0; i < split.length; i++) {
            updateMap(result, split[i], 1L);
        }

        for (int i = 0; i < 75; i++) {
            Map<String, Long> current = new HashMap<>();

            for (Map.Entry<String, Long> e : result.entrySet()) {
                if ("0".equals(e.getKey())) {
                    updateMap(current, "1", e.getValue());
                } else if (e.getKey().length() % 2 == 0) {
                    int mid = e.getKey().length() / 2;
                    updateMap(current, e.getKey().substring(0, mid), e.getValue());
                    updateMap(current, "" + Long.parseLong(e.getKey().substring(mid)), e.getValue());
                } else {
                    updateMap(current, "" + (Long.parseLong(e.getKey()) * 2024), e.getValue());
                }
            }

            result = current;
        }

        long total = 0;

        for (Long l : result.values()) {
            total += l;
        }

        System.out.println(total);
    }

    private static void updateMap(Map<String, Long> map, String key, Long value) {
        map.compute(key, (k, v) -> v == null ? value : (v + value));
    }
}
