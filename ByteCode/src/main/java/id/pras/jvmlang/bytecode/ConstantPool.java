package id.pras.jvmlang.bytecode;

/*
 * This file was generated by class id.pras.jvmlang.bytecode.Gen
 * which was executed by the gradle task GenJavaFiles
 * input path = /home/runner/work/jvmlang/jvmlang/ByteCode/sources/ConstantPool.txt
 */
// generated on Time: 16:33:51, Date: 02/10/2024 GMT+7
public final class ConstantPool {
  // for opCode this is the Size for instruction but for constant pool this is the size of the
  //constant pool tag
  public static final int SIZE = 1;
  public static final int CONSTANT_CLASS = 0x07;
  public static final String constant_class_ = "CONSTANT_Class";
  public static final int CONSTANT_FIELDREF = 0x09;
  public static final String constant_fieldref_ = "CONSTANT_Fieldref";
  public static final int CONSTANT_METHODREF = 0x0A;
  public static final String constant_methodref_ = "CONSTANT_Methodref";
  public static final int CONSTANT_INTERFACEMETHODREF = 0x0B;
  public static final String constant_interfacemethodref_ = "CONSTANT_InterfaceMethodref";
  public static final int CONSTANT_STRING = 0x08;
  public static final String constant_string_ = "CONSTANT_String";
  public static final int CONSTANT_INTEGER = 0x03;
  public static final String constant_integer_ = "CONSTANT_Integer";
  public static final int CONSTANT_FLOAT = 0x04;
  public static final String constant_float_ = "CONSTANT_Float";
  public static final int CONSTANT_LONG = 0x05;
  public static final String constant_long_ = "CONSTANT_Long";
  public static final int CONSTANT_DOUBLE = 0x06;
  public static final String constant_double_ = "CONSTANT_Double";
  public static final int CONSTANT_NAMEANDTYPE = 0x0C;
  public static final String constant_nameandtype_ = "CONSTANT_NameAndType";
  public static final int CONSTANT_UTF8 = 0x01;
  public static final String constant_utf8_ = "CONSTANT_Utf8";
  public static final int CONSTANT_METHODHANDLE = 0x0F;
  public static final String constant_methodhandle_ = "CONSTANT_MethodHandle";
  public static final int CONSTANT_METHODTYPE = 0x10;
  public static final String constant_methodtype_ = "CONSTANT_MethodType";
  public static final int CONSTANT_INVOKEDYNAMIC = 0x12;
  public static final String constant_invokedynamic_ = "CONSTANT_InvokeDynamic";
  public static final int CONSTANT_MODULE = 0x13;
  public static final String constant_module_ = "CONSTANT_Module";
  public static final int CONSTANT_PACKAGE = 0x14;
  public static final String constant_package_ = "CONSTANT_Package";
  public static final ConstantPool CONSTANT_Class = new ConstantPool(constant_class_, CONSTANT_CLASS);
  public static final ConstantPool CONSTANT_Fieldref = new ConstantPool(constant_fieldref_, CONSTANT_FIELDREF);
  public static final ConstantPool CONSTANT_Methodref = new ConstantPool(constant_methodref_, CONSTANT_METHODREF);
  public static final ConstantPool CONSTANT_InterfaceMethodref = new ConstantPool(constant_interfacemethodref_, CONSTANT_INTERFACEMETHODREF);
  public static final ConstantPool CONSTANT_String = new ConstantPool(constant_string_, CONSTANT_STRING);
  public static final ConstantPool CONSTANT_Integer = new ConstantPool(constant_integer_, CONSTANT_INTEGER);
  public static final ConstantPool CONSTANT_Float = new ConstantPool(constant_float_, CONSTANT_FLOAT);
  public static final ConstantPool CONSTANT_Long = new ConstantPool(constant_long_, CONSTANT_LONG);
  public static final ConstantPool CONSTANT_Double = new ConstantPool(constant_double_, CONSTANT_DOUBLE);
  public static final ConstantPool CONSTANT_NameAndType = new ConstantPool(constant_nameandtype_, CONSTANT_NAMEANDTYPE);
  public static final ConstantPool CONSTANT_Utf8 = new ConstantPool(constant_utf8_, CONSTANT_UTF8);
  public static final ConstantPool CONSTANT_MethodHandle = new ConstantPool(constant_methodhandle_, CONSTANT_METHODHANDLE);
  public static final ConstantPool CONSTANT_MethodType = new ConstantPool(constant_methodtype_, CONSTANT_METHODTYPE);
  public static final ConstantPool CONSTANT_InvokeDynamic = new ConstantPool(constant_invokedynamic_, CONSTANT_INVOKEDYNAMIC);
  public static final ConstantPool CONSTANT_Module = new ConstantPool(constant_module_, CONSTANT_MODULE);
  public static final ConstantPool CONSTANT_Package = new ConstantPool(constant_package_, CONSTANT_PACKAGE);
  private String name;
  private int code;

  private ConstantPool(String name, int code){
    this.name=name;
    this.code=code;
  }
  public String getName() {
    return this.name;
  }
  public int getCode() {
    return this.code;
  }

  private ConstantPool(){}
}
