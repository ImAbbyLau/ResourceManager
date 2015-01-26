package com.yhj.APDP.pib;

import java.util.*;

/**
 * A PRC class. Contains a template to PRI object (java class) and an index of
 * PRID to the PRI object itself.
 * Also contains the name of the table
 */
public class PRCImpl implements PRC {

  protected String name; // a recognizable name for PRC??
  protected byte[] prcIndex; // the PRC identifier
  protected Class priTemplate; // a template for PRI
  protected Hashtable pris;

  public PRCImpl(Class c, byte[] prcid) {
    this.priTemplate = c;
    if (prcid != null) {
      prcIndex = new byte[prcid.length];
      for (int i = 0; i < prcid.length; i++) {
        prcIndex[i] = prcid[i];
      }
    }
    this.pris = new Hashtable();
  }

  public PRI getPRI(byte prid) {
    return (PRI) this.pris.get(new Byte(prid));
  }

  public Class getTemplate() {
    return this.priTemplate;
  }

  public byte[] getIndex() {
    return this.prcIndex;
  }

  public void putPRI(byte prid, PRI obj) throws InvalidPRIException {
    if (!priTemplate.isInstance(obj))
      throw new InvalidPRIException("Invalid PRI: expect type: "+priTemplate);
    this.pris.put(new Byte(prid), obj);
  }

  public void removePRI(byte prid) {
    this.pris.remove(new Byte(prid));
  }

  public Enumeration getPRIs() {
    return pris.elements();
  }

  public boolean hasPRI(byte prid) {
    return pris.containsKey(new Byte(prid));
  }

  public int countPRIs() {
    return pris.size();
  }

  /**
   * Check if the given PRID is an instance of a given PRC index.
   * Just check by comparing both arrays up to the length of PRID - 1
   */
  public static boolean isMemberOf(byte[] prcIndex, byte[] prid) {
    if ((prcIndex == null) || (prid == null)) return false;
    if (prcIndex.length != (prid.length - 1)) return false;
    for (int i = 0; i < prcIndex.length; i++) {
      if (prcIndex[i] != prid[i]) return false;
    }
    return true;
  }
}
