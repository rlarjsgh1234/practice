package p1002;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Try = sc.nextInt();


        for (int i = 1; i <= Try; i++) {
            int Jox = sc.nextInt();
            int Joy = sc.nextInt();
            int Ryr = sc.nextInt();
            int Bax = sc.nextInt();
            int Bay = sc.nextInt();
            int Ryr2 = sc.nextInt();

            double d = Math.sqrt(Math.pow(Jox - Bax, 2) + Math.pow(Joy - Bay, 2));

            if (d == 0 && Ryr == Ryr2) {
                System.out.println(-1);
            } else if (d > Ryr + Ryr2) {
                System.out.println(0);
            } else if (d <Math.abs(Ryr - Ryr2)) {
                System.out.println(0);
            } else if (d == Ryr + Ryr2) {
                System.out.println(1);
            } else if (d == Math.abs(Ryr -Ryr2)) {
                System.out.println(1);
            } else {
                System.out.println(2);
            }
        }


    }
}