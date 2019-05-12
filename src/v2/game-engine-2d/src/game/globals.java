package game;

public final class globals {

    public static final short CATEGORY_PLAYER = 0x0001,
            CATEGORY_ENTITY = 0x0002,
            CATEGORY_STRUCTURE = 0x0004;
    public static final short MASK_BACKGROUND = MASK.BACKGROUND.value, MASK_ENTITY = MASK.ENTITY.value,
            MASK_PLAYER = MASK.PLAYER.value, MASK_STRUCTURE = MASK.STRUCTURE.value;

    public static enum MASK {
        BACKGROUND((short) 0), ENTITY((short) (CATEGORY_PLAYER | CATEGORY_STRUCTURE)),
        PLAYER((short) (CATEGORY_ENTITY | CATEGORY_STRUCTURE)), STRUCTURE((short) (CATEGORY_PLAYER | CATEGORY_ENTITY));
        public final short value;

        private MASK(short value) {
            this.value = value;
        }

        public static short getValue(String identifier) {
            try {
                return MASK.valueOf(identifier).value;
            } catch (IllegalArgumentException iae) {
                return Short.parseShort(identifier);
            }
        }

        public static String getKey(short val) {
            MASK[] masks = MASK.values();
            for (int i = 0; i < masks.length; i++) {
                if (val == masks[i].value) {
                    return masks[i].toString();
                }
            }
            return "" + val;
        }

    }

    private globals() {

    }

}
