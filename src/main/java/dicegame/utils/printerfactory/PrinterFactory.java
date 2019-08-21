package dicegame.utils.printerfactory;

public class PrinterFactory {
    public Printer getPrinter(String printerType) {
        switch (printerType.toUpperCase()){
            case "CONSOLE" :
                return new ConsolePrinter();
            case "TEXTFILE":
                return new TextFilePrinter();
            case "LOGFILE":
                return new LogFilePrinter();
                default: return null;

        }
    }
}
