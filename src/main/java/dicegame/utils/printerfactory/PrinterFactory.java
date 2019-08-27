package dicegame.utils.printerfactory;

public final class PrinterFactory { //NOPMD

    public Printer getPrinter(final String printerType) {
        switch (printerType.toUpperCase()) {
            case "CONSOLE":
                return new ConsolePrinter(); //NOPMD
            case "TEXTFILE":
                return new TextFilePrinter(); //NOPMD
            case "LOGFILE":
                return new LogFilePrinter(); //NOPMD
            default:
                return null;

        }
    }
}
