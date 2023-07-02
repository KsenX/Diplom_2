package praktikum.pojo;

import java.util.List;

public class OrderCreate {
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    private List<String> ingredients;

    public OrderCreate(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
