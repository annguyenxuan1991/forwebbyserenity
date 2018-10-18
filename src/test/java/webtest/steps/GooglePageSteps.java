package webtest.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.WebElementHelper;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import webtest.pageelements.PageUrlConstants;


public class GooglePageSteps extends PageObject {

    private static final By SEARCH_BOX_BY = By.id("lst-ib");
    private static final By SEARCH_BUTTON_BY = By.cssSelector(".lsbb input[value='Google Search']");

    @Given("^User is on Google page$")
    public void userIsOnGooglePage() {
        getDriver().get(PageUrlConstants.GOOGLE_PAGE_URL);
    }


    @When("^user input \"([^\"]*)\" on Search box$")
    public void userInputOnSearchBox(String searchKey) {
        WebElementHelper.waitAndSendKey(SEARCH_BOX_BY, searchKey);
    }

    @And("^user clicks Search button$")
    public void userClicksSearchButton() {
        WebElementHelper.waitAndClick(SEARCH_BUTTON_BY);
    }

    @Then("^that is it .-.$")
    public void thatIsIt() {
        System.out.println("That's it!!!");
    }
}
