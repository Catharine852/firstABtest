package com.afanaseva.pages;


import com.afanaseva.pages.elements.Calendar;
import com.afanaseva.pages.elements.Checkbox;
import com.afanaseva.pages.elements.DropdownType1;
import com.afanaseva.pages.elements.RadioButton;
import com.afanaseva.testdata.PracticeFormValidData;
import com.afanaseva.utils.Web;

import java.util.List;

public class PracticeFormPage extends BasePage {

    private static final String FIRST_NAME = "//*[@id='firstName']";
    private static final String LAST_NAME = "//*[@id='lastName']";
    private static final String USER_EMAIL = "//*[@id='userEmail']";
//    private static final String GENDER_RADIO = "//*[@name='gender']";
    private static final String USER_MOBILE = "//*[@id='userNumber']";
    private static final String DATE_OF_BIRTH = "//*[@id='dateOfBirthInput']";

//    private static final String SUBJECTS_WRAP = "//div[@id='subjectsWrapper']";
    private static final String SUBJECTS_INPUT = "//input[@id='subjectsInput']";
    private static final String SUBJECTS_OPTION = "//div[contains(@class, 'auto-complete__menu')]//*[text()='%s']";

//    private static final String HOBBIES = "//*[@type='checkbox']";
    private static final String SELECT_PICTURE = "//*[@id='uploadPicture']";
    private static final String ADDRESS = "//textarea[@id='currentAddress']";
    private static final String STATE = "//div[@id='state']";
    private static final String CITY = "//div[@id='city']";
    private static final String SUBMIT_BUTTON = "//button[@id='submit']";


    public PracticeFormPage enterFirstName(String name) {
        Web.typeText(FIRST_NAME, name);
        return this;
    }

    public PracticeFormPage enterLastName(String surname) {
        Web.typeText(LAST_NAME, surname);
        return this;
    }

    public PracticeFormPage enterEmail(String mail) {
        Web.typeText(USER_EMAIL, mail);
        return this;
    }

    public PracticeFormPage chooseGender(String option) {
        RadioButton.byLabel("Gender").chooseOption(option);
        return this;
    }

    public PracticeFormPage enterMobilePhone(String phone) {
        Web.typeText(USER_MOBILE, phone);
        return this;
    }

    public PracticeFormPage specifyDateOfBirth(String dateOfBirth) {
        clickElement(Web.findElements(DATE_OF_BIRTH).get(0));
        Calendar.setDate(dateOfBirth);
        checkDate(dateOfBirth);
        return this;
    }

    private static void checkDate(String date) {
        Web.waitUntil("Date was not set in calendar",
                () -> Web.findElements(DATE_OF_BIRTH).get(0).getAttribute("value").equals(date));
    }

    public PracticeFormPage addSubjects(List<String> subjects) {
        for (String subject : subjects) {
            addSubject(subject);
        }
        return this;
    }

    private void addSubject(String subject) {
        Web.typeText(SUBJECTS_INPUT, subject);
        clickElement(Web.findElement(String.format(SUBJECTS_OPTION, subject)));
    }

    public PracticeFormPage addHobbies(List<String> hobbies) {
        for (String hobby : hobbies) {
            Checkbox.byLabel("Hobbies").checkOption(hobby);
        }
        return this;
    }

    public PracticeFormPage uploadPic(String picture) {
        Web.findElement(SELECT_PICTURE).sendKeys(picture);
        return this;
    }

    public PracticeFormPage addAddress(String address) {
        Web.typeText(ADDRESS, address);
        return this;
    }

    public PracticeFormPage selectState(String state) {
        DropdownType1.byPath(STATE).setValue(state);
        return this;
    }

    public PracticeFormPage selectCity(String city) {
        DropdownType1.byPath(CITY).setValue(city);
        return this;
    }

    public ModalWindow submit() {
        clickElement(Web.findElement(SUBMIT_BUTTON));
        return new ModalWindow();
    }

    public PracticeFormPage fillFormWithValidData() {
        return this
                .enterFirstName(PracticeFormValidData.FIRST_NAME)
                .enterLastName(PracticeFormValidData.LAST_NAME)
                .enterEmail(PracticeFormValidData.EMAIL)
                .chooseGender(PracticeFormValidData.GENDER)
                .enterMobilePhone(PracticeFormValidData.PHONE)
                .specifyDateOfBirth(PracticeFormValidData.DATE_OF_BIRTH)
                .addSubjects(PracticeFormValidData.SUBJECTS)
                .addHobbies(PracticeFormValidData.HOBBIES)
                .uploadPic(PracticeFormValidData.PICTURE)
                .addAddress(PracticeFormValidData.ADDRESS)
                .selectState(PracticeFormValidData.STATE)
                .selectCity(PracticeFormValidData.CITY);
    }
}
