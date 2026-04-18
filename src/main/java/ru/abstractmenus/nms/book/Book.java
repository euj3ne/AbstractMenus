package ru.abstractmenus.nms.book;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import ru.abstractmenus.data.BookData;

import java.util.List;
import java.util.stream.Collectors;

public class Book {

    private final BookData bookData;

    public Book(BookData bookData) {
        this.bookData = bookData;
    }

    public void open(Player player) {
        player.openBook(createItem());
    }

    private ItemStack createItem() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) item.getItemMeta();

        if (meta != null) {
            meta.setAuthor(bookData.getAuthor());
            meta.setTitle(bookData.getTitle());
            List<Component> pages = bookData.getPages().stream()
                    .map(p -> LegacyComponentSerializer.legacySection().deserialize(p))
                    .collect(Collectors.toList());
            meta.pages(pages);
            item.setItemMeta(meta);
        }

        return item;
    }

    public static Book create(BookData bookData) {
        return new Book(bookData);
    }
}
