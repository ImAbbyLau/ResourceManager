package com.yhj.APDP.cops;

/**
 * General CopsException. All other exceptions are inherited from this class.
 *
 */

public class CopsException extends Exception {
  public CopsException() {}
  public CopsException(String s) {
    super(s);
  }
}
