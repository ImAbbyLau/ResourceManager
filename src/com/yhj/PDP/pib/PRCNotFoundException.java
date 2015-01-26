package com.yhj.PDP.pib;

import com.yhj.PDP.cops.CopsException;

/**
 * This exception is generated if trying to put PRI where PRC has not been
 * inserted to the PIB.
 */
public class PRCNotFoundException extends PIBException {
  public PRCNotFoundException() {}
  public PRCNotFoundException(String str) {super(str);}
}