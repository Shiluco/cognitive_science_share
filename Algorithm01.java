package my_code;

class Algorithm01 {

    private static Algorithm01 instance;
    Tools tools = Tools.getInstance();

    private Algorithm01() {

        // その他の初期化処理
    }

    public static Algorithm01 getInstance() {
        if (instance == null) {
            instance = new Algorithm01();
        }
        return instance;
    }

    public void Think() {
        if (tools.ourAttackTurn) {

            if (Tools.endFarstAttack) {
                tools.updateEnemyValue();
            }
            System.out.println("");
            tools.enemyValuesViwe();
            System.out.println("");
            tools.shipView();
            System.out.println("");
            tools.setEnableAttackPoints();
            System.out.println("");
            tools.printOurStatus();
            tools.printEnemyStatus();
            System.out.println("");

            // 自分たちの攻撃結果から敵の位置を推測

            // 全部0ならB4に攻撃
            if (tools.IsAllZero()) {
                tools.randomSayAttackPoint();
                tools.askOurAttackResult();
            } else if (tools.enemyLastAttackResult == 2) {
                // 何処かに移動する
                tools.Randomescape();

            } else {
                tools.sayAttackPoint(tools.maxEnableAttackValuePoint());
                tools.askOurAttackResult();
            }

        } else {
            if (tools.IsEnemyAttack) {
                tools.reflectEnemyAttackResult();
            } else {
                System.out.println("敵は移動した");
            }

            

        }
    }
}
