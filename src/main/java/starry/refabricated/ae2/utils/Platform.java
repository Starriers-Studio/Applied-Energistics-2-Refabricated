package starry.refabricated.ae2.utils;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public class Platform {

    public static Transaction openOrJoinTx() {
        return Transaction.openNested(Transaction.getCurrentUnsafe());
    }

}
