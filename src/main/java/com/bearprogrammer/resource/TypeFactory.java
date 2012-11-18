package com.bearprogrammer.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load all available {@link Type}s.
 * 
 * @author Vinicius Isola (viniciusisola@gmail.com)
 */
public class TypeFactory {
	
	private static Logger logger = LoggerFactory.getLogger(TypeFactory.class);
	private static Type [] types = null;
	
	/**
	 * Search for a specified <code>ResourceType</code> by its
	 * {@link Type#getSupportedTypes() identification}.
	 * 
	 * @param typeIdentifier
	 *            The identifier to look for.
	 * @return A resource type if found, null otherwise.
	 */
	public static Type getType(String typeIdentifier) {
		for (Type type : getTypes()) {
			for (String typeId : type.getSupportedTypes()) {
				if (typeId.equals(typeIdentifier)) {
					return type;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Return all {@link Type}s available in the class path.
	 * 
	 * @return Return all types loaded by a <code>java.util.ServiceLoader</code>.
	 */
	public synchronized static Type [] getTypes () {
		if (types == null) {
			List<Type> typeList = new ArrayList<Type>();
			
			ServiceLoader<Type> loader = ServiceLoader.load(Type.class);
			for (Type type : loader) {
				logger.debug("Resource type found: " + type.getClass().getName());
				typeList.add(type);
			}
			types = typeList.toArray(new Type[typeList.size()]);
		}
		return types;
	}

}