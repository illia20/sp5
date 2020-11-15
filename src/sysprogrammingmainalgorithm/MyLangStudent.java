package sysprogrammingmainalgorithm;

import JavaTeacherLib.LlkContext;
import JavaTeacherLib.Node;
import JavaTeacherLib.TableNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class MyLangStudent {
    private int axioma;
    private boolean create;
    private LinkedList<Node> language;
    private LinkedList<TableNode> lexemaTable;
    private int[] terminals;
    private int[] nonterminals;
    private int[] epsilonNerminals;
    private LlkContext[] termLanguarge;

    public MyLangStudent(String fileLang) {
        this.create = false;
        this.language = new LinkedList();
        this.lexemaTable = new LinkedList();
        this.readGrammar(fileLang);
        Iterator i$ = this.language.iterator();
        if (i$.hasNext()) {
            Node tmp = (Node)i$.next();
            int[] ii = tmp.getRoole();
            this.axioma = ii[0];
        }

        this.terminals = this.createTerminals();
        this.nonterminals = this.createNonterminals();
        this.termLanguarge = this.createTerminalLang();
    }
    public void setEpsilonNonTerminals(int[] epsilon){
        this.epsilonNerminals = epsilon;
    }
    public void printEpsilonNonTerminals(){
        System.out.println("List of eps-NonTerminals");
        if(this.epsilonNerminals != null){
            for(int i = 0; i < this.epsilonNerminals.length; i++){
                System.out.println(this.epsilonNerminals[i] + " " + this.getLexemaText(this.epsilonNerminals[i]));
            }
        }
    }
    public boolean isCreate() {
        return this.create;
    }
    public int[] createEpsilonNonterminals() {
        int[] nonterminals = new int[this.nonterminals.length];
        int count = 0;
        Iterator iter = this.language.iterator();

        Node node;
        while(iter.hasNext()) {
            node = (Node)iter.next();
            node.setTeg(0);
        }

        boolean flag;
        int i;
        loop:
        do {
            flag = false;
            iter = this.language.iterator();

            while(true) {
                int[] type;
                do {
                    if (!iter.hasNext()) {
                        continue loop;
                    }
                    node = (Node)iter.next();
                    type = node.getRoole();
                    for(i = 1; i < type.length && type[i] <= 0; ++i) {
                        int q = 0;
                        while (q < count && nonterminals[q] != type[i]){
                            q++;
                        }
                        if(q == count) break;
                    }
                } while(i != type.length);

                int q = 0;
                while(q < count && nonterminals[q] != type[0]){
                    q++;
                }
                if (q == count) {
                    nonterminals[count++] = type[0];
                    flag = true;
                }
            }
        } while(flag);

        int[] result = new int[count];

        for(i = 0; i < count; ++i) {
            result[i] = nonterminals[i];
        }

        return result;
    }
    private int[] createTerminals() {
        int count = 0;
        Iterator i$ = this.lexemaTable.iterator();

        TableNode tmp;
        while(i$.hasNext()) {
            tmp = (TableNode)i$.next();
            if (tmp.getLexemaCode() > 0) {
                ++count;
            }
        }

        int[] terminal = new int[count];
        count = 0;
        i$ = this.lexemaTable.iterator();

        while(i$.hasNext()) {
            tmp = (TableNode)i$.next();
            if (tmp.getLexemaCode() > 0) {
                terminal[count] = tmp.getLexemaCode();
                ++count;
            }
        }

        return terminal;
    }
    private int[] createNonterminals() {
        int count = 0;
        Iterator i$ = this.lexemaTable.iterator();

        TableNode tmp;
        while(i$.hasNext()) {
            tmp = (TableNode)i$.next();
            if (tmp.getLexemaCode() < 0) {
                ++count;
            }
        }

        int[] nonterminal = new int[count];
        count = 0;
        i$ = this.lexemaTable.iterator();

        while(i$.hasNext()) {
            tmp = (TableNode)i$.next();
            if (tmp.getLexemaCode() < 0) {
                nonterminal[count] = tmp.getLexemaCode();
                ++count;
            }
        }

        return nonterminal;
    }
    private LlkContext[] createTerminalLang() {
        LlkContext[] cont = new LlkContext[this.terminals.length];

        for(int ii = 0; ii < this.terminals.length; ++ii) {
            int[] trmWord = new int[]{this.terminals[ii]};
            cont[ii] = new LlkContext();
            cont[ii].addWord(trmWord);
        }

        return cont;
    }
    private void readGrammar(String fname) {
        char[] lexema = new char[180];
        int[] roole = new int[80];

        BufferedReader s;
        try {
            s = new BufferedReader(new FileReader(fname));
        } catch (FileNotFoundException var24) {
            System.out.print("File: " + fname + "not found\n");
            this.create = false;
            return;
        }

        for(int ii = 0; ii < lexema.length; ++ii) {
            lexema[ii] = 0;
        }

        int[] pravilo = new int[80];

        int line;
        for(line = 0; line < pravilo.length; ++line) {
            pravilo[line] = 0;
        }

        int pos = 0;
        // boolean poslex = false;
        int q = 0;
        // boolean leftLexema = false;
        int posRoole = 0;
        line = 0;
        String readline = null;
        int readpos = 0;
        int readlen = 0;

        try {
            int newLexemaCode;
            TableNode nodeTmp;
            Node nod;
            while(s.ready()) {
                if (readline == null || readpos >= readlen) {
                    readline = s.readLine();
                    if (line == 0 && readline.charAt(0) == '\ufeff') {
                        readline = readline.substring(1);
                    }

                    readlen = readline.length();
                    ++line;
                }

                for(readpos = 0; readpos < readlen; ++readpos) {
                    char litera = readline.charAt(readpos);
                    String strTmp;
                    boolean ii;
                    Iterator i$;
                    TableNode tmp;
                    switch(q) {
                        case 0:
                            if (litera == ' ' || litera == '\t' || litera == '\r' || litera == '\n' || litera == '\b') {
                                break;
                            }

                            if (readpos == 0 && posRoole > 0 && (litera == '!' || litera == '#')) {
                                nod = new Node(roole, posRoole);
                                this.language.add(nod);
                                if (litera == '!') {
                                    posRoole = 1;
                                    break;
                                }

                                posRoole = 0;
                            }

                            pos = 0;
                            pos = pos + 1;
                            lexema[pos] = litera;
                            if (litera == '#') {
                                q = 1;
                            } else if (litera == '\\') {
                                --pos;
                                q = 3;
                            } else {
                                q = 2;
                            }
                            break;
                        case 1:
                            lexema[pos++] = litera;
                            if (litera != '#' && readpos != 0) {
                                break;
                            }

                            strTmp = new String(lexema, 0, pos);
                            nodeTmp = new TableNode(strTmp, -2147483648);
                            ii = true;
                            i$ = this.lexemaTable.iterator();

                            while(i$.hasNext()) {
                                tmp = (TableNode)i$.next();
                                if (nodeTmp.equals(tmp)) {
                                    ii = false;
                                    break;
                                }
                            }

                            if (ii) {
                                this.lexemaTable.add(nodeTmp);
                            }

                            newLexemaCode = this.getLexemaCode(strTmp, -2147483648);
                            roole[posRoole++] = newLexemaCode;
                            pos = 0;
                            q = 0;
                            break;
                        case 2:
                            if (litera == '\\') {
                                --pos;
                                q = 3;
                            } else {
                                if (litera != ' ' && readpos != 0 && litera != '#' && litera != '\r' && litera != '\t') {
                                    lexema[pos++] = litera;
                                    continue;
                                }

                                strTmp = new String(lexema, 0, pos);
                                nodeTmp = new TableNode(strTmp, 268435456);
                                ii = true;
                                i$ = this.lexemaTable.iterator();

                                while(i$.hasNext()) {
                                    tmp = (TableNode)i$.next();
                                    if (nodeTmp.equals(tmp)) {
                                        ii = false;
                                        break;
                                    }
                                }

                                if (ii) {
                                    this.lexemaTable.add(nodeTmp);
                                }

                                newLexemaCode = this.getLexemaCode(strTmp, 268435456);
                                roole[posRoole++] = newLexemaCode;
                                pos = 0;
                                q = 0;
                                --readpos;
                            }
                            break;
                        case 3:
                            lexema[pos++] = litera;
                            q = 2;
                    }
                }
            }

            if (pos != 0) {
                int code;
                if (q == 1) {
                    code = -2147483648;
                } else {
                    code = 268435456;
                }

                String strTmp = new String(lexema, 0, pos);
                nodeTmp = new TableNode(strTmp, code);
                boolean ii = true;
                Iterator i$ = this.lexemaTable.iterator();

                while(i$.hasNext()) {
                    TableNode tmp = (TableNode)i$.next();
                    if (nodeTmp.equals(tmp)) {
                        ii = false;
                        break;
                    }
                }
                if (ii) {
                    this.lexemaTable.add(nodeTmp);
                }
                newLexemaCode = this.getLexemaCode(strTmp, code);
                roole[posRoole++] = newLexemaCode;
            }
            if (posRoole > 0) {
                nod = new Node(roole, posRoole);
                this.language.add(nod);
            }
            this.create = true;
        } catch (IOException e) {
            System.out.println(e.toString());
            this.create = false;
        }

    }
    private int getLexemaCode(String lexema, int lexemaClass) {
        Iterator i$ = this.lexemaTable.iterator();

        TableNode tmp;
        do {
            if (!i$.hasNext()) {
                return 0;
            }

            tmp = (TableNode)i$.next();
        } while(!tmp.getLexemaText().equals(lexema) || (tmp.getLexemaCode() & -16777216) != lexemaClass);

        return tmp.getLexemaCode();
    }
    public void printGramma() {
        int count = 0;
        Iterator i$ = this.language.iterator();

        while(i$.hasNext()) {
            Node tmp = (Node)i$.next();
            int[] roole = tmp.getRoole();
            ++count;
            System.out.print("" + count);

            for(int ii = 0; ii < roole.length; ++ii) {
                if (ii == 1) {
                    System.out.print(" ::= ");
                }

                System.out.print(this.getLexemaText(roole[ii]) + " ");
                if (roole.length == 1) {
                    System.out.print(" ::= ");
                }
            }

            System.out.println("");
        }
    }
    public String getLexemaText(int code) {
        Iterator i$ = this.lexemaTable.iterator();

        TableNode tmp;
        do {
            if (!i$.hasNext()) {
                return null;
            }

            tmp = (TableNode)i$.next();
        } while(tmp.getLexemaCode() != code);

        return tmp.getLexemaText();
    }
    public void printTerminals() {
        System.out.println("СПИСОК ТЕРМІНАЛІВ:");
        if (this.terminals != null) {
            for(int ii = 0; ii < this.terminals.length; ++ii) {
                System.out.println("" + (ii + 1) + "  " + this.terminals[ii] + "\t " + this.getLexemaText(this.terminals[ii]));
            }

        }
    }
    public void printNonterminals() {
        System.out.println("СПИСОК НЕТЕРМІНАЛІВ:");

        for(int ii = 0; ii < this.nonterminals.length; ++ii) {
            System.out.println("" + (ii + 1) + "  " + this.nonterminals[ii] + "\t " + this.getLexemaText(this.nonterminals[ii]));
        }

    }
    public int[] getTerminals(){ return this.terminals;}
    public int[] getNonTerminals(){ return this.nonterminals; }
    public boolean createNonProdRools() {
        if (this.getNonTerminals().length == 0) {
            return true;
        } else {
            int[] prodtmp = new int[this.getNonTerminals().length];
            int count = 0;
            Iterator i$ = this.language.iterator();

            Node tmp;
            while(i$.hasNext()) {
                tmp = (Node)i$.next();
                tmp.setTeg(0);
            }

            int ii;
            boolean upr;
            int[] rool1;
            label117:
            do {
                upr = false;
                i$ = this.language.iterator();

                while(true) {
                    int ii1;
                    do {
                        do {
                            if (!i$.hasNext()) {
                                continue label117;
                            }

                            tmp = (Node)i$.next();
                            rool1 = tmp.getRoole();
                        } while(tmp.getTeg() == 1);

                        for(ii = 1; ii < rool1.length; ++ii) {
                            if (rool1[ii] <= 0) {
                                for(ii1 = 0; ii1 < count && prodtmp[ii1] != rool1[ii]; ++ii1) {
                                }

                                if (ii1 == count) {
                                    break;
                                }
                            }
                        }
                    } while(ii != rool1.length);

                    for(ii1 = 0; ii1 < count && prodtmp[ii1] != rool1[0]; ++ii1) {
                    }

                    if (ii1 == count) {
                        prodtmp[count++] = rool1[0];
                    }

                    tmp.setTeg(1);
                    upr = true;
                }
            } while(upr);

            if (count == prodtmp.length) {
                System.out.print("В граматиці непродуктивні правила відсутні\n");
                return true;
            } else {
                System.out.print("Непродуктивні правила: \n");
                i$ = this.language.iterator();

                while(true) {
                    do {
                        if (!i$.hasNext()) {
                            return true;
                        }

                        tmp = (Node)i$.next();
                    } while(tmp.getTeg() == 1);

                    rool1 = tmp.getRoole();

                    for(ii = 0; ii < rool1.length; ++ii) {
                        if (ii == 1) {
                            System.out.print(" ::= ");
                        }

                        System.out.print(this.getLexemaText(rool1[ii]) + " ");
                        if (rool1.length == 1) {
                            System.out.print(" ::= ");
                        }
                    }

                    System.out.println("");
                }
            }
        }
    }
    public boolean createNonDosNeterminals() {
        int[] nonTerminals = this.getNonTerminals();
        int[] dosnerminals = new int[nonTerminals.length];
        int count = 0;
        boolean iter = false;
        if (nonTerminals == null) {
            return true;
        } else {
            Iterator i$ = this.language.iterator();
            Node tmp;
            if (i$.hasNext()) {
                tmp = (Node)i$.next();
                dosnerminals[0] = tmp.getRoole()[0];
                count = 1;
            }

            boolean upr;
            int ii;
            int ii1;
            label109:
            do {
                upr = false;
                i$ = this.language.iterator();

                while(true) {
                    int[] rool1;
                    do {
                        if (!i$.hasNext()) {
                            continue label109;
                        }

                        tmp = (Node)i$.next();
                        rool1 = tmp.getRoole();

                        for(ii = 0; ii < count && dosnerminals[ii] != rool1[0]; ++ii) {
                        }
                    } while(ii == count);

                    for(ii = 1; ii < rool1.length; ++ii) {
                        if (rool1[ii] < 0) {
                            for(ii1 = 0; ii1 < count && dosnerminals[ii1] != rool1[ii]; ++ii1) {
                            }

                            if (ii1 == count) {
                                dosnerminals[count] = rool1[ii];
                                upr = true;
                                ++count;
                            }
                        }
                    }
                }
            } while(upr);

            int help = nonTerminals.length - count;
            if (help == 0) {
                System.out.println("В граматиці недосяжних нетерміналів немає");
                return true;
            } else {
                int[] nonDosNeterminals = new int[help];
                help = 0;

                for(ii = 0; ii < nonTerminals.length; ++ii) {
                    for(ii1 = 0; ii1 < count && nonTerminals[ii] != dosnerminals[ii1]; ++ii1) {
                    }

                    if (ii1 == count) {
                        nonDosNeterminals[help++] = nonTerminals[ii];
                    }
                }

                for(ii = 0; ii < nonDosNeterminals.length; ++ii) {
                    System.out.println("Недосяжний нетермінал: " + this.getLexemaText(nonDosNeterminals[ii]) + "\n ");
                }

                return true;
            }
        }
    }
}
