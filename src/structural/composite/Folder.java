package structural.composite;

import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemItem {

    private final String name;
    private final List<FileSystemItem> items = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemItem item) {
        items.add(item);
    }

    public void remove(FileSystemItem item) {
        items.remove(item);
    }

    @Override
    public void show() {
        System.out.println("Folder: " + name);

        for (FileSystemItem item : items) {
            item.show();
        }
    }
}
