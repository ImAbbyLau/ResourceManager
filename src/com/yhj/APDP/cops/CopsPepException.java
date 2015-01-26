package com.yhj.APDP.cops;

/**
 * CopsException that happens at PEP side.
 */

public class CopsPepException extends CopsException {
  /* the code (reason) for this exception */
  private int code = 0;

  private Exception e = null;

  /* Constructors */
  public CopsPepException() {
    super();
  }

  public CopsPepException(Exception e, int code) {
    super();
    this.e = e;
    this.code = code;
  }

  public CopsPepException(String s, int code) {
    super(s);
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public Exception getNestedException() {
    return e;
  }

  public String getMessage() {
    StringBuffer sb = new StringBuffer(64);
    sb.append("CopsPepException with code: ");
    sb.append(code);
    sb.append('\n');
    if (e != null) {
      sb.append(e.getMessage());
    } else {
      String s = super.getMessage();
      if (s != null) sb.append(s);
    }

    return sb.toString();
  }
}
