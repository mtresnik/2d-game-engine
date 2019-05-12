package game;

import game.objects.Player;
import game.objects.Structure;
import game.objects.Entity;
import game.objects.Background;
import game.objects.GameObject;
import util.Updatable;
import game.data.LevelData;
import game.data.intuitive.BackgroundData;
import game.data.intuitive.EntityData;
import game.data.intuitive.StructureData;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import game.physics.PhysicsObject;
import game.render.Renderable;

public class Level implements Renderable, Updatable {

    public List<Background> backgroundList;
    public List<Structure> structureList;
    public List<Entity> entityList;
    public Player player;

    public Level() {
        backgroundList = new ArrayList();
        structureList = new ArrayList();
        entityList = new ArrayList();
    }

    // Converts Level object --> fileLocation.xml
    public void save(String fileLocation) {
        // generate MapData object
        LevelData ld = LevelData.generate(this);
        ld.save(fileLocation);
    }

    // Converts fileLocation.xml --> Level object
    public static Level load(String fileLocation) {
        LevelData ld = LevelData.load(fileLocation);
        return generate(ld);
    }

    public static Level generate(LevelData data) {
        Level retLevel = new Level();
        for (StructureData strel : data.structure_collection.elements) {
            PhysicsObject strelPhys = PhysicsObject.staticObject(globals.MASK_STRUCTURE, strel.physicsObjectData.location_meters, strel.physicsObjectData.dimensions_meters);
            Structure struct = new Structure(strel.name, strelPhys, strel.renderObjectData.file_location);
            retLevel.structureList.add(struct);
        }
        for (BackgroundData bel : data.background_collection.elements) {
            String backrground_name = (bel.name == null ? "background_" + retLevel.structureList.size() : bel.name);
            PhysicsObject belPhys = PhysicsObject.staticObject(globals.MASK_BACKGROUND, bel.physicsObjectData.location_meters, bel.physicsObjectData.dimensions_meters);
            Background back = new Background(backrground_name, belPhys, bel.renderObjectData.file_location);
            retLevel.backgroundList.add(back);
        }
        for (EntityData eel : data.entity_data.elements) {
            String entity_name = (eel.name == null ? "entity_" + retLevel.entityList.size() : eel.name);
            PhysicsObject entityPhys = PhysicsObject.dynamicPhysicsObject(globals.MASK_ENTITY, eel.physicsObjectData.hitbox, eel.physicsObjectData.location_meters, eel.physicsObjectData.dimensions_meters);
            Entity ent = new Entity(entity_name, entityPhys, Player.PLAYER_SPEED, eel.renderObjectData.file_location);
            retLevel.entityList.add(ent);
        }

        if (data.player_data != null) {
            String player_name = (data.player_data.name == null ? "player_1" : data.player_data.name);
            PhysicsObject playerPhys = PhysicsObject.dynamicPhysicsObject(globals.MASK_PLAYER, data.player_data.physicsObjectData.hitbox, data.player_data.physicsObjectData.location_meters, data.player_data.physicsObjectData.dimensions_meters);
            retLevel.player = new Player(player_name, playerPhys, data.player_data.renderObjectData.file_location);
        }
        return retLevel;
    }

    @Override
    public String toString() {
        return "Level{" + "backgroundList=" + backgroundList + ", structureList=" + structureList + ", entityList=" + entityList + ", player=" + player + '}';
    }

    @Override
    public void render() {
        for (Background b : backgroundList) {
            b.render();
        }
        // create a list of structures, entities, and the player and sort them from least hitbox to greatest hitbox. draw based on order
        List<GameObject> renderList = new ArrayList();
        renderList.addAll(structureList);
        renderList.addAll(entityList);
        if (player != null) {
            renderList.add(player);
        }
        // Z- Filtering based on Hitbox
        renderList.sort(new Comparator<GameObject>() {
            @Override
            public int compare(GameObject t, GameObject t1) {
                return Float.compare(t.physicsObject.hitboxBody.getPosition().y, t1.physicsObject.hitboxBody.getPosition().y);
            }
        });
        for (GameObject obj : renderList) {
            obj.render();
        }
    }

    @Override
    public void update() {
        for (Background b : backgroundList) {
            b.update();
        }
        for (Structure s : structureList) {
            s.update();
        }
        for (Entity e : entityList) {
            e.update();
        }
        if (player != null) {
            player.update();
        }
    }

