package sysprogrammingmainalgorithm;
import java.lang.*;
import java.util.*;

import JavaTeacherLib.*;

public class MainStudent {

    public static void main(String[] args) {
        String readline;
        boolean result;
        String fileName;
        MyLang testlang = null;
        MyLangStudent lang = null;
        int codeAction, llk=1, textLen;
        String [] menu= { "*1.  Прочитати граматику з файла  ",
                " 2.  Лабораторна робота. Клас будує студент",
                " 3.  Надрукувати граматику",
                "*4.  Побудувати списки терміналів та нетерміналів",
                "*5.  Пошук непродуктивних нетерміналів",
                "*6.  Пошук недосяжних нетерміналів",
                "*7.  Побудова списку епсілон-нетерміналів",
                " 8.  Друк списку епсілон-нетерміналів",
                " 9. Вихід з системи"
        };

        Scanner scan = new Scanner(System.in);
        do  {
            codeAction=0;
            String upr;
            for (String ss: menu) System.out.println(ss); // вивести меню
            System.out.println("Введіть код дії або end:");
            do {  // цикл перебору даних
                try {
                    readline = scan.nextLine();
                    upr = readline;
                    if (upr.trim().equals("end") ) return;
                    codeAction=new Integer (upr.trim());
                }
                catch(Exception ee)
                { System.out.println ("Невірний код дії, повторіть: ");
                    continue;
                }
                if (codeAction >=1  &&  codeAction<=menu.length ) {
                    if (menu [codeAction-1].substring(0, 1).equals("+"))  {
                        System.out.println("Елемент меню " +codeAction+" повторно виконати неможливо");
                        continue ;
                    }
                    int itmp;
                    for (itmp=0; itmp < codeAction-1; itmp++)
                        if (menu[itmp].substring(0, 1).equals("*")) break;
                    if (itmp !=codeAction-1) {
                        System.out.println ("Виконайте попередні елементи меню, що позначені * : ");
                        continue ;
                    }
                    break;
                }
                else {
                    System.out.println ("Невірний код дії, повторіть: ");
                    continue ;
                }
            }  while (true);
            // перевірка на виконання усіх попередніх дій
            result=false;
            switch (codeAction) {
                case 1: //1. Прочитати граматику з файла",
                    System.out.println ("Введіть ім'я файлу граматики: ");
                    try {
                        readline = scan.nextLine();
                        fileName = readline;
                        System.out.println ("Ім'я файлу граматики: "+ fileName);
                        fileName = fileName.trim();
                    }
                    catch(Exception ee)
                    { System.out.println ("Системна помилка: "+ee.toString());
                        return;
                    }
                    System.out.println ("Введіть значення параметра k : ");
                    try {
                        readline = scan.nextLine();
                        String llkText = readline;
                        llkText = llkText.trim();
                        llk=Integer.parseInt(llkText);
                    }
                    catch(Exception ee)
                    { System.out.println ("Системна помилка: "+ee.toString());
                        return;
                    }
                    lang = new MyLangStudent(fileName);
                    // if (!lang.isCreate()) break;  //не створили об'єкт
                    testlang = new MyLang(fileName, llk);
                    System.out.println ("Граматика прочитана успішно");
                    result=true;
                    for (int jj=0;  jj<menu.length; jj++) {
                        if (menu [jj].substring(0, 1).equals(" ")) continue;
                        menu [jj]=menu [jj].replace(menu [jj].charAt(0), '*') ;
                    }
                    break;
                case 2: //2. Лабораторна робота студента
                    //  метод для скінчених автоматів
                    //Побудова списку епсілон-нетерміналів
                    int [] epsilon1 = lang.createEpsilonNonterminals();
                    lang.setEpsilonNonTerminals(epsilon1);
                    testlang.setEpsilonNonterminals(epsilon1);
                    result=true;
                    break;
                case 3:  // Надрукувати граматику
                    lang.printGramma();
                    break;
                case 4:  // надрукувати список терміналів та нетерміналів
                    lang.printTerminals();
                    lang.printNonterminals();
                    result=true;
                    break;
                case 5: // вивести непродуктивні правила
                    result=lang.createNonProdRools();
                    break;
                case 6: // недосяжні нетермінали
                    result=lang.createNonDosNeterminals();
                    break;
                case 7:  //Побудова списку епсілон-нетерміналів
                    int [] epsilon=lang.createEpsilonNonterminals ();
                    lang.setEpsilonNonTerminals(epsilon);
                    testlang.setEpsilonNonterminals(epsilon);
                    result=true;
                    break;
                case 8: //Друк списку епсілон-нетерміналів
                    lang.printEpsilonNonTerminals();
                    break;
                case 9:
                    return;
                case 27:
                    break;
            }  // кінець switch
            // блокуємо елемент обробки
            if (result) // функція виконана успішно
                if (menu [codeAction-1].substring(0, 1).equals("*"))
                    menu [codeAction-1]=menu [codeAction-1].replace('*', '+') ;
        } while (true);  //глобальний цикл  обробки

    }  // кінець main

}
