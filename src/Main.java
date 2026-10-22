import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Account main = new Account();
        main.loadFromFile();
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("Добро пожаловать!");
        while(flag){
            System.out.println("*** МойБанк ***\n1. Добавить доход\n2. Добавить расход\n3. Показать историю и балланс\n4. Очистить историю\n5. Выйти из программы");
            String choise = scan.nextLine();
            switch(choise) {
                case "1":
                    System.out.print("Введите операцию в формате:\nСумма: ");
                    try {
                        String summaStr = scan.nextLine();
                        float b = Float.parseFloat(summaStr);
                        System.out.print("Название: ");
                        String nazv = scan.nextLine();
                        System.out.print("Дата (формат DD/MM/YY): ");
                        String date = scan.nextLine();
                        Transaction trans = new Transaction(b, nazv, date);
                        main.newTransaction(trans.getValue(), trans.getName(), trans.getDate());
                        System.out.println("Операция успешно завершена.");
                    } catch (Exception e) {
                        System.out.println("Ошибка! Нужно ввести число.");
                        scan.nextLine();
                    }
                    break;
                case "2":
                    System.out.print("Введите операцию в формате:\nСумма: ");
                    try {
                        String summaStr = scan.nextLine();
                        float b = Float.parseFloat(summaStr);
                        b = 0 - b;
                        System.out.print("Название: ");
                        String nazv = scan.nextLine();
                        System.out.print("Дата (формат DD/MM/YY): ");
                        String date = scan.nextLine();
                        if(main.tryTranscaction(b)){
                            System.out.println("Операция успешно завершена.");
                            Transaction trans = new Transaction(b, nazv, date);
                            main.newTransaction(trans.getValue(), trans.getName(), trans.getDate());
                        } else {
                            System.out.println("Отклонено, недостаточно средств");
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка! Нужно ввести число.");
                        scan.nextLine();
                    }
                    break;
                case "3":
                    System.out.println("Сумма | Название | Дата");
                    System.out.println(main.getHistory());
                    System.out.println("Баланс: " + main.getWallet() + " RUB");
                    break;
                case "4":
                    System.out.println("Вы точно хотите отчистить историю? Y/N");
                    String a = scan.nextLine();
                    switch (a){
                        case "Y":
                            main.clearHistory();
                            System.out.println("Успешно");
                            break;
                        case "N":
                            System.out.println("Возвращение...");
                            break;
                        default:
                            System.out.println("Неверная операция");
                    }
                    break;
                case "5":
                    main.saveToFile();
                    flag = false;
                    System.out.println("Всего доброго!");
                    break;

                default:
                    System.out.println("Неверный выбор, попробуйте снова.\n");
            }
        }

    }
}