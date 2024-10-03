package id.pras.jvmlang.bytecode;

public class InterfacesInfo {
  private short count;
  private short[] index;
  
  public InterfacesInfo(short count){
    this.count=count;
    this.index=new short[count];
  }
  
  public InterfacesInfo(short count, short[] index){
    this.count=count;
    this.index=index;
  }
  
  public ConstantPool getConstantPool(){
    return ConstantPool.CONSTANT_Class;
  }
  
  public void setIndex(int index_index, short value){
    this.index[index_index]=value;
  }
  
  public short getIndex(int index_index){
    return index[index_index];
  }
}
