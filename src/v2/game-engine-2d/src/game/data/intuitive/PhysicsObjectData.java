package game.data.intuitive;

import game.data.globals.TAGS;
import game.globals;
import jbox2d.dynamics.BodyType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import game.physics.Hitbox;
import game.physics.PhysicsObject;

public class PhysicsObjectData {
    
    public final BodyType type;
    public final Hitbox hitbox;
    public final short MASK;
    public float[] location_meters;
    public float[] dimensions_meters;
    public final static String tagName = "physics_object";
    
    public PhysicsObjectData(BodyType type, Hitbox hitbox, short MASK, float[] location_meters, float[] dimensions_meters) {
        this.type = type;
        this.hitbox = hitbox;
        this.MASK = MASK;
        this.location_meters = location_meters;
        this.dimensions_meters = dimensions_meters;
    }
    
    public PhysicsObjectData(Element physics_element) {
        String type_T = physics_element.getElementsByTagName(TAGS.type).item(0).getTextContent();
        
        Element hitbox_T = (Element) physics_element.getElementsByTagName(TAGS.hitbox).item(0);
        Element hitbox_location_element = (Element) hitbox_T.getElementsByTagName(TAGS.location).item(0);
        Element hitbox_dimensions_element = (Element) hitbox_T.getElementsByTagName(TAGS.dimensions).item(0);
        
        String MASK_T = physics_element.getElementsByTagName(TAGS.MASK).item(0).getTextContent();
        
        Element location_T = (Element) physics_element.getElementsByTagName(TAGS.location).item(0);
        
        Element dimensions_T = (Element) physics_element.getElementsByTagName(TAGS.dimensions).item(0);
        
        this.type = BodyType.valueOf(type_T);
        
        float[] hitbox_location = GameObjectData.parseFloatValues(hitbox_location_element, TAGS.xy);
        float[] hitbox_dimensions = GameObjectData.parseFloatValues(hitbox_dimensions_element, TAGS.wh);
        
        this.hitbox = new Hitbox(hitbox_location, hitbox_dimensions);
        
        this.MASK = globals.MASK.getValue(MASK_T);
        
        this.location_meters = GameObjectData.parseFloatValues(location_T, TAGS.xy);
        this.dimensions_meters = GameObjectData.parseFloatValues(dimensions_T, TAGS.wh);
        
    }
    
    public Element save(Document doc) {
        Element root = doc.createElement(tagName);

        // Body Type
        Element type_element = doc.createElement(TAGS.type);
        type_element.appendChild(doc.createTextNode(this.type.toString()));
        root.appendChild(type_element);

        // Mask
        Element mask_element = doc.createElement(TAGS.MASK);
        mask_element.appendChild(doc.createTextNode(globals.MASK.getKey(this.MASK)));
        root.appendChild(mask_element);

        // Location
        Element location_element = doc.createElement(TAGS.location);
        location_element = GameObjectData.parseFloatWithTags(doc, location_element, TAGS.xy, this.location_meters);
        root.appendChild(location_element);

        // Dimensions
        Element dimensions_element = doc.createElement(TAGS.dimensions);
        dimensions_element = GameObjectData.parseFloatWithTags(doc, dimensions_element, TAGS.wh, this.dimensions_meters);
        root.appendChild(dimensions_element);

        // Hitbox
        Element hitbox_element = doc.createElement(TAGS.hitbox);
        Element hitbox_location = doc.createElement(TAGS.location);
        hitbox_location = GameObjectData.parseFloatWithTags(doc, hitbox_location, TAGS.xy, hitbox.location());
        Element hitbox_dimensions = doc.createElement(TAGS.dimensions);
        hitbox_dimensions = GameObjectData.parseFloatWithTags(doc, hitbox_dimensions, TAGS.wh, hitbox.dimensions());
        hitbox_element.appendChild(hitbox_location);
        hitbox_element.appendChild(hitbox_dimensions);
        root.appendChild(hitbox_element);
        
        return root;
        
    }
    
    public static PhysicsObjectData convertFromGame(PhysicsObject physicsObject) {
        BodyType type = physicsObject.type;
        Hitbox hitbox = physicsObject.hitbox;
        final short MASK = physicsObject.MASK;
        float[] location_meters = physicsObject.getLocationMeters();
        float[] dimensions_meters = physicsObject.getDimensionsMeters();
        
        return new PhysicsObjectData(type, hitbox, MASK, location_meters, dimensions_meters);
    }
    
    public PhysicsObject generate() {
        return new PhysicsObject(type, 0.0f, MASK, location_meters, dimensions_meters);
        
    }
    
}
