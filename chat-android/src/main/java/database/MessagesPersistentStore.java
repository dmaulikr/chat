package database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mk.chat.core.data.MessageData;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by mladen on 9/23/17.
 */

public class MessagesPersistentStore<T> {

    Set<Consumer<MessageData<T>>> newMessageSubscribers = new HashSet<>();
    Set<Consumer<MessageData<T>>> messageEditedSubscribers = new HashSet<>();
    Set<Consumer<MessageData<T>>> messageDeletedSubscribers = new HashSet<>();

    int idForNext = 0;

    DataConverter dataConverter = new DataConverter();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("messages");

    public void subscribe_NewMessage(Consumer<MessageData<T>> subscriber){
        newMessageSubscribers.add(subscriber);
    }

    public void subscribe_MessageEdited(Consumer<MessageData<T>> subscriber){
        messageEditedSubscribers.add(subscriber);
    }

    public void subscribe_MessageDeleted(Consumer<MessageData<T>> subscriber){
        messageDeletedSubscribers.add(subscriber);
    }

    public void persist(MessageData<T> messageData, Runnable onComplete, Runnable onFailure){

        rootRef.push().setValue(dataConverter.messageToDb(messageData))
                .addOnCompleteListener(command -> {
                    if(onComplete != null)
                        onComplete.run();
                    messageData.setIsOnline(idForNext);
                    idForNext++;
                })
                .addOnFailureListener(command -> {
                    if(onFailure != null)
                        onFailure.run();
                });
    }

    public void getAllMessages(Consumer<MessageData<T>> onComplete, Consumer<MessageData<T>> onFailure){
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
