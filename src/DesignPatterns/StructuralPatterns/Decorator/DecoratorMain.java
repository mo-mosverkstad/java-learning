package DesignPatterns.StructuralPatterns.Decorator;

public class DecoratorMain {

    public static void main(String[] args) {
        String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";

        DataSource dataSource = new CompressionDecorator(new EncryptionDecorator(new FileDataSource("test.txt")));
        // DataSource dataSource = new EncryptionDecorator(new CompressionDecorator(new FileDataSource("test.txt")));
        writeToFile(dataSource, salaryRecords);
        readFromFile(dataSource);
    }


    private static void writeToFile(DataSource dataSource, String data) {
        System.out.println("--------- original data ---------");
        System.out.println(data);
        System.out.println("-----------------------------------");
        dataSource.writeData(data);
    }

    private static void readFromFile(DataSource dataSource) {
        System.out.println("--------- data in file ---------");
        String data = dataSource.readData();
        System.out.println("-----------------------------------");
        System.out.println(data);
    }
}
