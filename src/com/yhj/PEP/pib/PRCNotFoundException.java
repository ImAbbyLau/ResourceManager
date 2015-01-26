package com.yhj.PEP.pib;

import com.yhj.PEP.cops.CopsException;

/**
 * This exception is generated if trying to put PRI where PRC has not been
 * inserted to the PIB.
 */
public class PRCNotFoundException extends PIBException {
  public PRCNotFoundException() {}
  public PRCNotFoundException(String str) {super(str);}
}