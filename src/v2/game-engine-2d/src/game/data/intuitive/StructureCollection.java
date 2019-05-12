package game.data.intuitive;

import game.objects.Structure;
import game.data.LevelData;
import game.data.globals;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class StructureCollection extends DataCollection<Structure, StructureData> {

    public StructureCollection(Document doc, Element root) {
        super(doc, root);
    }

    public StructureCollection(Document doc, List<StructureData> elements) {
        super(doc, elements);
    }

    public StructureCollection(List<Structure> structures, Document doc) {
        super(structures, doc);
    }

    @Override
    public String nodeName() {
        return globals.TAGS.STRUCTURE_PL;
    }

    @Override
    public String childNodeName() {
        return globals.TAGS.STRUCTURE;
    }

    @Override
    public StructureData generate(Node node) {
        return new StructureData(this.doc, (Element) node);
    }

    @Override
    public StructureData generate(Structure gameObject) {
        return new StructureData(this.doc, gameObject);
    }

}
