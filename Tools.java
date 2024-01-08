
package my_code;

import java.util.*;
import java.io.*;

class Tools {

    private static Tools instance;

    public static int ourLastAttackResult;
    public static int enemyLastAttackResult;
    public static String ourLastMovePosition;
    public static String enemyLastMovePosition;
    private int[][] enemyValue;
    private int[][] ourValue;
    public static boolean ourAttackTurn;
    public static boolean endFarstAttack;
    public static boolean IsEnemyAttack;

    int[][] ourValues = new int[5][5];
    int[][] enemyValues = new int[5][5];
    int[][] enableAttackPoints = new int[5][5];

    private static String lastOurAttackPoint;

    Scanner scanner = new Scanner(System.in);

    private Random random = new Random();
    private String[] coordinates = { "B2", "B4", "D2", "D4" };
    public EnemyShip enemyShip;

    private Ship ourShip01;
    private Ship ourShip02;
    private Ship ourShip03;
    private Ship ourShip04;

    private String enemyLastAttackPoint;
    private String enemyLastMovePoint;

    private Tools() {
        for (int i = 0; i < ourValues.length; i++) {
            for (int j = 0; j < ourValues[i].length; j++) {
                ourValues[i][j] = 0;
                enemyValues[i][j] = 0;
                enableAttackPoints[i][j] = 0;
            }
        }

        enemyLastAttackPoint = "HH";
        enemyLastMovePoint = "";

        lastOurAttackPoint = "HH";
        ourLastAttackResult = 0;
        enemyLastAttackPoint = "HH";
        enemyLastAttackResult = 0;

        ourShip01 = new Ship(true, "A4", 3);
        ourShip02 = new Ship(true, "B1", 3);
        ourShip03 = new Ship(true, "D5", 3);
        ourShip04 = new Ship(true, "E2", 3);

        enemyShip = new EnemyShip(4, 12);
        IsEnemyAttack = true;
    }

    public static Tools getInstance() {
        // インスタンスがまだ作成されていない場合にのみ作成
        if (instance == null) {
            instance = new Tools();
        }
        return instance;
    }

    /// getterとsetter///

    public String getEnemyLastAttackPoint() {
        return enemyLastAttackPoint;
    }

    // enemyLastAttackPointのセッター
    public void setEnemyLastAttackPoint(String enemyLastAttackPoint) {
        this.enemyLastAttackPoint = enemyLastAttackPoint;
    }

    // enemyLastMovePointのゲッター
    public String getEnemyLastMovePoint() {
        return enemyLastMovePoint;
    }

    // enemyLastMovePointのセッター
    public void setEnemyLastMovePoint(String enemyLastMovePoint) {
        this.enemyLastMovePoint = enemyLastMovePoint;
    }

    public void setOurValues(int[][] ourValues) {
        this.ourValues = ourValues;
    }

    public int[][] getOurValues() {
        return ourValues;
    }

    public void setEnemyValues(int[][] enemyValues) {
        this.enemyValues = enemyValues;
    }

    public int[][] getEnemyValues() {
        return enemyValues;
    }

    public String getLastOurAttackPoint() {
        return lastOurAttackPoint;
    }

    public void setLastOurAttackPoint(String lastOurAttackPoint) {

        this.lastOurAttackPoint = lastOurAttackPoint;
    }

    public void sayAttackPoint(String AttackPoint) {

        setLastOurAttackPoint(AttackPoint);
        System.out.println(AttackPoint + "に攻撃！");
    }

    public boolean IsAllZero() {
        for (int i = 0; i < enemyValues.length; i++) {
            for (int j = 0; j < enemyValues[i].length; j++) {
                if (enemyValues[i][j] != 0 && enemyValues[i][j] != -1) {
                    return false; // 0以外の要素が見つかったらfalseを返す
                }
            }
        }

        return true; // すべての要素が0である場合にtrueを返す
    }

    public void enemyValuesViwe() {
        System.out.println("敵のいる可能性の値");
        System.out.println("  1|2|3|4|5");
        for (int i = 0; i < enemyValues.length; i++) {
            System.out.print((char) ('A' + i) + "|");
            for (int j = 0; j < enemyValues[i].length; j++) {
                System.out.print(enemyValues[i][j] + " "); // 要素をスペース区切りで出力
            }
            System.out.println(); // 行を改行して次の行へ移行
        }
    }

