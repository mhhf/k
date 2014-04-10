// Copyright (C) 2014 K Team. All Rights Reserved.
package org.kframework.utils.options;

import java.util.Comparator;
import java.util.Set;

import com.beust.jcommander.ParameterDescription;
import com.google.common.collect.Sets;

public class SortedParameterDescriptions implements Comparator<ParameterDescription> {

    private Set<Class<?>> experimentalClasses;
    
    public SortedParameterDescriptions(Class<?>... experimentalClasses) {
        this.experimentalClasses = Sets.newHashSet(experimentalClasses);
    }
    
    @Override
    public int compare(ParameterDescription first, ParameterDescription second) {
        boolean isFirstExperimental = experimentalClasses.contains(first.getObject().getClass());
        boolean isSecondExperimental = experimentalClasses.contains(second.getObject().getClass());
        
        if (isFirstExperimental && !isSecondExperimental) {
            return 1;
        } else if (!isFirstExperimental && isSecondExperimental) {
            return -1;
        } else {
            return first.getLongestName().compareTo(second.getLongestName());
        }
    }
}