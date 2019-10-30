package com.duyvu.viethundictionary.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;

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

    public Word(String word, Type type, String description, Category category){
        if (word.length() > 0)
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        this.word = word;
        this.type = type;
        this.description =  description;
        this.category = category;
    }

    public static Word[] populateData(){
        return new Word[] {
                new Word("Alma", Type.NOUN, "táo", Category.DEFAULT),
                new Word("Citrom", Type.NOUN, "chanh", Category.DEFAULT),
                new Word("Dinyer", Type.NOUN, "dưa", Category.DEFAULT),
                new Word("Eper", Type.NOUN, "dâu", Category.DEFAULT),
                new Word("Gyümölcs", Type.NOUN, "hoa quả", Category.DEFAULT)
        };

    }
}
