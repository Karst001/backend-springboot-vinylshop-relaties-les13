package nl.novi.vinylshop.dtos.stock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StockRequestDTO {
    String condition;  //without annotation it is a optional field
    @NotNull    //mandatory value and always positive
    @Positive
    double price;


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
