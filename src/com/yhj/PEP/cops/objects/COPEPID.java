package com.yhj.PEP.cops.objects;

import java.io.*;

public class COPEPID extends AbstractCOObject {
  public String pepid;

  /**
   * Construct and initialise this object with a given pepid.
   *
   * @param pepid the string that uniquely describes PEP
   */
  public COPEPID(String pepid) {
    // length is header + string
    super(new COHeader((short) pepid.length(), (byte) COObject.PEPID, (byte) 1));
    this.pepid = pepid;
  }
  
  /**
   * Construct this object based on the header and the input data.
   *
   * @param coh this object's header.
   * @param bytes the input that makes up the body of this object.
   */
  public COPEPID(COHeader coh, byte[] bytes) {
    super(coh);
    this.pepid = new String(bytes);
  }
  
  /**
   * Write this object to the given output stream.
   * 
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    byte[] buf = pepid.getBytes();
    os.write(buf, 0, buf.length);
    
    // padding with zeroes to the next 32-bit boundary
    int remainder = buf.length % 4;
    if (remainder != 0) {
      remainder = 4 - remainder;
      for (; remainder > 0; remainder--) {
        os.write(0);
      }
    }
  }
  
  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; pepid: ").append(pepid);
    
    return result.toString();
  }

}
