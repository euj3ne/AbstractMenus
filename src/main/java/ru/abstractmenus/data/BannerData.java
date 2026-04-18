package ru.abstractmenus.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

import java.util.ArrayList;
import java.util.List;

public class BannerData {

    private final List<Pattern> patterns;

    public BannerData(List<Pattern> patterns){
        this.patterns = patterns;
    }

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public static class Serializer implements NodeSerializer<BannerData> {

        private static final JsonParser JSON_PARSER = new JsonParser();
        private static final DyeColor[] BANNER_COLORS = {
            DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
            DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY,
            DyeColor.LIGHT_GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
            DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK
        };

        @Override
        public BannerData deserialize(Class type, ConfigNode node) throws NodeSerializeException {
            if (node.isList() || node.isMap()) {
                return new BannerData(node.getList(Pattern.class));
            } else {
                return new BannerData(parseFromJson(node, node.getString()));
            }
        }

        private List<Pattern> parseFromJson(ConfigNode node, String json) throws NodeSerializeException {
            List<Pattern> patterns = new ArrayList<>();
            JsonObject root = JSON_PARSER.parse(json).getAsJsonObject();
            JsonArray patternsArray = root.get("BlockEntityTag").getAsJsonObject()
                    .get("Patterns").getAsJsonArray();

            for (JsonElement element : patternsArray){
                JsonObject object = element.getAsJsonObject();
                String pattern = object.get("Pattern").getAsString();
                byte colorByte = object.get("Color").getAsByte();

                @SuppressWarnings("deprecation")
                PatternType patternType = Registry.BANNER_PATTERN.get(NamespacedKey.minecraft(pattern));
                DyeColor color = colorByte >= 0 && colorByte < BANNER_COLORS.length ? BANNER_COLORS[colorByte] : null;

                if (patternType == null) throw new NodeSerializeException(node, "Cannot serialize banner pattern from JSON");
                if (color == null) throw new NodeSerializeException(node, "Cannot serialize banner color from JSON");

                patterns.add(new Pattern(color, patternType));
            }

            return patterns;
        }
    }
}
