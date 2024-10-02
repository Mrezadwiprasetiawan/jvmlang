package id.pras.jvmlang.bytecode;

/**
 * Kelas {@code AccessFlags} dihasilkan oleh kelas id.pras.jvmlang.bytecode.Gen
 * yang dieksekusi dari gradle task GenJavaFiles
// digenerasi pada Time: 01:57:24, Date: 03/10/2024 GMT+7
public final class AccessFlags {
  public static final int SIZE = 4;
  public static final int ACC_PUBLIC = 0x0001;
  public static final String acc_public_ = "ACC_PUBLIC";
  public static final int ACC_PRIVATE = 0x0002;
  public static final String acc_private_ = "ACC_PRIVATE";
  public static final int ACC_PROTECTED = 0x0004;
  public static final String acc_protected_ = "ACC_PROTECTED";
  public static final int ACC_STATIC = 0x0008;
  public static final String acc_static_ = "ACC_STATIC";
  public static final int ACC_FINAL = 0x0010;
  public static final String acc_final_ = "ACC_FINAL";
  public static final int ACC_SYNCHRONIZED = 0x0020;
  public static final String acc_synchronized_ = "ACC_SYNCHRONIZED";
  public static final int ACC_VOLATILE = 0x0040;
  public static final String acc_volatile_ = "ACC_VOLATILE";
  public static final int ACC_TRANSIENT = 0x0080;
  public static final String acc_transient_ = "ACC_TRANSIENT";
  public static final int ACC_NATIVE = 0x0100;
  public static final String acc_native_ = "ACC_NATIVE";
  public static final int ACC_INTERFACE = 0x0200;
  public static final String acc_interface_ = "ACC_INTERFACE";
  public static final int ACC_ABSTRACT = 0x0400;
  public static final String acc_abstract_ = "ACC_ABSTRACT";
  public static final int ACC_STRICT = 0x1000;
  public static final String acc_strict_ = "ACC_STRICT";
  public static final int ACC_SYNTHETIC = 0x8000;
  public static final String acc_synthetic_ = "ACC_SYNTHETIC";
  public static final int ACC_ANNOTATION = 0x4000;
  public static final String acc_annotation_ = "ACC_ANNOTATION";
  public static final int ACC_ENUM = 0x2000;
  public static final String acc_enum_ = "ACC_ENUM";
  public static final AccessFlags Acc_public = new AccessFlags(acc_public_, ACC_PUBLIC);
  public static final AccessFlags Acc_private = new AccessFlags(acc_private_, ACC_PRIVATE);
  public static final AccessFlags Acc_protected = new AccessFlags(acc_protected_, ACC_PROTECTED);
  public static final AccessFlags Acc_static = new AccessFlags(acc_static_, ACC_STATIC);
  public static final AccessFlags Acc_final = new AccessFlags(acc_final_, ACC_FINAL);
  public static final AccessFlags Acc_synchronized = new AccessFlags(acc_synchronized_, ACC_SYNCHRONIZED);
  public static final AccessFlags Acc_volatile = new AccessFlags(acc_volatile_, ACC_VOLATILE);
  public static final AccessFlags Acc_transient = new AccessFlags(acc_transient_, ACC_TRANSIENT);
  public static final AccessFlags Acc_native = new AccessFlags(acc_native_, ACC_NATIVE);
  public static final AccessFlags Acc_interface = new AccessFlags(acc_interface_, ACC_INTERFACE);
  public static final AccessFlags Acc_abstract = new AccessFlags(acc_abstract_, ACC_ABSTRACT);
  public static final AccessFlags Acc_strict = new AccessFlags(acc_strict_, ACC_STRICT);
  public static final AccessFlags Acc_synthetic = new AccessFlags(acc_synthetic_, ACC_SYNTHETIC);
  public static final AccessFlags Acc_annotation = new AccessFlags(acc_annotation_, ACC_ANNOTATION);
  public static final AccessFlags Acc_enum = new AccessFlags(acc_enum_, ACC_ENUM);
  private String name;
  private int code;

  private AccessFlags(String name, int code){
    this.name=name;
    this.code=code;
  }
  public String getName() {
    return this.name;
  }
  public int getCode() {
    return this.code;
  }

  private AccessFlags(){}
}
