import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JsonClass {

    @SerializedName(value = "set_1", alternate = "set")
    private ArrayList<Integer> firstArray;
    @SerializedName(value = "set_2")
    @Expose(deserialize = false)
    private ArrayList<Integer> secondArray;

    public JsonClass(ArrayList<Integer> firstArray, ArrayList<Integer> secondArray) {
        this.firstArray = firstArray;
        this.secondArray = secondArray;
    }

    public ArrayList<Integer> getFirstArray() {
        return firstArray;
    }

    public void setFirstArray(ArrayList<Integer> firstArray) {
        this.firstArray = firstArray;
    }

    public ArrayList<Integer> getSecondArray() {
        return secondArray;
    }

    public void setSecondArray(ArrayList<Integer> secondArray) {
        this.secondArray = secondArray;
    }

    @Override
    public String toString() {
        return "JsonClass{" +
                "set_1=" + firstArray +
                ", set_2=" + secondArray +
                '}';
    }
}
