package cz.cvut.fel.jee.message;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 28.1.14
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public interface IMessageCallback extends Serializable {

    public void call();

}
