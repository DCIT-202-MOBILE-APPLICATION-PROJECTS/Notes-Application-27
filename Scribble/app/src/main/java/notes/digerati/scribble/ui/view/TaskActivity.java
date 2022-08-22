package notes.digerati.scribble.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;
import notes.digerati.scribble.ui.adapter.RecyclerViewAdapter;
import notes.digerati.scribble.databinding.ActivityTaskBinding;

public class TaskActivity extends AppCompatActivity {

    ActivityTaskBinding binding;
    ArrayList<NoteModel> mNoteList;
    DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTaskBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        // initialise firebase database
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        RecyclerViewAdapter mRecyclerViewAdapter = new RecyclerViewAdapter(this, mNoteList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}