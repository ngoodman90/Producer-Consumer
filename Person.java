import java.util.Date;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Noam on 2017-03-29.
 */
public class Person implements PersonInterface
{
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static Random rand = new Random();

    private int id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private int height;

    public  Person()
    {
        this.id = atomicInteger.getAndIncrement();//atomic so all id's are distinct
        this.firstName = Character.toString((char)(rand.nextInt(26) + 'a'));
        this.lastName =  Character.toString((char)(rand.nextInt(26) + 'a'));
        this.birthday = new Date();
        this.height = ThreadLocalRandom.current().nextInt(1, Constants.MAX_HEIGHT);
    }

    public Person(int id, String firstName, String lastName, Date birthday, int height) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.height = height;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public String toString()
    {
        StringJoiner sj = new StringJoiner(":", "[", "]");
        sj.add(Integer.toString(id)).add(Integer.toString(height));
        return sj.toString();
    }

}
