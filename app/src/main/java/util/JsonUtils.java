package util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;

import cn.cwkj.bluetooth.data.ByteEntity;


/**
 * Explain: json转换工具类
 * Author: hwk
 * Time: 2017/2/13 14:06
 */
public class JsonUtils {
    public static CharacterIterator it;
    public static char c;
    public static int col;

    public JsonUtils() {
    }

    /**
     * json转成ByteEntity集合。
     * json串中不包含宫缩数据   旧版
     *
     * @param json
     * @return
     */
    public static ArrayList<ByteEntity> generateByteEntityList1(String json) {
        ArrayList<ByteEntity> arrayList = new ArrayList<ByteEntity>();
        ByteEntity byteEntity = new ByteEntity();
        try {
            JSONArray jsonArray = new JSONArray(json);
            int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ByteEntity byteEntity2 = (ByteEntity) byteEntity.clone();
                int y = jsonObject.getInt("y");
                int t = jsonObject.getInt("t");
                int a = jsonObject.getInt("a");
                byteEntity2.setFhr1(y);
                byteEntity2.setTaidong(t);
                byteEntity2.setTaidong_auto(a);
                arrayList.add(byteEntity2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * json转成ByteEntity集合。
     * json串中包含宫缩数据
     *
     * @param json
     * @return
     */
    public static ArrayList<ByteEntity> generateByteEntityList2(String json) {
        ArrayList<ByteEntity> arrayList = new ArrayList<ByteEntity>();
        ByteEntity byteEntity = new ByteEntity();
        try {
            JSONArray jsonArray = new JSONArray(json);
            int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ByteEntity byteEntity2 = (ByteEntity) byteEntity.clone();
                int y = jsonObject.getInt("y");
                int t = jsonObject.getInt("t");
                int a = jsonObject.getInt("a");
                int o = jsonObject.getInt("o");
                byteEntity2.setFhr1(y);
                byteEntity2.setTaidong(t);
                byteEntity2.setTaidong_auto(a);
                byteEntity2.setToco(o);
                arrayList.add(byteEntity2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    /**
     * json转成ByteEntity集合。
     * json1中取y、t、a，json2中取o
     *
     * @param json1
     * @param json2
     * @return
     */
    public static ArrayList<ByteEntity> generateByteEntityList3(String json1, String json2) {
        ArrayList<ByteEntity> arrayList = new ArrayList<ByteEntity>();
        ByteEntity byteEntity = new ByteEntity();
        int size;
        try {
            JSONArray jsonArray1 = new JSONArray(json1);
            JSONArray jsonArray2 = new JSONArray(json2);
            int size1 = jsonArray1.length();
            int size2 = jsonArray2.length();
            if (size1 >= size2) {
                size = size2;
            } else {
                size = size1;
            }
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
                JSONObject jsonObject2 = (JSONObject) jsonArray2.get(i);
                int y = jsonObject1.getInt("y");
                int t = jsonObject1.getInt("t");
                int a = jsonObject1.getInt("a");
                int o = jsonObject2.getInt("o");
                ByteEntity byteEntity2 = (ByteEntity) byteEntity.clone();
                byteEntity2.setFhr1(y);
                byteEntity2.setTaidong(t);
                byteEntity2.setTaidong_auto(a);
                byteEntity2.setToco(o);
                arrayList.add(byteEntity2);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("size", arrayList.size() + "");
        return arrayList;
    }

    /**
     * json转成ByteEntity集合。
     * json1中取y、t、a，
     * o为空，直接赋值为10
     *
     * @param json1
     * @return
     */
    public static ArrayList<ByteEntity> generateByteEntityList4(String json1) {
        ArrayList<ByteEntity> arrayList = new ArrayList<ByteEntity>();
        ByteEntity byteEntity = new ByteEntity();
        try {
            JSONArray jsonArray1 = new JSONArray(json1);
            int size = jsonArray1.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
                int y = jsonObject1.getInt("y");
                int t = jsonObject1.getInt("t");
                int a = jsonObject1.getInt("a");
                ByteEntity byteEntity2 = (ByteEntity) byteEntity.clone();
                byteEntity2.setFhr1(y);
                byteEntity2.setTaidong(t);
                byteEntity2.setTaidong_auto(a);
                byteEntity2.setToco(0);
                arrayList.add(byteEntity2);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("size", arrayList.size() + "");
        return arrayList;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param time
     * @return
     */
    public static String times(long time) {
        SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return fm2.format(date);
    }

    /**
     * 验证一个字符串是否是合法的JSON串
     *
     * @param input 要验证的字符串
     * @return true-合法 ，false-非法
     */
    public boolean validate(String input) {
        input = input.trim();
        boolean ret = valid(input);
        return ret;
    }

    private boolean valid(String input) {

        if ("".equals(input)) return true;

        boolean ret = true;
        it = new StringCharacterIterator(input);
        c = it.first();
        col = 1;
        if (!value()) {
            ret = error("value", 1);
        } else {
            skipWhiteSpace();
            if (c != CharacterIterator.DONE) {
                ret = error("end", col);
            }
        }

        return ret;
    }

    private boolean value() {
        return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
    }

    private boolean literal(String text) {
        CharacterIterator ci = new StringCharacterIterator(text);
        char t = ci.first();
        if (c != t) {
            return false;
        }

        int start = col;
        boolean ret = true;
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
            if (t != nextCharacter()) {
                ret = false;
                break;
            }
        }
        nextCharacter();
        if (!ret) error("literal " + text, start);
        return ret;
    }

    private boolean array() {
        return aggregate('[', ']', false);
    }

    private boolean object() {
        return aggregate('{', '}', true);
    }

    private boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
        if (c != entryCharacter) return false;
        nextCharacter();
        skipWhiteSpace();
        if (c == exitCharacter) {
            nextCharacter();
            return true;
        }

        for (; ; ) {
            if (prefix) {
                int start = col;
                if (!string()) return error("string", start);
                skipWhiteSpace();
                if (c != ':') return error("colon", col);
                nextCharacter();
                skipWhiteSpace();
            }
            if (value()) {
                skipWhiteSpace();
                if (c == ',') {
                    nextCharacter();
                } else if (c == exitCharacter) {
                    break;
                } else {
                    return error("comma or " + exitCharacter, col);


                }
            } else {
                return error("value", col);
            }
            skipWhiteSpace();
        }

        nextCharacter();
        return true;
    }

    private boolean number() {
        if (!Character.isDigit(c) && c != '-') return false;
        int start = col;
        if (c == '-') nextCharacter();
        if (c == '0') {
            nextCharacter();
        } else if (Character.isDigit(c)) {
            while (Character.isDigit(c))
                nextCharacter();
        } else {
            return error("number", start);
        }
        if (c == '.') {
            nextCharacter();
            if (Character.isDigit(c)) {
                while (Character.isDigit(c))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        if (c == 'e' || c == 'E') {
            nextCharacter();
            if (c == '+' || c == '-') {
                nextCharacter();
            }
            if (Character.isDigit(c)) {
                while (Character.isDigit(c))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        return true;
    }

    private boolean string() {
        if (c != '"') return false;

        int start = col;
        boolean escaped = false;
        for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
            if (!escaped && c == '\\') {
                escaped = true;
            } else if (escaped) {
                if (!escape()) {
                    return false;
                }
                escaped = false;
            } else if (c == '"') {
                nextCharacter();
                return true;
            }
        }
        return error("quoted string", start);
    }

    private boolean escape() {
        int start = col - 1;
        if (" \\\"/bfnrtu".indexOf(c) < 0) {
            return error("escape sequence \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t or \\uxxxx ", start);
        }
        if (c == 'u') {
            if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())
                    || !ishex(nextCharacter())) {
                return error("unicode escape sequence \\uxxxx ", start);
            }
        }
        return true;
    }

    private boolean ishex(char d) {
        return "0123456789abcdefABCDEF".indexOf(c) >= 0;
    }

    private char nextCharacter() {
        c = it.next();
        ++col;
        return c;
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(c)) {
            nextCharacter();
        }
    }

    private boolean error(String type, int col) {
// System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
        return false;
    }
}
