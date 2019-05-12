package main.java.engine.output.render.objects.font;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.output.render.objects._2d.TextureFrame;
import main.java.engine.output.render.objects.atlas.FontAtlas;
import main.java.utilities.math.vector.MathVector2D;

public class DrawString implements Drawable {

    protected FontAtlas atlas;
    protected final String representation;
    protected MathVector2D<Double> percentCoordinates;
    protected Double percentToPixelRatio;
    protected Double[] percentDimensions;
    protected DrawCharacter[] characters;


    public DrawString(FontAtlas atlas, String representation, MathVector2D<Double> percentCoordinates, Double[] percentDimensions) {
        this.atlas = atlas;
        this.representation = representation;
        this.percentCoordinates = percentCoordinates;
        this.percentDimensions = percentDimensions;
        justifyDimensions();
        initCharacters();
    }



    @Override
    public void draw() {
        for (DrawCharacter currentCharacter : characters) {
            currentCharacter.draw();
        }
    }


    @Override
    public boolean inBounds(MathVector2D<Double> percentCoordinates) {
        for (DrawCharacter currentCharacter : characters) {
            if (currentCharacter.inBounds(percentCoordinates)) {
                return true;
            }
        }
        return false;
    }


    protected void justifyDimensions() {
        int fontSize = this.atlas.getFont().getMetadata().size;
        int fontWidth = this.atlas.getWidthPixels(this.representation.toCharArray());
        this.percentToPixelRatio = Math.min(this.percentDimensions[0] / fontWidth, this.percentDimensions[1] / fontSize);
    }


    protected void initCharacters() {
        this.characters = new DrawCharacter[this.representation.length()];
        for (int index = 0; index < this.representation.length(); index++) {
            char currentCharacter = this.representation.charAt(index);
            TextureFrame currentTextureFrame = this.atlas.getTextureFrame(currentCharacter);
            CharacterFrame currentCharacterFrame = new CharacterFrame(currentCharacter, currentTextureFrame);
            MathVector2D<Double> coordinates
                    = new MathVector2D(
                            this.cursorXPosition(),
                            this.percentCoordinates.getY() + currentCharacterFrame.getY_offset() * this.percentToPixelRatio);
            DrawCharacter characterToAdd = new DrawCharacter(currentCharacterFrame, coordinates, this.percentToPixelRatio);
            this.characters[index] = characterToAdd;
        }
    }


    protected Double cursorXPosition() {
        Double retValue = this.percentCoordinates.getX();
        for (DrawCharacter currentCharacter : this.characters) {
            if (currentCharacter == null) {
                continue;
            }
            retValue += (currentCharacter.frame.getX_advance()) * this.percentToPixelRatio;
        }
        return retValue;
    }


}
