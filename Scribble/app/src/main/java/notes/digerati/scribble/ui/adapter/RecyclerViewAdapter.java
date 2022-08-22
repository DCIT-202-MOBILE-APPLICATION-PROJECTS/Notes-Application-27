package notes.digerati.scribble.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import notes.digerati.scribble.R;
import notes.digerati.scribble.data.NoteModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>  {

    private Context context;
    private ArrayList<NoteModel> mNoteList;

    public RecyclerViewAdapter(Context context, ArrayList<NoteModel> mNoteList) {
        this.context = context;
        this.mNoteList = mNoteList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_area, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        NoteModel model = mNoteList.get(position);
        holder.title.setText(model.getTitle());
        holder.date.setText(model.getTimestamp().toString());
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
