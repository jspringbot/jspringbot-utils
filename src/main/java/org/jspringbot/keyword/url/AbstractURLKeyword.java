package org.jspringbot.keyword.url;

import org.jspringbot.Keyword;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractURLKeyword implements Keyword {

    @Autowired
    protected URLHelper helper;

    public abstract Object execute(Object[] params) throws Exception;
}
