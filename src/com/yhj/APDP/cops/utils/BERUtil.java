package com.yhj.APDP.cops.utils;

/**
 * A Collection of utility methods to work with BER encoding.
 * If an instance of this class is created with a given array of bytes,
 * it acts as a parser.
 * Call the method nextObject(). Obtain the type from getType().
 * And get the appropriate object.
 * More work needs to be done on this class.
 */
public class BERUtil {

  // added. means no more data. used in parsing incoming data.
  public static final byte UNDEFINED    = -1;

  public static final byte BOOLEAN      = 1;
  public static final byte INTEGER      = 2;
  public static final byte BIT_STRING   = 3;
  public static final byte OCTET_STRING = 4;
  public static final byte NULL         = 5;
  public static final byte OBJECT_ID    = 6;
  public static final byte REAL         = 9;

  protected byte[] data = null;
  protected int index = 0;
  protected Object obj = null;
  protected byte[] array = null;
  protected byte type;

  public BERUtil(byte[] input) {
    if (input != null) {
      this.data = new byte[input.length];
      for (int i = 0; i < input.length; i++) {
        this.data[i] = input[i];
      }
    }
  }

  public byte nextObject() {
    if ((data == null) || (index > data.length - 2)) {
      // no more data/object.
      this.obj = null;
      this.array = null;
      this.type = UNDEFINED;
      return UNDEFINED;
    }
    int length = data[index+1] & 255; // make it unsigned
    switch (data[index]) {
    case BOOLEAN:
      this.array = null;
      this.obj = new Boolean(decodeBoolean(data, index, length+2));
      break;
    case INTEGER:
      this.array = null;
      this.obj = new Integer(decodeInteger(data, index, length+2));
      break;
    case OCTET_STRING:
      this.obj = null;
      this.array = decodeOctetString(data, index, length+2);
      break;
    case OBJECT_ID:
      this.obj = null;
      this.array = decodeObjectId(data, index, length+2);
      break;
    default:
      this.array = null;
      this.obj = null;
      break;
    }
    this.type = data[index];
    index += 2 + length;
    return this.type;
  }

  public byte getType() {
    return type;
  }

  public Object getObject() {
    return obj;
  }

  public int getInteger() {
    return ((Integer) obj).intValue();
  }

  public boolean getBoolean() {
    return ((Boolean) obj).booleanValue();
  }

  public byte[] getArray() {
    return array;
  }

  public static byte[] encode(byte[] data, byte type) {
    if (data == null) return encodeNull();

    switch (type) {
    case BOOLEAN:
      return encodeBoolean(data);
    case INTEGER:
      return encodeInteger(data);
    case OCTET_STRING:
      return encodeOctetString(data);
    case NULL:
      return encodeNull();
    case OBJECT_ID:
      return encodeObjectId(data);
    default:
      return data; // the default is not to encode the data???
    }
  }

  /**
   * Convert an integer to an array of bytes in two's complement form.
   * This just check for ranges and create the size of the array accordingly.
   */
  public static byte[] encodeInteger(int integer) {
    byte[] data;
    if ((integer <= Byte.MAX_VALUE) && (integer >= Byte.MIN_VALUE)) {
      data = new byte[1];
      data[0] = (byte) integer;
    } else if ((integer <= Short.MAX_VALUE) && (integer >= Short.MIN_VALUE)) {
      data = new byte[2];
      data[0] = (byte) integer;
      data[1] = (byte) (integer >>> 8);
    } else if ((integer <= Math.pow(2, 24-1) - 1) && (integer >= -Math.pow(2, 24-1))) {
      data = new byte[3];
      data[0] = (byte) integer;
      data[1] = (byte) (integer >>> 8);
      data[2] = (byte) (integer >>> 16);
    } else if ((integer <= Integer.MAX_VALUE) && (integer >= Integer.MIN_VALUE)) {
      data = new byte[4];
      data[0] = (byte) integer;
      data[1] = (byte) (integer >>> 8);
      data[2] = (byte) (integer >>> 16);
      data[3] = (byte) (integer >>> 24);
    } else {
      data = new byte[0];
    }
    return encodeInteger(data);
  }

  public static byte[] encodeBoolean(boolean b) {
    byte[] result = {BOOLEAN, 1, (byte) (b? 1 : 0)};

    return result;
  }

  public static byte[] encodeBoolean(byte[] data) {
    if (data == null) return encodeNull();

    byte[] result = new byte[3];
    result[0] = BOOLEAN;
    result[1] = (byte) 1;
    result[2] = data[0];

    return result;
  }

  public static byte[] encodeInteger(byte[] data) {
    if (data == null) return encodeNull();

    byte[] result = new byte[2 + data.length];
    result[0] = INTEGER;
    result[1] = (byte) data.length;
    int length = result[1];
    for (int i = 0; i < length; i++) {
      result[i+2] = data[(length-1) - i];
    }

    return result;
  }

  public static byte[] encodeOctetString(byte[] data) {
    if (data == null) return encodeNull();

    byte[] result = new byte[2 + data.length];
    result[0] = OCTET_STRING;
    result[1] = (byte) data.length;
    int length = result[1];
    for (int i = 0; i < length; i++) {
      result[i+2] = data[i];
    }

    return result;
  }

  protected static byte[] encodeNull() {
    byte[] result = {NULL, 0};
    return result;
  }

  /**
   * Object Id must have at least 2 data. BER encoding compress theses two bytes
   * into a single byte with the relationship = 40 * data[0] + data[1]
   */
  public static byte[] encodeObjectId(byte[] data) {
    if (data == null) return encodeNull();

    byte[] result = new byte[2 + (data.length - 1)];
    result[0] = OBJECT_ID;
    result[1] = (byte) (data.length - 1);
    int length = result[1];
    try {
      result[2] = (byte) (40 * data[0] + data[1]);
    } catch (IndexOutOfBoundsException e) {
      System.err.println("Invalid Object id. Must have at least 2 bytes");
    }
    for (int i = 2; i < data.length; i++) {
      result[i+1] = data[i];
    }

    return result;
  }

  public static int decodeInteger(byte[] data, int offset, int length) {
    int result = 0;
    for (int i = offset + length - 1; i > offset + 2; i--) {
      result += (data[i] & 255) << ((offset + length - 1 - i) * 8);
    }
    result += data[offset+2] << ((length - 3) * 8); // keep the sign for the last one.
    return result;
  }

  public static byte[] decodeOctetString(byte[] data, int offset, int length) {
    byte[] result = new byte[length - 2];
    for (int i = 0; i < length - 2; i++) {
      result[i] = data[offset+2+i];
    }
    return result;
  }

  public static boolean decodeBoolean(byte[] data, int offset, int length) {
    return (data[offset+length-1] == 0)? false : true;
  }

  public static byte[] decodeObjectId(byte[] data) {
    return decodeObjectId(data, 0, data.length);
  }

  public static byte[] decodeObjectId(byte[] data, int offset, int length) {
    byte[] result = new byte[length - 1];
    result[0] = (byte) (data[offset + 2] / 40);
    result[1] = (byte) (data[offset + 2] % 40);
    for (int i = 2; i < length - 1; i++) {
      result[i] = data[offset + 2 + i - 1];
    }
    return result;
  }
}
