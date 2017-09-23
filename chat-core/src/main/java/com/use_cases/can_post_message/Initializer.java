package com.use_cases.can_post_message;

/**
 * Created by mladen on 9/23/17.
 */

class Initializer {

    private static final Initializer ourInstance = new Initializer();
    static Initializer getInstance() {
        return ourInstance;
    }
    private Initializer() {
    }
}
