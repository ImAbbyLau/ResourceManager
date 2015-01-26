package com.yhj.MPDP.cops.objects;

import java.io.*;

/**
 * The class for Decision Flags.
 */

public class CODecFlag extends AbstractCOObject implements CODecision {

  // command-code
  public static final short NULL = 0;
  public static final short INSTALL = 1;
  public static final short REMOVE = 2;

  // flags
  public static final short TRIGGER_ERROR = 1;

  public short commandCode;
  public short flags;

  public CODecFlag(short commandCode, short flags) {
    super(new COHeader((short) 4, (byte) COObject.DECISION, FLAG));
    this.commandCode = commandCode;
    this.flags = flags;
  }

  public CODecFlag(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      commandCode = dis.readShort();
      flags = dis.readShort();
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }

  public short getCommandCode() {
    return commandCode;
  }

  public short getFlags() {
    return flags;
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    DataOutputStream dos = new DataOutputStream(os);

    super.writeTo(os);
    dos.writeShort(commandCode);
    dos.writeShort(flags);
  }

  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; commandCode: ").append(commandCode);
    result.append("; flags: ").append(flags);
    return result.toString();
  }
}
