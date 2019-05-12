package main.java.utilities.io;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class output {

    public final static class structures {

        public static enum color_code {

            RED(0xff0000),
            ORANGE(0xff8000),
            YELLOW(0xffff00),
            GREEN(0x80ff00),
            BLUE(0x0000ff),
            INDIGO(0x8000ff),
            VIOLET(0xff00ff),
            WHITE(0xffffffff),
            BLACK(0x00000000);

            private rgba info;


            private color_code(int hex_value) {
                this.info = new rgba(hex_value);
            }


        }

        public static class rgba {

            public final byte r, g, b, a;


            public rgba(byte r, byte g, byte b, byte a) {
                this.r = r;
                this.g = g;
                this.b = b;
                this.a = a;
            }


            public rgba(byte[] rgba) {
                this(rgba[0], rgba[1], rgba[2], rgba[3]);
            }


            public rgba(int value) {
                this(ByteBuffer.allocate(4).putInt(value).array());
            }


            public byte[] toPixel() {
                return new byte[]{r, g, b, a};
            }


            public int toInt() {
                return ByteBuffer.wrap(this.toPixel()).getInt();
            }


            public boolean equals(byte[] other) {
                return Arrays.equals(this.toPixel(), other);
            }


            public boolean equals(rgba other) {
                return Arrays.equals(this.toPixel(), other.toPixel()) || this == other;
            }


        }


        private structures() {

        }


    }


    private output() {

    }


}
