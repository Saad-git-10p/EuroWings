    package com.qa.pageObjects;

    import com.codeborne.selenide.Condition;
    import com.codeborne.selenide.ElementsCollection;
    import com.codeborne.selenide.Selenide;
    import com.codeborne.selenide.SelenideElement;
    import org.openqa.selenium.By;

    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;

    import static com.codeborne.selenide.CollectionCondition.size;
    import static com.codeborne.selenide.Condition.*;
    import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
    import static com.codeborne.selenide.Selenide.*;
    import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
    import static java.lang.Thread.sleep;

    public class FlightStatusPage {

        private final SelenideElement departureAirportButton = $(By.xpath("//span[text()='Departure airport']/parent::div/parent::button"));
        private final SelenideElement destinationAirportButton = $(By.xpath("//span[text()='Destination airport']/parent::div/parent::button"));
        private final SelenideElement DepartureDateField= $(By.cssSelector("input[name='datepicker_input_39']"));
        private final SelenideElement showFlightStatusButton = $(By.xpath("//button[contains(@class, 'a-cta a-cta-prio1') and @data-component-name='cta']"));
        private final SelenideElement DepartureInputElement = $("#station-select-228-input");
        private final SelenideElement DestinationInputElement = $("#station-select-232-input");
        private final ElementsCollection flightCards = $$(".o-search-flight-status__card");
        private final SelenideElement noFlightsMessage = $(".o-search-flight-status__display-info p.a-paragraph");




        public void
        setDepartureAirport(String departure) throws InterruptedException {
           // sleep(3000);
            departureAirportButton.click();

            DepartureInputElement.shouldBe(visible).setValue(departure).pressEnter();
        }

        public void setDestinationAirport(String destination) throws InterruptedException {
          //  sleep(2000);
            destinationAirportButton.click();
            //sleep(4000);
            DestinationInputElement.shouldBe(visible).setValue(destination).pressEnter();
        }

        public void setTravelDate(String date) throws InterruptedException {
            sleep(3000);
            DepartureDateField.click();
            // Assuming the date format is dd/MM/yyyy
            String[] dateParts = date.split("/");
            String day = dateParts[0];

            //Using the Selenide selector to handle collections properly
            $$(".calendar-date input.calendar-date__input").findBy(Condition.attribute("aria-label", day + ". July 2024")).click();
        }



        public void clickShowFlightStatus() {
            $(showFlightStatusButton).click();
        }

        public void verifyFlightDetails(String departure, String destination) throws InterruptedException {
            // Ensure flight status cards are present
          //  sleep(4000);

            flightCards.shouldHave(sizeGreaterThan(0));

            for (SelenideElement card : flightCards) {
                // Locate and verify the departure time
                SelenideElement departureSection = card.$x(".//div[contains(@class, 'detail-section') and .//p[text()='Departure']]");
                departureSection.should(exist);
                String departureTime = departureSection.$x(".//p[@class='a-paragraph a-paragraph--left']").text();

                // Locate and verify the arrival time
                SelenideElement arrivalSection = card.$x(".//div[contains(@class, 'detail-section') and .//p[text()='Arrival']]");
                arrivalSection.should(exist);
                String arrivalTime = arrivalSection.$x(".//p[@class='a-paragraph a-paragraph--left']").text();

                // Print out the times
                System.out.println("Departure: " + departureTime);
                System.out.println("Arrival: " + arrivalTime);

                // Verify the departure and destination codes
                card.$(".o-search-flight-status__card-airports").shouldHave(Condition.text(departure), Condition.text(destination));
            }

        }


        public void verifyNoFlightsVisible() {
             flightCards.shouldHave(size(0));
        }

        public void verifyNoFlightsMessage(String expectedMessage) {
            noFlightsMessage.should(exist).shouldHave(text(expectedMessage));
        }
    }
