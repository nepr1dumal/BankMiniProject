import Bank.*;
import UI.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Account main = new Account();
        Contacts contacts = new Contacts();
        Stocks stocks = new Stocks();
        StocksPortfolio stocksPortfolio = new StocksPortfolio(stocks);
        CreditInfo creditInfo = new CreditInfo();
        ArrayList<Saveable> saveables = new ArrayList<>();
        saveables.add(main);
        saveables.add(contacts);
        saveables.add(stocks);
        saveables.add(stocksPortfolio);
        saveables.add(creditInfo);
        Scanner scan = new Scanner(System.in);
        Menu menu = new Menu(scan, main, contacts, stocks, stocksPortfolio, creditInfo);
        for (Saveable saveable : saveables) {
            saveable.loadFromFile();
        }
        boolean flag = true;
        System.out.println("Добро пожаловать!");
        while(flag){
            System.out.println("*** МойБанк ***\n1. Новая операция\n2. Контакты\n3. Акции\n4. Кредит\n5. Показать историю и баланс\n6. Очистить историю\n7. Выйти из программы");
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
                    menu.creditOperation();
                    break;
                case "5":
                    menu.showHistoryAndBalance();
                    break;
                case "6":
                    menu.clearHistory();
                    break;
                case "7":
                    for (Saveable saveable : saveables) {
                        saveable.saveToFile();
                    }
                    menu.exit();
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.\n");
            }
        }
    }
}