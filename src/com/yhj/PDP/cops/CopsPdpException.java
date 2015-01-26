package com.yhj.PDP.cops;

public class CopsPdpException extends Exception {
  /* the code (reason) for this exception */
  private int code = 0;

  private Exception e = null;

  /* Constructors */
  public CopsPdpException() {
    super();
  }

  public CopsPdpException(Exception e, int code) {
    super();
    this.e = e;
    this.code = code;
  }

  public CopsPdpException(String s, int code) {
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
    sb.append("CopsPdpException with code: ");
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
