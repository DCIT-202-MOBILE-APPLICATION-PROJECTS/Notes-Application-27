package notes.digerati.scribble.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.Objects;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;
import notes.digerati.scribble.data.Utility;
import notes.digerati.scribble.ui.view.WorkSpaceActivity;

public class RecyclerViewAdapter extends FirestoreRecyclerAdapter<NoteModel, RecyclerViewHolder> {

    private Context context;
     private final ArrayList<NoteModel> mNoteList;

    public RecyclerViewAdapter(Context context,
                              ArrayList<NoteModel> mNoteList,
                               @Nullable FirestoreRecyclerOptions<NoteModel> options) {
        super(Objects.requireNonNull(options));
        this.context = context;
       this.mNoteList = mNoteList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new RecyclerViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull RecyclerViewHolder holder,
                                    int position, @NonNull NoteModel model) {
        holder.title.setText(model.getTitle());
        holder.mNote.setCardElevation(0.5F);
        holder.date.setText(Utility.timeStampToString(model.getTimestamp()));
        holder.itemView.setOnClickListener( view -> {
            Intent intent = new Intent(context, WorkSpaceActivity.class);
            intent.putExtra("title",model.getTitle());
            intent.putExtra("content",model.getContent());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return mNoteList.size();
    }


}

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    CardView mNote;
    TextView title, date;
    ImageButton colorPallet, icDelete;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mNote = itemView.findViewById(R.id.card_view);
        title = itemView.findViewById(R.id.tv_text_title);
        date = itemView.findViewById(R.id.tv_card_date);
        colorPallet = itemView.findViewById(R.id.ib_pallet);
        icDelete = itemView.findViewById(R.id.ib_delete);
    }
}
