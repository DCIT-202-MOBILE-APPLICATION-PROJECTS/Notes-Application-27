package notes.digerati.scribble.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;
import notes.digerati.scribble.data.Utility;
import notes.digerati.scribble.ui.adapter.RecyclerViewAdapter;
import notes.digerati.scribble.databinding.ActivityTaskBinding;

public class TaskActivity extends AppCompatActivity {

    ActivityTaskBinding binding;
    ArrayList<NoteModel> mNoteList;
    // DatabaseReference mDatabaseRef;
    RecyclerView mRecyclerView = findViewById(R.id.recyclerView);;
    RecyclerViewAdapter mRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTaskBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        // initialise firebase database
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        setupRecyclerView();

    }


    private void setupRecyclerView() {
        Query query = Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<NoteModel> options = new FirestoreRecyclerOptions.Builder<NoteModel>()
                .setQuery(query, NoteModel.class).build();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerViewAdapter = new RecyclerViewAdapter( this, mNoteList, options);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRecyclerViewAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerViewAdapter.notifyDataSetChanged();
    }
}