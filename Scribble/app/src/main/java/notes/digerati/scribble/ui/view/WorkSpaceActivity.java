package notes.digerati.scribble.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;
import notes.digerati.scribble.data.Utility;
import notes.digerati.scribble.databinding.ActivityWorkSpaceBinding;

public class WorkSpaceActivity extends AppCompatActivity {

    ActivityWorkSpaceBinding binding;
    EditText etTitle, etContent;
    ImageButton mImageButtonCurrentPaint;
    String title, content, docId;
    int color;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWorkSpaceBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


    //     LinearLayout linearLayoutColors = findViewById(R.id.ll_colors);
   //    mImageButtonCurrentPaint = (ImageButton) linearLayoutColors[1];

        binding.violet.setOnClickListener(this::paintClicked);
        binding.indigo.setOnClickListener(this::paintClicked);
        binding.green.setOnClickListener(this::paintClicked);
        binding.red.setOnClickListener(this::paintClicked);
        binding.amber.setOnClickListener(this::paintClicked);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        mImageButtonCurrentPaint = findViewById(R.id.ib_pallet);

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
        mImageButtonCurrentPaint.setImageResource(color);

        binding.btnSave.setOnClickListener(view -> saveNote());
    }


    private void saveNote(){
        String noteTitle = etTitle.getText().toString();
        String noteContent = etContent.getText().toString();
        int color = mImageButtonCurrentPaint.getSolidColor();


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

    private void saveNoteToFirebase(NoteModel note) {
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

    private void paintClicked(View view) {
        Toast.makeText(this, "paint clicked", Toast.LENGTH_SHORT).show();
        if (view != mImageButtonCurrentPaint) {
            ImageButton imageButton = (ImageButton) view;
            String colorTag = imageButton.getTag().toString();
            imageButton.setImageDrawable(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.pallet_pressed
                    )
            );

            mImageButtonCurrentPaint.setImageDrawable(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.pallet_normal
                    )
            );

            mImageButtonCurrentPaint = (ImageButton) view;
        }
    }
}