package com.example.zy.stry.lib;

/**
 * Created by wendy on 15-10-13.
 */

public interface Function<R, T> {
     T apply(R in);
}

//public interface Function<JSONObject, Void> {
//     Void apply(JSONObject json);
//}
