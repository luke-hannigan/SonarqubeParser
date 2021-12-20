/*
 * Test class, used for creating test Issues for data.txt output.
 *
 * This class should be changed to reflect the incoming data from sonarqube.
 * Expected fields:
 *      category
 *      rating: May be calculated based on OWASP vulnerabilities?
 *      OWASP vulnerabilities(Urgent, Critical, High, Medium, Low)
 *      HotSpots
 */

package com.backend_app;

public class Issue {
    private String category;
    private char rating;

    public Issue(String category, char rating) {
        this.category = category;
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public char getRating() {
        return rating;
    }

    public void setRating(char rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        // Format can be created here, currently returning a comma-separated line
        return String.format("%s %c\n", category, rating);
    }
}
