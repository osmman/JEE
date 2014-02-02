/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
    
    protected Class<T> typeXml;
    protected Class<W> typeEntity;

    public LinkAdapter(Class<T> type, Class<W> typeEntity) {
        this.typeXml = type;
        this.typeEntity = typeEntity;
    }

    @Override
    public W unmarshal(T v) throws Exception {
        W w = typeEntity.newInstance();
        w.setId(v.getId());
        return w;
    }

    @Override
    public T marshal(W v) throws Exception {
        T t = typeXml.newInstance();
        if(v.getId() != null) {
            t.setId(v.getId());
        }
        return t;
    }
    
}
