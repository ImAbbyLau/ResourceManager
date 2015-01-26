package com.yhj.PEP.cops.objects;

import java.io.*;
import com.yhj.PEP.cops.*;

/**
 * This is the class AcctTimer.
 */
public class COAcctTimer extends COTimer {

  public COAcctTimer(short acctTimer) {
    super(new COHeader(clen, COObject.ACCT_TIMER, (byte) 1), acctTimer);
  }

  public COAcctTimer(COHeader coh, byte[] bytes) throws CopsException {
    super(coh, bytes);
  }
}
