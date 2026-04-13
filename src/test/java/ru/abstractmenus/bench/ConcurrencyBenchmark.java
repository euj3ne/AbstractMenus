package ru.abstractmenus.bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Бенчмарк: HashMap vs ConcurrentHashMap для хранилищ меню и профилей.
 *
 * Текущие проблемы:
 * - MenuManager.menus = HashMap (не потокобезопасный, read/write из разных потоков)
 * - ProfileStorage.profiles = ConcurrentHashMap (потокобезопасный, но без cleanup)
 * - MenuManager.openedMenus = ConcurrentHashMap (итерируется каждый тик)
 *
 * Бенчмарк показывает стоимость разных стратегий доступа к Map
 * при характерных для плагина нагрузках.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class ConcurrencyBenchmark {

    private HashMap<UUID, String> hashMap;
    private ConcurrentHashMap<UUID, String> concurrentMap;
    private UUID[] keys;

    @Param({"10", "50", "100"})
    private int playerCount;

    @Setup
    public void setup() {
        hashMap = new HashMap<>();
        concurrentMap = new ConcurrentHashMap<>();
        keys = new UUID[playerCount];

        for (int i = 0; i < playerCount; i++) {
            UUID uuid = UUID.randomUUID();
            keys[i] = uuid;
            hashMap.put(uuid, "menu_" + i);
            concurrentMap.put(uuid, "menu_" + i);
        }
    }

    // === Итерация по всем записям (UpdateTask pattern) ===

    @Benchmark
    public void hashMap_iterate(Blackhole bh) {
        for (Map.Entry<UUID, String> entry : hashMap.entrySet()) {
            bh.consume(entry.getKey());
            bh.consume(entry.getValue());
        }
    }

    @Benchmark
    public void concurrentMap_iterate(Blackhole bh) {
        for (Map.Entry<UUID, String> entry : concurrentMap.entrySet()) {
            bh.consume(entry.getKey());
            bh.consume(entry.getValue());
        }
    }

    // === Lookup по ключу (getMenu / getProfile pattern) ===

    @Benchmark
    public void hashMap_lookup(Blackhole bh) {
        bh.consume(hashMap.get(keys[0]));
    }

    @Benchmark
    public void concurrentMap_lookup(Blackhole bh) {
        bh.consume(concurrentMap.get(keys[0]));
    }

    // === Put + remove цикл (join/quit pattern) ===

    @Benchmark
    public void hashMap_putRemove(Blackhole bh) {
        UUID uuid = UUID.randomUUID();
        hashMap.put(uuid, "test");
        bh.consume(hashMap.remove(uuid));
    }

    @Benchmark
    public void concurrentMap_putRemove(Blackhole bh) {
        UUID uuid = UUID.randomUUID();
        concurrentMap.put(uuid, "test");
        bh.consume(concurrentMap.remove(uuid));
    }
}
