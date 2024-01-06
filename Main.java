package my_code;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Tools tools = new Tools(); // Toolsのインスタンスを作成

        Algorithm01 algorithm = Algorithm01.getInstance(); // Algorithm01のインスタンスを作成

        Scanner scanner = new Scanner(System.in);

        String LastEnemyAttackPoint = "";
        String LastEnemyMovePoint = "";
        int turnCount = 0;
        Tools.endFarstAttack = false;

        System.out.println("先行:f 後攻:s");
        // 先行後攻の選択
        CONFIRM: while (true) {
            String input = scanner.nextLine();

            if ("f".equals(input)) {
                System.out.println("先行が選ばれました。");
                Tools.ourAttackTurn = true;
                break CONFIRM;

            } else if ("s".equals(input)) {
                System.out.println("後攻が選ばれました。");
                Tools.ourAttackTurn = false;
                break CONFIRM;
            } else {
                System.out.println("無効な入力です。");
            }
        }

        ENDGAME: while (true) {
            System.out.println("ターン" + (turnCount + 1));

            if (Tools.ourAttackTurn) {
                System.out.println("我々の攻撃ターンです。");

                algorithm.Think();
                tools.askOurAttackResult();
                Tools.endFarstAttack = true;
            } else {
                System.out.println("相手の攻撃ターンです。");
                System.out.println("相手の行動を入力してください。");
                CONFIRM: while (true) {
                    String input = scanner.nextLine();
                    if ("a".equals(input)) {
                        System.out.println("敵はどこに攻撃してきましたか？");
                        LastEnemyAttackPoint = scanner.nextLine();

                        Tools.ourAttackTurn = false;
                        break CONFIRM;

                    } else if ("m".equals(input)) {
                        System.out.println("敵はどの用に移動しましたか？");
                        LastEnemyMovePoint = scanner.nextLine();
                        Tools.ourAttackTurn = false;
                        break CONFIRM;
                    } else {
                        System.out.println("無効な入力です。");
                    }
                }

            }

            Tools.ourAttackTurn = !Tools.ourAttackTurn;
            turnCount++;

        }

    }
}
