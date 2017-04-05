package org.feup.lgp2d.helpwin.dao.repositories;

import org.feup.lgp2d.helpwin.customExceptions.IllegalArgumentExceptionMapper;

public abstract class Assert {

    public static void notNull(Object obj) {
        if (obj == null)
            throw new IllegalArgumentExceptionMapper("We couldn't find an entity with that data");
    }
}
