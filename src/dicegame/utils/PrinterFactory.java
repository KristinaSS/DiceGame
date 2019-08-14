package dicegame.utils;

public class PrinterFactory {
    public Printer getPrinter(String printerType) {
        if (printerType == null) {
            return null;
        }
        if (printerType.equalsIgnoreCase("CONSOLE")) {
            return new ConsolePrinter();

        } else if (printerType.equalsIgnoreCase("TEXTFILE")) {
            return new TextFilePrinter();

        }
        return null;
    }
}
