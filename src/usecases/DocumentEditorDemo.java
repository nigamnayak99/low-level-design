package usecases;


import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

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

    DocumentEditor(Document document) {
        this.document = document;
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

}


class DocumentEditorDemo {
    public static void main(String[] args) {
        Document document = new Document("A Simple Document", null);
        DocumentEditor documentEditor = new DocumentEditor(document);
        documentEditor.addImage("C://Nigam/Home/Nigam_nayak.png", "Nigam's Photo");
        documentEditor.newLine();
        documentEditor.addText("Nigam");
        documentEditor.space();
        documentEditor.addText("Nayak");
        IO.println(documentEditor.render());
    }
}