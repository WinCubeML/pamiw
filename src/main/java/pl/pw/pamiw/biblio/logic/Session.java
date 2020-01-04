package pl.pw.pamiw.biblio.logic;

import java.util.Random;

public class Session {
    public static String createSessionIdForCookie() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 24; i++) {
            int n = r.nextInt(16);
            if (n < 10)
                sb.append(n);
            else {
                switch (n) {
                    case 10:
                        sb.append('A');
                        break;
                    case 11:
                        sb.append('B');
                        break;
                    case 12:
                        sb.append('C');
                        break;
                    case 13:
                        sb.append('D');
                        break;
                    case 14:
                        sb.append('E');
                        break;
                    case 15:
                        sb.append('F');
                        break;
                }
            }
        }
        return sb.toString();
    }
}
