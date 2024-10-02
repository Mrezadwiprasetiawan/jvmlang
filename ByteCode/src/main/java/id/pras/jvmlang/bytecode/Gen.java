package id.pras.jvmlang.bytecode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;

/**
 * Kelas {@code Gen} adalah generator yang bertanggung jawab untuk menghasilkan file-file Java yang
 * merepresentasikan setiap instruksi dalam biner dari bytecode Java. Kelas ini membaca input dari
 * file dan menghasilkan output ke file lain, serta menyediakan opsi untuk mengkonfigurasi perilaku
 * generasi berdasarkan argumen baris perintah.
 *
 * <p>Proses generasi termasuk membaca file input, mengidentifikasi instruksi, dan menghasilkan file
 * output yang berisi deklarasi kelas Java dengan variabel konstan yang sesuai.
 *
 * <p>Kelas ini tidak dapat diubah atau diwarisi.
 *
 * <p>Note! file ini tidak akan diikutsertakan dalam proses build
 *
 * @author [Mrezadwiprasetiawan]
 * @version 1.0
 */
public final class Gen {
  /**
   * Metode utama untuk menjalankan aplikasi.
   *
   * @param args Argumen baris perintah yang berisi jalur file input dan output serta opsi lain.
   * @throws IOException Jika terjadi kesalahan saat membaca atau menulis file.
   */
  public static void main(String[] args) throws IOException {
    String input = "";
    String output = "";
    ArrayList<String> vars = new ArrayList<>();
    ArrayList<String> vals = new ArrayList<>();
    ArrayList<String> genVars = new ArrayList<>();
    ArrayList<String> genVals = new ArrayList<>();
    ArrayList<String[]> fields = null;
    ArrayList<Integer[]> fieldsSizes = null;
    ArrayList<String[]> fieldsNames = null;
    boolean isConstantPool = false;
    boolean isHeader = false;
    boolean isAccessFlags = false;
    String packageName = "";

    if (args.length == 0) {
      System.out.println("--help atau -h untuk bantuan");
      return;
    }
    /**
     * Check for each argumen -i for input path -o for output path -cp for mark is constant pool
     * because i want to make generator have different behaviour when generate constant pool
     */
    for (int i = 0; i < args.length; i++) {
      System.out.print("Identify args at Index : ");
      if (i == 0) sp(String.valueOf(i));
      else lsp(String.valueOf(i));
      String arg = args[i];
      if (isHelp(arg)) {
        cp();
        printHelp();
        return;
      }
      if (isInput(arg)) {
        if (i + 1 < args.length && !isHelp(args[i + 1]) && !isOutput(args[i + 1])) {
          input = args[i + 1];
        } else {
          cp();
          System.out.println("require input path after " + args[i]);
          return;
        }
      }
      if (isOutput(arg)) {
        if (i + 1 < args.length && !isHelp(args[i + 1]) && !isInput(args[i + 1])) {
          output = args[i + 1];
        } else {
          cp();
          System.out.println("require output path after " + args[i]);
          return;
        }
      }

      if (isPackage(arg)) {
        if (i + 1 < args.length && !isHelp(args[i + 1]) && !isInput(args[i + 1]))
          packageName = args[i + 1];
        else {
          cp();
          System.out.println("require package name after " + args[i]);
          return;
        }
      }

      if (isHeader(arg)) isHeader = true;
      if (isConstantPool(arg)) isConstantPool = true;
      if (isAccessFlags(arg)) isAccessFlags = true;
      if (i == args.length - 1) cp();
    }
    // if constant pool then init fields
    if (isConstantPool) {
      fields = new ArrayList<>();
      fieldsSizes = new ArrayList<>();
      fieldsNames = new ArrayList<>();
    }

    // print input and output path that was set
    System.out.println("input = " + input + "\noutput = " + output);

    BufferedReader reader = new BufferedReader(new FileReader(input));

    String buffer;
    int count = 0;
    // Identify per line
    while ((buffer = reader.readLine()) != null) {
      System.out.print("Identify line at Index : ");
      if (count == 0) sp(String.valueOf(count));
      else lsp(String.valueOf(count));

      String[] buffers = buffer.split(" ");
      String var = buffers[0];
      vars.add(var);
      String val = buffers[1];
      vals.add(val);

      // if its constant pool copy the field
      if (isConstantPool) {
        int offset = 2;
        String[] field = new String[buffers.length - offset];
        System.arraycopy(buffers, offset, field, 0, field.length);
        fields.add(field);
      }
      count++;
    }
    reader.close();

    // clear stdout progress line
    cp();

    BufferedWriter writer = new BufferedWriter(new FileWriter(output));
    String className = output.substring(output.lastIndexOf("/") + 1, output.length() - 5);
    // write comment to mark that file output was generated by this program
    writer.write(
        packageName.isEmpty()
            ? "// define your package here\n\n"
            : "package " + packageName + ";\n\n");
    writer.write(
        "/**\n * Kelas {@code "
            + className
            + "} dihasilkan oleh kelas id.pras.jvmlang.bytecode.Gen\n"
            + " * yang dieksekusi dari gradle task GenJavaFiles\n");
    writer.write("// digenerasi pada " + getTimeString() + " GMT+7\n");

    // start generating
    // declare as final because I don't want to make it inheritable
    writer.write("public final class " + className + " {\n");

    if (!isHeader && !isAccessFlags) writer.write("  public static final int SIZE = 1;\n");
    else writer.write("  public static final int SIZE = 4;\n");

    // loop for each input file line to generate corresponding output
    for (int i = 0; i < vars.size(); i++) {
      System.out.print("Generate constant at Index : ");
      if (i == 0) sp(String.valueOf(i));
      else lsp(String.valueOf(i));

      // generate pair
      String genVar = vars.get(i).toUpperCase();
      genVars.add(genVar);
      writer.write("  public static final int " + genVar + " = 0x" + vals.get(i) + ";\n");

      // add _ to make it  cant conflict with internal java keyword
      String genVal = vars.get(i).toLowerCase() + "_";
      genVals.add(genVal);
      writer.write("  public static final String " + genVal + " = \"" + vars.get(i) + "\";\n");

      if (isConstantPool) {
        Integer[] fieldSizes = new Integer[fields.get(i).length / 2];
        String[] fieldNames = new String[fieldSizes.length];
        for (int j = 0; j < fields.get(i).length; j += 2) {
          // generate pair fields
          String sizeS = fields.get(i)[j];
          String name = fields.get(i)[j + 1];
          StringBuilder sb = new StringBuilder(name);
          while (name.contains("[")) {
            sb.deleteCharAt(name.indexOf("["));
            name = sb.toString();
          }
          while (name.contains("]")) {
            sb.deleteCharAt(name.indexOf("]"));
            name = sb.toString();
          }
          int sizeI = sizeS.charAt(1) - '0';
          fieldSizes[j / 2] = sizeI;
          fieldNames[j / 2] = name;

          writer.write(
              "  public static final int "
                  + vars.get(i).toUpperCase()
                  + "_"
                  + name.toUpperCase()
                  + "_SIZE = "
                  + sizeI
                  + ";\n");
          writer.write(
              "  public static final String "
                  + vars.get(i).toLowerCase()
                  + "_"
                  + name.toLowerCase()
                  + " = \""
                  + name
                  + "\";\n");
        }
        fieldsSizes.add(fieldSizes);
        fieldsNames.add(fieldNames);
      }
      writer.flush();
    }

    // clear stdout progress line
    cp();

    // generate Object
    if (!isConstantPool && !isHeader) {
      for (int i = 0; i < genVars.size(); i++) {
        String var =
            Character.toUpperCase(vars.get(i).charAt(0)) + vars.get(i).substring(1).toLowerCase();
        writer.write(
            "  public static final "
                + className
                + " "
                + var
                + " = new "
                + className
                + "("
                + genVals.get(i)
                + ", "
                + genVars.get(i)
                + ");\n");
      }
    } else if (isConstantPool) {
      for (int i = 0; i < genVars.size(); i++) {
        String var = Character.toUpperCase(vars.get(i).charAt(0)) + vars.get(i).substring(1);
        String resIntArr = "new int[]{";
        String resStrArr = "new String[]{";
        for (Integer integer : fieldsSizes.get(i)) resIntArr += integer + ", ";
        for (String s : fieldsNames.get(i)) resStrArr += "\"" + s + "\"" + ", ";
        writer.write(
            "  public static final "
                + className
                + " "
                + var
                + " = new "
                + className
                + "("
                + genVals.get(i)
                + ", "
                + genVars.get(i)
                + ","
                + resIntArr.substring(0, resIntArr.length() - 1)
                + "}, "
                + resStrArr.substring(0, resStrArr.length() - 1)
                + "}"
                + ");\n");
      }
    } else if (isHeader) {
      for (int i = 2; i < vars.size() - 2; i += 2) {
        String var = Character.toUpperCase(vars.get(i).charAt(0)) + vars.get(i).substring(1);

        writer.write(
            "  public static final "
                + className
                + " Java"
                + var.substring(var.indexOf("_"))
                + " = new "
                + className
                + "("
                + genVals.get(i)
                + ", "
                + genVars.get(i)
                + ","
                + genVals.get(i + 1)
                + ", "
                + genVars.get(i + 1)
                + ");\n");
      }
    }

    // generate encapsulation vars and constructor
    if (!isConstantPool && !isHeader) {
      writer.write("  private String name;\n");
      writer.write("  private int code;\n");

      // private because this class is a singleton
      writer.write(
          "\n  private "
              + className
              + "(String name, int code){\n    this.name=name;\n    this.code=code;\n  }\n");
      // getter and setter
      writer.write(genGet("String", "name", 2));
      writer.write(genGet("int", "code", 2));
    } else if (isConstantPool) {
      writer.write("  private String name;\n");
      writer.write("  private int tag;\n");
      writer.write("  private int[] fieldSizes;\n");
      writer.write("  private String[] fields;\n");
      writer.write(
          "\n  private "
              + className
              + "(String name, int tag, int[] fieldSizes, String[] fields){\n    this.name=name;\n    this.tag=tag;\n    this.fieldSizes=fieldSizes;\n    this.fields=fields;\n  }\n");
      // getter and setter
      writer.write(genGet("String", "name", 2));
      writer.write(genGet("int", "tag", 2));
      writer.write(genGet("int[]", "fieldSizes", 2));
      writer.write(genGet("String[]", "fields", 2));
    } else if (isHeader) {
      writer.write("  private String minor_version_name;\n");
      writer.write("  private String major_version_name;\n");
      writer.write("  private int minor_version;\n");
      writer.write("  private int major_version;\n");
      writer.write(
          "\n  private "
              + className
              + "(String minor_version_name, int minor_version, String major_version_name, int major_version)");
      writer.write(
          "{  this.minor_version_name = minor_version_name;\n  this.minor_version=minor_version;\n  this.major_version_name=major_version_name;\n  this.major_version=major_version;\n  }\n");
      writer.write(genGet("String", "minor_version_name", 2));
      writer.write(genGet("int", "minor_version", 2));
      writer.write(genGet("String", "major_version_name", 2));
      writer.write(genGet("int", "major_version", 2));
    }
    writer.write("\n  private " + className + "(){}\n");

    // close bracket of the class
    writer.write("}\n");

    // flush and close
    writer.flush();
    writer.close();

    System.out.println("Done!");
    System.out.println("saved to " + output);
  }

