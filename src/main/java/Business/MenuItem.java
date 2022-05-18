package Business;


import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    private int timesOrdered = 0;
    public void incrementTimesOrdered(){
        timesOrdered++;
    }
    public int getTimesOrdered(){
        return timesOrdered;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    String title;
    Double rating = 0.0;
    int calories = 0;
    int protein = 0;
    int fat = 0;
    int sodium = 0;
    int price = 0;

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

    public int getPrice() {
        return price;
    }

    abstract public double computePrice();
}
