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
 * Kelas ini bertanggung jawab untuk menghasilkan komentar Javadoc
 * dalam file Java. Ini akan membaca file input, menambahkan komentar, dan menyimpan hasilnya ke
 * file output.
 *
 * <p>Kelas ini tidak dapat diubah atau diwarisi.
 *
 * <p>Note! file ini tidak akan diikutsertakan dalam proses build
 *
 * @author [Mrezadwiprasetiawan]
 * @version 1.0
 */
public final class GenJavadocComment {
  private static ArrayList<String> inputLines = new ArrayList<>();

  /**
   * Metode utama untuk menjalankan aplikasi.
   *
   * @param args Argumen baris perintah yang berisi jalur file input dan output.
   * @throws IOException Jika terjadi kesalahan saat membaca atau menulis file.
   */
  public static void main(String[] args) throws IOException {
    String input = "";
    String output = "";

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
    }

    System.out.println("input path = " + input);
    System.out.println("output path = " + output);

    BufferedReader reader = new BufferedReader(new FileReader(input));

    String buffer;

    // Baca setiap baris dari file input
    String classname = "";
    System.out.println("reading line");
    while ((buffer = reader.readLine()) != null) {
      boolean before = false;
      if (buffer.contains("class"))
        for (String val : buffer.split(" ")) {
          if (before) {
            classname = val;
            before = false;
          }
          if (val.equals("class")) before = true;
        }
      inputLines.add(buffer);
    }
    reader.close();

    boolean first = true;
    BufferedWriter writer = new BufferedWriter(new FileWriter(output));
    System.out.println("writing line");
    for (String inputLine : inputLines) {
      if (first) {
        writer.write(
            "//digenerasi ulang oleh id.pras.jvmlang.GenJavadocComment pada "
                + getTimeString()
                + " GMT +7\n");
        first = false;
      }
      if (inputLine.contains("public static final")) {
        String indent = "";
        int c = 0;
        while (inputLine.substring(c).startsWith(indent)) {
          indent += "  ";
          c++;
        }
        if (!inputLine.contains("new")) {
          String[] inputsLine = inputLine.split(" ");
          String varName = "";
          c = 0;
          for (String input_Line : inputsLine) {
            if (input_Line.equals("final")) varName = inputsLine[c + 2];
            c++;
          }
          writer.write(
              indent + "/** {@value #" + varName + "} */\n");
        } else {
          writer.write(
              indent + "/** " + "Konstan Object untuk {@code " + classname + "}" + " */\n");
        }
      } else if (inputLine.contains("public") && !inputLine.contains(classname)) {
        String[] inputsLine = inputLine.split(" ");
        String typename = "";
        String varName = "";
        int c = 0;
        for (String input_Line : inputsLine) {
          if (input_Line.equals("public")) {
            typename = inputsLine[c + 1];
            varName = inputsLine[c + 2];
          }
          c++;
        }
        writer.write("/** @return " + typename + " " + varName + " */\n");
      }
      writer.write(inputLine + "\n");
      writer.flush();
    }
    System.out.println("successfully");
    System.out.println("saved to " + output);
    writer.close();
  }

  /**
   * Memeriksa apakah argumen adalah opsi bantuan.
   *
   * @param s Argumen yang akan diperiksa.
   * @return True jika argumen adalah opsi bantuan, false jika tidak.
   */
  private static boolean isHelp(String s) {
    return s.equals("--help") || s.equals("-h");
  }

  /**
   * Memeriksa apakah argumen adalah opsi input.
   *
   * @param s Argumen yang akan diperiksa.
   * @return True jika argumen adalah opsi input, false jika tidak.
   */
  private static boolean isInput(String s) {
    return s.equals("--input") || s.equals("-i");
  }

  /**
   * Memeriksa apakah argumen adalah opsi output.
   *
   * @param s Argumen yang akan diperiksa.
   * @return True jika argumen adalah opsi output, false jika tidak.
   */
  private static boolean isOutput(String s) {
    return s.equals("--output") || s.equals("-o");
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

  /** Menampilkan bantuan untuk pengguna. */
  private static void printHelp() {
    System.out.println("Generator public static final int value for java\n");
    System.out.println("-h or --help print this help");
    System.out.println("-i or --input <input path> tell where the input file");
    System.out.println("-o or --output <output path> tell where the genText will generate");
  }

  /**
   * Mengambil string waktu saat ini dalam format tertentu.
   *
   * @return String waktu saat ini.
   */
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
