package com.yhj.APDP.pib;

import com.yhj.APDP.cops.CopsException;

/**
 * This exception is generated if trying to put PRI where PRC has not been
 * inserted to the PIB.
 */
public class PRCNotFoundException extends PIBException {
  public PRCNotFoundException() {}
  public PRCNotFoundException(String str) {super(str);}
}