package it.unisa.zwhbackend.model.entity;

import it.unisa.zwhbackend.model.entity.Prodotto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ShoppingListRequest {
    // Getter e Setter
    private List<Prodotto> fridgeItems;
    private List<Prodotto> pantryItems;
    private List<Prodotto> dailyPlanItems;

}
