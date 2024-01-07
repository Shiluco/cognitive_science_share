package my_code;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Tools tools = Tools.getInstance(); // Toolsのインスタンスを作成

        Algorithm01 algorithm = Algorithm01.getInstance(); // Algorithm01のインスタンスを作成

        Scanner scanner = new Scanner(System.in);

        int turnCount = 0;
        tools.endFarstAttack = false;

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
            System.out.println(tools.ourAttackTurn ? "我々の攻撃ターンです。" : "敵の攻撃ターンです。");

            if (Tools.ourAttackTurn) {

                algorithm.Think();
                tools.askOurAttackResult();
                Tools.endFarstAttack = true;
            } else {

                tools.askEnemyAction();
                algorithm.Think();
            }

            tools.ourAttackTurn = !tools.ourAttackTurn;
            turnCount++;

        }

    }
}
