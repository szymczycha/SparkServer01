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
        get("/delete/:id", (req, res) -> deleteCar(req, res, cars));
        get("/update/:id", (req, res) -> updateCar(req, res, cars));;
        get("/deleteAll", (req, res) -> deleteAll(req, res, cars));
        get("/text", (req, res) -> textCar(req, res, cars));
        get("/json", (req, res) -> jsonCar(req, res, cars));
        get("/html", (req, res) -> htmlCar(req, res, cars));
    }

    private static String htmlCar(Request req, Response res, ArrayList<Car> cars) {
        String out = "";
        out+="<style>table, td{border: 1px solid black;}</style>" +
                "<table>\n" +
                "    <tbody>\n" +
                "    \n";
        for(Car car : cars){
            out+="<tr>" +
                    "<td>" +
                    car.getId() +
                    "</td>" +
                    "<td>" +
                    car.getModel() +
                    "</td>" +
                    "<td>" +
                    car.isDamaged() +
                    "</td>" +
                    "<td>" +
                    car.getDoors() +
                    "</td>" +
                    "<td>" +
                    car.getCountry() +
                    "</td>" +
                    "<td>" +
                    "<form action='/delete/"+car.getId()+"' >" +
                    "<input type='submit' value='usun'/>" +
                    "</form>" +
                    "</td>" +
                    "</tr>";
        }
        out+="</tbody>\n" +
                "</table>";
        return out;
    }

    private static String deleteAll(Request req, Response res, ArrayList<Car> cars) {
        for(int i = cars.size()-1; i>=0; i--){
            cars.remove(cars.get(i));
        }
        return "Cars deleted";
    }

    private static String updateCar(Request req, Response res, ArrayList<Car> cars) {
        for(Car car : cars){
            if(car.getId()==Integer.parseInt(req.params("id"))){
                car.setDamaged();
            }
        }
        return "Car updated";
    }

    private static String deleteCar(Request req, Response res, ArrayList<Car> cars) {
//        System.out.println(cars);
        for(Car car : cars){
//            System.out.println(car);
            if(car.getId()==Integer.parseInt(req.params("id"))){
                cars.remove(car);
                break;
            }
        }
        return "Car deleted";
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

    public String getModel() {
        return model;
    }

    public int getDoors() {
        return doors;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public String getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    private String model;
    private int doors;
    private boolean damaged;

    public void setDamaged() {
        damaged = !damaged;
    }

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