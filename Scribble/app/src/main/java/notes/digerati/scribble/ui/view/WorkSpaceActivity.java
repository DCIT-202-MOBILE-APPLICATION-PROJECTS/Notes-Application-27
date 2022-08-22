package notes.digerati.scribble.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;
import notes.digerati.scribble.databinding.ActivityWorkSpaceBinding;

public class WorkSpaceActivity extends AppCompatActivity {

    ActivityWorkSpaceBinding binding;
    EditText etTitle, etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWorkSpaceBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

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

    }
}