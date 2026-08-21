package structural.composite;

public class File implements FileSystemItem {
    public String name;

    @Override
    public void show() {
        System.out.println("File: "+this.name);
    }

    public File(String name) {
        this.name = name;
    }
}
