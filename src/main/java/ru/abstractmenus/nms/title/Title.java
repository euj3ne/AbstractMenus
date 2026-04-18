package ru.abstractmenus.nms.title;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title.Times;
import org.bukkit.entity.Player;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
public class Title {

    private String title;
    private String subtitle;
    private int fadeIn;
    private int fadeOut;
    private int stay;

    public void send(Player player) {
        player.showTitle(net.kyori.adventure.title.Title.title(
                LegacyComponentSerializer.legacySection().deserialize(title),
                LegacyComponentSerializer.legacySection().deserialize(subtitle),
                Times.times(
                        Duration.ofMillis(fadeIn * 50L),
                        Duration.ofMillis(stay * 50L),
                        Duration.ofMillis(fadeOut * 50L)
                )
        ));
    }
}
