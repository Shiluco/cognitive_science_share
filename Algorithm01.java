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

            tools.enemyValuesViwe();
            tools.shipView();
            tools.setEnableAttackPoints();
            tools.printOurStatus();
            tools.printEnemyStatus();

            // 自分たちの攻撃結果から敵の位置を推測

            // 全部0ならB4に攻撃
            if (tools.IsAllZero()) {
                tools.randomSayAttackPoint();
            } else {
                
                tools.sayAttackPoint(tools.maxEnableAttackValuePoint());
            }

        }
        else {
            tools.reflectEnemyAttackResult();
        }
    }
}
