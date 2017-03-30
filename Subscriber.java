/**
 * Created by Noam on 2017-03-30.
 */
public class Subscriber implements Observer {

    /*
    * Use of this class can be seen in CollectionTests.createObservers method.
    * */

    private String name;
    private SubjectInterface topic;

    public Subscriber(String name){
        this.name = name;
    }

    @Override
    public void setSubject(SubjectInterface sub) {
        this.topic = sub;
    }

    @Override
    public void update() {
        String msg = (String) topic.getUpdate(this);
        if(msg == null){
            System.out.println(name + ":: No new message");
        }else
            System.out.println(name + ": " + msg);
    }
}