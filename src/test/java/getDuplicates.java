import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;

public class getDuplicates {
    @Test
    public  void getDuplicates() throws Exception {
        LogReader fileReader = new LogReader();
        var logLines = fileReader.ReadLogs();
        System.out.println(logLines.size());
        FindLineClass clS = new FindLineClass();
        Integer time = 3;
        clS.CheckSendResponsePairs(logLines,time);
        var duplicates = clS.GetDuplicates(logLines);
        System.out.println("Duplicates found: " + duplicates.stream().count());

        for (LogLine duplicate:duplicates) {
            System.out.println("Duplicate id: " + duplicate.Id + " " + duplicate.Type);
            Assert.assertEquals("There are no duplicates: " , 0, "but found: " + duplicate.Type
                    +" "+ duplicate.Id );

        }
    }
}

