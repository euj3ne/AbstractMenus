package ru.abstractmenus.bench;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Точка входа для запуска JMH бенчмарков.
 *
 * Запуск всех:
 *   ./gradlew jmh
 *
 * Запуск одного:
 *   ./gradlew jmh -Pbench=PlaceholderReplace
 *
 * Доступные бенчмарки:
 * - PlaceholderReplaceBenchmark — String.replace() в цикле vs StringBuilder
 * - StringSplitBenchmark — split() vs indexOf для парсинга placeholder
 * - SlotContainsBenchmark — HashSet аллокация vs прямая итерация
 * - ItemBuildBenchmark — deep clone LinkedHashMap vs direct access
 * - ConcurrencyBenchmark — HashMap vs ConcurrentHashMap при разных нагрузках
 */
public class RunBenchmarks {

    public static void main(String[] args) throws RunnerException {
        String include = args.length > 0 ? args[0] : "ru.abstractmenus.bench.*";

        Options opt = new OptionsBuilder()
                .include(include)
                .build();

        new Runner(opt).run();
    }
}
