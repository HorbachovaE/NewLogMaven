import java.util.ArrayList;
import java.util.Date;

public class LogLine {
    public Date Date;
    public String Id;
    public LogType Type;
    public Integer Status; // add enum with Success or None

    public String GetIdAndType(){
        String IdType = Id +Type;
        return IdType;
    }
public boolean getEquals ( LogType type, String id)
{
    return type == this.Type && id.equals(this.Id);
}
}
