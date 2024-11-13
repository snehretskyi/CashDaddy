package pojo;

public class CategoriesPOJO extends DatabaseItemPojo {
    private int budget_id;
    private String category_type;

    public CategoriesPOJO(int category_id, String category_type) {
        super(category_id);
        this.category_type = category_type;
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

    public String toString() {return category_type;}
}
