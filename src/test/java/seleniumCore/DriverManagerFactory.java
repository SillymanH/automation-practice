package seleniumCore;

//Implementing the Factory Design Pattern
public class DriverManagerFactory {

    public static DriverManager getDriverManager(DriverType type){

        DriverManager driverManager;
        switch (type) {

//We will comment all the cases for now
//Until we create a driver manager class
//for each browser

//            case IE:
//                // driverManager = new IeDriverManager();
//                break;
//
//            case FIREFOX:
//               // driverManager = new FirefoxManager();
//                break;
//
//            case EDGE:
//                // driverManager = new EdgeManager();
//                break;

            default:
                driverManager = new ChromeDriverManager();
                break;
        }
        return driverManager;
    }

}
