import static spark.Spark.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static spark.Spark.externalStaticFileLocation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;
import spark.Response;
public class App {
    public static void main(String[] args) {
        ArrayList<Car> cars = new ArrayList<>();
        externalStaticFileLocation("C:\\Users\\4pa\\Desktop\\SparkServer01\\src\\main\\resources\\public");
        get("/add", (req, res) -> addCar(req, res, cars));
        get("/text", (req, res) -> textCar(req, res, cars));
        get("/json", (req, res) -> jsonCar(req, res, cars));
    }

    private static String textCar(Request req, Response res, ArrayList<Car> cars) {
        return cars.toString();
    }
    private static String jsonCar(Request req, Response res, ArrayList<Car> cars) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Car>>(){}.getType();
        res.type("application/json");
        return gson.toJson(cars, listType);
    }
    private static String addCar(Request req, Response res, ArrayList<Car> cars) {
        cars.add(new Car(cars.size()+1,req.queryParams("model"), Integer.parseInt(req.queryParams("doors")), Boolean.parseBoolean(req.queryParams("damaged")), req.queryParams("country")));
        System.out.println(cars);
        return "car added to list, size = " + cars.size();
    }
}



class Car{
    private int id;
    private String model;
    private int doors;
    private boolean damaged;
    private String country;

    public Car(int id, String model, int doors, boolean damaged, String country) {
        this.id = id;
        this.model = model;
        this.doors = doors;
        this.damaged = damaged;
        this.country = country;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                "model='" + model + '\'' +
                ", doors=" + doors +
                ", damaged=" + damaged +
                ", country='" + country + '\'' +
                '}';
    }
}