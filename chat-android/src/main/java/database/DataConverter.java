package database;

import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.mk.chat.core.data.MessageData;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by mladen on 9/24/17.
 */

public class DataConverter {

    public <T> Map<String, Object> messageToDb(MessageData<T> messageData){
        return new HashMap<String, Object>(){{
            put("content", messageData.getContent());
            put("user", messageData.getUser().getUsername());
            put("usersThatHaveSeenIt", messageData.getUsersThatHaveSeenIt());
        }};
    }

    public <T> MessageData<T> messageFromDb(DataSnapshot dataSnapshot){
        return null;
    }

    public <T> Collection<MessageData> messagesCollectionFromDb(DataSnapshot dataSnapshot){
        Collection<MessageData> ret = new HashSet<>();

        return ret;
    }
}
