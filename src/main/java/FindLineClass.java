import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindLineClass {
    public void CheckSendResponsePairs(ArrayList<LogLine> logs, Integer time) throws Exception {
        Map<String, String> sendResMap = new HashMap<>();
        int count = 0;

        for (LogLine line : logs) {
            var response = logs.stream()
                    .filter(resp -> resp.getEquals(LogType.Response, line.Id))
                    .findFirst().orElse(null);

            if ((logs.contains(line.Type.Send) && logs.contains(line.Id)) == (logs.contains(line.Type.Response)
                    && logs.contains(line.Id))
                    && IsCloserTimeResponse(line, response, time)) {
                sendResMap.put(String.valueOf(line.Type.Send), String.valueOf(line.Type.Response));
                count++;
            } else {
                throw new Exception("Error: no Response found for Send with ID ");
            }
        }
        System.out.println("Logs count:  " + count);
    }

    private boolean IsCloserTimeResponse(LogLine requestLine, LogLine responseLine, int time) {
        if (requestLine == null || responseLine == null) {
            return false;
        }
        return (requestLine.Date.getTime() - responseLine.Date.getTime()) < time;
    }

    public List<LogLine> GetDuplicates(ArrayList<LogLine> logs) {
        Map<String, List<LogLine>> matches = logs.stream()
                .collect(Collectors.groupingBy(LogLine::GetIdAndType));

        var incorrectGroups = matches.entrySet().stream()
                .filter(group -> group.getValue().stream().count() > 1)
                .map(g -> g.getValue().stream().findFirst().orElse(null))
                .toList();

        return incorrectGroups;

    }
}