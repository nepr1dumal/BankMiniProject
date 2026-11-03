import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Account main = new Account();
        Contacts contacts = new Contacts();
        Stocks stocks = new Stocks();
        Scanner scan = new Scanner(System.in);
        Menu menu = new Menu(scan, main, contacts, stocks);
        main.loadFromFile();
        contacts.loadContacts();
        boolean flag = true;
        System.out.println("Добро пожаловать!");
        while(flag){
            System.out.println("*** МойБанк ***\n1. Новая операция\n2. Контакты\n3. Акции\n4. Показать историю и баланс\n5. Очистить историю\n6. Выйти из программы");
            String choise = scan.nextLine();
            switch(choise) {
                case "1": {
                    menu.operations();
                    break;
                }
                case "2": {
                    menu.contactOperation();
                    break;
                }
                case "3": {
                    menu.operationStocks();
                    break;
                }
                case "4":
                    menu.showHistoryAndBalance();
                    break;
                case "5":
                    menu.clearHistory();
                    break;
                case "6":
                    menu.exit();
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.\n");
            }
        }
    }
}