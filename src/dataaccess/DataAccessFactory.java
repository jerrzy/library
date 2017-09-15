package dataaccess;

public class DataAccessFactory{
    public static DataAccess getInstance(){
        return DataAccessFacadeHolder.INSTANCE;
    }

    static class DataAccessFacadeHolder{
        public final static DataAccess INSTANCE = new DataAccessFacade();
    }
}
