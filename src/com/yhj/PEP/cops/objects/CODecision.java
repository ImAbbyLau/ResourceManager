package com.yhj.PEP.cops.objects;

/**
 * The interface that contains the constants for Decision objects
 */

public interface CODecision {
  // These are the constants that defines the C-Type for Cops Decision objects
  public static final byte FLAG = 1;
  public static final byte STATELESS_DATA = 2;
  public static final byte REPLACEMENT_DATA = 3;
  public static final byte CLIENT_SPECIFIC_DEC_DATA = 4;
  public static final byte NAMED_DEC_DATA = 5;
}
