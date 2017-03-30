/**
 * Created by Noam on 2017-03-29.
 */
public interface CollectionInterface
{
    void add(Person person);
    Person remove() throws InterruptedException;
    void publish(String msg);
}
