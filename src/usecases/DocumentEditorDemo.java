package usecases;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

interface DocumentElement {
    String render();
}

class TextElement implements DocumentElement {
    private final String text;

    TextElement(String text) {
        this.text = text;
    }

    @Override
    public String render() {
        return text == null ? "" : text;
    }
}

class ImageElement implements DocumentElement{
    private final String imgPath;

    private final String altText;

    ImageElement(String path, String altText) {
        this.imgPath = path;
        this.altText = altText;
    }

    @Override
    public String render() {
        return imgPath == null ? altText : imgPath;
    }
}

class TabSpace implements DocumentElement {

    @Override
    public String render() {
        return "\t";
    }
}

class NewLine implements DocumentElement {

    @Override
    public String render() {
        return "\n";
    }
}

interface DocumentStorage {
    String save(String fileName, String content) throws IOException;
}

class FileSystemStorage implements DocumentStorage {

    private final Path directory;

    FileSystemStorage(Path directory) {
        this.directory = Objects.requireNonNull(directory);
    }

    @Override
    public String save(String fileName, String content) throws IOException {
        Files.createDirectories(directory);
        return Files.writeString(directory.resolve(fileName), content).toString();
    }
}

class InMemoryStorage implements DocumentStorage {
    private final Map<String, String> store = new HashMap<>();

    @Override public String save(String name, String content) {
        store.put(name, content); return "memory:" + name;
    }
    String read(String name) {
        return store.get(name);
    }
}

class Document {
    private final String title;
    private final List<DocumentElement> documentElementList;

    Document(String title, List<DocumentElement> documentElementList) {
        this.title = title;
        this.documentElementList = documentElementList == null ? new ArrayList<>() : documentElementList;
    }

    String getTitle() {
        return title;
    }

    void addElement(DocumentElement documentElement) {
        documentElementList.add(documentElement);
    }

    void addAllElement(List<DocumentElement> documentElementList) {
        documentElementList.addAll(documentElementList);
    }

    void remove(int index) {
        if (index < 0 || index > documentElementList.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range, size = " + documentElementList.size());
        }
    }

    String render() {
        StringBuilder sb = new StringBuilder(title).append("\n\n");
        for (DocumentElement documentElement: documentElementList) {
            sb.append(documentElement.render()).append(" ");
        }
        return sb.toString();
    }
}

class DocumentEditor {
    private final Document document;
    private final DocumentStorage documentStorage;

    DocumentEditor(Document document, DocumentStorage documentStorage) {
        this.document = document;
        this.documentStorage = documentStorage;
    }

    public void addText(String text){
        this.document.addElement(new TextElement(text));
    }

    public void addImage(String imagePath, String altText) {
        document.addElement(new ImageElement(imagePath, altText));
    }

    public void space() {
        this.document.addElement(new TabSpace());
    }

    public void newLine() {
        this.document.addElement(new NewLine());
    }

    public String render() {
        return document.render();
    }

    public String save() throws IOException {
        String name = document.getTitle().replaceAll("\\s+", "_") + ".txt";
        return documentStorage.save(name, document.render());
    }

}


class DocumentEditorDemo {
    public static void main(String[] args) throws IOException {
        Document document = new Document("A Simple Document", null);
        DocumentEditor documentEditor = new DocumentEditor(document, new FileSystemStorage(Path.of("out")));
        documentEditor.addImage("C://Nigam/Home/Nigam_nayak.png", "Nigam's Photo");
        documentEditor.newLine();
        documentEditor.addText("Nigam");
        documentEditor.space();
        documentEditor.addText("Nayak");
        IO.println(documentEditor.render());
        documentEditor.save();
    }
}