import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noam on 2017-03-30.
 */
public class Subject implements SubjectInterface {

    private List<Observer> observers;
    private String message;
    private final Object mutex = new Object();

    Subject()
    {
        observers = new ArrayList<>();
    }

    @Override
    public void register(Observer obj)
    {
        if(obj == null) throw new NullPointerException("Null Observer");
        synchronized (mutex) {
            if(!observers.contains(obj)) observers.add(obj);
        }
    }

    @Override
    public void unregister(Observer obj)
    {
        synchronized (mutex) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer subscriber : observers)
            subscriber.update();
    }

    @Override
    public Object getUpdate(Observer obj) {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
