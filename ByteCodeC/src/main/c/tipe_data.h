#ifndef TYPE_DATA_H
#define TYPE_DATA_H

/* Untuk memudahkan penggunaan String, disini String di dedefinisikan sebagai
 * const char*
 * Hal ini memudahkan dalam pendefinisiannya
 */
typedef const char* String;

/*
 * Struct untuk menyimpan data setiap Header untuk versi yang berbeda
 */
typedef struct{
  String version_minor_name;
  int version_minor;
  String version_major;
  int version_major;
}Header;

typedef struct{
  String tagName;
  int tag;
  String* fieldsCount;
  int* fields;
}ConstantPool;

typedef struct{
  String name;
  int code;
}OpCode;

#endif