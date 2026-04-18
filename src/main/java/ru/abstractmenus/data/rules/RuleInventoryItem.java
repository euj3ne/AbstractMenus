package ru.abstractmenus.data.rules;

import ru.abstractmenus.api.inventory.ItemProperty;
import ru.abstractmenus.api.inventory.Slot;
import ru.abstractmenus.data.properties.PropItemsAdder;
import ru.abstractmenus.data.properties.PropMmoItem;
import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.abstractmenus.api.inventory.Menu;
import ru.abstractmenus.api.inventory.Item;
import ru.abstractmenus.api.Rule;
import ru.abstractmenus.api.Logger;
import ru.abstractmenus.menu.item.InventoryItem;
import ru.abstractmenus.util.SlotUtil;
import ru.abstractmenus.util.bukkit.ItemUtil;

import java.util.List;

public class RuleInventoryItem implements Rule {

    private final List<Item> items;

    private RuleInventoryItem(List<Item> items){
        this.items = items;
    }

    @Override
    public boolean check(Player player, Menu menu, Item clickedItem) {
        for(Item item : items) {
            if (item instanceof InventoryItem) {
                try {
                    ItemStack built = item.build(player, menu);
                    Slot slot = ((InventoryItem)item).getSlot(player, menu);

                    for (int index : SlotUtil.collect(slot)) {
                        ItemStack inventoryItem = player.getInventory().getItem(index);
                        if (!matchesItem(inventoryItem, item, built, player)) return false;
                    }
                } catch (Exception e){
                    Logger.severe("Cannot check item in player inventory: " + e.getMessage());
                }
            } else {
                try {
                    ItemStack built = item.build(player, menu);
                    if (!hasEnough(player, item, built, player)) return false;
                } catch (Exception e){
                    Logger.severe("Cannot check item in player inventory: " + e.getMessage());
                }
            }
        }

        return true;
    }

    private boolean matchesItem(ItemStack playerItem, Item item, ItemStack built, Player player) {
        ItemProperty prop = singleProp(item);
        if (prop instanceof PropItemsAdder) return ((PropItemsAdder) prop).matches(playerItem, player);
        if (prop instanceof PropMmoItem) return ((PropMmoItem) prop).matches(playerItem, player);
        return ItemUtil.isSimilar(built, playerItem);
    }

    private boolean hasEnough(Player player, Item item, ItemStack built, Player p) {
        ItemProperty prop = singleProp(item);
        if (prop instanceof PropItemsAdder || prop instanceof PropMmoItem) {
            int count = 0;
            for (ItemStack invItem : player.getInventory().getContents()) {
                if (invItem != null && matchesItem(invItem, item, built, p)) count += invItem.getAmount();
            }
            return count >= built.getAmount();
        }
        return player.getInventory().containsAtLeast(built, built.getAmount());
    }

    private ItemProperty singleProp(Item item) {
        java.util.Map<String, ItemProperty> props = item.getProperties();
        if (props.size() == 1) return props.values().iterator().next();
        return null;
    }

    public static class Serializer implements NodeSerializer<RuleInventoryItem> {

        @Override
        public RuleInventoryItem deserialize(Class type, ConfigNode node) throws NodeSerializeException {
            return new RuleInventoryItem(node.getList(Item.class));
        }

    }
}
