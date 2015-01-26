package com.yhj.PEP.cops.objects;

import java.io.*;
import com.yhj.PEP.cops.CopsException;

/**
 * This class is the default decision object that holds data.
 * Includes: Stateless Data, Replacement Data, Client Specific Data
 * and Named Decision Data.
 * Subclass this in the extension of this COPS protocol
 * and overrides the method parseData(byte[] data) to setup the object.
 */
public class CODecData extends AbstractCOObject implements CODecision {

  protected byte[] data;

  /**
   * This leaves the data for the subclass.
   */
  public CODecData(COHeader coh) {
    super(coh);
  }

  public CODecData(byte ctype) {
    super(new COHeader((short) 0, DECISION, ctype));
  }

  public CODecData(COHeader coh, byte[] data) throws CopsException {
    super(coh);
    parseData(data);
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    if (data != null) {
      os.write(data);
      int remainder = data.length % 4;
      if (remainder != 0)
        pad(os, 4 - remainder);
    }
  }

  /**
   * This method setup this object.
   * Override this to setup the object in extension.
   */
  protected void parseData(byte[] data) throws CopsException {
    if (data != null) {
      this.data = new byte[data.length];
      // copying data
      for (int i = 0; i < data.length; i++) {
        this.data[i] = data[i];
      }
    }
  }
}