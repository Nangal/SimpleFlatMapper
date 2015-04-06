package org.sfm.reflect.meta;

import org.sfm.map.MapperBuildingException;
import org.sfm.reflect.ConstructorDefinition;
import org.sfm.reflect.Parameter;

import java.util.*;

final class ObjectPropertyFinder<T> implements PropertyFinder<T> {
	
	private final List<ConstructorDefinition<T>> eligibleConstructorDefinitions;
	private final ObjectClassMeta<T> classMeta;
	private final Map<String, PropertyFinder<?>> subPropertyFinders = new HashMap<String, PropertyFinder<?>>();

    ObjectPropertyFinder(ObjectClassMeta<T> classMeta) throws MapperBuildingException {
		this.classMeta = classMeta;
		this.eligibleConstructorDefinitions = classMeta.getConstructorDefinitions() != null ? new ArrayList<ConstructorDefinition<T>>(classMeta.getConstructorDefinitions()) : null;
	}

	/* (non-Javadoc)
	 * @see org.sfm.reflect.PropertyFinder#findProperty(org.sfm.utils.PropertyNameMatcher)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> PropertyMeta<T, E> findProperty(final PropertyNameMatcher propertyNameMatcher) {
		// check for constructor
		PropertyMeta<T, ?> prop = lookForConstructor(propertyNameMatcher);

		if (prop == null) {
			prop = lookForProperty(propertyNameMatcher);
			
			if (prop == null) {
				prop = lookForSubPropertyInConstructors(propertyNameMatcher);
				
				if (prop == null) {
					prop = lookForSubProperty(propertyNameMatcher);
					
				} else {
					ConstructorPropertyMeta<T, ?> constructorProperty = (ConstructorPropertyMeta<T, ?>) ((SubPropertyMeta<T, ?>)prop).getOwnerProperty();
					removeNonMatching(constructorProperty.getParameter());
				}
			}
			
		} else {
			ConstructorPropertyMeta<T, ?> constructorProperty = (ConstructorPropertyMeta<T, ?>) prop;
			removeNonMatching(constructorProperty.getParameter());
		}

		return (PropertyMeta<T, E>) prop;
	}

    @SuppressWarnings("unchecked")
    @Override
    public <E> ConstructorPropertyMeta<T, E> findConstructor(ConstructorDefinition<T> constructorDefinition) {
        ConstructorPropertyMeta<T, ?> prop = lookForConstructor(constructorDefinition);

        if (prop != null) {
            removeNonMatching(prop.getParameter());
        }

        return (ConstructorPropertyMeta<T, E>) prop;
    }



    private ConstructorPropertyMeta<T, ?> lookForConstructor(final ConstructorDefinition<T> constructorDefinition) {
        if (classMeta.getConstructorProperties() != null) {
            Parameter parameter = constructorDefinition.getParameters()[0];
            for (ConstructorPropertyMeta<T, ?> prop : classMeta.getConstructorProperties()) {
                if (prop.getName().equals(parameter.getName())
                        && prop.getParameter().equals(parameter)) {
                    return prop;
                }
            }
        }

        return null;
    }

    private ConstructorPropertyMeta<T, ?> lookForConstructor(final PropertyNameMatcher propertyNameMatcher) {
		if (classMeta.getConstructorProperties() != null) {
			for (ConstructorPropertyMeta<T, ?> prop : classMeta.getConstructorProperties()) {
				if (propertyNameMatcher.matches(getColumnName(prop))
						&& hasConstructorMatching(prop.getParameter())) {
					return prop;
				}
			}
		}
		
		return null;
	}

	private PropertyMeta<T, ?> lookForProperty(final PropertyNameMatcher propertyNameMatcher) {
		for (PropertyMeta<T, ?> prop : classMeta.getProperties()) {
			if (propertyNameMatcher.matches(getColumnName(prop))) {
				return prop;
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PropertyMeta<T, ?> lookForSubPropertyInConstructors(final PropertyNameMatcher propertyNameMatcher) {
		if (classMeta.getConstructorProperties() != null) {
			for (ConstructorPropertyMeta<T, ?> prop : classMeta.getConstructorProperties()) {
				PropertyNameMatcher subPropMatcher = propertyNameMatcher.partialMatch(getColumnName(prop));
				if (subPropMatcher != null && hasConstructorMatching(prop.getParameter())) {
					PropertyMeta<?, ?> subProp = lookForSubProperty(subPropMatcher, prop);
					if (subProp != null) {
						return new SubPropertyMeta(classMeta.getReflectionService(), prop, subProp);
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PropertyMeta<T, ?> lookForSubProperty(final PropertyNameMatcher propertyNameMatcher) {
		for (PropertyMeta<T, ?> prop : classMeta.getProperties()) {
			PropertyNameMatcher subPropMatcher = propertyNameMatcher.partialMatch(getColumnName(prop));
			if (subPropMatcher != null) {
				PropertyMeta<?, ?> subProp =  lookForSubProperty(subPropMatcher, prop);
				if (subProp != null) {
					return new SubPropertyMeta(classMeta.getReflectionService(), prop, subProp);
				}
			}
		}

		return null;
	}
	
	private PropertyMeta<?, ?> lookForSubProperty(
			final PropertyNameMatcher propertyNameMatcher,
			final PropertyMeta<T, ?> prop) {
		PropertyFinder<?> subPropertyFinder = subPropertyFinders.get(getColumnName(prop));
		if (subPropertyFinder == null) {
			subPropertyFinder = prop.getClassMeta().newPropertyFinder();
			subPropertyFinders.put(prop.getName(), subPropertyFinder);
		}

		return subPropertyFinder.findProperty(propertyNameMatcher);
	}

    private String getColumnName(PropertyMeta<T, ?> prop) {
        return this.classMeta.getAlias(prop.getName());
    }


    private void removeNonMatching(Parameter param) {
		ListIterator<ConstructorDefinition<T>> li = eligibleConstructorDefinitions.listIterator();
		while(li.hasNext()){
			ConstructorDefinition<T> cd = li.next();
			if (!cd.hasParam(param)) {
				li.remove();
			}
		}
	}
	
	private boolean hasConstructorMatching(Parameter param) {
		for(ConstructorDefinition<T> cd : eligibleConstructorDefinitions) {
			if (cd.hasParam(param)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<ConstructorDefinition<T>> getEligibleConstructorDefinitions() {
		return eligibleConstructorDefinitions;
	}


}
