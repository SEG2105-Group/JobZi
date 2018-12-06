package com.arom.jobzi;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.search.SearchQuery;
import com.arom.jobzi.search.SearchResult;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.service.Review;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.TimeUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Deliverable4Test {

    @Rule
    public ActivityTestRule<HomeOwnerSearchActivity> searchActivityActivityRule = new ActivityTestRule<HomeOwnerSearchActivity>(HomeOwnerSearchActivity.class);

    private HomeOwnerSearchActivity searchActivity;

    @Before
    public void setup() {

        searchActivity = searchActivityActivityRule.getActivity();

    }

    @Test
    public void testProfileRating() {

        ServiceProviderProfile profile = new ServiceProviderProfile();

        profile.setRating(4.5);

        Assert.assertEquals(profile.getRating(), 4.5, 0.1);

    }

    @Test
    public void testReviewRating() {

        Review review = new Review();

        review.setRating(3.0);

        Assert.assertEquals(review.getRating(), 3.0, 0.1);

    }

    @Test
    public void testReviewComment() {

        Review review = new Review();

        review.setComment("This guy is pretty good.");

        Assert.assertEquals(review.getComment(), "This guy is pretty good.");

    }

    @Test
    public void testSearchQueryRating() {

        SearchQuery query = new SearchQuery();

        query.setRating(1.5);

        Assert.assertEquals(query.getRating(), 1.5, 0.1);

    }

    @Test
    public void testSearchQueryWeekday() {

        SearchQuery query = new SearchQuery();

        query.setWeekday(TimeUtil.Weekday.FRIDAY);

        Assert.assertEquals(query.getWeekday(), TimeUtil.Weekday.FRIDAY);

    }

    @Test
    public void testSearchQueryService() {

        Service service = new Service();
        service.setId("abc-test-id");// Only this field matters.
        service.setName("Test Name");
        service.setRate(10.5);

        SearchQuery query = new SearchQuery();

        query.setService(service);

        Assert.assertEquals(query.getService(), service);

    }

    @Test
    public void testSearchQueryAvailability() {

        Availability availability = TimeUtil.createDefaultAvailability();

        SearchQuery query = new SearchQuery();

        query.setAvailability(availability);

        Assert.assertEquals(query.getAvailability(), availability);

    }

    @Test
    public void testSearchResult() {

        List<User> userList = new ArrayList<User>();

        User user = new User();
        user.setId("user-test-id");
        userList.add(user);

        SearchResult result = new SearchResult();

        result.setServiceProviders(userList);

        Assert.assertEquals(result.getServiceProviders(), userList);

    }

    @Test
    @UiThreadTest
    public void testSearchRatingBar() {

        RatingBar ratingBar = searchActivity.findViewById(R.id.ratingBar);
        ratingBar.setRating(3.5f);

        Assert.assertEquals(ratingBar.getRating(), 3.5f, 0.1);

    }

    @Test
    @UiThreadTest
    public void testWeekdaySpinner() {

        Spinner spinner = searchActivity.findViewById(R.id.dayOfWeekSpinner);
        spinner.setSelection(TimeUtil.Weekday.SATURDAY.ordinal());

        Assert.assertEquals(spinner.getSelectedItem(), TimeUtil.Weekday.SATURDAY);

    }

}
