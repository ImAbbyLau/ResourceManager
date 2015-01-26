package com.yhj.PDP.cops;

public interface CopsConstants {
  static final short COPS_PDP_PORT = 3288;
  static final int COPS_PDP_MAX_CON = 1024;

  /* Version: 4 bits */
  static final int COPS_VERSION = 1;  // RFC 2748

  /* Flags: 4 bits */
  static final int COPS_FLAG_SOLICITED = 0x01;

  /* Op Code: 8 bits */
  static final int COPS_OPCODE_REQ = 1;
  static final int COPS_OPCODE_DEC = 2;
  static final int COPS_OPCODE_RPT = 3;
  static final int COPS_OPCODE_DRQ = 4;
  static final int COPS_OPCODE_SSQ = 5;
  static final int COPS_OPCODE_OPN = 6;
  static final int COPS_OPCODE_CAT = 7;
  static final int COPS_OPCODE_CC  = 8;
  static final int COPS_OPCODE_KA  = 9;
  static final int COPS_OPCODE_SSC = 10;
  static final int COPS_MAX_OPCODE = 10;

  /* c-num: 8 bits */
  static final int COPS_CNUM_HANDLE    = 1;
  static final int COPS_CNUM_CONTEXT   = 2;
  static final int COPS_CNUM_ININTF    = 3;
  static final int COPS_CNUM_OUTINTF   = 4;
  static final int COPS_CNUM_REASON    = 5;
  static final int COPS_CNUM_DECISION  = 6;
  static final int COPS_CNUM_LDPDEC    = 7;
  static final int COPS_CNUM_ERROR     = 8;
  static final int COPS_CNUM_CLIENTSI  = 9;
  static final int COPS_CNUM_KATIMER   = 10;
  static final int COPS_CNUM_PEPID     = 11;
  static final int COPS_CNUM_REPORT    = 12;
  static final int COPS_CNUM_REDIRECT  = 13;
  static final int COPS_CNUM_LASTPDP   = 14;
  static final int COPS_CNUM_ACCTTIMER = 15;
  static final int COPS_CNUM_INTEGRITY = 16;
  static final int COPS_MAX_CNUM = 16;
}