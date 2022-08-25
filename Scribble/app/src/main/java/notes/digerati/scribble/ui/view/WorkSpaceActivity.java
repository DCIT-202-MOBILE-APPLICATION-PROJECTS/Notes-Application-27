package notes.digerati.scribble.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;
import notes.digerati.scribble.data.Utility;
import notes.digerati.scribble.databinding.ActivityWorkSpaceBinding;

public class WorkSpaceActivity extends AppCompatActivity {

    ActivityWorkSpaceBinding binding;
    EditText etTitle, etContent;
    String title, content, docId;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWorkSpaceBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        // receive data
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if (docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        etTitle.setText(title);
        etContent.setText(content);

        binding.btnSave.setOnClickListener(view -> saveNote());
    }


    void saveNote(){
        String noteTitle = etTitle.getText().toString();
        String noteContent = etContent.getText().toString();


        if (noteTitle.isEmpty()) {
            etTitle.setError("Title cannot be empty");
        }

        NoteModel model = new NoteModel();
        model.setTitle(noteTitle);
        model.setContent(noteContent);
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