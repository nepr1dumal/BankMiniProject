import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Account main = new Account();
        Contacts contacts = new Contacts();
        Scanner scan = new Scanner(System.in);
        Menu menu = new Menu(scan, main, contacts);
        main.loadFromFile();
        contacts.loadContacts();
        boolean flag = true;
        System.out.println("Добро пожаловать!");
        while(flag){
            System.out.println("*** МойБанк ***\n1. Новая операция\n2. Контакты\n3. Показать историю и баланс\n4. Очистить историю\n5. Выйти из программы");
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
                case "3":
                    menu.showHistoryAndBalance();
                    break;
                case "4":
                    menu.clearHistory();
                    break;
                case "5":
                    menu.exit();
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.\n");
            }
        }

    }
}