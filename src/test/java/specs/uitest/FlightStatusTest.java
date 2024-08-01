package specs.uitest;
import hooks.BaseTest;
import log.Log;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class FlightStatusTest extends BaseTest {

    // Defining data providers for flight Dates
    @DataProvider(name = "flightDates")
    public Object[][] createFlightDates() {
        return new Object[][] {
                {"CGN", "BER", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "Testing flight status for today's date"},
                {"CGN", "BER", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "Testing flight status for tomorrow's date"}
        };
    }

    @Test(dataProvider = "flightDates", priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void testFlightStatus(String departureAirport, String destinationAirport, String travelDate, String message) throws InterruptedException {
        System.out.println(message);
        flightStatusPage.setDepartureAirport(departureAirport);
        flightStatusPage.setDestinationAirport(destinationAirport);
        flightStatusPage.setTravelDate(travelDate);
        flightStatusPage.clickShowFlightStatus();
        flightStatusPage.verifyFlightDetails(departureAirport, destinationAirport);

        Log.Info("Completed test for flight status");
    }


    @Test(priority = 1)
    @Severity(SeverityLevel.NORMAL)
    public void testNoFlightsVisibleOnPageLoad() {
         flightStatusPage.verifyNoFlightsVisible();
        flightStatusPage.verifyNoFlightsMessage("Your are currently not oberserving any flights.");
        System.out.println("Verified no flights data visible");
    }
}