  private static boolean isHelp(String s) {
    return s.equals("--help") || s.equals("-h");
  }

  private static boolean isConstantPool(String s) {
    return s.equals("-cp") || s.equals("--constant-pool");
  }

  private static boolean isInput(String s) {
    return s.equals("--input") || s.equals("-i");
  }

  private static boolean isOutput(String s) {
    return s.equals("--output") || s.equals("-o");
  }

  private static boolean isPackage(String s) {
    return s.equals("--package") || s.equals("-p");
  }

  private static boolean isHeader(String s) {
    return s.equals("-H") || s.equals("-header");
  }

  private static boolean isAccessFlags(String s) {
    return s.equals("-af") || s.equals("--access-flags");
  }

  private static void printHelp() {
    System.out.println("Generator public static final int value for java\n");
    System.out.println("-h or --help print this help");
    System.out.println("-i or --input <input path> tell where the input file");
    System.out.println("-o or --output <output path> tell where the genText will generate");
    System.out.println(
        "-cp or --constant-pool to make Generator generate constant pool instead of some pairing variable");
    System.out.println("-p or --package <package-name> what is your package name?");
    System.out.println(
        "-H or --header to make Generator  generate Header instead of some pairing variable");
    System.out.println(
        "-af or --access-flags to make Generator  generate Access Flags instead of some pairing variable");
  }

