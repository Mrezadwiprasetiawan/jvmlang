package id.pras.jvmlang.bytecode;

import java.util.ArrayList;

public final class Class {
  private Header header;
  private short constantpoolcount;
  private ConstantPool constantpool;
  private AccessFlags[] accessflags;
  private short this_class;
  private short super_class;
  private short interface_count;
  private short methodcount;
  private ArrayList<OpCode[]>opcode;
  
  //unimplemented yet
  public Class(){
    
  }
}
