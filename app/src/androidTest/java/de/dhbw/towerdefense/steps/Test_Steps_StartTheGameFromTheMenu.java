package de.dhbw.towerdefense.steps;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.*;

import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import de.dhbw.R;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

public class Test_Steps_StartTheGameFromTheMenu extends GreenCoffeeSteps {

    @Given("I am on the main page")
    public void i_am_on_the_main_page() {
        onViewWithId(R.layout.activity_main).checkIfIsDisplayed();
    }

    @When("I press the start game button")
    public void i_press_the_start_game_button() {
        onViewWithId(R.id.spinner_choose_difficulties).checkIfIsDisplayed();
        onViewWithId(R.id.spinner_choose_difficulties).click();
    }

    @Then("I am on the choose difficulty dropdown")
    public void i_am_on_the_choose_difficulty_dropdown() {
        onViewWithId(R.id.spinner_choose_difficulties).checkIfContains("easy");
    }

    @Then("I choose easy by clicking on it")
    public void i_choose_easy_by_clicking_on_it() {
        onData(allOf(is(instanceOf(String.class)), is("easy"))).perform(click());
    }

    @Then("I am on the game page")
    public void i_am_on_the_game_page() {
        onViewWithId(R.layout.activity_game).checkIfIsDisplayed();
    }

    @Then("the map with easy settings is loaded")
    public void the_map_with_easy_settings_is_loaded() {
        //TODO
    }

    @Then("I choose medium by clicking on it")
    public void i_choose_medium_by_clicking_on_it() {
        //TODO
    }

    @Then("the map with medium settings is loaded")
    public void the_map_with_medium_settings_is_loaded() {
        //TODO
    }

    @Then("I choose hard by clicking on it")
    public void i_choose_hard_by_clicking_on_it() {
        //TODO
    }

    @Then("the map with hard settings is loaded")
    public void the_map_with_hard_settings_is_loaded() {
        //TODO
    }

}
