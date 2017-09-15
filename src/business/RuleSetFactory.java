package business;

import java.awt.*;
import java.util.HashMap;

public class RuleSetFactory{
    private RuleSetFactory(){}
    static HashMap<Class<? extends Object>, RuleSet> map = new HashMap<>();

    static {
        map.put(LibraryMember.class, new LibraryMemberRuleSet());
        map.put(Book.class, new BookRuleSet());
    }
    public static RuleSet getRuleSet(Object c) {
        Class<? extends Object> cl = c.getClass();
        if(!map.containsKey(cl)) {
            throw new IllegalStateException("No RuleSet found for this Component");
        }
        return map.get(cl);
    }
}
