package com.duyvu.viethundictionary.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

@Entity
public class Word {

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

    public static final int categoryPrivateInt = Word.Category.toInt(Word.Category.PRIVATE);

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "word")
    public String word;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "category")
    public Category category;

    public Word(String word, String description, Category category){
        this.word = word;
        this.description =  description;
        this.category = category;
    }

    public static Word[] populateData(){
        return new Word[] {
                new Word("Alma", "táo", Category.DEFAULT),
                new Word("Citrom", "chanh", Category.DEFAULT),
                new Word("Dinyer", "dưa", Category.DEFAULT),
                new Word("Eper", "dâu", Category.DEFAULT),
                new Word("Gyümölcs", "hoa quả", Category.DEFAULT)
        };

    }
}
