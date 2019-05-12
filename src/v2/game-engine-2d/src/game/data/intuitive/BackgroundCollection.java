package game.data.intuitive;

import game.data.LevelData;
import game.data.globals;
import game.objects.Background;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class BackgroundCollection extends DataCollection<Background, BackgroundData>{

    public BackgroundCollection(Document doc, Element root) {
        super(doc, root);
    }

    public BackgroundCollection(Document doc, List<BackgroundData> elements) {
        super(doc, elements);
    }

    public BackgroundCollection(List<Background> backgrounds, Document doc) {
        super(backgrounds, doc);
    }

    @Override
    public String nodeName() {
        return globals.TAGS.BACKGROUND_PL;
    }

    @Override
    public String childNodeName() {
        return globals.TAGS.BACKGROUND;
    }

    @Override
    public BackgroundData generate(Node node) {
        return new BackgroundData(this.doc, (Element) node);
    }

    @Override
    public BackgroundData generate(Background gameObject) {
        return new BackgroundData(this.doc, gameObject);
    }

}
