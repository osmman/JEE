/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.EntityObject;
import cz.cvut.fel.jee.rest.entity.EntityXml;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author saljack
 * @param <T>
 * @param <W>
 */
public class LinkAdapter<T extends EntityXml, W extends EntityObject> extends XmlAdapter<T, W>{
    
    protected Class<T> type;

    public LinkAdapter(Class<T> type) {
        this.type = type;
    }

    @Override
    public W unmarshal(T v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T marshal(W v) throws Exception {
        T t = type.newInstance();
        t.setId(v.getId());
        return t;
    }
    
}
