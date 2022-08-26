package notes.digerati.scribble.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;
import notes.digerati.scribble.data.Utility;
import notes.digerati.scribble.databinding.ActivityWorkSpaceBinding;

public class WorkSpaceActivity extends AppCompatActivity {

    ActivityWorkSpaceBinding binding;
    EditText etTitle, etContent;
    ImageButton imageButton;
    String title, content, docId;
    int color;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWorkSpaceBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        imageButton = findViewById(R.id.ib_pallet);

        // receive data
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");
        color = getIntent().getIntExtra("color", R.drawable.amber);

        if (docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        etTitle.setText(title);
        etContent.setText(content);
        imageButton.setImageResource(color);

        binding.btnSave.setOnClickListener(view -> saveNote());
    }


    void saveNote(){
        String noteTitle = etTitle.getText().toString();
        String noteContent = etContent.getText().toString();
        int color = imageButton.getSolidColor();


        if (noteTitle.isEmpty() && noteContent.isEmpty()) {
            etTitle.setError("Title cannot be empty");
            etContent.setError("Please type something");
        }

        NoteModel model = new NoteModel();
        model.setTitle(noteTitle);
        model.setContent(noteContent);
        model.setColor(color);
        model.setTimestamp(Timestamp.now());

        saveNoteToFirebase(model);

    }

    void saveNoteToFirebase(NoteModel note) {
        DocumentReference documentReference;
        if (isEditMode) {
            // update the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        } else {
            // create a new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }


        documentReference.set(note).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // note is added
                Utility.showToast(WorkSpaceActivity.this, "Note added successfully");
                finish();
            } else {
                Utility.showToast(WorkSpaceActivity.this, "Failed whilst adding note");
            }
        });
    }
}