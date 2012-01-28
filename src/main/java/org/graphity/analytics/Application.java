/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.graphity.analytics;

import java.util.HashSet;
import java.util.Set;
import org.graphity.util.ModelWriter;
import org.graphity.util.ResourcelXSLTWriter;

/**
 *
 * @author Pumba
 */
public class Application extends javax.ws.rs.core.Application
{
    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> s = new HashSet<Class<?>>();
	
        s.add(Resource.class);
        s.add(FrontPageResource.class);
	
	s.add(ModelWriter.class);
	s.add(ResourcelXSLTWriter.class);
	
        return s;
    }

    @Override
    public Set<Object> getSingletons()
    {
        Set<Object> s = new HashSet<Object>();

        //s.add(new FrontPageResource());

	return s;
    }
}