    public void animationFrame() {
        for (Background b : backgroundList) {
            b.renderObject.advance();
        }
        for (Structure s : structureList) {
            s.renderObject.advance();
        }
        for (Entity e : entityList) {
            e.renderObject.advance();
        }
        if (player != null) {
            player.renderObject.advance();
        }
    }

    public void updateValuesModify(LevelData data) {
        // <editor-fold desc="player">
        if (data.player_data != null) {
            PhysicsObject playerPhys = PhysicsObject.dynamicPhysicsObject(globals.MASK_PLAYER, data.player_data.physicsObjectData.hitbox, data.player_data.physicsObjectData.location_meters, data.player_data.physicsObjectData.dimensions_meters);
            this.player = new Player(data.player_data.name, playerPhys, data.player_data.renderObjectData.file_location);
            // System.out.println("playerUPDATE:" + this.player);
        }
        if (this.player != null) {
            Game.player.initPhysicsObject();
        }
        // </editor-fold>

        // TODO
        // <editor-fold desc="entities">
        if (data.entity_data != null) {
            List<Entity> toReplaceList = new ArrayList();
            System.out.println("entity_list_0:" + this.entityList);
            List<Entity> newEntityList = new ArrayList(this.entityList);
            for (EntityData elem : data.entity_data.elements) {
                if (elem.replacement != null) {
                    for (Entity s : this.entityList) {
                        if (elem.replacement.equals(s.name)) {
                            toReplaceList.add(s);
                        }
                    }
                } else {
                    PhysicsObject entityPhys = PhysicsObject.dynamicPhysicsObject(globals.MASK_ENTITY, elem.physicsObjectData.hitbox, elem.physicsObjectData.location_meters, elem.physicsObjectData.dimensions_meters);
                    Entity tempEntity = new Entity(elem.name, entityPhys, Player.PLAYER_SPEED, elem.renderObjectData.file_location);
                    newEntityList.add(tempEntity);
                }
            }
            for (Entity s : toReplaceList) {
                newEntityList.remove(s);
            }
            for (EntityData elem : data.entity_data.elements) {
                if (elem.replacement != null) {
                    for (Entity e : this.entityList) {
                        if (elem.replacement.equals(e.name)) {
//                            PhysicsObject entityPhys = PhysicsObject.dynamicPhysicsObject(globals.MASK_ENTITY, elem.physicsObjectData.hitbox, elem.coordinates, elem.dimensions);
                            Entity tempEntity = new Entity(elem.name, elem.physicsObjectData.generate(), elem.movement_speed, elem.renderObjectData.file_location);
                            newEntityList.add(tempEntity);
                            System.out.println("tempEntity:" + tempEntity);
                        }
                    }
                } else {
                    System.out.println("replacePrevious:" + elem.replacement);
                }
            }
            this.entityList = newEntityList;
            System.out.println("entity_list_1:" + this.entityList);
        }
        // </editor-fold>

        // <editor-fold desc="structures">
        if (data.structure_collection != null) {
            List<Structure> toReplaceList = new ArrayList();
            List<Structure> newStructureList = new ArrayList(this.structureList);
            for (StructureData strel : data.structure_collection.elements) {
                if (strel.replacement != null) {
                    for (Structure s : this.structureList) {
                        if (strel.replacement.equals(s.name)) {
                            toReplaceList.add(s);
                        }
                    }
                } else {
                    PhysicsObject physStruct = PhysicsObject.staticStructureObject(strel.physicsObjectData.location_meters, strel.physicsObjectData.dimensions_meters);
                    Structure tempStruct = new Structure(strel.name, physStruct, strel.renderObjectData.file_location);
                    newStructureList.add(tempStruct);
                }
            }
            for (Structure s : toReplaceList) {
                newStructureList.remove(s);
            }
            for (StructureData strel : data.structure_collection.elements) {
                if (strel.replacement != null) {
                    for (Structure s : this.structureList) {
                        if (strel.replacement.equals(s.name)) {
                            PhysicsObject physStruct = PhysicsObject.staticStructureObject(strel.physicsObjectData.location_meters, strel.physicsObjectData.dimensions_meters);
                            Structure tempStruct = new Structure(strel.name, physStruct, strel.renderObjectData.file_location);
                            newStructureList.add(tempStruct);
                            System.out.println("tempStruct:" + tempStruct);
                        }
                    }
                }
            }
            this.structureList = newStructureList;
        }
        for (Structure s : this.structureList) {
            s.initPhysicsObject();
        }
        // </editor-fold>

        // TODO
        // <editor-fold desc="backgrounds">
        if (data.background_collection != null) {
            List<Background> toReplaceList = new ArrayList();
            List<Background> newBackgroundList = new ArrayList(this.backgroundList);
            for (BackgroundData elem : data.background_collection.elements) {
                if (elem.replacement != null) {
                    for (Background b : this.backgroundList) {
                        if (elem.replacement.equals(b.name)) {
                            toReplaceList.add(b);
                        }
                    }
                } else {
                    PhysicsObject belPhys = PhysicsObject.staticObject(globals.MASK_BACKGROUND, elem.physicsObjectData.location_meters, elem.physicsObjectData.dimensions_meters);
                    Background tempBack = new Background(elem.name, belPhys, elem.renderObjectData.file_location);
                    newBackgroundList.add(tempBack);
                }
            }
            for (Background b : toReplaceList) {
                newBackgroundList.remove(b);
            }
            for (BackgroundData elem : data.background_collection.elements) {
                if (elem.replacement != null) {
                    for (Background b : this.backgroundList) {
                        if (elem.replacement.equals(b.name)) {
                            PhysicsObject belPhys = PhysicsObject.staticObject(globals.MASK_BACKGROUND, elem.physicsObjectData.location_meters, elem.physicsObjectData.dimensions_meters);
                            Background tempBack = new Background(elem.name, belPhys, elem.renderObjectData.file_location);
                            newBackgroundList.add(tempBack);
                            System.out.println("tempBack:" + tempBack);
                        }
                    }
                }
            }
            this.backgroundList = newBackgroundList;
        }
        for (Background b : this.backgroundList) {
            b.initPhysicsObject();
        }
        // </editor-fold>
    }

