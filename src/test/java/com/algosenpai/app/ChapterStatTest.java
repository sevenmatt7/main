package com.algosenpai.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.algosenpai.app.stats.ChapterStat;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ChapterStatTest {

    /**
     * Create a chapterStat, convert it to string then back, and compare the 2 objects to see if they are equal.
     */
    @Test
    public void stringParsingTest() throws Exception {
        ChapterStat chapterStat = new ChapterStat("Foo Bar",1,2,3,4,1,75.0,"Sample Comment");

        String storage = chapterStat.toString();

        ChapterStat copy = ChapterStat.parseString(storage);
        assertEquals(chapterStat, copy);
    }

    /**
     * Tests the parsing of the edge case of empty strings.
     */
    @Test
    public void stringParsing_emptyStrings() throws Exception {
        ChapterStat chapterStat = new ChapterStat("",0,0,0,0,0,0,"");

        String storage = chapterStat.toString();

        ChapterStat copy = ChapterStat.parseString(storage);
        assertEquals(chapterStat, copy);
    }

    /**
     * Performs stringParsingTest on 1000 randomly generated ChapterStats.
     */
    @Test
    public void stringParsing_randomTest() throws Exception {

        ChapterStat chapterStat;
        ChapterStat copy;

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {

            chapterStat = new ChapterStat(
                    RandomString.make(random.nextInt(100) + 1),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextDouble() * random.nextInt(),
                    RandomString.make(random.nextInt(100) + 1)
            );
            copy = ChapterStat.parseString(chapterStat.toString());
            assertEquals(chapterStat,copy);
        }



    }

    /**
     * Checks that dividing by 0 in the recalculateStats function does not cause a crash.
     */
    @Test
    public void recalculateStats_divideByZero() {
        ChapterStat stat = new ChapterStat("Test",0,0,0,0,0,0,"");
        stat.recalculateStats();
    }
}