    public void shipView() {
        String[][] grid = new String[5][5];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = "_";
            }
        }

        placeShipOnGrid(ourShip01, grid);
        placeShipOnGrid(ourShip02, grid);
        placeShipOnGrid(ourShip03, grid);
        placeShipOnGrid(ourShip04, grid);

        System.out.println("我々の船の位置");
        System.out.println("  1|2|3|4|5");
        for (int i = 0; i < grid.length; i++) {
            System.out.print((char) ('A' + i) + "|");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void placeShipOnGrid(Ship ship, String[][] grid) {
        if (!ship.isOurShip()) {
            return;
        }

        String position = ship.getPosition();
        int row = position.charAt(0) - 'A';
        int col = Integer.parseInt(position.substring(1)) - 1;

        grid[row][col] = String.valueOf(ship.getHp());
    }

    public void askOurAttackResult() {
        System.out.println("攻撃結果を入力してください。");
        System.err.println("0:ハズレ！ 1:波高し 2:命中 3:撃沈");

        CONFIRM: while (true) {
            String input = scanner.nextLine();
            if ("0".equals(input)) {

                System.out.println("ハズレ！");

                ourLastAttackResult = 0;
                break CONFIRM;

            } else if ("1".equals(input)) {
                System.out.print("我々の相手への攻撃結果:");
                System.out.println("波高し！");

                ourLastAttackResult = 1;
                break CONFIRM;
            } else if ("2".equals(input)) {
                System.out.print("我々の相手への攻撃結果:");
                System.out.println("命中！");

                ourLastAttackResult = 2;
                break CONFIRM;
            } else if ("3".equals(input)) {
                System.out.print("我々の相手への攻撃結果:");
                System.out.println("撃沈！");

                ourLastAttackResult = 3;
                break CONFIRM;
            } else {
                System.out.println("無効な入力です。");
            }
        }
    }

    public void askEnemyAction() {
        System.out.println("相手の行動を入力してください。a:攻撃 m:移動");
        CONFIRM: while (true) {

            String input = scanner.nextLine();
            if ("a".equals(input)) {
                IsEnemyAttack = true;
                System.out.println("敵はどこに攻撃してきましたか？例:A1,B2,C3,D4,E5");
                setEnemyLastAttackPoint(scanner.nextLine());

                break CONFIRM;

            } else if ("m".equals(input)) {
                IsEnemyAttack = false;
                System.out.println("敵はどの用に移動しましたか？例:n1,s2,w1,e2");
                setEnemyLastMovePoint(scanner.nextLine());

                break CONFIRM;
            } else {
                System.out.println("無効な入力です。");
            }
        }
    }

    public void updateEnemyValue() {

        String attackPoint = getLastOurAttackPoint();
        int row = attackPoint.charAt(0) - 'A';
        int col = Integer.parseInt(attackPoint.substring(1)) - 1;

        switch (Tools.ourLastAttackResult) {
            case 0:
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, true, -1);

                break;
            case 1:
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
                break;
            case 2:
                enemyValues[row][col] = 9;
                enemyShip.setEnemySumHp(enemyShip.getEnemySumHp() - 1);
                break;
            case 3:
                System.out.println("撃破！");
                enemyShip.setEnemySumHp(enemyShip.getEnemySumHp() - 1);
                enemyShip.setEnemyCount(enemyShip.getEnemyCount() - 1);
                break;
            default:
                // その他の値の場合の処理（必要に応じて）
        }
    }

    // public void updateEnemyValueByEnemyAttack() {

    // String attackPoint = getEnemyLastAttackPoint();
    // int row = attackPoint.charAt(0) - 'A';
    // int col = Integer.parseInt(attackPoint.substring(1)) - 1;

    // switch (Tools.ourLastAttackResult) {
    // case 0:
    // enemyValues[row][col] = -1;
    // updateSurroundingCells(row, col, true, -1);

    // break;
    // case 1:
    // enemyValues[row][col] = -1;
    // updateSurroundingCells(row, col, false, 1);
    // break;
    // case 2:
    // enemyValues[row][col] = 9;
    // enemyShip.setEnemySumHp(enemyShip.getEnemySumHp() - 1);
    // break;
    // case 3:
    // System.out.println("撃破！");
    // enemyShip.setEnemySumHp(enemyShip.getEnemySumHp() - 1);
    // enemyShip.setEnemyCount(enemyShip.getEnemyCount() - 1);
    // break;
    // default:
    // // その他の値の場合の処理（必要に応じて）
    // }
    // }

    public void reflectEnemyAttackResult() {
        String position = enemyLastAttackPoint;
        int row = position.charAt(0) - 'A';
        int col = Integer.parseInt(position.substring(1)) - 1;

        if (enemyLastAttackPoint.equals(ourShip01.getPosition())) {
            ourShip01.setHp(ourShip01.getHp() - 1);
            if (ourShip01.getHp() == 0) {
                System.out.println("");
                System.out.println("----");
                System.out.println("撃沈！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 3;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
            } else {
                System.out.println("");
                System.out.println("----");
                System.out.println("命中！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 2;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);

            }
        } else if (enemyLastAttackPoint.equals(ourShip02.getPosition())) {
            ourShip02.setHp(ourShip02.getHp() - 1);

            if (ourShip02.getHp() == 0) {
                System.out.println("");
                System.out.println("----");
                System.out.println("撃沈！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 3;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
            } else {
                System.out.println("");
                System.out.println("----");
                System.out.println("命中！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 2;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
            }
        } else if (enemyLastAttackPoint.equals(ourShip03.getPosition())) {
            ourShip03.setHp(ourShip03.getHp() - 1);
            if (ourShip03.getHp() == 0) {
                System.out.println("");
                System.out.println("----");
                System.out.println("撃沈！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 3;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
            } else {
                System.out.println("");
                System.out.println("----");
                System.out.println("命中！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 2;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
            }
        } else if (enemyLastAttackPoint.equals(ourShip04.getPosition())) {
            ourShip04.setHp(ourShip04.getHp() - 1);
            if (ourShip04.getHp() == 0) {
                System.out.println("");
                System.out.println("----");
                System.out.println("撃沈！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 3;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
            } else {
                System.out.println("");
                System.out.println("----");
                System.out.println("命中！");
                System.out.println("----");
                System.out.println("");
                enemyLastAttackResult = 2;
                enemyValues[row][col] = -1;
                updateSurroundingCells(row, col, false, 1);
            }
        } else if (enableAttackPoints[row][col] == 1) {
            System.out.println("");
            System.out.println("-----");
            System.out.println("波高し！");
            System.out.println("-----");
            System.out.println("");
            enemyLastAttackResult = 1;
            // 評価がむかしいのでスキップ
        } else {
            System.out.println("");
            System.out.println("-----");
            System.out.println("ハズレ！");
            System.out.println("-----");
            System.out.println("");
            enemyValues[row][col] = -1;
            updateSurroundingCells(row, col, false, 1);
            enemyLastAttackResult = 0;
        }

    }

    private void updateSurroundingCells(int row, int col, boolean IsSetNo, int gain) {
        int[] dx = { -1, 0, 1, 0, -1, -1, 1, 1 };
        int[] dy = { 0, -1, 0, 1, -1, 1, -1, 1 };

        for (int i = 0; i < dx.length; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (newRow >= 0 && newRow < enemyValues.length && newCol >= 0 && newCol < enemyValues[0].length) {
                if (IsSetNo) {
                    enemyValues[newRow][newCol] = gain;
                } else {
                    enemyValues[newRow][newCol] += gain;
                }
            }
        }
    }

    private void updateSurroundingCellsForSetPoint(int row, int col, boolean IsSetNo, int gain) {
        int[] dx = { -1, 0, 1, 0, -1, -1, 1, 1 };
        int[] dy = { 0, -1, 0, 1, -1, 1, -1, 1 };

        for (int i = 0; i < dx.length; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (newRow >= 0 && newRow < enemyValues.length && newCol >= 0 && newCol < enemyValues[0].length) {
                if (IsSetNo) {
                    enableAttackPoints[newRow][newCol] = gain;
                } else {
                    enableAttackPoints[newRow][newCol] += gain;
                }
            }
        }
    }

    public void setEnableAttackPoints() {
        // 一旦全部0にする
        for (int i = 0; i < enableAttackPoints.length; i++) {
            for (int j = 0; j < enableAttackPoints[i].length; j++) {
                enableAttackPoints[i][j] = 0;
            }
        }
        // いるマスをに2を入れる

        int[] index = ABCto123(ourShip01);
        if (ourShip01.getHp() != 0) {
            enableAttackPoints[index[0]][index[1]] = 2;
        }
        index = ABCto123(ourShip02);
        if (ourShip02.getHp() != 0) {
            enableAttackPoints[index[0]][index[1]] = 2;
        }

        index = ABCto123(ourShip03);
        if (ourShip03.getHp() != 0) {
            enableAttackPoints[index[0]][index[1]] = 2;
        }

        index = ABCto123(ourShip04);
        if (ourShip04.getHp() != 0) {
            enableAttackPoints[index[0]][index[1]] = 2;
        }

        // その周りを1にする
        for (int i = 0; i < enableAttackPoints.length; i++) {
            for (int j = 0; j < enableAttackPoints[i].length; j++) {
                if (enableAttackPoints[i][j] == 2) {
                    updateSurroundingCellsForSetPoint(i, j, true, 1);
                }
            }

        }
        // 2のマスを0にする
        index = ABCto123(ourShip01);
        enableAttackPoints[index[0]][index[1]] = 0;
        index = ABCto123(ourShip02);
        enableAttackPoints[index[0]][index[1]] = 0;
        index = ABCto123(ourShip03);
        enableAttackPoints[index[0]][index[1]] = 0;
        index = ABCto123(ourShip04);
        enableAttackPoints[index[0]][index[1]] = 0;
        // 表示する
        System.err.println("攻撃可能なマス");
        System.out.println("  1|2|3|4|5");
        for (int i = 0; i < enableAttackPoints.length; i++) {
            System.out.print((char) ('A' + i) + "|");
            for (int j = 0; j < enableAttackPoints[i].length; j++) {
                System.out.print(enableAttackPoints[i][j] + " ");
            }
            System.out.println(); // 各行の末尾で改行
        }

    }

    public int[] ABCto123(Ship ship) {
        if (!ship.isOurShip()) {
            return null; // 無効な場合はnullを返す
        }

        String position = ship.getPosition();
        int row = position.charAt(0) - 'A';
        int col = Integer.parseInt(position.substring(1)) - 1;
        return new int[] { row, col }; // 行と列のインデックスを返す
    }

    public String maxEnableAttackValuePoint() {
        int maxIndexlistCount = 25; // 探索する座標の数
        int[][] fixEnemyValues = new int[enemyValues.length][enemyValues[0].length];

        // enemyValuesの深いコピーを作成
        for (int i = 0; i < enemyValues.length; i++) {
            System.arraycopy(enemyValues[i], 0, fixEnemyValues[i], 0, enemyValues[i].length);
        }

        for (int h = 0; h < maxIndexlistCount; h++) {
            int max = -1; // 最大値の初期化
            int maxI = -1, maxJ = -1; // 最大値の座標を保存する変数

            for (int i = 0; i < fixEnemyValues.length; i++) {
                for (int j = 0; j < fixEnemyValues[i].length; j++) {
                    if (fixEnemyValues[i][j] > max) {
                        max = fixEnemyValues[i][j];
                        maxI = i;
                        maxJ = j;
                    }
                }
            }

            if (maxI != -1 && enableAttackPoints[maxI][maxJ] == 1) {
                // 対応するenableAttackPointsが1の場合、座標を文字列で返す
                return (char) (maxI + 'A') + String.valueOf(maxJ + 1);
            }

            // その座標の値をリセットして次の最大値を探す
            if (maxI != -1) {
                fixEnemyValues[maxI][maxJ] = 0;
            }
        }

        return ""; // 適切な座標が見つからない場合は空文字列を返す

        // int max = 0;
        // int[] maxIndex = new int[2];
        // for (int i = 0; i < enemyValues.length; i++) {
        // for (int j = 0; j < enemyValues[i].length; j++) {
        // if (enemyValues[i][j] > max) {
        // max = enemyValues[i][j];
        // maxIndex[0] = i;
        // maxIndex[1] = j;
        // }
        // }
        // }
        // return (char) (maxIndex[0] + 'A') + String.valueOf(maxIndex[1] + 1);
    }

    public void randomSayAttackPoint() {
        int randomIndex = random.nextInt(coordinates.length);
        sayAttackPoint(coordinates[randomIndex]);
    }

    public void printEnemyStatus() {
        System.out.print("敵の数: " + enemyShip.getEnemyCount());
        System.out.println(" HP: " + enemyShip.getEnemySumHp());
    }

    public void printOurStatus() {
        System.out.print("我々の数: " + ourShip01.getHp());
        System.out.print(" " + ourShip02.getHp());
        System.out.print(" " + ourShip03.getHp());
        System.out.print(" " + ourShip04.getHp());
        System.out
                .println(" 我々の総HP: " + (ourShip01.getHp() + ourShip02.getHp() + ourShip03.getHp() + ourShip04.getHp()));
    }

    public void Randomescape() {

        // 船のリスト
        Ship[] ships = { ourShip01, ourShip02, ourShip03, ourShip04 };

        Ship selectedShip;
        int attempts = 0; // 無限ループを避けるための試行回数カウンタ

        do {
            // ランダムに船を選択
            selectedShip = ships[random.nextInt(ships.length)];
            attempts++;

            // すべての船がhpが0の場合、ループを抜ける
            if (attempts > ships.length) {
                return;
            }
        } while (selectedShip.getHp() == 0); // hpが0の船を除外
        // 選択された船の位置を取得
        String position = selectedShip.getPosition();
        int row = position.charAt(0) - 'A';
        int col = Integer.parseInt(position.substring(1)) - 1;

        int newRow, newCol;

        // 新しい座標を計算するまでループ
        do {
            // ランダムに選択する要素（行または列）
            boolean isRow = random.nextBoolean();

            // ランダムに -2 から 2 の値を足す
            int delta = random.nextInt(5) - 2;

            if (isRow) {
                newRow = row + delta;
                newCol = col;
            } else {
                newRow = row;
                newCol = col + delta;
            }

            // 座標を範囲内に収める
            newRow = Math.min(Math.max(newRow, 0), 4);
            newCol = Math.min(Math.max(newCol, 0), 4);
        } while ((newRow == row && newCol == col) || enableAttackPoints[newRow][newCol] == 2);

        // 新しい位置を設定
        char newRowChar = (char) ('A' + newRow);
        String newPosition = newRowChar + Integer.toString(newCol + 1);
        selectedShip.setPosition(newPosition);

        // 移動した方向と距離を計算
        int rowDelta = newRow - row;
        int colDelta = newCol - col;

        // 移動した方向と距離を出力
        String moveDirection = "";
        if (rowDelta != 0) {
            moveDirection = (rowDelta > 0 ? "下に" : "上に") + Math.abs(rowDelta) + "マス";
        } else if (colDelta != 0) {
            moveDirection = (colDelta > 0 ? "右に" : "左に") + Math.abs(colDelta) + "マス";
        }

        System.out.println("移動した方向と距離: " + moveDirection);
    }

}

class Ship {
    private boolean isOurShip;
    private String position;
    private int hp;

    public Ship(boolean isOurShip, String position, int hp) {
        this.isOurShip = isOurShip;
        this.position = (isOurShip) ? position : "??";
        this.hp = hp;
    }

    public boolean isOurShip() {
        return isOurShip;
    }

    public void setOurShip(boolean myShip) {
        isOurShip = myShip;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (this.isOurShip) {
            this.position = position;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

class EnemyShip {
    private int enemyCount; // 敵の数
    private int enemySumHp; // 敵の総HP

    public EnemyShip(int enemyCount, int enemySumHp) {
        this.enemyCount = enemyCount;
        this.enemySumHp = enemySumHp;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getEnemySumHp() {
        return enemySumHp;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public void setEnemySumHp(int enemySumHp) {
        this.enemySumHp = enemySumHp;
    }

    public void damageEnemy(int damage) {
        this.enemySumHp -= damage;
        if (this.enemySumHp < 0) {
            this.enemySumHp = 0;
        }
    }

    public void decreaseEnemyCount() {
        if (this.enemyCount > 0) {
            this.enemyCount--;
        }
    }

}
