//digenerasi ulang oleh id.pras.jvmlang.GenJavadocComment pada Time: 02:17:07, Date: 03/10/2024 GMT +7
package id.pras.jvmlang.bytecode;

/**
 * Kelas {@code AccessFlags} dihasilkan oleh kelas id.pras.jvmlang.bytecode.Gen
 * yang dieksekusi dari gradle task GenJavaFiles
 */
// digenerasi pada Time: 02:17:06, Date: 03/10/2024 GMT+7
public final class AccessFlags {
  /** {@value #SIZE} */
  public static final int SIZE = 4;
  /** {@value #ACC_PUBLIC} */
  public static final int ACC_PUBLIC = 0x0001;
  /** {@value #acc_public_} */
  public static final String acc_public_ = "ACC_PUBLIC";
  /** {@value #ACC_PRIVATE} */
  public static final int ACC_PRIVATE = 0x0002;
  /** {@value #acc_private_} */
  public static final String acc_private_ = "ACC_PRIVATE";
  /** {@value #ACC_PROTECTED} */
  public static final int ACC_PROTECTED = 0x0004;
  /** {@value #acc_protected_} */
  public static final String acc_protected_ = "ACC_PROTECTED";
  /** {@value #ACC_STATIC} */
  public static final int ACC_STATIC = 0x0008;
  /** {@value #acc_static_} */
  public static final String acc_static_ = "ACC_STATIC";
  /** {@value #ACC_FINAL} */
  public static final int ACC_FINAL = 0x0010;
  /** {@value #acc_final_} */
  public static final String acc_final_ = "ACC_FINAL";
  /** {@value #ACC_SYNCHRONIZED} */
  public static final int ACC_SYNCHRONIZED = 0x0020;
  /** {@value #acc_synchronized_} */
  public static final String acc_synchronized_ = "ACC_SYNCHRONIZED";
  /** {@value #ACC_VOLATILE} */
  public static final int ACC_VOLATILE = 0x0040;
  /** {@value #acc_volatile_} */
  public static final String acc_volatile_ = "ACC_VOLATILE";
  /** {@value #ACC_TRANSIENT} */
  public static final int ACC_TRANSIENT = 0x0080;
  /** {@value #acc_transient_} */
  public static final String acc_transient_ = "ACC_TRANSIENT";
  /** {@value #ACC_NATIVE} */
  public static final int ACC_NATIVE = 0x0100;
  /** {@value #acc_native_} */
  public static final String acc_native_ = "ACC_NATIVE";
  /** {@value #ACC_INTERFACE} */
  public static final int ACC_INTERFACE = 0x0200;
  /** {@value #acc_interface_} */
  public static final String acc_interface_ = "ACC_INTERFACE";
  /** {@value #ACC_ABSTRACT} */
  public static final int ACC_ABSTRACT = 0x0400;
  /** {@value #acc_abstract_} */
  public static final String acc_abstract_ = "ACC_ABSTRACT";
  /** {@value #ACC_STRICT} */
  public static final int ACC_STRICT = 0x1000;
  /** {@value #acc_strict_} */
  public static final String acc_strict_ = "ACC_STRICT";
  /** {@value #ACC_SYNTHETIC} */
  public static final int ACC_SYNTHETIC = 0x8000;
  /** {@value #acc_synthetic_} */
  public static final String acc_synthetic_ = "ACC_SYNTHETIC";
  /** {@value #ACC_ANNOTATION} */
  public static final int ACC_ANNOTATION = 0x4000;
  /** {@value #acc_annotation_} */
  public static final String acc_annotation_ = "ACC_ANNOTATION";
  /** {@value #ACC_ENUM} */
  public static final int ACC_ENUM = 0x2000;
  /** {@value #acc_enum_} */
  public static final String acc_enum_ = "ACC_ENUM";
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_public = new AccessFlags(acc_public_, ACC_PUBLIC);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_private = new AccessFlags(acc_private_, ACC_PRIVATE);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_protected = new AccessFlags(acc_protected_, ACC_PROTECTED);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_static = new AccessFlags(acc_static_, ACC_STATIC);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_final = new AccessFlags(acc_final_, ACC_FINAL);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_synchronized = new AccessFlags(acc_synchronized_, ACC_SYNCHRONIZED);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_volatile = new AccessFlags(acc_volatile_, ACC_VOLATILE);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_transient = new AccessFlags(acc_transient_, ACC_TRANSIENT);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_native = new AccessFlags(acc_native_, ACC_NATIVE);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_interface = new AccessFlags(acc_interface_, ACC_INTERFACE);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_abstract = new AccessFlags(acc_abstract_, ACC_ABSTRACT);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_strict = new AccessFlags(acc_strict_, ACC_STRICT);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_synthetic = new AccessFlags(acc_synthetic_, ACC_SYNTHETIC);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_annotation = new AccessFlags(acc_annotation_, ACC_ANNOTATION);
  /** Konstan Object untuk {@code AccessFlags} */
  public static final AccessFlags Acc_enum = new AccessFlags(acc_enum_, ACC_ENUM);
  private String name;
  private int code;

  private AccessFlags(String name, int code){
    this.name=name;
    this.code=code;
  }
/** @return String getName() */
  public String getName() {
    return this.name;
  }
/** @return int getCode() */
  public int getCode() {
    return this.code;
  }

  private AccessFlags(){}
}
