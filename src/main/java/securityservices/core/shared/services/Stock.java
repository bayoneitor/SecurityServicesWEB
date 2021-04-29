package securityservices.core.shared.services;

import java.util.HashMap;

public class Stock {

    protected HashMap<String, Integer> stock = new HashMap<String, Integer>();
    protected String storePlace;

    public int updateStock(String name, int amount) {
        if (name != null && name.trim().length() > 0) {
            //Add if not exists
            if (!stock.containsKey(name)) {
                if (amount > 0) {
                    stock.put(name, amount);
                    return 0;
                }
                return -2;
            }

            int StockAmount = this.getAmount(name);
            if (StockAmount > 0) {
                int NewStockAmount = StockAmount + amount;
                //if amount > 0, because negative number = error
                if (NewStockAmount < 0) {
                    //want more than exists
                    return -3;
                } else if (NewStockAmount == 0) {
                    //delete
                    return this.delStock(name);
                } else {
                    //update with new stock
                    stock.put(name, NewStockAmount);
                    return 0;
                }
            }
        }
        return -1;
    }

    public int getAmount(String name) {
        if (stock.containsKey(name)) {
            return stock.get(name);
        }
        return -1;
    }

    public int delStock(String name) {
        if (stock.containsKey(name)) {
            stock.remove(name);
            return 0;
        }
        return -1;
    }

    public String[] getLines() {
        String[] lines;
        String content = stock.entrySet().toString();
        lines = content.replace("[", "").replace("]", "").replace(" ", "").replace("=", ":").split(",");
        return lines;
    }

    public int getNumLines() {
        return stock.size();
    }
}
