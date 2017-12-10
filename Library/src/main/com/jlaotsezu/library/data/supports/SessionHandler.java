package main.com.jlaotsezu.library.data.supports;

import com.sun.istack.internal.Nullable;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Dùng để loại bỏ những coilerplate code về begin tranction , end transaction
 */
public class SessionHandler {
    public static final int BATCH_SIZE = 20;
    private Session session;
    public SessionHandler(Session session){
        this.session = session;
    }
    public interface RunCUDInSession {
        boolean run(Session session);
    }
    public interface FetchSession<TYPE>{
        @Nullable
        TYPE run(Session session);
    }
    public <DATA> DATA runFetch(FetchSession<DATA> todo){
        Transaction transaction = session.beginTransaction();
        DATA data = todo.run(session);
        transaction.commit();
        //session.close();
        return data;
    }
    /**
     * Trả về xem transaction có thực hiện thành công hay không.
     * @param todo
     * @return
     */
    public boolean runCUD(RunCUDInSession todo){
        Transaction transaction = session.beginTransaction();
        boolean transactionOk;
        try {
            transactionOk = todo.run(session);
        }
        catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
            for(StackTraceElement message : e.getStackTrace()){
                System.out.println(message);
            }
            transactionOk = false;
        }

        if(transactionOk){
            transaction.commit();
        }
        else{
            transaction.rollback();
        }
        //session.close();
        return transactionOk;
    }
}
