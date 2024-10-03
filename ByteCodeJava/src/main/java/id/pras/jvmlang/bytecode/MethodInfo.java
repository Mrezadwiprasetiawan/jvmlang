package id.pras.jvmlang.bytecode;

public class MethodInfo {
  private AccessFlags[] accessflags;
  private short name_index;
  private short descriptor_index;
  private Attribute[] attributes;
  
  //unset attribute count
  public MethodInfo(AccessFlags[] accessflags, short name_index, short descriptor_index, short attributeCount){
    this.accessflags=accessflags;
    this.name_index=name_index;
    this.descriptor_index= descriptor_index;
    this.attributes=new Attribute[attributeCount];
  }
  
  public MethodInfo(AccessFlags[] accessflags, short name_index, short descriptor_index, Attribute[] attributes){
    this.accessflags=accessflags;
    this.name_index=name_index;
    this.descriptor_index= descriptor_index;
    this.attributes=attributes;
  }
}
