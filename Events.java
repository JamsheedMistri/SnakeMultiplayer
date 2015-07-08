package snake;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Events implements ValueEventListener{
	
	//String name;
	long data;
	static boolean update = false;
	
	public Events() {
		//this.name = name;
		//this.data = data;
	}

	@Override
	public void onCancelled(FirebaseError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataChange(DataSnapshot arg0) {
		update = true;
		data = (Long) arg0.getValue();
		update = false;
	}

}
