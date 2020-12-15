package day6;

import java.util.List;

public class TravelerGroup {
    private final boolean[] anyoneAnsweredYes;
    private final boolean[] everyoneAnsweredYes;
    
    public TravelerGroup(final List<String> persons) {
        anyoneAnsweredYes = new boolean[26];
        everyoneAnsweredYes = new boolean[26];
        for (int i = 0; i < 26; ++i) {
            everyoneAnsweredYes[i] = true;
        }
        
        for(final String person : persons) {
            final boolean[] answers = new boolean[26];
            for (int i = 0; i < person.length(); ++i) {
                final int question = person.charAt(i) - 'a';
                answers[question] = true;
            }
            for (int i = 0; i < answers.length; ++i) {
                if (answers[i]) {
                    anyoneAnsweredYes[i] = true;
                } else {
                    everyoneAnsweredYes[i] = false;
                }
            }
        }
    }
    
    public int getAnyoneAnsweredYesCount() {
        int count = 0;
        
        for (int i = 0; i < anyoneAnsweredYes.length; ++i) {
            if (anyoneAnsweredYes[i]) {
                ++count;
            }
        }
        
        return count;
    }
    
    public int getEveryoneAnsweredYesCount() {
        int count = 0;
        
        for (int i = 0; i < everyoneAnsweredYes.length; ++i) {
            if (everyoneAnsweredYes[i]) {
                ++count;
            }
        }
        
        return count;
    }
}
