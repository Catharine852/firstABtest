package com.afanaseva.testdata;


import com.google.common.collect.Lists;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PracticeFormValidData {
    String FIRST_NAME = "John";
    String LAST_NAME = "Doe";
    String EMAIL = "test@gmail.com";
    String GENDER = "Male";
    String PHONE = "1234567890";
    String DATE_OF_BIRTH = "15 Mar 1986";
    List<String> SUBJECTS = Lists.asList("Chemistry", new String[]{"History"});
    List<String> HOBBIES = Lists.asList("Sports", new String[]{"Reading"});
    String PICTURE = Paths.get("src/main/java/com/afanaseva/testdata/pictureToUpload.png").toAbsolutePath().toString();
    String ADDRESS = "Piccadilly 12";
    String STATE = "NCR";
    String CITY = "Gurgaon";

    static Map<String, String> expectedData() {
        Map<String, String> data = new HashMap<>();
        data.put("Student Name", FIRST_NAME + " " + LAST_NAME);
        data.put("Student Email", EMAIL);
        data.put("Gender", GENDER);
        data.put("Mobile", PHONE);
        data.put("Date of Birth", DATE_OF_BIRTH);
        data.put("Subjects", SUBJECTS.toString().substring(1, SUBJECTS.toString().length() - 1));
        data.put("Hobbies", HOBBIES.toString().substring(1, HOBBIES.toString().length() - 1));
        data.put("Picture", PICTURE.substring(PICTURE.lastIndexOf("\\") + 1));
        data.put("Address", ADDRESS);
        data.put("State and City", STATE + " " + CITY);
        return data;
    }
}
