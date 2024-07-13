package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.ArrayList;
import it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking.OrderP;
import java.util.HashMap;

public class RegisterFacade {
    private Register reg;
    private static RegisterFacade instance;
    private HashMap<Integer, OrderP> orders;

    private RegisterFacade() {
        this.reg = new Register();
        this.orders = new HashMap<>();
       
    }
    
    public static RegisterFacade getIstance() {
        if (instance == null) {
            instance = new RegisterFacade();
        }
        return instance;
    }

    public void addOrd(Order o) {
        reg.addOrd(o);
    }

    public Order selectOrder(int id) {
        OrderP or;
        if (this.orders.containsKey(id)) 
            or = this.orders.get(id);
         else {
            or = (OrderP) reg.selectOrder(id);
            this.orders.put(id, or);
         }

        return or;
    }

    public ArrayList<Order> selectOrderWhereClient(String email) {
        return reg.selectOrderWhereClient(email);
    }
}