  // load cursor pos -> save -> print
  private static void lsp(String s) {
    System.out.print("\33[u\33[s" + s);
  }

  // clear line
  private static void cp() {
    System.out.print("\33[2K\33[G");
  }

  // save cursor -> print
  private static void sp(String s) {
    System.out.print("\33[s" + s);
  }

  // fixed by chatGpt
  private static String genGet(String type, String name, int indent) {
    StringBuilder result = new StringBuilder();
    StringBuilder sb = new StringBuilder(name);

    // Mengganti huruf pertama menjadi huruf kapital
    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    String cap = sb.toString();

    // Membuat string indentasi
    String indentStr = " ".repeat(indent);

    // Menambahkan kode getter
    result
        .append(indentStr)
        .append("public ")
        .append(type)
        .append(" get")
        .append(cap)
        .append("() {\n")
        .append(indentStr)
        .append("  return this.")
        .append(name)
        .append(";\n")
        .append(indentStr)
        .append("}\n");

    return result.toString();
  }

  private static String getTimeString() {
    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.ofHours(7));

    // Mendapatkan komponen waktu
    int year = now.getYear();
    int month =
        now.getMonthValue(); // Mengembalikan nilai bulan dari 1 (Januari) hingga 12 (Desember)
    int day = now.getDayOfMonth(); // Mengembalikan tanggal
    int hour = now.getHour(); // Mengembalikan jam
    int minute = now.getMinute(); // Mengembalikan menit
    int second = now.getSecond(); // Mengembalikan detik

    // Mengonversi ke string
    return String.format(
        "Time: %02d:%02d:%02d, Date: %02d/%02d/%d", hour, minute, second, day, month, year);
  }
}
