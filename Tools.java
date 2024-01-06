
package my_code;

import java.util.*;
import java.io.*;

class Tools {

    public static int ourLastAttackResult;
    public static int enemyLastAttackResult;
    public static String ourLastMovePosition;
    public static String enemyLastMovePosition;
    private int[][] enemyValue;
    private int[][] ourValue;
    public static boolean ourAttackTurn;
    public static boolean endFarstAttack;

    int[][] ourValues = new int[5][5];
    int[][] enemyValues = new int[5][5];
    int[][] enableAttackPoints = new int[5][5];

    private static String lastOurAttackPoint;

    Scanner scanner = new Scanner(System.in);

    private Ship ourShip01;
    private Ship ourShip02;
    private Ship ourShip03;
    private Ship ourShip04;
    private Ship enemyShip01;
    private Ship enemyShip02;
    private Ship enemyShip03;
    private Ship enemyShip04;

    Tools() {
        for (int i = 0; i < ourValues.length; i++) {
            for (int j = 0; j < ourValues[i].length; j++) {
                ourValues[i][j] = 0;
                enemyValues[i][j] = 0;
                enableAttackPoints[i][j] = 0;
            }
        }

        lastOurAttackPoint = "HH";
        ourLastAttackResult = 0;

        ourShip01 = new Ship(true, "A4", 3);
        ourShip02 = new Ship(true, "B1", 3);
        ourShip03 = new Ship(true, "D5", 3);
        ourShip04 = new Ship(true, "E2", 3);

        enemyShip01 = new Ship(false, "??", 3);
        enemyShip02 = new Ship(false, "??", 3);
        enemyShip03 = new Ship(false, "??", 3);
        enemyShip04 = new Ship(false, "??", 3);
    }
    /// getterとsetter///

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
                if (enemyValues[i][j] != 0) {
                    return false; // 0以外の要素が見つかったらfalseを返す
                }
            }
        }

        return true; // すべての要素が0である場合にtrueを返す
    }

    public void enemyValuesViwe() {
        System.out.println("敵のいる可能性の値");
        for (int i = 0; i < enemyValues.length; i++) {
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
        for (int i = 0; i < grid.length; i++) {
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
                System.out.println("波高し！");

                ourLastAttackResult = 1;
                break CONFIRM;
            } else if ("2".equals(input)) {
                System.out.println("命中！");

                ourLastAttackResult = 2;
                break CONFIRM;
            } else if ("3".equals(input)) {
                System.out.println("撃沈！");

                ourLastAttackResult = 3;
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

        // for (int i = 0; i < ourValues.length; i++) {
        // for (int j = 0; j < ourValues[i].length; j++) {
        // System.out.println(enemyValues[i][j] );

        // }
        // }

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
                break;
            case 3:
                System.out.println("撃破！");
                break;
            default:
                // その他の値の場合の処理（必要に応じて）
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
                } else  {
                    enemyValues[newRow][newCol] += gain;
                }
            }
        }
    }


    private void updateSurroundingCellsForEAP(int row, int col, boolean IsSetNo, int gain) {
        int[] dx = { -1, 0, 1, 0, -1, -1, 1, 1 };
        int[] dy = { 0, -1, 0, 1, -1, 1, -1, 1 };

        for (int i = 0; i < dx.length; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (newRow >= 0 && newRow < enemyValues.length && newCol >= 0 && newCol < enemyValues[0].length) {
                if (IsSetNo) {
                    enableAttackPoints[newRow][newCol] = gain;
                } else  {
                    enableAttackPoints[newRow][newCol] += gain;
                }
            }
        }
    }


    public String getMaxValuePoint() {
        int maxVal = enemyValues[0][0];
        int maxRow = 0;
        int maxCol = 0;

        for (int i = 0; i < enemyValues.length; i++) {
            for (int j = 0; j < enemyValues[i].length; j++) {
                if (enemyValues[i][j] > maxVal) {
                    maxVal = enemyValues[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            }
        }

        // 行のインデックスをアルファベットに変換
        char rowChar = (char) ('A' + maxRow);
        // 列のインデックスは1から始まるように調整
        int colIndex = maxCol + 1;

        // 結果を文字列で返す
        return rowChar + Integer.toString(colIndex);
    }

    public void setEnableAttackPoint() {

        int[] index = ABCto123(ourShip01);
        enableAttackPoints[index[0]][index[1]] = 2;
        index = ABCto123(ourShip02);
        enableAttackPoints[index[0]][index[1]] = 2;
        index = ABCto123(ourShip03);
        enableAttackPoints[index[0]][index[1]] = 2;
        index = ABCto123(ourShip04);
        enableAttackPoints[index[0]][index[1]] = 2;

        for (int i = 0; i < enemyValues.length; i++) {
            for (int j = 0; j < enemyValues[i].length; j++) {
                if (enemyValues[i][j] == 0) {
                    enableAttackPoints[i][j] = 1;
                }
            }
        }

        index = ABCto123(ourShip01);
        enableAttackPoints[index[0]][index[1]] = 0;
        index = ABCto123(ourShip02);
        enableAttackPoints[index[0]][index[1]] = 0;
        index = ABCto123(ourShip03);
        enableAttackPoints[index[0]][index[1]] = 0;
        index = ABCto123(ourShip04);
        enableAttackPoints[index[0]][index[1]] = 0;

        System.err.println("攻撃可能な場所");
        for (int i = 0; i < enemyValues.length; i++) {
            for (int j = 0; j < enemyValues[i].length; j++) {
                if (enemyValues[i][j] == 0) {
                    System.out.print(enableAttackPoints[i][j]);
                    System.err.print(" ");
                }

            }
            System.err.println("");
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
}

class Ship {
    private boolean isOurShip;
    private String position;
    private int hp;


    public Ship( boolean isOurShip, String position, int hp) {
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
