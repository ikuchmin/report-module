package ru.osslabs.modules.report.functions;

/**
 * Created by ikuchmin on 06.11.15.
 */
public interface Consumer3<T, U, A> {
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param a the third input argument
     */
    void accept(T t, U u, A a);

}
