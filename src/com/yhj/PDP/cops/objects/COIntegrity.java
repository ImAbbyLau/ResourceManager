package com.yhj.PDP.cops.objects;

import java.io.*;

public class COIntegrity extends AbstractCOObject {
  
  public int keyID;
  public int sequenceNumber;
  public byte[] digest;
  public static short clen = 20;
  
  public COIntegrity(int keyID, int sequenceNumber, byte[] digest) {
    super(new COHeader(clen, (byte) COObject.INTEGRITY, (byte) 1));
    this.keyID = keyID;
    this.sequenceNumber = sequenceNumber;
    this.digest = new byte[digest.length];
    for (int i = 0; i < digest.length; i++) {
      this.digest[i] = digest[i];
    }
  }
  
  public COIntegrity(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try { 
      keyID = dis.readInt();
      sequenceNumber = dis.readInt();
      dis.read(digest, 0 ,12);
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }
  
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    DataOutputStream dos = new DataOutputStream(os);
    
    dos.writeInt(keyID);
    dos.writeInt(sequenceNumber); // required
    dos.write(digest);// ????
  }
  
  public String toString() {
    System.out.println("integrity");
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; Key ID: ").append(keyID);
    result.append("; Sequence Number: ").append(sequenceNumber);
    result.append("; Keyed Message Digest: ").append(digest);
    return result.toString();
  }
}
