package Business;

public class BaseProduct extends MenuItem {

    public BaseProduct(String title, Double rating, int calories, int protein, int fat, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }
    public BaseProduct(){

    }

    @Override
    public double computePrice(){
        return price;
    }
}
