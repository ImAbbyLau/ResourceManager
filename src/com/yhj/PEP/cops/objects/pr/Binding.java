package com.yhj.PEP.cops.objects.pr;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class just provide a convenient binding for the messages
 */

public class Binding {
  public COPRPRID prid;
  public COPREPD epd;

  public Binding(COPRPRID prid, COPREPD epd) {
    this.prid = prid;
    this.epd = epd;
  }

  public Binding(byte[] prid, byte[] epd) {
    this.prid = new COPRPRID(prid);
    this.epd = new COPREPD(epd);
  }

  public short length() {
    return (short) (prid.totalLength() + epd.totalLength());
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    prid.writeTo(os);
    epd.writeTo(os);
  }

  public String toString() {
    return prid.toString() + ';' + epd.toString();
  }
}