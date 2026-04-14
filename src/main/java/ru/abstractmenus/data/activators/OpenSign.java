package ru.abstractmenus.data.activators;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.abstractmenus.api.Activator;
import ru.abstractmenus.api.Handlers;
import ru.abstractmenus.api.ValueExtractor;
import ru.abstractmenus.api.text.Colors;
import ru.abstractmenus.extractors.BlockExtractor;
import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

import java.util.List;

public class OpenSign extends Activator {

    private final List<String> text;

    private OpenSign(List<String> text) {
        this.text = text;
    }

//    @EventHandler
//    public void onTableClick(PlayerInteractEvent event) {
//        if (!ActivatorUtil.checkHand(event)) return;
//
//        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
//            Block block = event.getClickedBlock();
//
//            if (block == null) return;
//
//            if (block.getType().toString().toLowerCase().contains("sign")) {
//                Sign sign = (Sign) block.getState();
//                boolean equals = true;
//
//                for (int i = 0; i < text.size(); i++) {
//                    String line = Handlers.getPlaceholderHandler().replace(event.getPlayer(), text.get(i));
//
//                    if (!line.equalsIgnoreCase(sign.getLine(i))) {
//                        equals = false;
//                    }
//                }
//
//                if (equals)
//                    openMenu(block, event.getPlayer());
//            }
//        }
//    }

    @EventHandler
    public void onTableClick(PlayerInteractEvent event) {
        if (!ActivatorUtil.checkHand(event)) {
            return;
        }

        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (!(action.equals(Action.RIGHT_CLICK_BLOCK) || !action.equals(Action.LEFT_CLICK_BLOCK))
                || block == null
                || !Tag.SIGNS.isTagged(block.getType())
        ) {
            return;
        }

        Sign sign = (Sign) block.getState();
        Player player = event.getPlayer();
        for (int i = 0; i < text.size(); i++) {
            String line = Handlers.getPlaceholderHandler().replace(player, text.get(i));
            if (!line.equalsIgnoreCase(sign.getLine(i))) {
                return;
            }
        }

        openMenu(block, player);
    }


    @Override
    public ValueExtractor getValueExtractor() {
        return BlockExtractor.INSTANCE;
    }

    public static class Serializer implements NodeSerializer<OpenSign> {

        @Override
        public OpenSign deserialize(Class type, ConfigNode node) throws NodeSerializeException {
            return new OpenSign(Colors.ofList(node.getList(String.class)));
        }

    }
}
