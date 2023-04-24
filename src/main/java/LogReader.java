import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReader {
    public ArrayList<LogLine> ReadLogs() {
        ArrayList<LogLine> logLines = new ArrayList<>();
        ArrayList<String> fileLogLines = this.ReadLineFromLogFile();

        for (String line : fileLogLines) {
            String[] tokens = line.split("\\s{2,}");

            String dateToken = tokens[0];
            String idToken = "";
            String bodyToken = tokens[3];
            String statusCode = null;
            LogType type = LogType.Send;

            if (bodyToken.contains("Http Code")) {
                statusCode = bodyToken.split("\\s+")[4];
                statusCode = statusCode.substring(0, statusCode.length() - 1);
                type = LogType.Response;
            }

            Pattern pattern = Pattern.compile("\"id\":\"(\\d+)\"");
            Matcher matcher = pattern.matcher(bodyToken);

            if (matcher.find()) {
                idToken = matcher.group(1);
            }

            var logLine = new LogLine();
            try {
                logLine.Date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(dateToken);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            logLine.Id = idToken;
            logLine.Type = type;
            logLine.Status = statusCode == null ? null : Integer.parseInt(statusCode);

            logLines.add(logLine);
        }

        return logLines;
    }

    private ArrayList<String> ReadLineFromLogFile() {
        ArrayList<String> logLines = new ArrayList<String>();

        try {

            FileReader fileReader = new FileReader("data.log");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                logLines.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logLines;
    }
}

