package com.duyvu.viethundictionary.models;

import android.content.Context;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.duyvu.viethundictionary.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Word implements Serializable {

    public enum Category {
        DEFAULT, PRIVATE;

        @TypeConverter
        public static Category getByOrdinal(int ordinal) {
            Category ret = null;
            for (Category cat : Category.values()) {
                if (cat.ordinal() == ordinal) {
                    ret = cat;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Category category) {
            return category.ordinal();
        }
    }

    public enum Type {
        NOUN, VERB, ADVERB, ADJECTIVE, DETERMINER, PRONOUN, CONJUNCTION, PREPOSITION;

        @TypeConverter
        public static Type getByOrdinal(int ordinal) {
            Type ret = null;
            for (Type cat : Type.values()) {
                if (cat.ordinal() == ordinal) {
                    ret = cat;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Type type) {
            return type.ordinal();
        }
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "word")
    public String word;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "category")
    public Category category;

    @ColumnInfo(name = "type")
    public Type type;

    public void setWord(String word) {
        this.word = word;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Word(String word, Type type, String description, Category category){
        if (word.length() > 0)
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        this.word = word;
        this.type = type;
        this.description =  description;
        this.category = category;
    }

    private static Word[] processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<Word> words = new ArrayList<>();
        int eventType = parser.getEventType();
        Word tempWord = new Word("", Type.NOUN, "", Category.DEFAULT);;
        Log.d("123321321", words.size()+ "");

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;


            if (eventType == XmlPullParser.START_TAG) {

                eltName = parser.getName();
                Log.d("RUNNING", eltName);

                if ("word".equals(eltName)) {
                    tempWord = new Word("", Type.NOUN, "", Category.DEFAULT);
                    words.add(tempWord);
                    Log.d("WordWord", words.size()+ "");
                } else {
                    if ("name".equals(eltName)) {
                        tempWord.setWord(parser.nextText());
                    } else if ("type".equals(eltName)) {
                        tempWord.setType(Type.valueOf(parser.nextText()));
                    } else if ("description".equals(eltName)) {
                        tempWord.setDescription(parser.nextText());
                    }
                }

            }

            eventType = parser.next();
        }
        Log.d("12332132133", words.size()+ "");

        return words.toArray(new Word[words.size()]);
    }

    public static Word[] populateData(Context context){
        /*
        return new Word[] {
                new Word("Alma", Type.NOUN, "táo", Category.DEFAULT),
                new Word("Citrom", Type.NOUN, "chanh", Category.DEFAULT),
                new Word("Dinyer", Type.NOUN, "dưa", Category.DEFAULT),
                new Word("Eper", Type.NOUN, "dâu", Category.DEFAULT),
                new Word("Gyümölcs", Type.NOUN, "hoa quả", Category.DEFAULT)
        };
        */

        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = context.getResources().openRawResource(R.raw.dictionary);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            Log.d("123123123", "123123123");


            return processParsing(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
