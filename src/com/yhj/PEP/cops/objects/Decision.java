package com.yhj.PEP.cops.objects;

import java.io.*;

public class Decision {

  // Compulsory
  protected COContext context = null;
  protected CODecFlag flag = null;
  
  // Optional Components
  // Decision: Stateless Data
  protected CODecData stateless = null;
  // Decision: Replacement Data
  protected CODecData replacement = null;
  // Decision: Client Specific Decision Data
  protected CODecData clientSI = null;
  // Decision: Named Decision Data
  protected CODecData named = null;

  public short commandCode;


  /**
   * minimal constructor for this object
   */
  public Decision(COContext context, CODecFlag flag) {
    this.context = context;
    this.flag = flag;
  }

  /**
   * minimal constructor for Decision, only contains context and flags
   */
  public Decision(short rType, short mType, short commandCode, short flags) {
    this.context = new COContext(rType, mType);
    this.commandCode=commandCode;
    this.flag = new CODecFlag(commandCode, flags);
  }

  /**
   * complete constructor. Assume that all the objects have been created.
   */
  public Decision(COContext coc, CODecFlag flag, CODecData stateless,
                  CODecData replacement, CODecData clientSI, CODecData named) {
    this.context = coc;
    this.flag = flag;
    this.stateless = stateless;
    this.replacement = replacement;
    this.clientSI = clientSI;
    this.named = named;
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    context.writeTo(os);
    flag.writeTo(os);

    // writing optional components
    if (stateless != null) stateless.writeTo(os);
    if (replacement != null) replacement.writeTo(os);
    if (clientSI != null) clientSI.writeTo(os);
    if (named != null) named.writeTo(os);
  }

  // a bunch of getters
  public COContext getContext() {
    return context;
  }
  public CODecFlag getFlag() {
    return flag;
  }
  public CODecData getStateless() {
    return stateless;
  }
  public CODecData getReplacement() {
    return replacement;
  }
  public CODecData getClientSI() {
    return clientSI;
  }
  public CODecData getNamed() {
    return named;
  }

  // a bunch of setters
  public void setContext(COContext context) {
    this.context = context;
  }
  public void setFlag(CODecFlag flag) {
    this.flag = flag;
  }
  public void setStateless(CODecData stateless) {
    this.stateless = stateless;
  }
  public void setReplacement(CODecData replacement) {
    this.replacement = replacement;
  }
  public void setClientSI(CODecData clientSI) {
    this.clientSI = clientSI;
  }
  public void setNamed(CODecData named) {
    this.named = named;
  }

  public int length() {
    int result = context.totalLength() + flag.totalLength();
    if (stateless != null) result += stateless.totalLength();
    if (replacement != null) result += replacement.totalLength();
    if (clientSI != null) result += clientSI.totalLength();
    if (named != null) result += named.totalLength();

    return result;
  }

  public String toString() {
    StringBuffer result = new StringBuffer("Decision: ");
    result.append(super.toString());
    result.append(";Context: ").append(context);
    result.append(";Flag: ").append(flag);
    if (stateless != null) {
      result.append(";Stateless: ").append(stateless);
    }
    if (replacement != null) {
      result.append(";Replacement: ").append(replacement);
    }
    if (clientSI != null) {
      result.append(";ClientSI: ").append(clientSI);
    }
    if (named != null) {
      result.append(";Named: ").append(named);
    }

    return result.toString();
  }
}
