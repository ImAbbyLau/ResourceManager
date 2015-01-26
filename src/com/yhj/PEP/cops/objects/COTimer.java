package com.yhj.PEP.cops.objects;

import java.io.*;
import com.yhj.PEP.cops.*;

/**
 * This class is the class of Cops Object that holds timer.
 * This class is subclassed to 2 class that are Cops Object.
 * They are: COKATimer and COAcctTimer
 */
public class COTimer extends AbstractCOObject {

  private short timer;
  public static short clen = 4; // length of body of object

  public COTimer(COHeader coh, short timer) {
    super(coh);
    this.timer = timer;
  }

  public COTimer(COHeader coh, byte[] bytes) throws CopsException {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      // ignore the first 2 bytes
      dis.readShort();
      timer = dis.readShort();
    } catch (EOFException eof) {
      throw new CopsException("Invalid Timer object.");
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }

  public short getTimer() {
    return timer;
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
    dos.writeShort((short) 0); // must be 0
    dos.writeShort(timer);
  }
  
  public String toString() {
	    StringBuffer result = new StringBuffer();
	    result.append(super.toString());
	    result.append(";Timer: ").append(timer);
	    return result.toString();
  }
}
  
