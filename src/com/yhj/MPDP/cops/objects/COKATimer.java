package com.yhj.MPDP.cops.objects;

import java.io.*;
import com.yhj.MPDP.cops.*;

/**
 * This is the class KATimer.
 */
public class COKATimer extends COTimer {

  public COKATimer(short kaTimer) {
    super(new COHeader(clen, COObject.KA_TIMER, (byte) 1), kaTimer);
  }

  public COKATimer(COHeader coh, byte[] bytes) throws CopsException {
    super(coh, bytes);
  }
}
