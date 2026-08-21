package structural.composite;

/**
 * Demonstrates the Composite Design Pattern using a file-system hierarchy.
 *
 * <p>The pattern allows individual objects (Leaf) and groups of objects
 * (Composite) to be treated uniformly through a common interface.
 *
 * <p>In this example:
 * <ul>
 *     <li>{@code FileSystemItem} - Component</li>
 *     <li>{@code File} - Leaf</li>
 *     <li>{@code Folder} - Composite</li>
 * </ul>
 *
 * @return void
 */

public class Main {

    void main() {
        File file1 = new File("resume.pdf");
        File file2 = new File("photo.jpg");

        Folder documents = new Folder("Documents");

        documents.add(file1);
        documents.add(file2);

        Folder root = new Folder("Root");
        root.add(documents);

        root.show();
    }
}
