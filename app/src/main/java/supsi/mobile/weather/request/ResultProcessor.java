package supsi.mobile.weather.request;

public interface ResultProcessor<T> {

    void processResult(T t);

}
