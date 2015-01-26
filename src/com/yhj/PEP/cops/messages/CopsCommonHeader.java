package com.yhj.PEP.cops.messages;

import java.io.*;
import com.yhj.PEP.cops.objects.*;

public class CopsCommonHeader {
  public byte version;
  public byte flags;
  public byte opcode;
  public short clientType;
  public int messageLength = 0;

  public static final int clen = 8;
  public static final byte SOLICITED = 1;

  public CopsCommonHeader(byte version, byte flags, byte opcode, short clientType) {
    this.version = version;
    this.flags = flags;
    this.opcode = opcode;
    this.clientType = clientType;
  }

  public CopsCommonHeader(byte version, byte flags, byte opcode, short clientType, int msgLength) {
    this.version = version;
    this.flags = flags;
    this.opcode = opcode;
    this.clientType = clientType;
    this.messageLength = msgLength;
  }

  /**
   * Construct this object purely on array of bytes.
   *
   * @param bytes the array of bytes from which this object is created.
   */
  public CopsCommonHeader(byte[] bytes) {
    if (bytes.length >= 8) {
      // Interpret the array of bytes
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes, 0, 8); // only need 8 bytes
      DataInputStream dis = new DataInputStream(bais);
      try {
        byte vf = dis.readByte(); //version & flag , 8 bits
        this.version = (byte) (vf >> 4); //first 4 bits
        this.flags = (byte) (vf - (version << 4)); // second 4 bits
        this.opcode = dis.readByte();
        this.clientType = dis.readShort(); // reads 2 bytes
        this.messageLength = dis.readInt(); //reads 4 bytes
        dis.close();
      } catch (IOException e) {e.printStackTrace();} //shouldn't catch this.
    } else {
      // TODO: throw exception???
    }
  }

  public byte[] toBytes() {
    byte[] result = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    try {
      dos.writeByte((version << 4) + flags);
      dos.writeByte(opcode);
      dos.writeShort(clientType);
      dos.writeInt(messageLength);

      result = baos.toByteArray();
    } catch (IOException e) {
      // this should never happens, since we are only dealing with ByteArrayOutputStream
      e.printStackTrace();
    } finally {
      try {dos.close();}
      catch (IOException e) {} // ignore if trap exception in closing
    }

    return result;
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    DataOutputStream dos = new DataOutputStream(os);

    dos.writeByte((version << 4) + flags);
    dos.writeByte(opcode);
    dos.writeShort(clientType);
    dos.writeInt(messageLength);
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("version: ").append(version);
    result.append("; flags: ").append(flags);
    result.append("; opcode: ").append(opcode);
    result.append("; clientType: ").append(clientType);
    result.append("; msgLength: ").append(messageLength);

    return result.toString();
  }

  public void setSolicited(boolean sol) {
    if (sol)
      flags = (byte) (flags & SOLICITED);
    else
      flags = (byte) (flags & ~SOLICITED);
  }
}
