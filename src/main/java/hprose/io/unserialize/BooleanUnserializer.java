/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * BooleanUnserializer.java                               *
 *                                                        *
 * boolean unserializer class for Java.                   *
 *                                                        *
 * LastModified: Jun 24, 2015                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/

package hprose.io.unserialize;

import hprose.io.HproseTags;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public final class BooleanUnserializer implements HproseUnserializer, HproseTags {

    public final static BooleanUnserializer instance = new BooleanUnserializer();

    final static boolean read(HproseReader reader, ByteBuffer buffer, int tag) throws IOException {
        switch (tag) {
            case '0': return false;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': return true;
            case TagInteger: return ValueReader.readInt(buffer) != 0;
            case TagLong: return !(BigInteger.ZERO.equals(ValueReader.readBigInteger(buffer)));
            case TagDouble: return ValueReader.readDouble(buffer) != 0.0;
            case TagEmpty: return false;
            case TagNaN: return true;
            case TagInfinity: buffer.get(); return true;
            case TagUTF8Char: return "\00".indexOf(ValueReader.readChar(buffer)) == -1;
            case TagString: return Boolean.parseBoolean(StringUnserializer.readString(reader, buffer));
            case TagRef: return Boolean.parseBoolean(reader.readRef(buffer, String.class));
            default: throw ValueReader.castError(reader.tagToString(tag), boolean.class);
        }
    }

    final static boolean read(HproseReader reader, InputStream stream, int tag) throws IOException {
        switch (tag) {
            case '0': return false;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': return true;
            case TagInteger: return ValueReader.readInt(stream) != 0;
            case TagLong: return !(BigInteger.ZERO.equals(ValueReader.readBigInteger(stream)));
            case TagDouble: return ValueReader.readDouble(stream) != 0.0;
            case TagEmpty: return false;
            case TagNaN: return true;
            case TagInfinity: stream.read(); return true;
            case TagUTF8Char: return "\00".indexOf(ValueReader.readChar(stream)) == -1;
            case TagString: return Boolean.parseBoolean(StringUnserializer.readString(reader, stream));
            case TagRef: return Boolean.parseBoolean(reader.readRef(stream, String.class));
            default: throw ValueReader.castError(reader.tagToString(tag), boolean.class);
        }
    }

    public final static boolean read(HproseReader reader, ByteBuffer buffer) throws IOException {
        int tag = buffer.get();
        if (tag == TagTrue) return true;
        if (tag == TagFalse) return false;
        if (tag == TagNull) return false;
        return read(reader, buffer, tag);
    }

    public final static boolean read(HproseReader reader, InputStream stream) throws IOException {
        int tag = stream.read();
        if (tag == TagTrue) return true;
        if (tag == TagFalse) return false;
        if (tag == TagNull) return false;
        return BooleanUnserializer.read(reader, stream, tag);
    }

    public final Object read(HproseReader reader, ByteBuffer buffer, Class<?> cls, Type type) throws IOException {
        return BooleanUnserializer.read(reader, buffer);
    }

    public final Object read(HproseReader reader, InputStream stream, Class<?> cls, Type type) throws IOException {
        return BooleanUnserializer.read(reader, stream);
    }

}
