package usecases;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

interface DocumentElement {
    String render();
}

class TextElement implements DocumentElement {
    private String text;

    TextElement(String text) {
        this.text = text;
    }
    void setText(String text) {
        this.text = Objects.requireNonNull(text, "text must not be null");
    }

    String getText() {
        return text;
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

    int size() {
        return documentElementList.size();
    }

    void addAllElement(List<DocumentElement> documentElementList) {
        documentElementList.addAll(documentElementList);
    }

    DocumentElement remove(int index) {
        if (index < 0 || index > documentElementList.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range, size = " + documentElementList.size());
        }
        return documentElementList.remove(index);
    }

    String render() {
        StringBuilder sb = new StringBuilder(("\n\n")).append(title);
        for (DocumentElement documentElement: documentElementList) {
            sb.append("\n").append(documentElement.render()).append(" ");
        }
        return sb.toString();
    }

    void insert(int index, DocumentElement element) {
        Objects.requireNonNull(element, "element must not be null");
        checkIndex(index, documentElementList.size());                // size() means append
        documentElementList.add(index, element);
    }


    /** Replaces text at index and returns the previous text. */
    String replaceText(int index, String newText) {
        checkIndex(index, documentElementList.size() - 1);
        DocumentElement e = documentElementList.get(index);
        if (!(e instanceof TextElement))
            throw new IllegalArgumentException("Element at " + index + " is not a text element");
        TextElement t = (TextElement) e;
        String old = t.getText();
        t.setText(newText);
        return old;
    }

    private void checkIndex(int index, int max) {
        if (index < 0 || index > max)
            throw new IndexOutOfBoundsException("Index " + index + " out of range [0," + max + "]");
    }

}

class DocumentEditor {
    private final Document document;
    private final DocumentStorage documentStorage;
    private final EditHistory history = new EditHistory();

    DocumentEditor(Document document, DocumentStorage documentStorage) {
        this.document = document;
        this.documentStorage = documentStorage;
    }

    public void addText(String text){
        int index =  document.size();
        perform(new InsertElementCommand(document, index, new TextElement(text)));
    }

    public void addImage(String imagePath, String altText) {
        int index =  document.size();
        perform(new InsertElementCommand(document, index, new ImageElement(imagePath, altText)));
    }

    void insertText(int index, String text) {
        perform(new InsertElementCommand(document, index, new TextElement(text)));
    }
    void editText(int index, String newText) {
        perform(new EditTextCommand(document, index, newText));
    }
    void remove(int index) {
        perform(new RemoveElementCommand(document, index));
    }

    public void space() {
        perform(new SpaceCommand(document, document.size()));
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

    boolean undo() {
        return history.undo();
    }

    boolean redo() {
        return history.redo();
    }

    private void perform(Command command) {
        new EditorAction(command, history).trigger();
    }

}

interface Command {
    void execute();
    void undo();
}

final class InsertElementCommand implements Command {
    private final Document document;
    private final int index;
    private final DocumentElement element;

    InsertElementCommand(Document document, Integer index, DocumentElement element) {
        this.document = document;
        this.index = index == null ? document.size() : index;
        this.element = element;
    }

    @Override public void execute() {
        document.insert(index, element);
    }
    @Override public void undo() {
        document.remove(index);
    }
}

final class RemoveElementCommand implements Command {
    private final Document document;
    private final int index;
    private DocumentElement removed;                    // captured during execute()

    RemoveElementCommand(Document document, int index) { this.document = document; this.index = index; }
    @Override public void execute() {
        removed = document.remove(index);
    }
    @Override public void undo() {
        document.insert(index, removed);
    }
}

final class EditTextCommand implements Command {
    private final Document document;
    private final int index;
    private final String newText;
    private String previousText;                        // captured during execute()

    EditTextCommand(Document document, int index, String newText) {
        this.document = document;
        this.index = index;
        this.newText = newText;
    }
    @Override public void execute() {
        previousText = document.replaceText(index, newText);
    }
    @Override public void undo() {
        document.replaceText(index, previousText);
    }
}

final class SpaceCommand implements Command {
    private final Document document;
    private final int index;

    SpaceCommand(Document document, Integer index) {
        this.document = Objects.requireNonNull(document, "document must not be null");
        this.index = index == null ? document.size() : index;
    }

    @Override
    public void execute() {
        document.insert(index, new TabSpace());
    }

    @Override
    public void undo() {
        document.remove(index);
    }
}

final class NewLineCommand implements Command {
    private final Document document;
    private final int index;

    NewLineCommand(Document document, Integer index) {
        this.document = Objects.requireNonNull(document, "document must not be null");
        this.index = index == null ? document.size() : index;
    }

    @Override
    public void execute() {
        document.insert(index, new NewLine());
    }

    @Override
    public void undo() {
        document.remove(index);
    }
}

class EditHistory {
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    void record(Command executedCommand) {
        undoStack.push(executedCommand);
        redoStack.clear();
    }

    boolean undo() {
        if (undoStack.isEmpty()) return false;
        Command c = undoStack.pop();
        c.undo();
        redoStack.push(c);
        return true;
    }

    boolean redo() {
        if (redoStack.isEmpty()) return false;
        Command c = redoStack.pop();
        c.execute();
        undoStack.push(c);
        return true;
    }
}

// Sender: gets a ready-made command from the client, never creates one
class EditorAction {
    private final Command command;
    private final EditHistory history;

    EditorAction(Command command, EditHistory history) {
        this.command = Objects.requireNonNull(command);
        this.history = Objects.requireNonNull(history);
    }

    void trigger() {
        command.execute();                              // if this throws, nothing is recorded
        history.record(command);
    }
}


class DocumentEditorDemo {
    public static void main(String[] args) throws IOException {
        Document document = new Document("A Simple Document", null);
        DocumentEditor editor = new DocumentEditor(document, new FileSystemStorage(Path.of("out")));
        editor.addText("Hello, World!");
        editor.addImage("logo.png", "Company logo");
        IO.println(editor.render());

        editor.insertText(2, "Inserted line");
        IO.println(editor.render());

        editor.undo();
        IO.println(editor.render());// image comes back
        editor.undo();
        IO.println(editor.render());// edit reverted
        editor.redo();
        IO.println(editor.render());
        //documentEditor.save();
    }
}
