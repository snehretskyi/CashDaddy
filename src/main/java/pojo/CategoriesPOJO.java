package pojo;

public class CategoriesPOJO {
    private int category_id;
    private int budget_id;
    private String category_type;

    public CategoriesPOJO(int category_id, int budget_id, String category_type) {
        this.category_id = category_id;
        this.budget_id = budget_id;
        this.category_type = category_type;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getBudget_id() {
        return budget_id;
    }

    public void setBudget_id(int budget_id) {
        this.budget_id = budget_id;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }
}
