package main.java.cf.dataProvider;

import main.java.cf.utils.ConfigLoader;
import org.testng.annotations.DataProvider;

import java.util.List;

public class DataInputSet {


    @DataProvider(name = "Comments_GetPostIds")
    public Object[] getFeaturedToursSegmentIds() {

        List<Integer> postIds = ConfigLoader.getCommentsData("Samantha");
        return postIds.toArray();
    }

}
