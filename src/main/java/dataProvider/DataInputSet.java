package main.java.dataProvider;

import main.java.utils.ConfigLoader;
import org.testng.annotations.DataProvider;

import java.util.List;

public class DataInputSet {


    @DataProvider(name = "Comments_GetPostIds")
    public Object[] getFeaturedToursSegmentIds() {

        List<Integer> postIds = ConfigLoader.getCommentsData("Samantha");
        return postIds.toArray();
    }

}
