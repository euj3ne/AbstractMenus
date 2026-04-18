package ru.abstractmenus.extractors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.abstractmenus.api.ValueExtractor;
import ru.abstractmenus.util.bukkit.ItemUtil;

public class ItemStackExtractor implements ValueExtractor {

    public static final ItemStackExtractor INSTANCE = new ItemStackExtractor();

    @Override
    public String extract(Object obj, String placeholder) {
        if (obj instanceof ItemStack) {
            ItemStack item = (ItemStack) obj;

            String result = switch (placeholder) {
                case "item_type" -> item.getType().toString();
                case "item_data" -> "";
                case "item_amount" -> String.valueOf(item.getAmount());
                case "item_max_stack" -> String.valueOf(item.getMaxStackSize());
                case "item_serialized" -> ItemUtil.encodeStack(item);
                default -> null;
            };

            if (result != null) {
                return result;
            }

            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                return switch (placeholder) {
                    case "item_display_name" -> {
                        Component name = meta.displayName();
                        yield name != null ? PlainTextComponentSerializer.plainText().serialize(name) : "";
                    }
                    case "item_localized_name" -> {
                        Component name = meta.displayName();
                        yield name != null ? PlainTextComponentSerializer.plainText().serialize(name) : "";
                    }
                    case "item_model" -> String.valueOf(meta.getCustomModelData()); // 1.14+
                    default -> "";
                };
            }
        }

        return "";
    }
}
