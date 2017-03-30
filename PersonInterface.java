import java.util.Date;

/**
 * Created by Noam on 2017-03-29.
 */
public interface PersonInterface
{
    public int getId();
    public String getFirstName();
    public String getLastName();
    public Date getBirthday();
    public int getHeight();
    // etc… there may be more such get property methods
}