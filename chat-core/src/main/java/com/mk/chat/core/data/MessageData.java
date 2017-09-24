package com.mk.chat.core.data;


import java.util.Collection;

/**
 * Created by mladen on 9/20/17.
 */

public class MessageData<T> {

	int id = -1;
	UserData user;
	T content;
	Collection<String> usersThatHaveSeenIt;

	public MessageData(UserData user, T content) {
		this.user = user;
		this.content = content;
	}

	public UserData getUser() {
		return user;
	}

	public int getId(){ return id; }

	public T getContent(){
		return content;
	}

	public boolean isOnline(){
		return id != -1;
	}

	public Collection<String> getUsersThatHaveSeenIt() { return usersThatHaveSeenIt; }


	public void setIsOnline(int id){
		this.id = id;
	}

	public void setSeenByUser(UserData user){
		usersThatHaveSeenIt.add(user.getUsername());
	}
}
