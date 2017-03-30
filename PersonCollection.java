import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Noam on 2017-03-29.
 */

/*
* In order to change the ordering algorithm,
* all that is needed is to create a PersonCollection object
* with the suitable comparator.
* For example, if someone wanted to add a salary,
* they would add the field to the person interface and class,
* and then construct the PersonCollection object with the
* following comparator:
* Comparator.comparingInt(p -> p.getSalary())
* For a non number field, you can send the getter method of the
* field you want to order by.
* */


public class PersonCollection extends Subject implements CollectionInterface
{
    private List<Person> ll;
    private Comparator<Person> comparator;

    PersonCollection(Comparator<Person> comparator)
    {
        super();
        ll = Collections.synchronizedList(new LinkedList<>());
        this.comparator = comparator;
    }

    public void add(Person person)
    {
        int index = 0;
        Iterator iterator = ll.iterator();
        while (iterator.hasNext()) {
            if (comparator.compare(person, (Person) iterator.next()) < 0)
                break;
            index++;
        }
        ll.add(index, person);
        publish("Added :" + person.toString());
    }

    public Person remove() throws InterruptedException
    {
        Person person = null;
        if (ll.size() > 0)
        {
            person = ll.remove(0);
            publish("Removed :" + person.toString());
        }
        return person;
    }

    public void publish(String msg)
    {
        this.setMessage(msg);
        notifyObservers();
    }
}
