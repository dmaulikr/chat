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

public class MessagesCloudStore implements com.mk.chat.core.data_containers.MessagesCloudStore {

    Set<Consumer<MessageData>> newMessageSubscribers = new HashSet<>();
    Set<Consumer<MessageData>> messageEditedSubscribers = new HashSet<>();
    Set<Consumer<MessageData>> messageDeletedSubscribers = new HashSet<>();

    DataConverter dataConverter = new DataConverter();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("messages");

    public void subscribe_NewMessage(Consumer<MessageData> subscriber){
        newMessageSubscribers.add(subscriber);
    }

    public void subscribe_MessageEdited(Consumer<MessageData> subscriber){
        messageEditedSubscribers.add(subscriber);
    }

    public void subscribe_MessageDeleted(Consumer<MessageData> subscriber){
        messageDeletedSubscribers.add(subscriber);
    }

    public void persist(MessageData messageData, Runnable onComplete, Runnable onFailure){

        rootRef.push().setValue(dataConverter.messageToDb(messageData))
                .addOnCompleteListener(command -> {
                    if(onComplete != null)
                        onComplete.run();
                })
                .addOnFailureListener(command -> {
                    if(onFailure != null)
                        onFailure.run();
                });
    }

    public void getAllMessages(Consumer<MessageData> onComplete, Consumer<MessageData> onFailure){
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