    public void updateValuesRemove(LevelData data) {
        // <editor-fold desc="player">
        if (data.player_data != null) {
            this.player = null;
            // System.out.println("playerUPDATE:" + this.player);
        }
        if (this.player != null) {
            Game.player.initPhysicsObject();
        }
        // </editor-fold>

        // TODO
        // <editor-fold desc="entities">
        if (data.entity_data != null) {
            List<Entity> toRemoveList = new ArrayList();
            List<Entity> newEntityList = new ArrayList(this.entityList);
            System.out.println("entity_list_0:" + entityList);
            for (EntityData elem : data.entity_data.elements) {
                for (Entity s : this.entityList) {
                    if (elem.name.equals(s.name)) {
                        if (toRemoveList.contains(s) == false) {
                            toRemoveList.add(s);
                        }
                    }
                }
            }
            for (Entity s : toRemoveList) {
                newEntityList.remove(s);
            }
            this.entityList = newEntityList;
            System.out.println("entity_list_1:" + entityList);
        }
        for (Entity e : this.entityList) {
            e.initPhysicsObject();
        }
        // </editor-fold>

        // <editor-fold desc="structures">
        if (data.structure_collection != null) {
            List<Structure> toRemoveList = new ArrayList();
            List<Structure> newStructureList = new ArrayList(this.structureList);
            for (StructureData elem : data.structure_collection.elements) {
                for (Structure s : this.structureList) {
                    if (elem.name.equals(s.name)) {
                        if (toRemoveList.contains(s) == false) {
                            toRemoveList.add(s);
                        }
                    }
                }
            }
            for (Structure s : toRemoveList) {
                newStructureList.remove(s);
            }
            this.structureList = newStructureList;
        }
        for (Structure s : this.structureList) {
            s.initPhysicsObject();
        }
        // </editor-fold>

        // TODO
        // <editor-fold desc="backgrounds">
        if (data.background_collection != null) {
            List<Background> toRemoveList = new ArrayList();
            List<Background> newBackgroundList = new ArrayList(this.backgroundList);
            for (BackgroundData elem : data.background_collection.elements) {
                for (Background b : this.backgroundList) {
                    if (elem.name.equals(b.name)) {
                        if (toRemoveList.contains(b) == false) {
                            toRemoveList.add(b);
                        }
                    }
                }
            }
            for (Background b : toRemoveList) {
                newBackgroundList.remove(b);
            }
            this.backgroundList = newBackgroundList;
        }
        for (Background b : this.backgroundList) {
            b.initPhysicsObject();
        }
        // </editor-fold>
    }

    public Level clone() {
        Level retLevel = new Level();
        retLevel.player = this.player;
        retLevel.entityList = this.entityList;
        retLevel.backgroundList = this.backgroundList;
        retLevel.structureList = this.structureList;
        return retLevel;
    }

}
