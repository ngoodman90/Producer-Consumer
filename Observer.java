/**
 * Created by Noam on 2017-03-30.
 */
public interface Observer {

    //method to update the observer, used by subject
    void update();

    //attach with subject to observe
    void setSubject(SubjectInterface sub);
}