package p1012;
import java.util.Scanner;

public class Main {

    static int[][] field;
    static int[][] visited;
    static int N, M;



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();


        for (int i = 0; i < T; i++) {
            N = sc.nextInt();
            M = sc.nextInt();
            int K = sc.nextInt();

            field = new int[N][M];
            visited = new boolean[N][M];


            for(int j = 0; j < K; j++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                field[x][y] = 1;

            }



        }
    }
}
