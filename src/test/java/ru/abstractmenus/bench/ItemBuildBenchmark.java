package ru.abstractmenus.bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Бенчмарк: имитация паттерна клонирования и построения предметов.
 *
 * Текущая проблема в SimpleMenu.placeItems():
 * 1. Item.clone() — deep copy LinkedHashMap props (КАЖДЫЙ предмет)
 * 2. Item.build() — new ItemStack + apply ALL properties
 * 3. applyProperties() — getItemMeta()/setItemMeta() клонирует meta 2x на свойство
 *
 * На 54 слота × 10 свойств × 20 TPS = 21,600 клонов meta/сек.
 *
 * Этот бенчмарк замеряет стоимость clone() + map copy,
 * чтобы показать выигрыш от кеширования.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class ItemBuildBenchmark {

    // Имитация свойств предмета (LinkedHashMap как в SimpleItem)
    private LinkedHashMap<String, Object> materialProps;
    private LinkedHashMap<String, Object> simpleProps;
    private LinkedHashMap<String, Object> allProps;

    @Setup
    public void setup() {
        materialProps = new LinkedHashMap<>();
        materialProps.put("material", "DIAMOND_SWORD");

        simpleProps = new LinkedHashMap<>();
        simpleProps.put("name", "&6Super Sword %player_name%");
        simpleProps.put("lore", Arrays.asList("&7Line 1", "&7Line 2 %player_level%", "&7Line 3"));
        simpleProps.put("enchant", "DAMAGE_ALL:5");
        simpleProps.put("flags", Arrays.asList("HIDE_ENCHANTS", "HIDE_ATTRIBUTES"));
        simpleProps.put("amount", 1);
        simpleProps.put("model", 12345);
        simpleProps.put("unbreakable", true);
        simpleProps.put("color", "255,0,0");
        simpleProps.put("customTag", "some_value");

        allProps = new LinkedHashMap<>();
        allProps.putAll(materialProps);
        allProps.putAll(simpleProps);
    }

    // === Текущий паттерн: deep clone каждый раз ===

    @Benchmark
    public void clone_deepCopy(Blackhole bh) {
        // Имитация SimpleItem.clone() — создаёт новые LinkedHashMap
        LinkedHashMap<String, Object> clonedAll = new LinkedHashMap<>(allProps);
        LinkedHashMap<String, Object> clonedMat = new LinkedHashMap<>(materialProps);
        LinkedHashMap<String, Object> clonedSimple = new LinkedHashMap<>(simpleProps);
        bh.consume(clonedAll);
        bh.consume(clonedMat);
        bh.consume(clonedSimple);
    }

    // === Оптимизированный: без клонирования (read-only доступ) ===

    @Benchmark
    public void noClone_directAccess(Blackhole bh) {
        // Если свойства immutable, клонирование не нужно
        bh.consume(allProps);
        bh.consume(materialProps);
        bh.consume(simpleProps);
    }

    // === Полный цикл: 54 предмета с clone (текущий) ===

    @Benchmark
    public void fullMenu_withClone(Blackhole bh) {
        for (int i = 0; i < 54; i++) {
            LinkedHashMap<String, Object> clonedAll = new LinkedHashMap<>(allProps);
            LinkedHashMap<String, Object> clonedMat = new LinkedHashMap<>(materialProps);
            LinkedHashMap<String, Object> clonedSimple = new LinkedHashMap<>(simpleProps);
            // Имитация build: итерация по свойствам
            for (Map.Entry<String, Object> entry : clonedAll.entrySet()) {
                bh.consume(entry.getValue());
            }
        }
    }

    // === Полный цикл: 54 предмета без clone (оптимизированный) ===

    @Benchmark
    public void fullMenu_noClone(Blackhole bh) {
        for (int i = 0; i < 54; i++) {
            // Прямой доступ — без deep copy
            for (Map.Entry<String, Object> entry : allProps.entrySet()) {
                bh.consume(entry.getValue());
            }
        }
    }
}
