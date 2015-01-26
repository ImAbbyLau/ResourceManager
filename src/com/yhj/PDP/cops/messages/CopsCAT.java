package com.yhj.PDP.cops.messages;

import java.io.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;

public class CopsCAT extends AbstractCopsMessage {
  // Compulsory
  protected COKATimer kaTimer = null;

  // Optional components
  protected COKATimer acctTimer = null;
  protected COIntegrity integrity = null;

  /**
   * Construct the simplest of Accept message.
   * Only contains KATimer
   *
   * @param pepid the name that uniquely identifies PEP to PDP
   */

  public CopsCAT(short clientType, short kat){
    super((byte) CopsMessage.CAT, clientType);
    this.kaTimer = new COKATimer(kat);
    // set the message length
    int length = cch.clen + kaTimer.totalLength();
    setLength(length);
  }

  public CopsCAT(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    // interpret objects found inside CAT message
    CopsObjParser cop = getObjParser(bytes);
    COObject obj = cop.nextObject();
    // first one should be KATimer
    if ((obj == null) || (obj.getCNum() != COObject.KA_TIMER)) {
      throw new CopsException("Invalid message, expect KA_TIMER");
    }
    this.kaTimer = (COKATimer) obj;

    // check for other optional objects
    while ((obj = cop.nextObject()) != null) {
      switch (obj.getCNum()) {
      case COObject.ACCT_TIMER:
        this.acctTimer = (COKATimer) obj;
        break;
      case COObject.INTEGRITY:
        this.integrity = (COIntegrity) obj;
        break;
      default:
        // found invalid object
        throw new CopsException("Invalid object: get C-Num: " + obj.getCNum());
      }
    }
  }

  public short getKATimer() {
    if (kaTimer != null)
      return kaTimer.getTimer();
    return -1;
  }
  public short getAcctTimer() {
    if (acctTimer != null)
      return acctTimer.getTimer();
    return -1;
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    kaTimer.writeTo(os);

    // writing optional components
    if (acctTimer != null) {
      acctTimer.writeTo(os);
    }
    if (integrity != null) {
      integrity.writeTo(os);
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("CAT: ").append(super.toString());
    result.append(";kaTimer: ").append(kaTimer);
    if (acctTimer != null) result.append(";acctTimer: ").append(acctTimer);
    return result.toString();
  }
}
