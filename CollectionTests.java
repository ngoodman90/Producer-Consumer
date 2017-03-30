/**
 * Created by Noam on 2017-03-29.
 */
import org.junit.Test;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CollectionTests {

    @Test
    public void orderById() throws InterruptedException {
        PersonCollection personCollection = new PersonCollection(Comparator.comparingInt(p -> p.getId()));
        noConcurrencyTest(personCollection);
    }

    @Test
    public void orderByFirstName() throws InterruptedException {
        PersonCollection personCollection = new PersonCollection(Comparator.comparing(p -> p.getFirstName()));
        noConcurrencyTest(personCollection);
    }

    @Test
    public void orderByLastName() throws InterruptedException {
        PersonCollection personCollection = new PersonCollection(Comparator.comparing(p -> p.getLastName()));
        noConcurrencyTest(personCollection);
    }

    @Test
    public void orderByBirthday() throws InterruptedException {
        PersonCollection personCollection = new PersonCollection(Comparator.comparing(p -> p.getBirthday()));
        noConcurrencyTest(personCollection);
    }

    @Test
    public void orderByHeight() throws InterruptedException {
        PersonCollection personCollection = new PersonCollection(Comparator.comparingInt(p -> p.getHeight()));
        noConcurrencyTest(personCollection);
    }

    public void noConcurrencyTest(PersonCollection personCollection) throws InterruptedException {
        createObservers(personCollection);
        for (int i = 0; i < Constants.N; i++)
            personCollection.add(new Person());
        System.out.println();
        for (int i = 0; i < Constants.N; i++)
            personCollection.remove();
    }

    @Test
    public void concurrencyTest() throws InterruptedException {
        PersonCollection personCollection = new PersonCollection(Comparator.comparingInt(p -> p.getHeight()));
        concurrencyRun(personCollection);
    }

    @Test
    public void observerTest()
    {
        //create subject
        PersonCollection personCollection = new PersonCollection(Comparator.comparingInt(p -> p.getHeight()));

        createObservers(personCollection);

        //now send message to subject
        personCollection.publish("New Message");
    }

    @Test
    public void observerWithConcurrencyTest() throws InterruptedException {
        PersonCollection personCollection = new PersonCollection(Comparator.comparingInt(p -> p.getHeight()));

        createObservers(personCollection);

        concurrencyRun(personCollection);
    }

    public void concurrencyRun(PersonCollection personCollection) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Constants.NUM_OF_THREADS);
        for (int i = 0; i < Constants.NUM_OF_THREADS; i++)
        {
            executorService.execute(() ->
            {
                while (Math.random() < 0.9)
                {
                    if (Math.random() < 0.5)
                        personCollection.add(new Person());
                    else
                    {
                        try {
                            personCollection.remove();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        Thread.sleep(3000);
        shutdownExecutor(executorService);
    }

    private void shutdownExecutor(ExecutorService executorService)
    {
        try {
            System.out.println("attempt to shutdown executor");
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executorService.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    private void createObservers(PersonCollection personCollection)
    {
        Observer observer;
        for (int i = 0; i < Constants.NUM_OF_OBSERVERS; i++)
        {
            observer = new Subscriber("observer" + i);
            personCollection.register(observer);
            observer.setSubject(personCollection);
        }
    }
}
